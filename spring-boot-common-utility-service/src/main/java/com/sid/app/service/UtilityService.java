package com.sid.app.service;

import com.bazaarvoice.jolt.JsonUtils;
import com.sid.app.config.ApplicationProperties;
import com.sid.app.constants.ApplicationConstants;
import com.sid.app.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class UtilityService {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private TransformerService transformerService;

    public Mono<String> transform(Object request) {

        return transformerService.transform(request)
                .flatMap(response -> {
                    generateInputOutputFiles(request, response);
                    return Mono.just(response);
                });
    }

    private void generateInputOutputFiles(Object request, String response) {

        try {
            String req = JsonUtils.toJsonString(request);
            Path inputFilePath = Paths.get(properties.getJoltInputFile());
            Files.write(inputFilePath, req.getBytes());
            if (log.isInfoEnabled()) {
                log.info("generateInputOutputFiles() : Input file has been generated at path -> {}", properties.getJoltInputFile());
            }
            Path outputFilePath = Paths.get(properties.getJoltOutputFile());
            Files.write(outputFilePath, response.getBytes());
            if (log.isInfoEnabled()) {
                log.info("generateInputOutputFiles() : Output file has been generated at path -> {}", properties.getJoltOutputFile());
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("generateInputOutputFiles() : exception -> {}, message -> {}", e, e.getMessage());
            }
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), ApplicationConstants.FILE_WRITE_ERROR.concat(" : ").concat(e.getMessage()));
        }
    }

}