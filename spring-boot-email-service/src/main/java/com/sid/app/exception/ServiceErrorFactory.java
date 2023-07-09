package com.sid.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Siddhant Patni
 */
public interface ServiceErrorFactory {

    HttpStatus getHttpStatus();

    /**
     * Internal error code.
     */
    int getErrorCode();

    /**
     * Default error description.
     *
     * @return
     */
    String getDefaultDesc();

    /**
     * Throw new ServiceException.
     */
    default ServiceException newEx() {
        return new ServiceException(getHttpStatus(), getErrorCode(), getDefaultDesc());
    }

    /**
     * Throw new ServiceException.
     */
    default ServiceException newEx(Throwable throwable) {
        return new ServiceException(getHttpStatus(), getErrorCode(), getDefaultDesc(), throwable);
    }

    /**
     * Throw new ServiceException by accepting custom message from the caller.
     */
    default ServiceException newEx(String customErrMessage, Throwable throwable) {
        return new ServiceException(getHttpStatus(), getErrorCode(), customErrMessage, throwable);
    }

}