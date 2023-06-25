package com.sid.springboot.chatgpt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type App properties.
 *
 * @author Siddhant Patni
 */
@Data
@Component
public class AppProperties {

    @Value("${chat-gpt.url.completion}")
    private String chatGPTURL;

    @Value("${chat-gpt.token}")
    private String token;

    @Value("${chat-gpt.model}")
    private String model;

    @Value("${chat-gpt.temperature}")
    private int temperature;

    @Value("${chat-gpt.max_token}")
    private int maxToken;

    @Value("${chat-gpt.top_p}")
    private int topP;

    @Value("${chat-gpt.frequency_penalty}")
    private int frequencyPenalty;

    @Value("${chat-gpt.presence_penalty}")
    private int presencePenalty;

    @Value("${webclient.config.memory-buffer-size}")
    private int bufferSize;

    @Value("${webclient.config.timeout}")
    private int timeout;

    @Value("${webclient.config.retry.max-retry}")
    private int maxRetry;

    @Value("${webclient.config.retry.delay}")
    private int delay;

}