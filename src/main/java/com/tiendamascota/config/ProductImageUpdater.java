package com.tiendamascota.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;
import com.tiendamascota.service.ImagenService;

@Configuration
public class ProductImageUpdater {

    /**
     * Runner opcional que persiste nuevas URLs de imagen en la BD. Se activa
     * poniendo la propiedad `image.update-on-startup=true` (por defecto false).
     * Útil para ambientes donde no puedas ejecutar SQL manualmente (Railway).
     */
    @Bean
    public CommandLineRunner updateProductImagesOnStartup(ProductoRepository repo,
            ImagenService imagenService,
            ImageMappingsProperties imageMappingsProperties) {

        return args -> {
            boolean enabled = false;
            // Leer propiedad de sistema / env var: IMAGE_UPDATE_ON_STARTUP
            String flag = System.getProperty("image.update-on-startup");
            if (!StringUtils.hasText(flag)) {
                flag = System.getenv("IMAGE_UPDATE_ON_STARTUP");
            }
            if (StringUtils.hasText(flag)) {
                enabled = flag.equalsIgnoreCase("true") || flag.equals("1");
            }

            if (!enabled) {
                System.out.println("ProductImageUpdater: deshabilitado (image.update-on-startup=false)");
                return;
            }

            System.out.println("ProductImageUpdater: iniciado - actualizando imágenes de productos...");
            List<Producto> productos = repo.findAll();
            int updated = 0;

            for (Producto p : productos) {
                try {
                    String original = p.getImageUrl();
                    boolean needsUpdate = original == null || original.isBlank()
                            || original.startsWith("/")
                            || original.contains("unsplash.com")
                            || original.contains("images.unsplash");

                    if (!needsUpdate) continue;

                    // Intentar mapear por ID
                    String mapped = null;
                    if (p.getId() != null && imageMappingsProperties.getMappings() != null) {
                        mapped = imageMappingsProperties.getMappings().get(String.valueOf(p.getId()));
                    }

                    // Intentar mapear por slug (nombre)
                    if (!StringUtils.hasText(mapped) && p.getNombre() != null && imageMappingsProperties.getMappings() != null) {
                        String slug = p.getNombre().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
                        mapped = imageMappingsProperties.getMappings().get(slug);
                    }

                    // Si no hay mapping, generar por nombre
                    String newUrl = mapped;
                    if (!StringUtils.hasText(newUrl) && imagenService != null) {
                        newUrl = imagenService.generarImagenParaProducto(p.getNombre(), p.getCategory());
                    }

                    // Si sigue sin URL, usar default
                    if (!StringUtils.hasText(newUrl)) {
                        newUrl = imageMappingsProperties.getDefaultUrl();
                    }

                    if (StringUtils.hasText(newUrl) && !newUrl.equals(original)) {
                        p.setImageUrl(newUrl);
                        repo.save(p);
                        updated++;
                        System.out.println("Actualizada imagen producto id=" + p.getId() + " -> " + newUrl);
                    }

                } catch (Exception ex) {
                    System.err.println("Error actualizando producto id=" + p.getId() + ": " + ex.getMessage());
                }
            }

            System.out.println("ProductImageUpdater: finalizado. Productos actualizados: " + updated);
        };
    }
}
