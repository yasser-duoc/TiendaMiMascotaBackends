package com.tiendamascota.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiendamascota.config.ImageMappingsProperties;
import com.tiendamascota.dto.ProductoRequest;
import com.tiendamascota.dto.VerificarStockRequest;
import com.tiendamascota.dto.VerificarStockResponse;
import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;
import com.tiendamascota.service.ImagenService;
import com.tiendamascota.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping({"/productos"})
// @CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "API de gestión de productos")
public class ProductoController {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private OrdenService ordenService;
    @Autowired(required = false)
    private ImageMappingsProperties imageMappingsProperties;
    @Autowired(required = false)
    private ImagenService imagenService;
    
    
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<List<Producto>> obtenerTodos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            // Normalizar/Resolver URLs de imagen (devuelven URL absolutas para clientes)
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            productos.forEach(p -> p.setImageUrl(resolveImageUrl(p, baseUrl)));
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            var producto = productoRepository.findById(java.util.Objects.requireNonNull(id));
            
            if (producto.isPresent()) {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                producto.get().setImageUrl(resolveImageUrl(producto.get(), baseUrl));
                return ResponseEntity.ok(producto.get());
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("mensaje", "Producto no encontrado con ID: " + id);
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al obtener el producto: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener productos por categoría", description = "Retorna todos los productos de una categoría específica")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoRepository.findByCategory(categoria);
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        productos.forEach(p -> p.setImageUrl(resolveImageUrl(p, baseUrl)));
        return ResponseEntity.ok(productos);
    }

    /**
     * Normaliza la url de imagen que pueden venir en distintos formatos:
     * - Relativas ("/images/..") -> prefix con baseUrl (por ej. https://miapp)
     * - http:// -> https:// para evitar mixed-content
     * - urls sin esquema -> prefija https://
     */
    private String normalizeImageUrl(String url, String baseUrl) {
        if (url == null || url.isBlank()) return url;
        url = url.trim();

        // Relativas: /images/abc.jpg
        if (url.startsWith("/")) {
            return baseUrl + url;
        }

        // Doble slash //images... -> https://images...
        if (url.startsWith("//")) {
            return "https:" + url;
        }

        // Malformado: https:/images... -> https://images...
        if (url.startsWith("https:/") && !url.startsWith("https://")) {
            return url.replaceFirst("https:/", "https://");
        }

        // Forzar https para evitar mixed-content
        if (url.startsWith("http://")) {
            return url.replaceFirst("http://", "https://");
        }

        // Si no empieza con esquema, asume https
        if (!url.matches("^[a-zA-Z][a-zA-Z0-9+.-]*://.*")) {
            return "https://" + url;
        }

        return url;
    }

    /**
     * Resuelve la URL final de la imagen para un producto.
     * - Si existe un mapeo (por id o por slug de nombre) lo usa.
     * - Si la URL original proviene de Unsplash y no hay mapeo, usa default si está configurado.
     * - En otros casos aplica `normalizeImageUrl`.
     */
    private String resolveImageUrl(Producto producto, String baseUrl) {
        String original = producto.getImageUrl();
        if (original == null) return null;

        // 1) Intentar mapeo por ID
        if (producto.getId() != null && imageMappingsProperties != null && imageMappingsProperties.getMappings() != null) {
            String byId = imageMappingsProperties.getMappings().get(String.valueOf(producto.getId()));
            if (byId != null && !byId.isBlank()) return normalizeImageUrl(byId, baseUrl);
        }

        // 2) Intentar mapeo por slug del nombre
        if (producto.getNombre() != null && imageMappingsProperties != null && imageMappingsProperties.getMappings() != null) {
            String slug = producto.getNombre().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
            String byName = imageMappingsProperties.getMappings().get(slug);
            if (byName != null && !byName.isBlank()) return normalizeImageUrl(byName, baseUrl);
        }

        // 3) Si la URL original es Unsplash, relativa o vacía, intentar generar con ImagenService
        if (original.isBlank() || original.startsWith("/") || original.contains("unsplash.com") || original.contains("images.unsplash")) {
            // Si existe default configurado y queremos forzar su uso en vez de generar, se podría devolver aquí.
            // Primero intentamos generar una URL confiable con ImagenService
            if (imagenService != null) {
                try {
                    String gen = imagenService.generarImagenParaProducto(producto.getNombre(), producto.getCategory());
                    if (gen != null && !gen.isBlank()) {
                        return normalizeImageUrl(gen, baseUrl);
                    }
                } catch (Exception ex) {
                    // Si falla la generación, seguimos al fallback por default abajo
                }
            }

            // Si no se pudo generar, usar default si está configurado
            String def = imageMappingsProperties != null ? imageMappingsProperties.getDefaultUrl() : null;
            if (def != null && !def.isBlank()) return normalizeImageUrl(def, baseUrl);
        }

        // 4) En otros casos, devolver la url original normalizada
        return normalizeImageUrl(original, baseUrl);
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Petición inválida", content = @Content)
    })
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevo = productoRepository.save(java.util.Objects.requireNonNull(producto));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequest productoActualizado) {
        return productoRepository.findById(java.util.Objects.requireNonNull(id))
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescription(productoActualizado.getDescription());
                    producto.setPrice(productoActualizado.getPrice());
                    producto.setStock(productoActualizado.getStock());
                    producto.setCategory(productoActualizado.getCategory());
                    if (productoActualizado.getImageUrl() != null) producto.setImageUrl(productoActualizado.getImageUrl());
                    if (productoActualizado.getDestacado() != null) producto.setDestacado(productoActualizado.getDestacado());
                    if (productoActualizado.getValoracion() != null) producto.setValoracion(productoActualizado.getValoracion());
                    if (productoActualizado.getPrecioAnterior() != null) producto.setPrecioAnterior(productoActualizado.getPrecioAnterior());
                    return ResponseEntity.ok(productoRepository.save(java.util.Objects.requireNonNull(producto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto de la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (productoRepository.existsById(java.util.Objects.requireNonNull(id))) {
            productoRepository.deleteById(java.util.Objects.requireNonNull(id));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/verificar-stock")
    @Operation(summary = "Verificar stock de productos", description = "Verifica si hay stock disponible para una lista de productos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultado de la verificación", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Petición inválida", content = @Content)
    })
    public ResponseEntity<?> verificarStock(@RequestBody VerificarStockRequest request) {
        try {
            VerificarStockResponse response = ordenService.verificarStock(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al verificar stock: " + e.getMessage());
            error.put("disponible", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * ENDPOINT TEMPORAL - Eliminar después de limpiar duplicados
     * Elimina productos duplicados manteniendo solo los primeros 18
     */
    @PostMapping("/limpiar-duplicados")
    @Operation(summary = "Limpiar productos duplicados", description = "TEMPORAL: Elimina productos con ID > 18")
    public ResponseEntity<?> limpiarDuplicados() {
        try {
            long countBefore = productoRepository.count();
            
            // Obtener todos los productos con ID > 18
            List<Producto> duplicados = productoRepository.findAll().stream()
                .filter(p -> p.getId() != null && p.getId() > 18)
                .toList();
            
            // Eliminar duplicados
            if (!duplicados.isEmpty()) {
                productoRepository.deleteAll(duplicados);
            }
            
            long countAfter = productoRepository.count();
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Productos duplicados eliminados exitosamente");
            response.put("productosBefore", countBefore);
            response.put("productosAfter", countAfter);
            response.put("productosEliminados", countBefore - countAfter);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al limpiar duplicados: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // Endpoints de generación de imágenes removidos — se usan URLs directas ahora.
    
    // Endpoint de forzado removido — use `ProductoController` normal para editar imágenes.
}
