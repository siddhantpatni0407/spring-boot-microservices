package com.sid.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author Siddhant Patni
 * Utility class for common application functionalities.
 */
@Component
@Slf4j
@SuppressWarnings("PMD")
public class ApplicationUtils {

    /**
     * Converts an object to its JSON string representation.
     *
     * @param <T>    the type parameter
     * @param object the object to convert
     * @return the JSON string representation of the object
     */
    public static <T> String getJSONString(T object) {
        if (object != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Error occurred [{}] while converting to string [{}]", e.getMessage(), object);
                }
            }
        }
        return "";
    }

}