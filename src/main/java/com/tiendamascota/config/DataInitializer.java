package com.tiendamascota.config;

import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(ProductoRepository repo) {
        return args -> {
            Producto p1 = new Producto();
            p1.setCodigo("COM001");
            p1.setNombre("Comida para Perros Premium");
            p1.setDescripcion("Alimento balanceado para perros adultos");
            p1.setPrecio(15000);
            p1.setStock(50);
            p1.setStockCritico(10);
            p1.setCategoria("Comida");
            p1.setImagen("assets/img/Comida.jpg");
            repo.save(p1);
            
            Producto p2 = new Producto();
            p2.setCodigo("CAM001");
            p2.setNombre("Cama para Mascotas");
            p2.setDescripcion("Cama cómoda y resistente para mascotas");
            p2.setPrecio(25000);
            p2.setStock(20);
            p2.setStockCritico(5);
            p2.setCategoria("Accesorios");
            p2.setImagen("assets/img/cama2.png");
            repo.save(p2);
            
            Producto p3 = new Producto();
            p3.setCodigo("JUG001");
            p3.setNombre("Juguetes Variados");
            p3.setDescripcion("Set de juguetes para entretenimiento");
            p3.setPrecio(8000);
            p3.setStock(30);
            p3.setStockCritico(8);
            p3.setCategoria("Juguetes");
            p3.setImagen("assets/img/jugetes.png");
            repo.save(p3);
            
            Producto p4 = new Producto();
            p4.setCodigo("HIG001");
            p4.setNombre("Productos de Higiene");
            p4.setDescripcion("Kit completo de higiene para mascotas");
            p4.setPrecio(12000);
            p4.setStock(15);
            p4.setStockCritico(3);
            p4.setCategoria("Higiene");
            p4.setImagen("assets/img/higiene.png");
            repo.save(p4);
            
            Producto p5 = new Producto();
            p5.setCodigo("ACC001");
            p5.setNombre("Accesorios para Mascotas");
            p5.setDescripcion("Variedad de accesorios útiles para el día a día con tu mascota");
            p5.setPrecio(18000);
            p5.setStock(25);
            p5.setStockCritico(5);
            p5.setCategoria("Accesorios");
            p5.setImagen("assets/img/accesorios.png");
            repo.save(p5);
            
            Producto p6 = new Producto();
            p6.setCodigo("SAL001");
            p6.setNombre("Productos de Salud");
            p6.setDescripcion("Vitaminas y suplementos para mantener la salud de tu mascota");
            p6.setPrecio(22000);
            p6.setStock(18);
            p6.setStockCritico(4);
            p6.setCategoria("Salud");
            p6.setImagen("assets/img/salud.png");
            repo.save(p6);
            
            Producto p7 = new Producto();
            p7.setCodigo("PROD001");
            p7.setNombre("Producto Especial");
            p7.setDescripcion("Producto premium para el cuidado especial de mascotas");
            p7.setPrecio(35000);
            p7.setStock(10);
            p7.setStockCritico(2);
            p7.setCategoria("Premium");
            p7.setImagen("assets/img/prod.png");
            repo.save(p7);
        };
    }
}
