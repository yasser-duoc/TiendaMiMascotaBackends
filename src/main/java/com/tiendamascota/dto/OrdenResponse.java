package com.tiendamascota.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.tiendamascota.model.OrdenItem;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de creación de orden, incluye resumen y items de la orden", example = "{\"id\":1,\"numeroOrden\":\"ORD-20251117-001\",\"fecha\":\"2025-11-17T20:00:00\",\"estado\":\"completada\",\"total\":11980,\"mensaje\":\"Orden creada exitosamente\",\"items\":[{\"productoId\":1,\"productoNombre\":\"Pelota de Goma\",\"cantidad\":1,\"precioUnitario\":5990,\"subtotal\":5990}]}")
public class OrdenResponse {
    
    @Schema(description = "ID de la orden", example = "1")
    private Long id;
    @Schema(description = "Número único de orden", example = "ORD-20251117-001")
    private String numeroOrden;
    @Schema(description = "Fecha de creación")
    private LocalDateTime fecha;
    @Schema(description = "Estado de la orden", example = "completada")
    private String estado;
    @Schema(description = "Total de la orden", example = "11980")
    private Integer total;
    @Schema(description = "Mensaje informativo", example = "Orden creada exitosamente")
    private String mensaje;
    @Schema(description = "Items incluidos en la orden")
    private List<OrdenItem> items;
    
    public OrdenResponse() {
    }
    
    public OrdenResponse(Long id, String numeroOrden, LocalDateTime fecha, String estado, 
                        Integer total, String mensaje) {
        this.id = id;
        this.numeroOrden = numeroOrden;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroOrden() {
        return numeroOrden;
    }
    
    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Integer getTotal() {
        return total;
    }
    
    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public List<OrdenItem> getItems() {
        return items;
    }
    
    public void setItems(List<OrdenItem> items) {
        this.items = items;
    }
}
