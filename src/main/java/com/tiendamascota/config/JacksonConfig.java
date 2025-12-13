package com.tiendamascota.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Configuración de Jackson para permitir respuestas JSON grandes.
 * Necesario para campos que contienen imágenes Base64.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        
        // Deshabilitar la falla en campos vacíos
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // Configurar límites más altos para strings largos (como Base64)
        // Por defecto Jackson limita strings a 20MB, pero lo aumentamos para estar seguros
        objectMapper.getFactory().setStreamWriteConstraints(
            StreamWriteConstraints.builder()
                .maxNestingDepth(1000)
                .build()
        );
        
        return objectMapper;
    }
}
