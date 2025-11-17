package com.tiendamascota.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendamascota.dto.VerificarStockRequest;
import com.tiendamascota.dto.VerificarStockResponse;
import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;
import com.tiendamascota.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "API de gestión de productos")
public class ProductoController {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private OrdenService ordenService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos")
    public ResponseEntity<List<Producto>> obtenerTodos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico por su ID")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            var producto = productoRepository.findById(java.util.Objects.requireNonNull(id));
            
            if (producto.isPresent()) {
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
        return ResponseEntity.ok(productoRepository.findByCategory(categoria));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en la base de datos")
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevo = productoRepository.save(java.util.Objects.requireNonNull(producto));
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(java.util.Objects.requireNonNull(id))
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescription(productoActualizado.getDescription());
                    producto.setPrice(productoActualizado.getPrice());
                    producto.setStock(productoActualizado.getStock());
                    producto.setCategory(productoActualizado.getCategory());
                    producto.setImageUrl(productoActualizado.getImageUrl());
                    producto.setDestacado(productoActualizado.getDestacado());
                    producto.setValoracion(productoActualizado.getValoracion());
                    return ResponseEntity.ok(productoRepository.save(java.util.Objects.requireNonNull(producto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto de la base de datos")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (productoRepository.existsById(java.util.Objects.requireNonNull(id))) {
            productoRepository.deleteById(java.util.Objects.requireNonNull(id));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/verificar-stock")
    @Operation(summary = "Verificar stock de productos", description = "Verifica si hay stock disponible para una lista de productos")
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
            productoRepository.deleteAll(duplicados);
            
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
}
