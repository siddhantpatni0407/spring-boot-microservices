package com.sid.app.exception;

/**
 * @author Siddhant Patni
 */
public class FileNameNotCorrectError extends RuntimeException {

    private static final long serialVersionUID = -2359863924124256L;

    public FileNameNotCorrectError(String fileName) {
        super("File name contains invalid path sequence " + fileName);
    }

}