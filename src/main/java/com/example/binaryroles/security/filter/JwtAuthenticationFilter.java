package com.example.binaryroles.security.filter;

import com.example.binaryroles.exception.ForbiddenException;
import com.example.binaryroles.service.jwt.JwtService;
import com.example.binaryroles.service.auth.dto.PrincipalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.binaryroles.security.SecurityConstants.HEADER_KEY;
import static com.example.binaryroles.security.SecurityConstants.TOKEN_PREFIX;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HEADER_KEY);
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            try {
                token = token.replace(TOKEN_PREFIX, "");
                PrincipalDto principalDto = jwtService.parseAccessToken(token);
                authentication(principalDto);
                filterChain.doFilter(request, response);
            } catch (ForbiddenException e) {
                response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
            }
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "JWT is empty!");
        }
    }

    private void authentication(final PrincipalDto principalDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDto, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}