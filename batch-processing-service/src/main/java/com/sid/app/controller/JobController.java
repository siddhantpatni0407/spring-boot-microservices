package com.sid.app.controller;

import com.sid.app.constant.AppConstants;
import com.sid.app.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping(value = AppConstants.BATCH_PROCESSING_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> importCsvToDBJob() {
        if (log.isInfoEnabled()) {
            log.info("importCsvToDBJob() : import CSV to DB process - START");
        }
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            if (log.isErrorEnabled()) {
                log.error("Exception occurred with message -> {[]} and exception -> {}", e.getMessage(), e);
            }
            return Mono.error(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data has not imported to db"));
        } finally {
            if (log.isInfoEnabled()) {
                log.info("importCsvToDBJob() : import CSV to DB process - END");
            }
        }
        return Mono.just("Data has been imported to db");
    }

}