package com.sid.springboot.chatgpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Chat gpt final response.
 *
 * @author Siddhant Patni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGPTFinalResponse {

    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;

}