package com.tiendamascota.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CrearOrdenRequest {
    
    @JsonProperty("usuario_id")
    private Long usuarioId;
    
    @JsonProperty("es_invitado")
    private Boolean esInvitado;
    
    @JsonProperty("datos_envio")
    private DatosEnvio datosEnvio;
    
    private List<ItemOrden> items;
    private Integer subtotal;
    private Integer total;
    
    public CrearOrdenRequest() {
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
    
    public DatosEnvio getDatosEnvio() {
        return datosEnvio;
    }
    
    public void setDatosEnvio(DatosEnvio datosEnvio) {
        this.datosEnvio = datosEnvio;
    }
    
    public List<ItemOrden> getItems() {
        return items;
    }
    
    public void setItems(List<ItemOrden> items) {
        this.items = items;
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
    
    public static class DatosEnvio {
        @JsonProperty("nombre_completo")
        @JsonAlias({"nombre"})
        private String nombre;
        
        private String email;
        private String telefono;
        private String direccion;
        private String ciudad;
        private String region;
        
        @JsonProperty("codigo_postal")
        private String codigoPostal;
        
        @JsonProperty("metodo_pago")
        private String metodoPago;
        
        private String pais;
        
        public DatosEnvio() {
        }
        
        // Getters y Setters
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getTelefono() {
            return telefono;
        }
        
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        
        public String getDireccion() {
            return direccion;
        }
        
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        
        public String getCiudad() {
            return ciudad;
        }
        
        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }
        
        public String getRegion() {
            return region;
        }
        
        public void setRegion(String region) {
            this.region = region;
        }
        
        public String getCodigoPostal() {
            return codigoPostal;
        }
        
        public void setCodigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
        }
        
        public String getMetodoPago() {
            return metodoPago;
        }
        
        public void setMetodoPago(String metodoPago) {
            this.metodoPago = metodoPago;
        }
        
        public String getPais() {
            return pais;
        }
        
        public void setPais(String pais) {
            this.pais = pais;
        }
    }
    
    public static class ItemOrden {
        @JsonProperty("producto_id")
        @JsonAlias({"productoId"})
        private Integer productoId;
        
        private Integer cantidad;
        
        @JsonProperty("precio_unitario")
        @JsonAlias({"precioUnitario"})
        private Integer precioUnitario;
        
        public ItemOrden() {
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
        
        public Integer getPrecioUnitario() {
            return precioUnitario;
        }
        
        public void setPrecioUnitario(Integer precioUnitario) {
            this.precioUnitario = precioUnitario;
        }
    }
}