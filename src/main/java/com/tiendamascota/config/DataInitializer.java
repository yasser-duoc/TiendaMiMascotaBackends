package com.tiendamascota.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tiendamascota.repository.ProductoRepository;

@Configuration
public class DataInitializer {
    
    @Bean
    @SuppressWarnings("all")
    public CommandLineRunner initDatabase(ProductoRepository repo) {
        return args -> {
            // Los datos se cargarán vacíos, puedes agregarlos desde React o Android
        };
    }
}
