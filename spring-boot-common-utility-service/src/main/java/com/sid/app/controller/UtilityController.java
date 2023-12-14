package com.sid.app.controller;

import com.sid.app.constants.ApplicationConstants;
import com.sid.app.service.UtilityService;
import com.sid.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class UtilityController {

    @Autowired
    private UtilityService utilityService;

    @PostMapping(value = ApplicationConstants.JOLT_TRANSFORMATION_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> transform(@RequestBody Object request) {

        if (log.isInfoEnabled()) {
            log.info("transform() : ===== >>> JOLT Transform - START <<< =====");
        }

        return utilityService.transform(request)
                .flatMap(response -> {
                    if (log.isInfoEnabled()) {
                        log.info("transform() : request -> {}, response -> {}", ApplicationUtils.getJSONString(request), response);
                        log.info("transform() : ===== >>> JOLT Transform - END <<< =====");
                    }

                    return Mono.just(response);
                });

    }

}