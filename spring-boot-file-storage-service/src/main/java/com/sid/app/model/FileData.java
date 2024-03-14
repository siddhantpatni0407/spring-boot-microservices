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
@SuppressWarnings("PMD")
public class FileData {

    @JsonProperty("id")
    private long id;

    @JsonProperty("fileName")
    private String name;

    @JsonProperty("fileType")
    private String type;

    @JsonProperty("fileDownloadURL")
    private String fileDownloadURL;

    @JsonProperty("fileSize")
    private String fileSize;

    @JsonProperty("fileData")
    private String fileData;

    // Constructor
    public FileData(long id, String name, String type, String fileDownloadURL, String fileSize) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fileDownloadURL = fileDownloadURL;
        this.fileSize = fileSize;
    }

}