package com.sid.app.controller;

import com.sid.app.config.AppProperties;
import com.sid.app.constant.AppConstants;
import com.sid.app.entity.EmailTransaction;
import com.sid.app.model.EmailRequest;
import com.sid.app.service.EmailService;
import com.sid.app.validation.AppServiceErrors;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Siddhant Patni
 */
@RestController
@XSlf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppProperties properties;

    @PostMapping(value = AppConstants.EMAIL_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EmailTransaction> sendEmail(@Valid @RequestBody EmailRequest emailRequestDTO,
                                            @RequestPart(value = "attachment", required = false) Flux<FilePart> attachment) {
        if (attachment == null) {
            return emailService.sendEmail(emailRequestDTO, null);
        } else {
            return attachment
                    .collectList()
                    .flatMap(files -> {
                        if (files.size() > properties.getMaxFilesAllowed()) {
                            return Mono.error(AppServiceErrors.ATTACHMENT_LIMIT_ERROR.newEx());
                        }
                        return emailService.sendEmail(emailRequestDTO, files);
                    });
        }
    }
}