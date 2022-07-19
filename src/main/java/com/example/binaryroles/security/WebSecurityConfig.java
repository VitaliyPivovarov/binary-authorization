package com.example.binaryroles.security;

import com.example.binaryroles.security.filter.BinaryPermissionFilter;
import com.example.binaryroles.security.filter.JwtAuthenticationFilter;
import com.example.binaryroles.service.auth.AuthenticationService;
import com.example.binaryroles.service.endpoint.EndpointService;
import com.example.binaryroles.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.example.binaryroles.conroller.Endpoints.AUTH;
import static com.example.binaryroles.conroller.Endpoints.BASE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final EndpointService endpointService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .addFilterBefore(new JwtAuthenticationFilter(jwtService), BasicAuthenticationFilter.class)
                .addFilterBefore(new BinaryPermissionFilter(authenticationService, endpointService), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(BASE + AUTH + "/**");
    }
}
