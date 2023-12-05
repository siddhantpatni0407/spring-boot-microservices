package com.sid.sb.jwt.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegisterRequestDTO {

    @JsonProperty("username")
    private String username;

    @JsonProperty("userPassword")
    private String password;

    @JsonProperty("entityNo")
    private String entityNo;

    @JsonProperty("firstName")
    private String firstname;

    @JsonProperty("lastName")
    private String lastname;

    @JsonProperty("initial")
    private String initial;

    @JsonProperty("idNumber")
    private String idNumber;

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("endDate")
    private Date endDate;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("roleList")
    private List<String> roleList = new ArrayList<>();

}