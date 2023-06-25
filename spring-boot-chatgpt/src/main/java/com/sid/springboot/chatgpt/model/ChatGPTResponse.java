package com.sid.springboot.chatgpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Chat gpt response.
 *
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created")
    private Integer created;

    @JsonProperty("model")
    private String model;

    @JsonProperty("choices")
    private List<ChatGPTResponseChoice> choices;

    @JsonProperty("usage")
    private ChatGPTResponseUsage usage;

}