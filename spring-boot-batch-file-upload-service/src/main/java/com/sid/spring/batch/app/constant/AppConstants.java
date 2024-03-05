package com.sid.spring.batch.app.constant;

/**
 * @author Siddhant Patni
 */
public class AppConstants {

    /**
     * Below Constants are used for Endpoint.
     */
    public static final String BATCH_PROCESSING_GET_DATA_ENDPOINT = "/api/v1/batch-processing-service/getData";
    public static final String BATCH_PROCESSING_ENDPOINT = "/api/v1/batch-processing-service/importFile";

    /**
     * Below Constants are used for File.
     */
    public static final String FILE_PATH = "src/main/resources/customers1.csv";
    public static final String CSV_FILE_PATH = "src/main/resources/siddhant.csv";
    public static final String FILE_STORAGE_DIRECTORY = "C:\\Users\\Siddhant Patni\\Documents\\batch_files\\";

    /**
     * Below Constants are used for Configuration.
     */
    public static final String[] CSV_HEADER_DATA = {"id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob", "age"};
    public static final String REPOSITORY_METHOD_SAVE = "save";
    public static final String DATA_DELIMITER = ",";
    public static final int CSV_DATA_LINE_TO_SKIP = 1;

}