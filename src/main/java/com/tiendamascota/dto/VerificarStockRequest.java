package com.tiendamascota.dto;

import java.util.List;

public class VerificarStockRequest {
    
    private List<ItemStock> items;
    
    public VerificarStockRequest() {
    }
    
    public List<ItemStock> getItems() {
        return items;
    }
    
    public void setItems(List<ItemStock> items) {
        this.items = items;
    }
    
    public static class ItemStock {
        private Integer productoId;
        private Integer cantidad;
        
        public ItemStock() {
        }
        
        public Integer getProductoId() {
            return productoId;
        }
        
        public void setProductoId(Integer productoId) {
            this.productoId = productoId;
        }
        
        public Integer getCantidad() {
            return cantidad;
        }
        
        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
}
