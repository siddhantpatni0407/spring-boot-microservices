package com.sid.app.service;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.sid.app.config.ApplicationProperties;
import com.sid.app.constants.ApplicationConstants;
import com.sid.app.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class TransformerService {

    @Autowired
    private ApplicationProperties properties;

    public Mono<String> transform(Object request) {

        try {
            if (log.isDebugEnabled()) {
                log.debug("transform() : request -> {}", request);
            }

            List<Object> specs = JsonUtils.classpathToList(properties.getJoltSpecFile());
            Chainr chainr = Chainr.fromSpec(specs);
            String inputJoltReqPayload = JsonUtils.toJsonString(request);
            Object inputJSON = JsonUtils.jsonToObject(inputJoltReqPayload);

            if (log.isDebugEnabled()) {
                log.debug("transform() : inputJSON -> {}", JsonUtils.toJsonString(inputJSON));
            }

            Object output = chainr.transform(inputJSON);
            String jsonOutput = JsonUtils.toJsonString(output);

            if (log.isDebugEnabled()) {
                log.debug("transform() : transformedOutput -> {}", jsonOutput);
            }

            return Mono.just(jsonOutput);

        } catch (Exception e) {

            if (log.isErrorEnabled()) {
                log.error("transform() : Error occurred [{}] while transforming from string [{}]", e.getMessage(), request);
            }

            /**
             * Throwing custom Bad Request Exception when exception raised during transformation.
             */
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), ApplicationConstants.TRANSFORMATION_ERROR.concat(" : ").concat(e.getMessage()));
        }


    }

}