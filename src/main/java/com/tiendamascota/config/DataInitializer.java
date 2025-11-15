package com.tiendamascota.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;

@Configuration
public class DataInitializer {
    
    @Bean
    @SuppressWarnings("all")
    public CommandLineRunner initDatabase(ProductoRepository repo) {
        return args -> {
            // Alimento
            repo.save(crearProducto("Alimento Premium para Perros", 25990, "Alimento", 
                "Alimento completo y balanceado con ingredientes naturales para perros adultos de todas las razas.", 
                "/images/alimento-perros.jpg", 50, true, 4.5, 29990, "PetPro", 5.0));
            
            repo.save(crearProducto("Alimento Premium para Gatos", 22990, "Alimento", 
                "Alimento especialmente formulado para gatos adultos con proteínas de alta calidad.", 
                "/images/alimento-gatos.jpg", 45, true, 4.7, 26990, "PetPro", 4.0));
            
            repo.save(crearProducto("Snacks Naturales para Perros", 8990, "Alimento", 
                "Premios naturales sin colorantes ni saborizantes artificiales.", 
                "/images/snacks-perros.jpg", 100, false, 4.3, null, "NaturalPet", 0.5));
            
            // Juguetes
            repo.save(crearProducto("Pelota de Goma Resistente", 5990, "Juguetes", 
                "Pelota de goma duradera, ideal para juegos de lanzar y buscar.", 
                "/images/pelota-goma.jpg", 80, true, 4.6, 7990, null, null));
            
            repo.save(crearProducto("Juguete Interactivo para Gatos", 12990, "Juguetes", 
                "Juguete con plumas y sonido para estimular el instinto de caza de tu gato.", 
                "/images/juguete-gatos.jpg", 60, false, 4.4, null, null, null));
            
            repo.save(crearProducto("Cuerda de Juego para Perros", 6990, "Juguetes", 
                "Cuerda resistente perfecta para juegos de tira y afloja.", 
                "/images/cuerda-perros.jpg", 70, false, 4.2, null, null, null));
            
            // Accesorios
            repo.save(crearProducto("Collar Ajustable para Perros", 9990, "Accesorios", 
                "Collar ajustable con cierre de seguridad y anilla para correa.", 
                "/images/collar-perros.jpg", 90, false, 4.5, null, null, null));
            
            repo.save(crearProducto("Correa Retráctil 5m", 15990, "Accesorios", 
                "Correa retráctil de 5 metros con sistema de freno y bloqueo.", 
                "/images/correa-retractil.jpg", 55, true, 4.7, 18990, null, null));
            
            repo.save(crearProducto("Cama Acolchada para Mascotas", 29990, "Accesorios", 
                "Cama suave y acolchada con funda lavable para máximo confort.", 
                "/images/cama-mascotas.jpg", 35, true, 4.8, 34990, null, null));
            
            repo.save(crearProducto("Comedero Automático", 45990, "Accesorios", 
                "Comedero automático programable con dispensador de porciones.", 
                "/images/comedero-automatico.jpg", 25, true, 4.9, 52990, null, null));
            
            repo.save(crearProducto("Bebedero Fuente de Agua", 32990, "Accesorios", 
                "Bebedero tipo fuente con filtro de carbón y flujo continuo de agua.", 
                "/images/bebedero-fuente.jpg", 30, true, 4.7, 38990, null, null));
            
            repo.save(crearProducto("Transportadora para Mascotas", 39990, "Accesorios", 
                "Transportadora resistente con ventilación y puerta de seguridad.", 
                "/images/transportadora.jpg", 20, false, 4.6, null, null, null));
            
            // Higiene
            repo.save(crearProducto("Shampoo Hipoalergénico", 11990, "Higiene", 
                "Shampoo suave especial para pieles sensibles, sin parabenos.", 
                "/images/shampoo-mascotas.jpg", 65, false, 4.4, null, null, null));
            
            repo.save(crearProducto("Cepillo Desenredante", 8990, "Higiene", 
                "Cepillo con cerdas suaves para desenredar y dar brillo al pelaje.", 
                "/images/cepillo-mascotas.jpg", 75, false, 4.3, null, null, null));
            
            repo.save(crearProducto("Toallitas Húmedas para Mascotas", 5990, "Higiene", 
                "Toallitas húmedas con aloe vera para limpieza rápida y fresca.", 
                "/images/toallitas-mascotas.jpg", 120, true, 4.6, 6990, null, null));
            
            // Medicamentos
            repo.save(crearProducto("Antipulgas y Garrapatas", 18990, "Medicamentos", 
                "Tratamiento efectivo contra pulgas y garrapatas de larga duración.", 
                "/images/antipulgas.jpg", 40, true, 4.8, 22990, null, null));
            
            repo.save(crearProducto("Vitaminas para Mascotas", 14990, "Medicamentos", 
                "Suplemento vitamínico completo para reforzar el sistema inmune.", 
                "/images/vitaminas-mascotas.jpg", 50, false, 4.5, null, null, null));
            
            repo.save(crearProducto("Desparasitante Interno", 12990, "Medicamentos", 
                "Tabletas desparasitantes de amplio espectro para perros y gatos.", 
                "/images/desparasitante.jpg", 45, false, 4.6, null, null, null));
            
            System.out.println("✅ 18 productos cargados en la BD");
        };
    }
    
    private Producto crearProducto(String nombre, Integer price, String category, 
                                   String description, String imageUrl, Integer stock, 
                                   Boolean destacado, Double valoracion, Integer precioAnterior, 
                                   String marca, Double peso) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrice(price);
        p.setCategory(category);
        p.setDescription(description);
        p.setImageUrl(imageUrl);
        p.setStock(stock);
        p.setDestacado(destacado != null ? destacado : false);
        p.setValoracion(valoracion);
        p.setPrecioAnterior(precioAnterior);
        p.setMarca(marca);
        p.setPeso(peso);
        return p;
    }
}
