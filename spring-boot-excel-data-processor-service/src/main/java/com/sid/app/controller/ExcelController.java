package com.sid.app.controller;

import com.sid.app.constants.AppConstants;
import com.sid.app.model.Response;
import com.sid.app.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Siddhant Patni
 */
@RestController
@Slf4j
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping(value = AppConstants.UPLOAD_EXCEL_FILE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (log.isInfoEnabled()) {
            log.info("uploadExcelFile() : Upload Excel file - START");
        }
        Response response = new Response();
        // Check if the file is empty
        if (file.isEmpty()) {
            log.error("Error processing Excel file: Uploaded file is empty");
            response.setErrorMessage("Error processing Excel file: Uploaded file is empty");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        if (!ExcelService.isExcelFile(file.getContentType())) {
            log.error("Error uploading Excel file: Uploaded file is not an Excel file");
            response.setStatus("Error uploading Excel file: Uploaded file is not an Excel file");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            excelService.processExcelFile(file);
            response.setStatus("File uploaded and processed successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error uploading and processing Excel file: {}", e.getMessage());
            response.setStatus("Error uploading and processing Excel file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } finally {
            if (log.isInfoEnabled()) {
                log.info("uploadExcelFile() : Upload Excel file - END");
            }
        }
    }


    @GetMapping(value = AppConstants.DOWNLOAD_EXCEL_FILE_ENDPOINT)
    public ResponseEntity<byte[]> downloadExcelFile(@RequestParam("tableName") String tableName) {
        try {
            byte[] fileBytes = excelService.generateExcelFile(tableName);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=data.xlsx")
                    .body(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
