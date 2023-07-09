package com.sid.app.exception;

import com.sid.app.model.CustomExceptions;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Siddhant Patni
 */
@XSlf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public final @ResponseBody CustomExceptions handleServiceException(ServiceException serviceException) {

        if (log.isErrorEnabled()) {
            log.error("handleServerWebInputException() : errorDesc -> {}", serviceException.getReason());
        }

        return CustomExceptions
                .builder()
                .status(serviceException.getStatus().toString())
                .statusCode(serviceException.getRawStatusCode())
                .statusDesc(serviceException.getReason())
                .build();

    }
}
