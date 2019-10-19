package com.szabodev.example.security.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsFilter corsFilter(@Value("${config.environment}") String environment) {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        if ("local".equals(environment)) {
            config.setAllowCredentials(true);
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        }

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
