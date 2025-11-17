package com.tiendamascota.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalle de una orden en el historial del usuario", example = "{\"id\":1,\"numeroOrden\":\"ORD-20251117-001\",\"fecha\":\"2025-11-17T20:00:00\",\"estado\":\"completada\",\"subtotal\":5990,\"total\":5990,\"esInvitado\":false,\"usuarioId\":1,\"datosEnvio\":{\"nombre\":\"Cliente Uno\",\"email\":\"cliente@correo.com\",\"telefono\":\"987654321\",\"direccion\":\"Av. Siempre Viva 742\",\"ciudad\":\"Santiago\",\"region\":\"RM\",\"codigoPostal\":\"8320000\",\"metodoPago\":\"tarjeta\"},\"productos\":[{\"productoId\":1,\"nombre\":\"Pelota de Goma\",\"cantidad\":1,\"precioUnitario\":5990,\"imagen\":\"/images/pelota.jpg\"}]}")
public class OrdenHistorialResponse {
    
    @Schema(description = "ID de la orden", example = "1")
    private Long id;
    @Schema(description = "Número de orden", example = "ORD-20251117-001")
    private String numeroOrden;
    @Schema(description = "Fecha de la orden")
    private LocalDateTime fecha;
    @Schema(description = "Estado de la orden", example = "completada")
    private String estado;
    @Schema(description = "Total de la orden", example = "11980")
    private Integer total;
    @Schema(description = "Subtotal de la orden", example = "5990")
    private Integer subtotal;
    @Schema(description = "Si la orden fue realizada por invitado", example = "false")
    private Boolean esInvitado;
    @Schema(description = "ID del usuario propietario de la orden", example = "1")
    private Long usuarioId;
    @Schema(description = "Datos de envío")
    private DatosEnvioResponse datosEnvio;
    @Schema(description = "Productos incluidos en la orden")
    private List<ProductoOrdenResponse> productos;
    
    // Constructores
    public OrdenHistorialResponse() {
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
    
    public Integer getTotal() {
        return total;
    }
    
    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public Integer getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
    
    public Boolean getEsInvitado() {
        return esInvitado;
    }
    
    public void setEsInvitado(Boolean esInvitado) {
        this.esInvitado = esInvitado;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public DatosEnvioResponse getDatosEnvio() {
        return datosEnvio;
    }
    
    public void setDatosEnvio(DatosEnvioResponse datosEnvio) {
        this.datosEnvio = datosEnvio;
    }
    
    public List<ProductoOrdenResponse> getProductos() {
        return productos;
    }
    
    public void setProductos(List<ProductoOrdenResponse> productos) {
        this.productos = productos;
    }
    
    // Clase interna para datos de envío
    public static class DatosEnvioResponse {
        @Schema(description = "Nombre de la persona de contacto", example = "Cliente Uno")
        private String nombre;
        @Schema(description = "Email de contacto", example = "cliente@correo.com")
        private String email;
        private String telefono;
        private String direccion;
        private String ciudad;
        private String region;
        @Schema(description = "Código postal", example = "8320000")
        private String codigoPostal;
        @Schema(description = "Método de pago", example = "tarjeta")
        private String metodoPago;
        
        public DatosEnvioResponse() {
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
    }
    
    // Clase interna para productos
    public static class ProductoOrdenResponse {
        @Schema(description = "ID del producto", example = "1")
        private Integer productoId;
        @Schema(description = "Nombre del producto", example = "Pelota de Goma")
        private String nombre;
        @Schema(description = "Cantidad comprada", example = "1")
        private Integer cantidad;
        @Schema(description = "Precio unitario", example = "5990")
        private Integer precioUnitario;
        @Schema(description = "URL / path de la imagen", example = "/images/pelota.jpg")
        private String imagen;
        
        public ProductoOrdenResponse() {
        }
        
        // Getters y Setters
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
        
        public String getImagen() {
            return imagen;
        }
        
        public void setImagen(String imagen) {
            this.imagen = imagen;
        }
    }
}
