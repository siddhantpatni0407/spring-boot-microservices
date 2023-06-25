package com.sid.springboot.chatgpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Chat gpt request.
 *
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTRequest {

    @JsonProperty("model")
    private String model;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("temperature")
    private int temperature;

    @JsonProperty("max_tokens")
    private int max_tokens;

    @JsonProperty("top_p")
    private int top_p;

    @JsonProperty("frequency_penalty")
    private int frequency_penalty;

    @JsonProperty("presence_penalty")
    private int presence_penalty;

}