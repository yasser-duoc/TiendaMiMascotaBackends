package com.tiendamascota.service;

import org.springframework.stereotype.Service;

@Service
public class ImagenService {
    
    /**
     * Genera URL de imagen específica según el nombre exacto del producto
     * Usando imágenes directas y confiables de tiendas veterinarias reales
     */
    public String generarImagenParaProducto(String nombreProducto, String categoria) {
        String nombre = nombreProducto.toLowerCase().trim();
        
        // === MAPEO DIRECTO POR NOMBRE DE PRODUCTO ===
        
        // ALIMENTOS
        if (nombre.contains("alimento") && nombre.contains("perro")) {
            return "https://ferosor.cl/307-large_default/alimento-para-perro-cachorro-fit-formula-saco-10-kg.jpg";
        }
        if (nombre.contains("alimento") && nombre.contains("gato")) {
            return "https://www.superzoo.cl/on/demandware.static/-/Sites-SuperZoo-master-catalog/default/dwf30e77a8/images/549a0-web.jpg";
        }
        
        // SNACKS/PREMIOS
        if (nombre.contains("snack") || nombre.contains("premio") || nombre.contains("golosina") || nombre.contains("natural")) {
            return "https://dojiw2m9tvv09.cloudfront.net/42482/product/X_snacknaturaldogvacuno3074.jpg?129&time=1763349073";
        }
        
        // JUGUETES
        if (nombre.contains("pelota")) {
            return "https://cdnx.jumpseller.com/guaudor/image/43385770/resize/1280/1280?1702321041";
        }
        if (nombre.contains("juguete") && nombre.contains("gato")) {
            return "https://www.distribuidoralira.cl/wp-content/uploads/2025/07/3936-3.jpg";
        }
        if (nombre.contains("cuerda")) {
            return "https://pobreguacho.cl/wp-content/uploads/2021/06/JUGUETE-CUERDA-BARRA-20cm.jpg";
        }
        
        // ACCESORIOS
        if (nombre.contains("collar")) {
            return "https://m.media-amazon.com/images/I/81c5yVZy8EL._AC_SX679_.jpg";
        }
        if (nombre.contains("correa")) {
            return "https://i5.walmartimages.cl/asr/756edadc-d882-4264-b196-95f66ef8cb2b.c8e887a6e0d86fa402fad618bd71ec36.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF";
        }
        if (nombre.contains("cama")) {
            return "https://arenaparamascotas.cl/wp-content/uploads/2025/05/Cama-Peluda-Gris-Wonder-Dog.webp";
        }
        if (nombre.contains("comedero")) {
            return "https://dojiw2m9tvv09.cloudfront.net/4953/product/X_smartfeedautomaticpetfeeder3808.jpg?68&time=1763349445";
        }
        if (nombre.contains("bebedero")) {
            return "https://faunasalud.cl/wp-content/uploads/2023/04/5-42.jpg";
        }
        if (nombre.contains("transportadora")) {
            return "https://pethome.cl/imagenes/productos/jaula-transportadora-kennel-para-perros-y-gatos.webp";
        }
        
        // HIGIENE
        if (nombre.contains("shampoo") || nombre.contains("champú")) {
            return "https://dragpharma.cl/wp-content/uploads/2023/05/CANISH-HIPOALERGENICOP.jpg";
        }
        if (nombre.contains("cepillo")) {
            return "https://www.clubdeperrosygatos.cl/wp-content/uploads/2021/11/cepillo-azul.webp";
        }
        if (nombre.contains("toallita")) {
            return "https://www.clubdeperrosygatos.cl/wp-content/uploads/2021/07/Toallas-Clorhexidina1-350x350-1.jpeg";
        }
        
        // MEDICAMENTOS
        if (nombre.contains("antipulgas") || nombre.contains("pulga") || nombre.contains("garrapata")) {
            return "https://rimage.ripley.cl/home.ripley/Attachment/MKP/6509/MPM10001708760/full_image-1.png";
        }
        if (nombre.contains("vitamina")) {
            return "https://dojiw2m9tvv09.cloudfront.net/42482/product/X_apetipet2294.jpg?129&time=1763349767";
        }
        if (nombre.contains("desparasitante")) {
            return "https://www.superzoo.cl/on/demandware.static/-/Sites-SuperZoo-master-catalog/default/dwcde8288c/images/4e903-flovovermic-1.jpg";
        }
        
        // FALLBACK POR CATEGORÍA
        return obtenerImagenPorCategoria(categoria);
    }
    
    /**
     * Imágenes por categoría (fallback)
     */
    private String obtenerImagenPorCategoria(String categoria) {
        return switch (categoria) {
            case "Alimento" -> "https://ferosor.cl/307-large_default/alimento-para-perro-cachorro-fit-formula-saco-10-kg.jpg";
            case "Juguetes" -> "https://cdnx.jumpseller.com/guaudor/image/43385770/resize/1280/1280?1702321041";
            case "Accesorios" -> "https://m.media-amazon.com/images/I/81c5yVZy8EL._AC_SX679_.jpg";
            case "Higiene" -> "https://dragpharma.cl/wp-content/uploads/2023/05/CANISH-HIPOALERGENICOP.jpg";
            case "Medicamentos" -> "https://rimage.ripley.cl/home.ripley/Attachment/MKP/6509/MPM10001708760/full_image-1.png";
            default -> "https://ferosor.cl/307-large_default/alimento-para-perro-cachorro-fit-formula-saco-10-kg.jpg";
        };
    }
}
