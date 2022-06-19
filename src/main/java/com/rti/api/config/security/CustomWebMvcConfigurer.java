package com.rti.api.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Value(value = "${cors.allowedOrigins}")
    private String allowedOrigins;

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET")
                .allowedHeaders("*")
                .maxAge(MAX_AGE_SECS);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configuration) {
        configuration.setUseTrailingSlashMatch(false);
    }
}