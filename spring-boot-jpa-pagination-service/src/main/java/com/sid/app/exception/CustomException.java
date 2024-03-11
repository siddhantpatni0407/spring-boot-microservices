package com.sid.app.exception;

/**
 * @author Siddhant Patni
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -2359863924124256L;

    public CustomException(String errorMessage) {
        super(errorMessage);
    }

}