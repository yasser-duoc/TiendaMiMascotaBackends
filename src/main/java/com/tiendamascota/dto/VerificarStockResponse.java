package com.tiendamascota.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de verificación de stock. `disponible` indica si todos los items están disponibles.", example = "{\"disponible\":false,\"productosAgotados\":[{\"productoId\":2,\"nombre\":\"Pelota de Goma\",\"cantidadSolicitada\":3,\"stockDisponible\":1,\"mensaje\":\"Solo quedan 1 unidades disponibles\"}]}")
public class VerificarStockResponse {
    
    @Schema(description = "Indica si todos los productos están disponibles")
    private Boolean disponible;
    @Schema(description = "Listado de productos con problema de stock")
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
        @Schema(description = "ID del producto", example = "2")
        private Integer productoId;
        @Schema(description = "Nombre del producto", example = "Pelota de Goma")
        private String nombre;
        @Schema(description = "Cantidad solicitada", example = "3")
        private Integer cantidadSolicitada;
        @Schema(description = "Stock disponible actualmente", example = "1")
        private Integer stockDisponible;
        @Schema(description = "Mensaje explicativo del problema de stock", example = "Solo quedan 1 unidades disponibles")
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
