package com.sid.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sid.app.validation.EmailList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.List;
/**
 * @author Siddhant Patni
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @EmailList
    @NotEmpty
    @JsonProperty("to")
    private List<String> to;

    @EmailList
    @JsonProperty("cc")
    private List<String> cc;

    @EmailList
    @JsonProperty("bcc")
    private List<String> bcc;

    @NotEmpty
    @JsonProperty("subject")
    private String subject;

    @NotEmpty
    @JsonProperty("body")
    private String body;

    @JsonProperty("priority")
    private String priority;

}