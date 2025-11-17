package com.tiendamascota.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request para crear una orden. Acepta snake_case y camelCase en campos comunes.", example = "{\"usuario_id\":1,\"es_invitado\":false,\"datos_envio\":{\"nombre_completo\":\"Cliente Uno\",\"email\":\"cliente@correo.com\",\"telefono\":\"987654321\",\"direccion\":\"Av. Siempre Viva 742\",\"ciudad\":\"Santiago\",\"region\":\"RM\",\"codigo_postal\":\"8320000\",\"pais\":\"Chile\",\"metodo_pago\":\"tarjeta\"},\"items\":[{\"producto_id\":1,\"cantidad\":1,\"precio_unitario\":5990}],\"subtotal\":5990,\"total\":5990}")
public class CrearOrdenRequest {
    
    @JsonProperty("usuario_id")
    @Schema(description = "ID del usuario que realiza la compra", example = "1")
    private Long usuarioId;
    
    @JsonProperty("es_invitado")
    @Schema(description = "Indica si el comprador es invitado (sin cuenta)", example = "false")
    private Boolean esInvitado;
    
    @JsonProperty("datos_envio")
    @Schema(description = "Información de envío del pedido")
    private DatosEnvio datosEnvio;
    
    @Schema(description = "Lista de items de la orden")
    private List<ItemOrden> items;

    @Schema(description = "Subtotal (suma de sub-totales de items)", example = "5990")
    private Integer subtotal;

    @Schema(description = "Total final de la orden", example = "5990")
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
        @Schema(description = "Nombre completo del receptor", example = "Cliente Uno")
        private String nombre;
        
        @Schema(description = "Email de contacto", example = "cliente@correo.com")
        private String email;
        private String telefono;
        private String direccion;
        private String ciudad;
        private String region;
        
        @JsonProperty("codigo_postal")
        @Schema(description = "Código postal", example = "8320000")
        private String codigoPostal;
        
        @JsonProperty("metodo_pago")
        @Schema(description = "Método de pago (tarjeta, efectivo, transferencia)", example = "tarjeta")
        private String metodoPago;
        
        @Schema(description = "País", example = "Chile")
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
        @Schema(description = "ID del producto", example = "1")
        private Integer productoId;
        
        @Schema(description = "Cantidad solicitada", example = "1")
        private Integer cantidad;
        
        @JsonProperty("precio_unitario")
        @JsonAlias({"precioUnitario"})
        @Schema(description = "Precio unitario en centavos (o la unidad monetaria usada)", example = "5990")
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