package com.tiendamascota.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orden_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdenItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonIgnore
    private Orden orden;
    
    @Column(nullable = false)
    private Integer productoId;
    
    private String productoNombre;
    
    // @Lob permite almacenar imágenes base64 largas
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String productoImagen;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Integer precioUnitario;
    
    @Column(nullable = false)
    private Integer subtotal;
    
    // Constructores
    public OrdenItem() {
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Orden getOrden() {
        return orden;
    }
    
    public void setOrden(Orden orden) {
        this.orden = orden;
    }
    
    public Integer getProductoId() {
        return productoId;
    }
    
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
    
    public String getProductoNombre() {
        return productoNombre;
    }
    
    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }
    
    public String getProductoImagen() {
        return productoImagen;
    }
    
    public void setProductoImagen(String productoImagen) {
        this.productoImagen = productoImagen;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Integer getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public Integer getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
