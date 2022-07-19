package com.example.binaryroles.conroller.auth;

import com.example.binaryroles.conroller.auth.dto.SignInDto;
import com.example.binaryroles.dao.user.UserEntity;
import com.example.binaryroles.exception.ConflictException;
import com.example.binaryroles.service.auth.AuthenticationService;
import com.example.binaryroles.service.endpoint.EndpointService;
import com.example.binaryroles.service.jwt.JwtService;
import com.example.binaryroles.service.jwt.dto.JwtDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.example.binaryroles.conroller.Endpoints.*;

@Slf4j
@RestController
@RequestMapping(BASE + AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final EndpointService endpointService;

    @PostMapping(SIGN_IN)
    @ResponseStatus(HttpStatus.OK)
    public JwtDto signIn(@RequestBody SignInDto signInDto) {
        UserEntity userEntity = authenticationService.loadUserByUsername(signInDto.getUsername());

        if (!passwordEncoder.matches(signInDto.getPassword(), userEntity.getPassword())) {
            throw new ConflictException("Wrong login or password!");
        }

        return jwtService.buildAccessToken(userEntity.getUid(), userEntity.getUsername(),
                authenticationService.buildBinaryPermission(userEntity, endpointService.getAllEndpoints()));
    }

}