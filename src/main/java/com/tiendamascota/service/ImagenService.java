package com.tiendamascota.service;

import org.springframework.stereotype.Service;

@Service
public class ImagenService {
    
    /**
     * Genera URL de imagen específica según el nombre exacto del producto
     * Usando imágenes directas y confiables sin APIs externas
     */
    public String generarImagenParaProducto(String nombreProducto, String categoria) {
        String nombre = nombreProducto.toLowerCase().trim();
        
        // === MAPEO DIRECTO POR NOMBRE DE PRODUCTO ===
        
        // ALIMENTOS
        if (nombre.contains("alimento") && nombre.contains("perro")) {
            return "https://images.pexels.com/photos/7210754/pexels-photo-7210754.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("alimento") && nombre.contains("gato")) {
            return "https://images.pexels.com/photos/6853522/pexels-photo-6853522.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        
        // SNACKS/PREMIOS
        if (nombre.contains("snack") || nombre.contains("premio") || nombre.contains("golosina")) {
            if (nombre.contains("perro")) {
                return "https://images.pexels.com/photos/7210490/pexels-photo-7210490.jpeg?auto=compress&cs=tinysrgb&w=400";
            }
            if (nombre.contains("gato")) {
                return "https://images.pexels.com/photos/7210745/pexels-photo-7210745.jpeg?auto=compress&cs=tinysrgb&w=400";
            }
        }
        
        // JUGUETES
        if (nombre.contains("pelota")) {
            return "https://images.pexels.com/photos/4588047/pexels-photo-4588047.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("cuerda")) {
            return "https://images.pexels.com/photos/5731889/pexels-photo-5731889.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("juguete") && nombre.contains("gato")) {
            return "https://images.pexels.com/photos/1440387/pexels-photo-1440387.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        
        // ACCESORIOS
        if (nombre.contains("collar")) {
            return "https://images.pexels.com/photos/6853515/pexels-photo-6853515.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("correa")) {
            return "https://images.pexels.com/photos/7210655/pexels-photo-7210655.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("cama")) {
            return "https://images.pexels.com/photos/4588056/pexels-photo-4588056.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("comedero") || nombre.contains("plato")) {
            return "https://images.pexels.com/photos/5745189/pexels-photo-5745189.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("bebedero")) {
            return "https://images.pexels.com/photos/6853511/pexels-photo-6853511.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("transportadora")) {
            return "https://images.pexels.com/photos/5731793/pexels-photo-5731793.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        
        // HIGIENE
        if (nombre.contains("shampoo") || nombre.contains("champú")) {
            return "https://images.pexels.com/photos/6489074/pexels-photo-6489074.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("cepillo")) {
            return "https://images.pexels.com/photos/6816861/pexels-photo-6816861.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("toallita")) {
            return "https://images.pexels.com/photos/7210339/pexels-photo-7210339.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        
        // MEDICAMENTOS
        if (nombre.contains("antipulgas") || nombre.contains("pulga")) {
            return "https://images.pexels.com/photos/5731769/pexels-photo-5731769.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("vitamina")) {
            return "https://images.pexels.com/photos/6489073/pexels-photo-6489073.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        if (nombre.contains("desparasitante")) {
            return "https://images.pexels.com/photos/5731766/pexels-photo-5731766.jpeg?auto=compress&cs=tinysrgb&w=400";
        }
        
        // FALLBACK POR CATEGORÍA
        return obtenerImagenPorCategoria(categoria);
    }
    
    /**
     * Imágenes por categoría usando Pexels (sin API, URLs directas)
     */
    private String obtenerImagenPorCategoria(String categoria) {
        return switch (categoria) {
            case "Alimento" -> "https://images.pexels.com/photos/7210754/pexels-photo-7210754.jpeg?auto=compress&cs=tinysrgb&w=400";
            case "Juguetes" -> "https://images.pexels.com/photos/4588047/pexels-photo-4588047.jpeg?auto=compress&cs=tinysrgb&w=400";
            case "Accesorios" -> "https://images.pexels.com/photos/6853515/pexels-photo-6853515.jpeg?auto=compress&cs=tinysrgb&w=400";
            case "Higiene" -> "https://images.pexels.com/photos/6489074/pexels-photo-6489074.jpeg?auto=compress&cs=tinysrgb&w=400";
            case "Medicamentos" -> "https://images.pexels.com/photos/5731769/pexels-photo-5731769.jpeg?auto=compress&cs=tinysrgb&w=400";
            default -> "https://images.pexels.com/photos/406014/pexels-photo-406014.jpeg?auto=compress&cs=tinysrgb&w=400";
        };
    }
}
