package com.sid.app.validation;

import com.sid.app.exception.ServiceErrorFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Siddhant Patni
 */
@Getter
@AllArgsConstructor
public enum AppServiceErrors implements ServiceErrorFactory {

    MAILING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1600, "Failed to send the email"),
    ATTACHMENT_LIMIT_ERROR(HttpStatus.BAD_REQUEST, 1601, "Attachment limit exceeds");

    private final HttpStatus httpStatus;

    private final int errorCode;

    private final String defaultDesc;

}