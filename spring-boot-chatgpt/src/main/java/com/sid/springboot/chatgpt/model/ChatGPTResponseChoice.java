package com.sid.springboot.chatgpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Chat gpt response choice.
 *
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponseChoice {

    @JsonProperty("text")
    private String text;

    @JsonProperty("index")
    private int index;

    @JsonProperty("logprobs")
    private String logprobs;

    @JsonProperty("finish_reason")
    private String finish_reason;

}