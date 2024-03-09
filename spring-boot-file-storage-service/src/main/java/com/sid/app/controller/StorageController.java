package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Siddhant Patni
 */

@RestController
@Slf4j
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping(value = AppConstants.FILE_UPLOAD_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("uploadImage() : Upload Image - START");
        }
        String uploadImage = String.valueOf(storageService.uploadImage(file));
        if (log.isInfoEnabled()) {
            log.info("uploadImage() : uploadImage -> {}", uploadImage);
            log.info("uploadImage() : Upload Image - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping(value = AppConstants.FILE_DOWNLOAD_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> downloadImage(@RequestParam(value = "fileName") String fileName) {
        if (log.isInfoEnabled()) {
            log.info("downloadImage() : Download Image - START");
        }
        byte[] imageData = storageService.downloadImage(fileName);
        if (log.isInfoEnabled()) {
            log.info("downloadImage() : imageData -> {}", imageData);
            log.info("downloadImage() : Download Image - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageData);

    }

}