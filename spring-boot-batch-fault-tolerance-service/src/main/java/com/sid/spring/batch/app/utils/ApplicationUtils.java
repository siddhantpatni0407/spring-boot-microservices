package com.sid.spring.batch.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationUtils {

    /**
     * Gets json string.
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return the json string
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