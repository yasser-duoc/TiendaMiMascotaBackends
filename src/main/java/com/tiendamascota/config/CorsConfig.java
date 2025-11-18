package com.tiendamascota.config;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:https://tienda-mi-mascota.vercel.app,http://localhost:5173,http://localhost:3000,http://10.0.2.2:8080}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        String allowed = Objects.requireNonNullElse(allowedOrigins, "");
        String[] origins = Arrays.stream(allowed.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

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
