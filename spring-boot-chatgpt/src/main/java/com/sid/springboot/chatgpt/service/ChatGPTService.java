package com.sid.springboot.chatgpt.service;

import com.sid.springboot.chatgpt.config.AppProperties;
import com.sid.springboot.chatgpt.model.ChatGPTFinalResponse;
import com.sid.springboot.chatgpt.model.ChatGPTRequest;
import com.sid.springboot.chatgpt.model.ChatGPTResponse;
import com.sid.springboot.chatgpt.util.AppUtils;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * The type Chat gpt service.
 *
 * @author Siddhant Patni
 */
@Service
@XSlf4j
public class ChatGPTService {

    @Autowired
    private ChatGPTIntegrationService chatGPTIntegrationService;

    @Autowired
    private AppProperties properties;

    /**
     * Gets answer.
     *
     * @param question the question
     * @return the answer
     */
    public Mono<ChatGPTFinalResponse> getAnswer(String question) {

        ChatGPTRequest chatGPTRequest = generateChatGPTRequest(question);

        if (log.isDebugEnabled()) {
            log.debug("getAnswer() : chatGPTRequest -> {}", AppUtils.getJSONString(chatGPTRequest));
        }

        return chatGPTIntegrationService.invokeChatGPTAPI(chatGPTRequest)
                .flatMap(chatGPTResponse -> {

                    if (log.isInfoEnabled()) {
                        log.info("getAnswer() : ChatGPT API. -> Request -> {} and Response -> {}", AppUtils.getJSONString(chatGPTRequest), AppUtils.getJSONString(chatGPTResponse));
                        log.info("getAnswer() : ===== >>> Chat GPT - END <<< =====");
                    }

                    ChatGPTFinalResponse chatGPTFinalResponse = generateChatGPTFinalResponse(question, chatGPTResponse);
                    return Mono.just(chatGPTFinalResponse);
                });
    }

    /**
     * Generate chat gpt request chat gpt request.
     *
     * @param question the question
     * @return the chat gpt request
     */
    public ChatGPTRequest generateChatGPTRequest(String question) {

        return ChatGPTRequest
                .builder()
                .model(properties.getModel())
                .prompt(question)
                .temperature(properties.getTemperature())
                .max_tokens(properties.getMaxToken())
                .top_p(properties.getTopP())
                .frequency_penalty(properties.getFrequencyPenalty())
                .presence_penalty(properties.getPresencePenalty())
                .build();
    }

    /**
     * Generate chat gpt final response chat gpt final response.
     *
     * @param question        the question
     * @param chatGPTResponse the chat gpt response
     * @return the chat gpt final response
     */
    public ChatGPTFinalResponse generateChatGPTFinalResponse(String question, ChatGPTResponse chatGPTResponse) {

        return ChatGPTFinalResponse
                .builder()
                .question(question)
                .answer(chatGPTResponse.getChoices().get(0).getText())
                .build();
    }

}