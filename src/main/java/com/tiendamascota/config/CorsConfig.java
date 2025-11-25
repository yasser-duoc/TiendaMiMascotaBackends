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
        String allowedRaw = Objects.requireNonNullElse(allowedOrigins, "");
        String[] origins = Arrays.stream(allowedRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty() && !s.equals("*")) // Explicitly filter out "*" to prevent errors
                .toArray(String[]::new);

        logger.info("CORS Configuration - Allowed Origins: {}", Arrays.toString(origins));

        // Map CORS for all endpoints inside the servlet context. The application sets
        // `server.servlet.context-path=/api` so mappings are evaluated relative to that.
        String[] allowed = origins.length == 0 ? new String[] {"https://tienda-mi-mascota.vercel.app"} : origins;

        registry.addMapping("/**")
            .allowedOriginPatterns(allowed)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
