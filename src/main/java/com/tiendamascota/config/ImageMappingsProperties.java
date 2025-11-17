package com.tiendamascota.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "image")
public class ImageMappingsProperties {

    /**
     * Mapeo de claves a URLs de imagen. La clave puede ser el ID del producto
     * (ej: 1) o un slug del nombre (ej: alimento-perros).
     */
    private Map<String, String> mappings;

    /** URL por defecto si no hay mapping para un producto */
    private String defaultUrl;

    public Map<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}
