package org.fpoly.nhom2.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.UUID;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUtilGCSImpl
 */
@Service
public class FileUtilGCSImpl implements FileUtil{

    private static final int BUFFER_SIZE = 2 * 1024 * 1024;
    private static final String BUCKET_NAME = "project2-fpoly.appspot.com";

    @Autowired
    private PhotoResizer photoResizer;

    /**
     * This is where backoff parameters are configured. Here it is aggressively
     * retrying with backoff, up to 10 times but taking no more that 15 seconds
     * total to do so.
     */
    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());

    @Override
    public String saveFile(MultipartFile multipartFile, int type) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString() + "." + extension;
        try {
            uploadFile(multipartFile.getInputStream(), fileName);
            if (type == LOGO) {
                uploadFile(photoResizer.resizeLogo(multipartFile.getInputStream(), extension), fileName);
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return fileName;
    }

    private void uploadFile(InputStream input, String filename) throws IOException{
        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
        GcsFilename fileName = new GcsFilename(BUCKET_NAME, filename);
        GcsOutputChannel outputChannel;
        outputChannel = gcsService.createOrReplace(fileName, instance);
        copy(input, Channels.newOutputStream(outputChannel));
    }

    @Override
    public InputStream openInputStream(String fileName) throws IOException {
        return null;
    }

    /**
     * Transfer the data from the inputStream to the outputStream. Then close both
     * streams.
     */
    private void copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}