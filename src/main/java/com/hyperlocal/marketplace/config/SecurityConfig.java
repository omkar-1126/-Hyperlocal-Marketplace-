package com.hyperlocal.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/login",
                        "/register-client",
                        "/register-freelancer",
                        "/client/**",
                        "/freelancer/**",
                        "/post-service",
                        "/save-service",
                        "/view-services",
                        "/book/**",
                        "/success",
                        "/images/**",
                        "/static/**",
                        "/css/**"
                ).permitAll()
                .anyRequest().permitAll()
        );
        return http.build();
    }
}