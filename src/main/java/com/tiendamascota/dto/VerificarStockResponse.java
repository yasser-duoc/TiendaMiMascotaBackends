package com.tiendamascota.dto;

import java.util.ArrayList;
import java.util.List;

public class VerificarStockResponse {
    
    private Boolean disponible;
    private List<ProductoAgotado> productosAgotados;
    
    public VerificarStockResponse() {
        this.productosAgotados = new ArrayList<>();
    }
    
    public Boolean getDisponible() {
        return disponible;
    }
    
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
    public List<ProductoAgotado> getProductosAgotados() {
        return productosAgotados;
    }
    
    public void setProductosAgotados(List<ProductoAgotado> productosAgotados) {
        this.productosAgotados = productosAgotados;
    }
    
    public static class ProductoAgotado {
        private Integer productoId;
        private String nombre;
        private Integer cantidadSolicitada;
        private Integer stockDisponible;
        private String mensaje;
        
        public ProductoAgotado() {
        }
        
        public ProductoAgotado(Integer productoId, String nombre, Integer cantidadSolicitada, 
                               Integer stockDisponible, String mensaje) {
            this.productoId = productoId;
            this.nombre = nombre;
            this.cantidadSolicitada = cantidadSolicitada;
            this.stockDisponible = stockDisponible;
            this.mensaje = mensaje;
        }
        
        public Integer getProductoId() {
            return productoId;
        }
        
        public void setProductoId(Integer productoId) {
            this.productoId = productoId;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public Integer getCantidadSolicitada() {
            return cantidadSolicitada;
        }
        
        public void setCantidadSolicitada(Integer cantidadSolicitada) {
            this.cantidadSolicitada = cantidadSolicitada;
        }
        
        public Integer getStockDisponible() {
            return stockDisponible;
        }
        
        public void setStockDisponible(Integer stockDisponible) {
            this.stockDisponible = stockDisponible;
        }
        
        public String getMensaje() {
            return mensaje;
        }
        
        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
