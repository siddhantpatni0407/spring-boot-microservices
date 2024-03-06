package com.sid.spring.batch.app.controller;

import com.sid.spring.batch.app.constant.AppConstants;
import com.sid.spring.batch.app.entity.Customer;
import com.sid.spring.batch.app.service.DataGeneratorService;
import com.sid.spring.batch.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @PostMapping(value = AppConstants.BATCH_PROCESSING_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String importCSVFile(@RequestParam("file") MultipartFile multipartFile) {
        if (log.isInfoEnabled()) {
            log.info("importCSVFile() : import CSV file to DB process - START");
        }
        try {
            String originalFileName = multipartFile.getOriginalFilename();
            File fileToImport = new File(AppConstants.FILE_STORAGE_DIRECTORY + originalFileName);
            File directory = new File(AppConstants.FILE_STORAGE_DIRECTORY);
            directory.mkdirs();
            multipartFile.transferTo(fileToImport);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fullPathFileName", AppConstants.FILE_STORAGE_DIRECTORY + originalFileName)
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            boolean fileDeleted = Files.deleteIfExists(Paths.get(AppConstants.FILE_STORAGE_DIRECTORY + originalFileName));
            boolean directoryDeleted = Files.deleteIfExists(Paths.get(AppConstants.FILE_STORAGE_DIRECTORY));
            if (fileDeleted) {
                if (log.isInfoEnabled()) {
                    log.info("importCSVFile() : file has been deleted from the temporary directory");
                }
            }
            if (directoryDeleted) {
                if (log.isInfoEnabled()) {
                    log.info("importCSVFile() : directory has been deleted");
                }
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            if (log.isErrorEnabled()) {
                log.error("importCSVFile() : Exception occurred with message -> {[]} and exception -> {}", e.getMessage(), e);
            }
            //return Mono.error(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data has not imported to db"));
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("importCSVFile() : Exception occurred with message -> {[]} and exception -> {}", e.getMessage(), e);
            }
            // return Mono.error(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to import the file"));
        } finally {
            if (log.isInfoEnabled()) {
                log.info("importCSVFile() : import CSV file to DB process - END");
            }
        }
        //return Mono.just("Data has been imported to db");
        return "Data has been imported from CSV and pushed to database";
    }

    @GetMapping(value = AppConstants.BATCH_PROCESSING_GET_DATA_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllData() {
        if (log.isInfoEnabled()) {
            log.info("getAllData() : Get All Data - START");
        }
        List<Customer> customerList = dataGeneratorService.getAllData();
        if (log.isInfoEnabled()) {
            log.info("getAllData() : allData -> {}", ApplicationUtils.getJSONString(customerList));
            log.info("getAllData() : Get All Data - END");
        }
        return customerList;
    }

}