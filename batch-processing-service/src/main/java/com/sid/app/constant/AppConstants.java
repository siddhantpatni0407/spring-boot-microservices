package com.sid.app.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author Siddhant Patni
 */
public class AppConstants {

    /**
     * The constant ENDPOINT.
     */
    public static final String BATCH_PROCESSING_ENDPOINT = "/api/v1/batch-processing-service/job";
    public static final String FILE_PATH = "src/main/resources/customers.csv";
    public static final String[] CSV_HEADER_DATA = {"id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob"};
    public static final String REPOSITORY_METHOD_SAVE = "save";
    public static final String DATA_DELIMITER = ",";
    public static final int CSV_DATA_LINE_TO_SKIP = 1;

}