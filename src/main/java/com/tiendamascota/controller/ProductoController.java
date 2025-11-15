package com.tiendamascota.controller;

import java.util.List;

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

import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "API de gestión de productos")
public class ProductoController {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos disponibles")
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoRepository.findAll());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico por su ID")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Integer id) {
        return productoRepository.findById(java.util.Objects.requireNonNull(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
}
