package com.sid.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * This is exception class for CustomException.
 */
public class CustomExceptions {

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusDesc")
    private String statusDesc;

}