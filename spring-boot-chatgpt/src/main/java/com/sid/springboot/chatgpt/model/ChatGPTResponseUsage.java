package com.sid.springboot.chatgpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Chat gpt response usage.
 *
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponseUsage {

    @JsonProperty("prompt_tokens")
    private int prompt_tokens;

    @JsonProperty("completion_tokens")
    private int completion_tokens;

    @JsonProperty("total_tokens")
    private int total_tokens;

}