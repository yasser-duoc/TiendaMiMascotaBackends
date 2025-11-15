package com.tiendamascota.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendamascota.dto.CrearOrdenRequest;
import com.tiendamascota.dto.OrdenResponse;
import com.tiendamascota.dto.VerificarStockRequest;
import com.tiendamascota.dto.VerificarStockResponse;
import com.tiendamascota.model.Orden;
import com.tiendamascota.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "*")
@Tag(name = "Órdenes", description = "API de gestión de órdenes de compra")
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;
    
    /**
     * Verificar disponibilidad de stock
     */
    @PostMapping("/verificar-stock")
    @Operation(summary = "Verificar stock disponible", 
               description = "Verifica si hay stock suficiente para los productos en el carrito")
    public ResponseEntity<VerificarStockResponse> verificarStock(@RequestBody VerificarStockRequest request) {
        try {
            VerificarStockResponse response = ordenService.verificarStock(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * Crear orden de compra
     */
    @PostMapping
    @Operation(summary = "Crear orden de compra", 
               description = "Crea una nueva orden y actualiza el stock de productos")
    public ResponseEntity<?> crearOrden(@RequestBody CrearOrdenRequest request) {
        try {
            OrdenResponse response = ordenService.crearOrden(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Obtener órdenes de un usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener órdenes por usuario", 
               description = "Retorna todas las órdenes de un usuario específico")
    public ResponseEntity<?> obtenerOrdenesPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Orden> ordenes = ordenService.obtenerOrdenesPorUsuario(usuarioId);
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al obtener órdenes: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
