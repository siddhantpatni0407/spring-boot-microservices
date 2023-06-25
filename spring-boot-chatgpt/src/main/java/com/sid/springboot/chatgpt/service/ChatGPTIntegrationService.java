package com.sid.springboot.chatgpt.service;

import com.sid.springboot.chatgpt.config.AppProperties;
import com.sid.springboot.chatgpt.exception.ServiceException;
import com.sid.springboot.chatgpt.model.ChatGPTRequest;
import com.sid.springboot.chatgpt.model.ChatGPTResponse;
import com.sid.springboot.chatgpt.util.AppUtils;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The type Chat gpt integration service.
 *
 * @author Siddhant Patni
 */
@Service
@XSlf4j
public class ChatGPTIntegrationService {

    @Autowired
    private AppProperties properties;

    @Autowired
    private AppUtils utils;

    @Autowired
    private WebClient webClientChatGPT;

    /**
     * Invoke chat gptapi mono.
     *
     * @param chatGPTRequest the chat gpt request
     * @return the mono
     */
    public Mono<ChatGPTResponse> invokeChatGPTAPI(ChatGPTRequest chatGPTRequest) {

        String url = properties.getChatGPTURL();

        if (log.isDebugEnabled()) {
            log.debug("invokeChatGPTAPI() : url -> {}", url);
        }

        return webClientChatGPT
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatGPTRequest)
                .headers(utils.getHttpHeaders())
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        return clientResponse.bodyToMono(ChatGPTResponse.class)
                                .flatMap(chatGPTResponse -> {
                                    if (log.isErrorEnabled()) {
                                        log.error("invokeChatGPTAPI() : Exception occurred for API -> {} with request -> {} and exception -> {}, Response -> {}",
                                                url, AppUtils.getJSONString(chatGPTRequest), clientResponse.statusCode(), AppUtils.getJSONString(chatGPTResponse));
                                    }
                                    String errorCode = clientResponse.statusCode().getReasonPhrase();
                                    return Mono.error(new ServiceException(clientResponse.statusCode(), clientResponse.statusCode().value(), errorCode));
                                });
                    }
                    return clientResponse.bodyToMono(ChatGPTResponse.class);
                }).retryWhen(utils.getRetry());
    }

}