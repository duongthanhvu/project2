/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fpoly.nhom2.controller;

//[END gcs_imports]
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A simple servlet that proxies reads and writes to its Google Cloud Storage
 * bucket.
 */
@Controller
public class GoogleCloudStorage {

    public static final boolean SERVE_USING_BLOBSTORE_API = false;

    /**
     * This is where backoff parameters are configured. Here it is aggressively
     * retrying with backoff, up to 10 times but taking no more that 15 seconds
     * total to do so.
     */
    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());

    /**
     * Used below to determine the size of chucks to read in. Should be > 1kb and <
     * 10MB
     */
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    private static final String BUCKET_NAME = "project2-fpoly.appspot.com";

    /**
     * Retrieves a file from GCS and returns it in the http response. If the request
     * path is /gcs/Foo/Bar this will be interpreted as a request to read the GCS
     * file named Bar in the bucket Foo.
     */
    // [START doGet]
    @GetMapping({ "/file/{filename}", "/file/cv/{filename}" })
    public void getImage(@PathVariable("filename") String filename, HttpServletResponse resp) throws IOException {

        resp.setContentType(getMIME(filename));
        GcsFilename fileName = new GcsFilename(BUCKET_NAME, filename);
        if (SERVE_USING_BLOBSTORE_API) {
            BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
            BlobKey blobKey = blobstoreService
                    .createGsBlobKey("/gs/" + fileName.getBucketName() + "/" + fileName.getObjectName());
            blobstoreService.serve(blobKey, resp);
        } else {
            GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
            copy(Channels.newInputStream(readChannel), resp.getOutputStream());
        }
    }

    private String getMIME(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (extension.equals("jpg")) {
            return "image/jpeg";
        } else if (extension.equals("pdf")) {
            return "application/pdf";
        } else if (extension.equals("png")) {
            return "image/png";
        } else {
            return "application/octet-stream";
        }
    }
    // [END doGet]

    /**
     * Writes the payload of the incoming post as the contents of a file to GCS. If
     * the request path is /gcs/Foo/Bar this will be interpreted as a request to
     * create a GCS file named Bar in bucket Foo.
     */
    // [START doPost]
    // @PostMapping("/gcs")
    // public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    //     GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
    //     GcsFilename fileName = new GcsFilename(BUCKET_NAME, null);
    //     GcsOutputChannel outputChannel;
    //     outputChannel = gcsService.createOrReplace(fileName, instance);
    //     copy(req.getInputStream(), Channels.newOutputStream(outputChannel));
    // }
    // [END doPost]

    // private GcsFilename getFileName(HttpServletRequest req) {
    // String[] splits = req.getRequestURI().split("/", 4);
    // if (!splits[0].equals("") || !splits[1].equals("gcs")) {
    // throw new IllegalArgumentException(
    // "The URL is not formed as expected. " + "Expecting /gcs/<bucket>/<object>");
    // }
    // return new GcsFilename(splits[2], splits[3]);
    // }

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