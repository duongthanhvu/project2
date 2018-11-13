package org.fpoly.nhom2.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUtil
 */
@Service
public interface FileUtil {

    public static final int LOGO = 0;
    public static final int PHOTO = 1;
    public static final int PDF = 2;
    
    public String saveFile(MultipartFile multipartFile, int type);

    public InputStream openInputStream(String fileName) throws IOException;
}