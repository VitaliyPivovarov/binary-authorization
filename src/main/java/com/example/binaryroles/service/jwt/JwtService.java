package com.example.binaryroles.service.jwt;

import com.example.binaryroles.exception.ForbiddenException;
import com.example.binaryroles.service.jwt.dto.JwtDto;
import com.example.binaryroles.service.auth.dto.PrincipalDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.binaryroles.security.SecurityConstants.*;

@Component
public class JwtService {

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.token.expired}")
    private int jwtAccessExpirationMs;

    @Value("${jwt.token.refresh}")
    private Long jwtRefreshExpirationMs;

    private Jws<Claims> jwtParse(String token) {
        Jws<Claims> jwt;
        try {
            jwt = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
        } catch (RuntimeException exc) {
            throw new ForbiddenException("Error parsing JWT!");
        }
        return jwt;
    }

    public PrincipalDto parseAccessToken(String accessToken) {
        Jws<Claims> jwt = jwtParse(accessToken);

        Date exp = jwt.getBody().getExpiration();
        if (new Date().after(exp)) throw new ForbiddenException("Jwt expired!");

        String userUid =  jwt.getBody().get(JWT_USER_UID_FIELD).toString();
        String username = jwt.getBody().get(JWT_USERNAME_FIELD).toString();
        Integer binaryPermission = Integer.valueOf(jwt.getBody().get(JWT_BINARY_PERMISSION_FIELD).toString());

        return new PrincipalDto(userUid, username, binaryPermission);
    }

    public JwtDto buildAccessToken(String userUid, String username, Integer binaryPermission) {
        String accessToken = Jwts.builder()
                .claim(JWT_USER_UID_FIELD, userUid)
                .claim(JWT_USERNAME_FIELD, username)
                .claim(JWT_BINARY_PERMISSION_FIELD, binaryPermission)
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        JwtDto jwtDto = new JwtDto();
        jwtDto.setAccessToken(accessToken);
        jwtDto.setRefreshToken(buildRefreshToken(userUid));
        return jwtDto;
    }

    public String buildRefreshToken(String userUid) {
        return Jwts.builder()
                .claim(JWT_USER_UID_FIELD, userUid)
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

}