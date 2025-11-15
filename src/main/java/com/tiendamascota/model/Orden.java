package com.tiendamascota.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordenes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Orden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String numeroOrden;
    
    private Long usuarioId;
    
    @Column(nullable = false)
    private Boolean esInvitado = false;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(nullable = false)
    private String estado; // completada, pendiente, cancelada
    
    @Column(nullable = false)
    private Integer subtotal;
    
    @Column(nullable = false)
    private Integer total;
    
    // Datos de envío
    private String nombreEnvio;
    private String emailEnvio;
    private String telefonoEnvio;
    private String direccionEnvio;
    private String ciudadEnvio;
    private String regionEnvio;
    private String codigoPostalEnvio;
    private String paisEnvio;
    
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenItem> items = new ArrayList<>();
    
    // Constructores
    public Orden() {
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
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Boolean getEsInvitado() {
        return esInvitado;
    }
    
    public void setEsInvitado(Boolean esInvitado) {
        this.esInvitado = esInvitado;
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
    
    public Integer getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
    
    public Integer getTotal() {
        return total;
    }
    
    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public String getNombreEnvio() {
        return nombreEnvio;
    }
    
    public void setNombreEnvio(String nombreEnvio) {
        this.nombreEnvio = nombreEnvio;
    }
    
    public String getEmailEnvio() {
        return emailEnvio;
    }
    
    public void setEmailEnvio(String emailEnvio) {
        this.emailEnvio = emailEnvio;
    }
    
    public String getTelefonoEnvio() {
        return telefonoEnvio;
    }
    
    public void setTelefonoEnvio(String telefonoEnvio) {
        this.telefonoEnvio = telefonoEnvio;
    }
    
    public String getDireccionEnvio() {
        return direccionEnvio;
    }
    
    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
    
    public String getCiudadEnvio() {
        return ciudadEnvio;
    }
    
    public void setCiudadEnvio(String ciudadEnvio) {
        this.ciudadEnvio = ciudadEnvio;
    }
    
    public String getRegionEnvio() {
        return regionEnvio;
    }
    
    public void setRegionEnvio(String regionEnvio) {
        this.regionEnvio = regionEnvio;
    }
    
    public String getCodigoPostalEnvio() {
        return codigoPostalEnvio;
    }
    
    public void setCodigoPostalEnvio(String codigoPostalEnvio) {
        this.codigoPostalEnvio = codigoPostalEnvio;
    }
    
    public String getPaisEnvio() {
        return paisEnvio;
    }
    
    public void setPaisEnvio(String paisEnvio) {
        this.paisEnvio = paisEnvio;
    }
    
    public List<OrdenItem> getItems() {
        return items;
    }
    
    public void setItems(List<OrdenItem> items) {
        this.items = items;
    }
}
