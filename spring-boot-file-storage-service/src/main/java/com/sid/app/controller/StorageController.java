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

import java.util.List;

/**
 * @author Siddhant Patni
 * Controller class for handling file storage operations.
 */
@RestController
@Slf4j
@CrossOrigin
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * Endpoint for uploading a file.
     *
     * @param file The file to be uploaded.
     * @return ResponseEntity containing the response status and message.
     * @throws Exception If an error occurs during file upload.
     */
    @PostMapping(value = AppConstants.FILE_UPLOAD_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("uploadFile() : Upload File Details - START");
        }
        Response response = storageService.uploadFile(file);

        if (log.isInfoEnabled()) {
            log.info("uploadFile() : response -> {}", ApplicationUtils.getJSONString(response));
            log.info("uploadFile() : Upload File Details - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    /**
     * Endpoint for downloading a file.
     *
     * @param id The ID of the file to be downloaded.
     * @return ResponseEntity containing the file data.
     */
    @GetMapping(value = AppConstants.FILE_DOWNLOAD_ENDPOINT)
    public ResponseEntity<?> downloadFile(@RequestParam(value = "id") long id) {
        if (log.isInfoEnabled()) {
            log.info("downloadFile() : Download FileDetails - START");
        }
        FileDetails fileDetails = storageService.downloadFile(id);

        if (log.isInfoEnabled()) {
            log.info("downloadFile() : fileDetails -> {}", ApplicationUtils.getJSONString(fileDetails));
            log.info("downloadFile() : Download FileDetails - END");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(fileDetails.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDetails.getName() + "\"")
                .body(new ByteArrayResource(fileDetails.getFileData()));

    }

    /**
     * Endpoint for retrieving details of all files.
     *
     * @return ResponseEntity containing a list of file details.
     */
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

    /**
     * Endpoint for deleting a file.
     *
     * @param id The ID of the file to be deleted.
     * @return ResponseEntity containing the response status and message.
     */
    @DeleteMapping(value = AppConstants.FILE_DELETE_ENDPOINT + "/" + "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Response> deleteFile(@PathVariable Long id) {
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