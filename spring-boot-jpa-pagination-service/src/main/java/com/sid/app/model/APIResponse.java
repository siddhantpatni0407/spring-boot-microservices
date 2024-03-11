package com.sid.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Siddhant Patni
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

    @JsonProperty("recordCount")
    int recordCount;

    @JsonProperty("response")
    T response;

}