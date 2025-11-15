package com.tiendamascota.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiendamascota.dto.CrearOrdenRequest;
import com.tiendamascota.dto.OrdenResponse;
import com.tiendamascota.dto.VerificarStockRequest;
import com.tiendamascota.dto.VerificarStockResponse;
import com.tiendamascota.model.Orden;
import com.tiendamascota.model.OrdenItem;
import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.OrdenRepository;
import com.tiendamascota.repository.ProductoRepository;

@Service
public class OrdenService {
    
    @Autowired
    private OrdenRepository ordenRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    /**
     * Verificar disponibilidad de stock
     */
    public VerificarStockResponse verificarStock(VerificarStockRequest request) {
        VerificarStockResponse response = new VerificarStockResponse();
        List<VerificarStockResponse.ProductoAgotado> agotados = new ArrayList<>();
        
        for (VerificarStockRequest.ItemStock item : request.getItems()) {
            Integer productoId = item.getProductoId();
            if (productoId == null) {
                agotados.add(new VerificarStockResponse.ProductoAgotado(
                    null,
                    "ID de producto inválido",
                    item.getCantidad(),
                    0,
                    "El ID del producto no puede ser nulo"
                ));
                continue;
            }
            
            Producto producto = productoRepository.findById(productoId).orElse(null);
            
            if (producto == null) {
                agotados.add(new VerificarStockResponse.ProductoAgotado(
                    item.getProductoId(),
                    "Producto no encontrado",
                    item.getCantidad(),
                    0,
                    "El producto no existe"
                ));
                continue;
            }
            
            Integer stock = producto.getStock();
            Integer cantidadSolicitada = item.getCantidad();
            if (stock != null && cantidadSolicitada != null && stock < cantidadSolicitada) {
                String mensaje = producto.getStock() == 0 
                    ? "Producto agotado" 
                    : "Solo quedan " + producto.getStock() + " unidades disponibles";
                    
                agotados.add(new VerificarStockResponse.ProductoAgotado(
                    item.getProductoId(),
                    producto.getNombre(),
                    cantidadSolicitada,
                    stock,
                    mensaje
                ));
            }
        }
        
        response.setDisponible(agotados.isEmpty());
        response.setProductosAgotados(agotados);
        
        return response;
    }
    
    /**
     * Crear orden de compra con actualización de stock
     */
    @Transactional
    public OrdenResponse crearOrden(CrearOrdenRequest request) throws Exception {
        // Verificar stock antes de procesar
        List<VerificarStockRequest.ItemStock> itemsStock = new ArrayList<>();
        for (CrearOrdenRequest.ItemOrden item : request.getItems()) {
            VerificarStockRequest.ItemStock itemStock = new VerificarStockRequest.ItemStock();
            itemStock.setProductoId(item.getProductoId());
            itemStock.setCantidad(item.getCantidad());
            itemsStock.add(itemStock);
        }
        
        VerificarStockRequest stockRequest = new VerificarStockRequest();
        stockRequest.setItems(itemsStock);
        VerificarStockResponse stockResponse = verificarStock(stockRequest);
        
        if (!stockResponse.getDisponible()) {
            throw new Exception("Stock insuficiente para uno o más productos");
        }
        
        // Crear la orden
        Orden orden = new Orden();
        orden.setNumeroOrden(generarNumeroOrden());
        orden.setUsuarioId(request.getUsuarioId());
        orden.setEsInvitado(Boolean.TRUE.equals(request.getEsInvitado()));
        orden.setFecha(LocalDateTime.now());
        orden.setEstado("completada");
        orden.setSubtotal(request.getSubtotal());
        orden.setTotal(request.getTotal());
        
        // Datos de envío
        if (request.getDatosEnvio() != null) {
            orden.setNombreEnvio(request.getDatosEnvio().getNombre());
            orden.setEmailEnvio(request.getDatosEnvio().getEmail());
            orden.setTelefonoEnvio(request.getDatosEnvio().getTelefono());
            orden.setDireccionEnvio(request.getDatosEnvio().getDireccion());
            orden.setCiudadEnvio(request.getDatosEnvio().getCiudad());
            orden.setRegionEnvio(request.getDatosEnvio().getRegion());
            orden.setCodigoPostalEnvio(request.getDatosEnvio().getCodigoPostal());
            orden.setPaisEnvio(request.getDatosEnvio().getPais());
        }
        
        // Crear items y actualizar stock
        for (CrearOrdenRequest.ItemOrden itemRequest : request.getItems()) {
            Integer productoId = itemRequest.getProductoId();
            if (productoId == null) {
                throw new Exception("El ID del producto no puede ser nulo");
            }
            
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new Exception("Producto no encontrado: " + itemRequest.getProductoId()));
            
            // Actualizar stock
            Integer stockActual = producto.getStock();
            Integer cantidadComprada = itemRequest.getCantidad();
            if (stockActual != null && cantidadComprada != null) {
                producto.setStock(stockActual - cantidadComprada);
            }
            productoRepository.save(producto);
            
            // Crear item de la orden
            OrdenItem item = new OrdenItem();
            item.setOrden(orden);
            item.setProductoId(itemRequest.getProductoId());
            item.setProductoNombre(producto.getNombre());
            Integer cantidad = itemRequest.getCantidad();
            Integer precioUnitario = itemRequest.getPrecioUnitario();
            item.setCantidad(cantidad);
            item.setPrecioUnitario(precioUnitario);
            if (cantidad != null && precioUnitario != null) {
                item.setSubtotal(cantidad * precioUnitario);
            }
            
            orden.getItems().add(item);
        }
        
        // Guardar orden
        orden = ordenRepository.save(orden);
        
        // Crear respuesta
        OrdenResponse response = new OrdenResponse();
        response.setId(orden.getId());
        response.setNumeroOrden(orden.getNumeroOrden());
        response.setFecha(orden.getFecha());
        response.setEstado(orden.getEstado());
        response.setTotal(orden.getTotal());
        response.setMensaje("Orden creada exitosamente");
        response.setItems(orden.getItems());
        
        return response;
    }
    
    /**
     * Obtener órdenes de un usuario
     */
    public List<Orden> obtenerOrdenesPorUsuario(Long usuarioId) {
        return ordenRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }
    
    /**
     * Generar número de orden único
     */
    private String generarNumeroOrden() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fecha = now.format(formatter);
        long count = ordenRepository.count() + 1;
        return String.format("ORD-%s-%03d", fecha, count);
    }
}
