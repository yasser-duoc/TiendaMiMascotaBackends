package com.tiendamascota.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductoRequest {

    @NotBlank
    @JsonAlias({"name", "nombre", "producto_nombre"})
    private String nombre;

    private String description;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotBlank
    private String category;

    private String imageUrl;
    private Boolean destacado;
    private Double valoracion;
    private Integer precioAnterior;

    public ProductoRequest() {}

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
}
