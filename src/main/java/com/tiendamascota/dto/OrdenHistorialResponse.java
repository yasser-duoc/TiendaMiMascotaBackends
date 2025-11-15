package com.tiendamascota.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenHistorialResponse {
    
    private Long id;
    private String numeroOrden;
    private LocalDateTime fecha;
    private String estado;
    private Integer total;
    private Integer subtotal;
    private Boolean esInvitado;
    private Long usuarioId;
    private DatosEnvioResponse datosEnvio;
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
        private String nombre;
        private String email;
        private String telefono;
        private String direccion;
        private String ciudad;
        private String region;
        private String codigoPostal;
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
        private Integer productoId;
        private String nombre;
        private Integer cantidad;
        private Integer precioUnitario;
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
