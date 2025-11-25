package com.tiendamascota.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("producto_id")
    private Integer id;
    
    @Column(nullable = false)
    @JsonProperty("producto_nombre")
    @JsonAlias({"name", "nombre", "producto_nombre"})
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Integer price;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(nullable = false)
    private String category;
    
    private String imageUrl;
    
    // Campos para React (Web)
    private Boolean destacado = false;
    private Double valoracion = 0.0;
    private Integer precioAnterior;
    
    // Campos específicos por categoría
    
    // Juguete
    private String material;
    private String tamano;
    
    // Higiene
    private String tipoHigiene;
    private String fragancia;
    
    // Alimento
    private String marca;
    private String tipo;
    private Double peso;
    
    // Accesorios
    private String tipoAccesorio;
    
    // Getters y Setters necesarios para ProductoController
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public Integer getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Integer precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getTipoHigiene() {
        return tipoHigiene;
    }

    public void setTipoHigiene(String tipoHigiene) {
        this.tipoHigiene = tipoHigiene;
    }

    public String getFragancia() {
        return fragancia;
    }

    public void setFragancia(String fragancia) {
        this.fragancia = fragancia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getTipoAccesorio() {
        return tipoAccesorio;
    }

    public void setTipoAccesorio(String tipoAccesorio) {
        this.tipoAccesorio = tipoAccesorio;
    }
}
