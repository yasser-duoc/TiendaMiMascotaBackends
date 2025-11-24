package com.tiendamascota.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendamascota.dto.CrearOrdenRequest;
import com.tiendamascota.dto.OrdenHistorialResponse;
import com.tiendamascota.dto.OrdenResponse;
import com.tiendamascota.dto.VerificarStockRequest;
import com.tiendamascota.dto.VerificarStockResponse;
import com.tiendamascota.model.Orden;
import com.tiendamascota.repository.OrdenRepository;
import com.tiendamascota.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/ordenes")
// @CrossOrigin(origins = "*")
@Tag(name = "Órdenes", description = "API de gestión de órdenes de compra")
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;
    @Autowired
    private OrdenRepository ordenRepository;
    
    /**
     * Verificar disponibilidad de stock
     */
    @PostMapping("/verificar-stock")
    @Operation(summary = "Verificar stock disponible", description = "Verifica si hay stock suficiente para los productos en el carrito")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultado de la verificación",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"disponible\":true, \"productosAgotados\":[]}"))),
        @ApiResponse(responseCode = "400", description = "Petición inválida", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<VerificarStockResponse> verificarStock(@org.springframework.web.bind.annotation.RequestBody VerificarStockRequest request) {
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
    @Operation(summary = "Crear orden de compra", description = "Crea una nueva orden y actualiza el stock de productos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Orden creada con éxito",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"mensaje\":\"Orden creada exitosamente\", \"id\":1, \"total\":11980}"))),
        @ApiResponse(responseCode = "400", description = "Error de validación o stock insuficiente",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"mensaje\":\"Stock insuficiente para uno o más productos\", \"status\":400}"))),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<?> crearOrden(@org.springframework.web.bind.annotation.RequestBody CrearOrdenRequest request) {
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
     * Obtener historial completo de órdenes de un usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener historial de órdenes por usuario", description = "Retorna todas las órdenes con detalles completos de un usuario específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de órdenes del usuario", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<?> obtenerHistorialOrdenes(@PathVariable long usuarioId) {
        try {
            List<OrdenHistorialResponse> ordenes = ordenService.obtenerHistorialOrdenes(usuarioId);
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al obtener órdenes: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Listar todas las órdenes (CRUD - Read All)
     */
    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Retorna todas las órdenes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de órdenes", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<?> listarTodas() {
        try {
            List<Orden> ordenes = ordenRepository.findAllWithItems();
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al listar órdenes: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener orden por ID (CRUD - Read)
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID", description = "Retorna una orden por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orden encontrada", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable long id) {
        try {
            java.util.Optional<Orden> opt = ordenRepository.findByIdWithItems(id);
            if (opt.isPresent()) {
                return ResponseEntity.ok(opt.get());
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("mensaje", "Orden no encontrada con ID: " + id);
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al obtener orden: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Actualizar orden (CRUD - Update)
     */
    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    @Operation(summary = "Actualizar orden", description = "Actualiza una orden existente")
    public ResponseEntity<?> actualizarOrden(@PathVariable long id, @RequestBody Orden ordenActualizada) {
        try {
            java.util.Optional<Orden> opt = ordenRepository.findById(id);
            if (opt.isPresent()) {
                Orden orden = opt.get();
                // Actualizar campos permitidos
                orden.setEstado(ordenActualizada.getEstado());
                orden.setNombreEnvio(ordenActualizada.getNombreEnvio());
                orden.setEmailEnvio(ordenActualizada.getEmailEnvio());
                orden.setTelefonoEnvio(ordenActualizada.getTelefonoEnvio());
                orden.setDireccionEnvio(ordenActualizada.getDireccionEnvio());
                orden.setCiudadEnvio(ordenActualizada.getCiudadEnvio());
                orden.setRegionEnvio(ordenActualizada.getRegionEnvio());
                orden.setCodigoPostalEnvio(ordenActualizada.getCodigoPostalEnvio());
                orden.setPaisEnvio(ordenActualizada.getPaisEnvio());
                orden.setMetodoPago(ordenActualizada.getMetodoPago());
                // No alteramos items ni usuarioId en esta ruta por seguridad
                Orden guardada = ordenRepository.save(orden);
                return ResponseEntity.ok(guardada);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("mensaje", "Orden no encontrada con ID: " + id);
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al actualizar orden: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Eliminar orden (CRUD - Delete)
     */
    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    @Operation(summary = "Eliminar orden", description = "Elimina una orden por su ID")
    public ResponseEntity<?> eliminarOrden(@PathVariable long id) {
        try {
            if (ordenRepository.existsById(id)) {
                ordenRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("mensaje", "Orden no encontrada con ID: " + id);
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error al eliminar orden: " + e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
