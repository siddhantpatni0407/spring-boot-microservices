package com.sid.springboot.chatgpt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Siddhant Patni
 */
public class ServiceException extends ResponseStatusException {

    private static final long serialVersionUID = -2359863924124256L;

    private final Integer errCode;

    public ServiceException(HttpStatus status, int errorCode, String reason) {
        super(status, reason);
        this.errCode = errorCode;
    }

    public ServiceException(HttpStatus status, int errorCode, String reason, Throwable cause) {
        super(status, reason, cause);
        this.errCode = errorCode;
    }

}