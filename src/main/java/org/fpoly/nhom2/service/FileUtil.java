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
 * @author Vu Duong
 */

@Service
public class FileUtil {

    @Autowired
    private Environment env;

    /**
     * Lưu multipart file vào bộ nhớ và trả về tên file sau khi lưu
     * @param multipartFile multipart file cần lưu
     * @return tên của file đã lưu trên bộ nhớ, nếu bị lỗi không thể lưu thì trả về <code>null</code>
     */

    public String saveFile(MultipartFile multipartFile){
        if(multipartFile == null || multipartFile.isEmpty()){
            return null;
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
        try {
            multipartFile.transferTo(new File(env.getProperty("vitriluufile"), fileName));
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