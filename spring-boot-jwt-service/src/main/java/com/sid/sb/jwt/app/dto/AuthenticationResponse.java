package com.sid.sb.jwt.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @JsonProperty("token")
    private String token;

    @JsonProperty("refresh")
    private String refresh;

}