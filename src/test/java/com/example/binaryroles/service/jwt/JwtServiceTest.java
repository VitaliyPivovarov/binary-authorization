package com.example.binaryroles.service.jwt;

import com.example.binaryroles.exception.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Test
    @DisplayName("Should throws an exception when the token is expired")
    void parseAccessTokenWhenTokenIsExpiredThenThrowsException() {
        String accessToken =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyVXVpZCIsImV4cCI6MTU5NjY0MzQ3N30.q-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7-_7";

        assertThrows(
                ForbiddenException.class,
                () -> {
                    jwtService.parseAccessToken(accessToken);
                });
    }

    @Test
    @DisplayName("Should throws an exception when the token is invalid")
    void parseAccessTokenWhenTokenIsInvalidThenThrowsException() {
        String accessToken = "invalid_token";

        assertThrows(
                ForbiddenException.class,
                () -> {
                    jwtService.parseAccessToken(accessToken);
                });
    }
}