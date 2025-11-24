package com.tiendamascota.config;

import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Value("${app.cors.allowed-origins:https://tienda-mi-mascota.vercel.app}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        String allowed = Objects.requireNonNullElse(allowedOrigins, "");
        String[] origins = Arrays.stream(allowed.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty() && !s.equals("*")) // Explicitly filter out "*" to prevent errors
                .toArray(String[]::new);

        logger.info("CORS Configuration - Allowed Origins: {}", Arrays.toString(origins));

        registry.addMapping("/api/**")
                .allowedOriginPatterns(origins.length == 0 ? new String[] {"https://tienda-mi-mascota.vercel.app"} : origins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
        
        // Also allow legacy /productos/* paths (frontend may call without /api prefix)
        registry.addMapping("/productos/**")
            .allowedOriginPatterns(origins.length == 0 ? new String[] {"https://tienda-mi-mascota.vercel.app"} : origins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
        
        // Allow auth endpoints for web and mobile
        registry.addMapping("/auth/**")
            .allowedOriginPatterns(origins.length == 0 ? new String[] {"https://tienda-mi-mascota.vercel.app"} : origins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
