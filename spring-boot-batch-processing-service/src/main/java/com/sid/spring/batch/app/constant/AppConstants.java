package com.sid.spring.batch.app.constant;

/**
 * @author Siddhant Patni
 */
public class AppConstants {

    /**
     * Below Constants are used for Endpoint.
     */
    public static final String BATCH_PROCESSING_DATA_ENDPOINT = "/api/v1/batch-processing-service/generateData";
    public static final String BATCH_PROCESSING_ENDPOINT = "/api/v1/batch-processing-service/job";

    /**
     * Below Constants are used for File.
     */
    public static final String FILE_PATH = "src/main/resources/customers.csv";
    public static final String CSV_FILE_PATH = "src/main/resources/siddhant.csv";

    /**
     * Below Constants are used for Configuration.
     */
    public static final String[] CSV_HEADER_DATA = {"id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob"};
    public static final String REPOSITORY_METHOD_SAVE = "save";
    public static final String DATA_DELIMITER = ",";
    public static final int CSV_DATA_LINE_TO_SKIP = 1;

}