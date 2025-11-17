package com.tiendamascota.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
            
            // Agregar más parámetros para mejorar relevancia
            String url = String.format("%s?query=%s&client_id=%s&per_page=1&orientation=squarish&order_by=relevant",
                UNSPLASH_API_URL, 
                keywords.replace(" ", "%20"), // URL encode espacios
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
     * Extrae palabras clave en inglés MÁS ESPECÍFICAS para buscar en Unsplash
     */
    private String extraerKeywords(String nombreProducto, String categoria) {
        String nombre = nombreProducto.toLowerCase();
        
        // === ALIMENTOS ===
        if (nombre.contains("alimento") || nombre.contains("comida") || nombre.contains("premium")) {
            if (nombre.contains("perro") || nombre.contains("perros")) {
                return "dog food bowl kibble"; // Más específico
            }
            if (nombre.contains("gato") || nombre.contains("gatos")) {
                return "cat food bowl kibble"; // Más específico
            }
            return "pet food bowl product";
        }
        
        // === SNACKS/PREMIOS ===
        if (nombre.contains("snack") || nombre.contains("premio") || nombre.contains("golosina") || nombre.contains("galleta")) {
            if (nombre.contains("perro") || nombre.contains("perros")) {
                return "dog treats bones snacks"; // Específico para premios
            }
            if (nombre.contains("gato") || nombre.contains("gatos")) {
                return "cat treats snacks";
            }
            return "pet treats snacks";
        }
        
        // === JUGUETES ===
        if (nombre.contains("pelota") || nombre.contains("ball")) {
            return "dog toy ball play"; // Pelota de juguete
        }
        if (nombre.contains("cuerda") || nombre.contains("rope")) {
            return "dog rope toy play"; // Cuerda de juego
        }
        if (nombre.contains("juguete")) {
            if (nombre.contains("perro") || nombre.contains("perros")) {
                return "dog toy play product";
            }
            if (nombre.contains("gato") || nombre.contains("gatos")) {
                return "cat toy play product";
            }
            return "pet toy play";
        }
        
        // === ACCESORIOS ===
        if (nombre.contains("collar")) {
            if (nombre.contains("gato")) {
                return "cat collar accessory";
            }
            return "dog collar leash accessory"; // Collar específico
        }
        if (nombre.contains("correa") || nombre.contains("leash")) {
            return "dog leash walk accessory"; // Correa específica
        }
        if (nombre.contains("cama") || nombre.contains("bed")) {
            if (nombre.contains("gato")) {
                return "cat bed sleep cushion";
            }
            return "dog bed sleep cushion"; // Cama específica
        }
        if (nombre.contains("comedero") || nombre.contains("bowl") || nombre.contains("plato")) {
            return "pet food bowl dish"; // Comedero específico
        }
        if (nombre.contains("bebedero") || nombre.contains("fuente") || nombre.contains("water")) {
            return "pet water fountain bowl"; // Bebedero específico
        }
        if (nombre.contains("transportadora") || nombre.contains("carrier")) {
            return "pet carrier transport crate"; // Transportadora específica
        }
        
        // === HIGIENE ===
        if (nombre.contains("shampoo") || nombre.contains("champú")) {
            return "pet shampoo grooming bottle"; // Shampoo específico
        }
        if (nombre.contains("cepillo") || nombre.contains("brush")) {
            return "pet brush grooming tool"; // Cepillo específico
        }
        if (nombre.contains("toallita") || nombre.contains("wipe")) {
            return "pet wipes cleaning"; // Toallitas específicas
        }
        
        // === MEDICAMENTOS ===
        if (nombre.contains("antipulgas") || nombre.contains("pulga") || nombre.contains("garrapata")) {
            return "pet medicine flea treatment"; // Antipulgas específico
        }
        if (nombre.contains("vitamina") || nombre.contains("supplement")) {
            return "pet vitamins supplement bottle"; // Vitaminas específicas
        }
        if (nombre.contains("desparasitante") || nombre.contains("parasit")) {
            return "pet medicine pills treatment"; // Desparasitante específico
        }
        
        // === FALLBACK POR ANIMAL ===
        if (nombre.contains("perro") || nombre.contains("perros")) {
            return "dog product accessory";
        }
        if (nombre.contains("gato") || nombre.contains("gatos")) {
            return "cat product accessory";
        }
        
        // === FALLBACK POR CATEGORÍA ===
        switch (categoria) {
            case "Alimento": 
                return "pet food bowl product";
            case "Juguetes": 
                return "pet toy play colorful";
            case "Accesorios": 
                return "pet accessories supplies";
            case "Higiene": 
                return "pet grooming supplies";
            case "Medicamentos": 
                return "pet medicine treatment";
            default: 
                return "pet supplies product";
        }
    }
    
    /**
     * Imágenes por defecto MEJORADAS si falla Unsplash
     */
    private String obtenerImagenPorDefecto(String categoria) {
        Map<String, String> defaults = Map.of(
            "Alimento", "https://images.unsplash.com/photo-1589924691995-400dc9ecc119?w=400", // Bowl con comida
            "Juguetes", "https://images.unsplash.com/photo-1535294435445-d7249524ef2e?w=400", // Juguetes coloridos
            "Accesorios", "https://images.unsplash.com/photo-1601758228041-f3b2795255f1?w=400", // Collar
            "Higiene", "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=400", // Productos de baño
            "Medicamentos", "https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=400" // Medicinas
        );
        return defaults.getOrDefault(categoria, "https://images.unsplash.com/photo-1450778869180-41d0601e046e?w=400");
    }
}
