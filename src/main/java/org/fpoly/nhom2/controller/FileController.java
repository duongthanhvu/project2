package org.fpoly.nhom2.controller;

import org.fpoly.nhom2.service.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * FileController
 */
@Controller
public class FileController {

    @Autowired
    private FileUtil fileUtil;

    /* @RequestMapping(value = "/file/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("fileName") String fileName) {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.IMAGE_JPEG);
        try {
            InputStreamResource inputStreamResource = new InputStreamResource(fileUtil.openInputStream(fileName));
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/file/cv/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getCV(@PathVariable("fileName") String fileName) {
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.APPLICATION_PDF);
        try {
            InputStreamResource inputStreamResource = new InputStreamResource(fileUtil.openInputStream(fileName));
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } */
}