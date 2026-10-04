package com.prashanth.urlshortener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class CorsConfig implements WebMvcConfigurer {
 
    // Comma-separated list of allowed frontend origins.
    // Local default: the Vite dev server. In production set APP_ALLOWED_ORIGINS, e.g.
    //   APP_ALLOWED_ORIGINS=https://snip.vercel.app,https://www.mydomain.com
    @Value("${app.allowed-origins:http://localhost:5173}")
    private String[] allowedOrigins;
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization", "Accept","application/json")
                .maxAge(3600); // browser caches the preflight answer for 1 hour
    }
}