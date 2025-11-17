package com.tiendamascota.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request para verificar stock de una lista de productos", example = "{\"items\":[{\"productoId\":1,\"cantidad\":1}]}")
public class VerificarStockRequest {
    
    @Schema(description = "Lista de items a verificar")
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
        @Schema(description = "ID del producto", example = "1")
        private Integer productoId;
        @Schema(description = "Cantidad requerida", example = "1")
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
