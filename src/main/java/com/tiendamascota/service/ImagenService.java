package com.tiendamascota.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiendamascota.dto.UnsplashPhoto;
import com.tiendamascota.dto.UnsplashResponse;

@Service
public class ImagenService {
    
    @Value("${unsplash.access.key}")
    private String accessKey;
    
    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/search/photos";
    private final RestTemplate restTemplate = new RestTemplate();
    
    /**
     * Genera URL de imagen automáticamente según el nombre del producto
     */
    public String generarImagenParaProducto(String nombreProducto, String categoria) {
        try {
            String keywords = extraerKeywords(nombreProducto, categoria);
            
            String url = String.format("%s?query=%s&client_id=%s&per_page=1&orientation=squarish",
                UNSPLASH_API_URL, 
                keywords, 
                accessKey
            );
            
            UnsplashResponse response = restTemplate.getForObject(url, UnsplashResponse.class);
            
            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                return response.getResults().get(0).getUrls().getSmall(); // URL 400x400
            }
        } catch (Exception e) {
            System.err.println("Error generando imagen desde Unsplash: " + e.getMessage());
        }
        
        return obtenerImagenPorDefecto(categoria);
    }
    
    /**
     * Extrae palabras clave en inglés para buscar en Unsplash
     */
    private String extraerKeywords(String nombreProducto, String categoria) {
        String nombre = nombreProducto.toLowerCase();
        
        // Mapeo español -> inglés
        if (nombre.contains("perro") || nombre.contains("perros")) {
            if (nombre.contains("alimento") || nombre.contains("comida")) return "dog food";
            if (nombre.contains("juguete") || nombre.contains("pelota")) return "dog toy";
            if (nombre.contains("collar")) return "dog collar";
            if (nombre.contains("correa")) return "dog leash";
            if (nombre.contains("cama")) return "dog bed";
            return "dog";
        }
        
        if (nombre.contains("gato") || nombre.contains("gatos")) {
            if (nombre.contains("alimento") || nombre.contains("comida")) return "cat food";
            if (nombre.contains("juguete")) return "cat toy";
            if (nombre.contains("collar")) return "cat collar";
            if (nombre.contains("cama")) return "cat bed";
            return "cat";
        }
        
        if (nombre.contains("alimento") || nombre.contains("comida")) return "pet food";
        if (nombre.contains("juguete") || nombre.contains("pelota")) return "pet toy";
        if (nombre.contains("collar")) return "pet collar";
        if (nombre.contains("correa")) return "pet leash";
        if (nombre.contains("shampoo") || nombre.contains("champú")) return "pet shampoo";
        if (nombre.contains("cama")) return "pet bed";
        if (nombre.contains("transportadora")) return "pet carrier";
        if (nombre.contains("vitamina")) return "pet vitamins";
        if (nombre.contains("desparasitante") || nombre.contains("antipulgas")) return "pet medicine";
        if (nombre.contains("cepillo")) return "pet brush";
        
        // Fallback por categoría
        switch (categoria) {
            case "Alimento": return "pet food";
            case "Juguetes": return "pet toy";
            case "Accesorios": return "pet accessories";
            case "Higiene": return "pet grooming";
            case "Medicamentos": return "pet medicine";
            default: return "pet product";
        }
    }
    
    /**
     * Imágenes por defecto si falla Unsplash
     */
    private String obtenerImagenPorDefecto(String categoria) {
        Map<String, String> defaults = Map.of(
            "Alimento", "https://images.unsplash.com/photo-1589924691995-400dc9ecc119?w=400",
            "Juguetes", "https://images.unsplash.com/photo-1535294435445-d7249524ef2e?w=400",
            "Accesorios", "https://images.unsplash.com/photo-1601758228041-f3b2795255f1?w=400",
            "Higiene", "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=400",
            "Medicamentos", "https://images.unsplash.com/photo-1450778869180-41d0601e046e?w=400"
        );
        return defaults.getOrDefault(categoria, "https://images.unsplash.com/photo-1450778869180-41d0601e046e?w=400");
    }
}
