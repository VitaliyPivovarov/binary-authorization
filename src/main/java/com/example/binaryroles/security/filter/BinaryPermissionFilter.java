package com.example.binaryroles.security.filter;

import com.example.binaryroles.exception.ForbiddenException;
import com.example.binaryroles.service.auth.AuthenticationService;
import com.example.binaryroles.service.auth.dto.PrincipalDto;
import com.example.binaryroles.service.endpoint.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class BinaryPermissionFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;
    private final EndpointService endpointService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            PrincipalDto principalDto = (PrincipalDto) authentication.getPrincipal();

            Integer binaryPermission = principalDto.getBinaryPermission();
            Optional<Integer> endpointIndex = endpointService.findIndexByUri(request.getMethod() + request.getRequestURI());

            if (endpointIndex.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (authenticationService.checkPermission(binaryPermission, endpointIndex)) {
                filterChain.doFilter(request, response);
            } else {
                throw new ForbiddenException("Not permission!");
            }
        }
    }
}