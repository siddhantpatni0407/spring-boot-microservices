package com.sid.app.exception;

/**
 * @author Siddhant Patni
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2359863924124256L;

    public ResourceNotFoundException(Long id) {
        super("Could not found the file with id " + id);
    }

}