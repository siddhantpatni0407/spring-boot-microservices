package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.entity.FileDetails;
import com.sid.app.model.FileData;
import com.sid.app.model.Response;
import com.sid.app.service.StorageService;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Siddhant Patni
 */

@RestController
@Slf4j
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping(value = AppConstants.FILE_UPLOAD_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> uploadFile(@RequestParam("image") MultipartFile file) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("uploadImage() : Upload File Details - START");
        }
        Response response = storageService.uploadImage(file);

        if (log.isInfoEnabled()) {
            log.info("uploadImage() : response -> {}", ApplicationUtils.getJSONString(response));
            log.info("uploadImage() : Upload File Details - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping(value = AppConstants.FILE_DOWNLOAD_ENDPOINT)
    public ResponseEntity<?> downloadFile(@RequestParam(value = "fileName") String fileName) {
        if (log.isInfoEnabled()) {
            log.info("downloadImage() : Download FileDetails - START");
        }
        FileDetails fileDetails = storageService.downloadImage(fileName);

        if (log.isInfoEnabled()) {
            log.info("downloadImage() : imageData -> {}", ApplicationUtils.getJSONString(fileDetails));
            log.info("downloadImage() : Download FileDetails - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(fileDetails.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + fileDetails.getName() + "\"")
                .body(new ByteArrayResource(fileDetails.getFileData()));

    }

    @GetMapping(value = AppConstants.ALL_FILES_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileData>> getAllFileDetails() {
        if (log.isInfoEnabled()) {
            log.info("getAllFileDetails() : Get all file details API - START");
        }
        List<FileData> fileData = storageService.getAllFileDetails();

        if (log.isInfoEnabled()) {
            log.info("getAllFileDetails() : fileData -> {}", ApplicationUtils.getJSONString(fileData));
            log.info("getAllFileDetails() : Get all file details API - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileData);

    }

    @DeleteMapping(value = AppConstants.FILE_DELETE_ENDPOINT + "/" + "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> deleteFile(@PathVariable Long id) {
        if (log.isInfoEnabled()) {
            log.info("deleteEmployee() : Delete File - START");
        }
        Response response = storageService.deleteFile(id);

        if (log.isInfoEnabled()) {
            log.info("deleteEmployee() : response -> {}", ApplicationUtils.getJSONString(response));
            log.info("deleteEmployee() : Delete File - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

}