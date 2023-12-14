package com.sid.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CustomException extends ResponseStatusException {

    private static final long serialVersionUID = -23598639273124256L;

    private final Integer errCode;

    public CustomException(HttpStatus status, int errorCode, String reason) {
        super(status, reason);
        this.errCode = errorCode;
    }

    public CustomException(HttpStatus status, int errorCode, String reason, Throwable cause) {
        super(status, reason, cause);
        this.errCode = errorCode;
    }
    
}