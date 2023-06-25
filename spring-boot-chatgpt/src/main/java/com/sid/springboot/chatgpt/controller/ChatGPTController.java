package com.sid.springboot.chatgpt.controller;

import com.sid.springboot.chatgpt.constant.AppConstants;
import com.sid.springboot.chatgpt.model.ChatGPTFinalResponse;
import com.sid.springboot.chatgpt.service.ChatGPTService;
import com.sid.springboot.chatgpt.util.AppUtils;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * The type Chat gpt controller.
 *
 * @author Siddhant Patni
 */
@RestController
@XSlf4j
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;

    /**
     * Gets answer.
     *
     * @param question String
     * @return the answer of given question
     */
    @GetMapping(value = AppConstants.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatGPTFinalResponse> getAnswer(@RequestParam(name = "question", required = true) String question) {

        if (log.isInfoEnabled()) {
            log.info("getAnswer() : ===== >>> Chat GPT - START <<< =====");
        }

        return chatGPTService.getAnswer(question)
                .flatMap(chatGPTFinalResponse -> {

                    if (log.isInfoEnabled()) {
                        log.info("getAnswer() : Request -> {} and Response -> {}", question, AppUtils.getJSONString(chatGPTFinalResponse));
                        log.info("getAnswer() : ===== >>> Chat GPT - END <<< =====");
                    }

                    return Mono.just(chatGPTFinalResponse);
                });
    }

}