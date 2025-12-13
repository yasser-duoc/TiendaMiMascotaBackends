package com.tiendamascota.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Configuración de Jackson para permitir respuestas JSON grandes.
 * Necesario para campos que contienen imágenes Base64.
 * 
 * IMPORTANTE: Esta configuración aumenta los límites de lectura/escritura
 * para soportar strings muy largos como imágenes Base64 (pueden ser >1MB).
 */
@Configuration
public class JacksonConfig {

    // 50MB - límite generoso para imágenes Base64 grandes
    private static final int MAX_STRING_LENGTH = 50 * 1024 * 1024;

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        
        // Deshabilitar la falla en campos vacíos
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // CRÍTICO: Configurar límites de LECTURA para strings largos (Base64)
        // Por defecto Jackson 2.15+ limita strings a ~20MB, esto lo aumenta
        objectMapper.getFactory().setStreamReadConstraints(
            StreamReadConstraints.builder()
                .maxStringLength(MAX_STRING_LENGTH)
                .maxNestingDepth(1000)
                .maxNumberLength(10000)
                .build()
        );
        
        // CRÍTICO: Configurar límites de ESCRITURA para strings largos (Base64)
        // Esto permite serializar productos con imágenes Base64 en listas
        objectMapper.getFactory().setStreamWriteConstraints(
            StreamWriteConstraints.builder()
                .maxNestingDepth(1000)
                .build()
        );
        
        System.out.println("[JacksonConfig] ✅ Configurado con maxStringLength=" + MAX_STRING_LENGTH + " bytes (~50MB)");
        
        return objectMapper;
    }
}
