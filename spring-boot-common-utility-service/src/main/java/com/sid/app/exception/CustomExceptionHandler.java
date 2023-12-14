package com.sid.app.exception;

import com.sid.app.constants.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is Exception Handler class for Software Entitlement.
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = ApplicationConstants.BAD_REQUEST)
    @ExceptionHandler(value = CustomException.class)
    public final @ResponseBody
    CustomExceptions handleCustomException(CustomException customException) {

        CustomExceptions customExceptions = new CustomExceptions();
        customExceptions.setStatusCode(customException.getErrCode());
        customExceptions.setStatus(String.valueOf(customException.getStatusCode()));
        customExceptions.setStatusDesc(customException.getReason());

        return customExceptions;
    }
}