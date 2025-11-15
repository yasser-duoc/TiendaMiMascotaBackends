package com.tiendamascota.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.tiendamascota.model.OrdenItem;

public class OrdenResponse {
    
    private Long id;
    private String numeroOrden;
    private LocalDateTime fecha;
    private String estado;
    private Integer total;
    private String mensaje;
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
