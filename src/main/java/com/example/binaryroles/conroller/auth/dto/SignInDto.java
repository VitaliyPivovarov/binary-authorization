package com.example.binaryroles.conroller.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(
        exclude = "password"
)
public class SignInDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
