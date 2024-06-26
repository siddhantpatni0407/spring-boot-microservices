package com.sid.sb.jwt.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityResponse {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Status", status.value());
        map.put("Message", message);
        map.put("TimeStamp", new Date());
        map.put("Data", responseObj);

        return new ResponseEntity<>(map, status);
    }

}