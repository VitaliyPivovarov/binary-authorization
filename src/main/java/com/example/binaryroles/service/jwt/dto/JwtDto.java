package com.example.binaryroles.service.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {

    private String accessToken;
    private String refreshToken;

}
