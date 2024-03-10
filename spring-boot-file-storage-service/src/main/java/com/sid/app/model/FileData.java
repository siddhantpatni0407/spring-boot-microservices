package com.sid.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Siddhant Patni
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileData {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fileName")
    private String name;

    @JsonProperty("fileType")
    private String type;

    @JsonProperty("fileData")
    private String fileData;

}