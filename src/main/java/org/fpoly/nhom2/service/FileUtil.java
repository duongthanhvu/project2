package org.fpoly.nhom2.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Xử lý việc ghi multipartfile vào bộ nhớ và truy xuất file đã lưu
 * 
 * @author Vu Duong
 */

@Service
public class FileUtil {

    public static final int LOGO = 0;
    public static final int PHOTO = 1;
    public static final int PDF = 2;
    @Autowired
    private Environment env;
    @Autowired
    private PhotoResizer photoResizer;

    /**
     * Lưu multipart file vào bộ nhớ và trả về tên file sau khi lưu
     * 
     * @param multipartFile multipart file cần lưu
     * @param type loại file cần lưu
     * @return tên của file đã lưu trên bộ nhớ, nếu bị lỗi không thể lưu thì trả về
     *         <code>null</code>
     */

    public String saveFile(MultipartFile multipartFile, int type) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
        try {
            multipartFile.transferTo(new File(env.getProperty("vitriluufile"), fileName));
            if (type == LOGO) {
                photoResizer.resizeLogo(new File(env.getProperty("vitriluufile"), fileName));
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return fileName;
    }

    public InputStream openInputStream(String fileName) throws IOException {
        File file = new File(env.getProperty("vitriluufile") + "/" + fileName);
        InputStream inputStream = FileUtils.openInputStream(file);
        return inputStream;
    }
}