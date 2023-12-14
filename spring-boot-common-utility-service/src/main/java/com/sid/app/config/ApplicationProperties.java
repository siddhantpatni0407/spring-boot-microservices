package com.sid.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationProperties {

    @Value("${jolt.spec-file}")
    private String joltSpecFile;

    @Value("${jolt.input-file}")
    private String joltInputFile;

    @Value("${jolt.output-file}")
    private String joltOutputFile;

}