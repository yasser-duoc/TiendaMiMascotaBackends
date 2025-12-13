package com.tiendamascota.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendamascota.model.Producto;
import com.tiendamascota.repository.ProductoRepository;

@RestController
public class HealthController {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "UP");
        body.put("timestamp", Instant.now().toString());

        try {
            long count = productoRepository.count();
            body.put("db", "UP");
            body.put("productCount", count);
            return ResponseEntity.ok(body);
        } catch (Exception ex) {
            body.put("db", "DOWN");
            body.put("error", ex.getMessage());
            body.put("status", "DEGRADED");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
        }
    }
    
    /**
     * Endpoint de diagnóstico para verificar serialización de productos con Base64
     */
    @GetMapping("/health/json-test")
    public ResponseEntity<Map<String, Object>> jsonTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", Instant.now().toString());
        
        try {
            // Obtener configuración de Jackson
            var factory = objectMapper.getFactory();
            var readConstraints = factory.streamReadConstraints();
            var writeConstraints = factory.streamWriteConstraints();
            
            result.put("jackson_maxStringLength", readConstraints.getMaxStringLength());
            result.put("jackson_maxNestingDepth_read", readConstraints.getMaxNestingDepth());
            result.put("jackson_maxNestingDepth_write", writeConstraints.getMaxNestingDepth());
            
            // Contar productos y sus tamaños de imagen
            List<Producto> productos = productoRepository.findAll();
            result.put("totalProductos", productos.size());
            
            Map<String, Object> productosInfo = new HashMap<>();
            int productosConBase64 = 0;
            long totalBase64Length = 0;
            
            for (Producto p : productos) {
                Map<String, Object> pInfo = new HashMap<>();
                pInfo.put("nombre", p.getNombre());
                
                if (p.getImageUrl() != null) {
                    int len = p.getImageUrl().length();
                    pInfo.put("imageUrl_length", len);
                    pInfo.put("isBase64", p.getImageUrl().startsWith("data:"));
                    
                    if (p.getImageUrl().startsWith("data:")) {
                        productosConBase64++;
                        totalBase64Length += len;
                    }
                } else {
                    pInfo.put("imageUrl_length", 0);
                    pInfo.put("isBase64", false);
                }
                
                productosInfo.put("producto_" + p.getId(), pInfo);
            }
            
            result.put("productosConBase64", productosConBase64);
            result.put("totalBase64Length", totalBase64Length);
            result.put("productos", productosInfo);
            
            // Intentar serializar manualmente para verificar
            try {
                String json = objectMapper.writeValueAsString(productos);
                result.put("serializacionExitosa", true);
                result.put("jsonLength", json.length());
                result.put("status", "OK");
            } catch (Exception serEx) {
                result.put("serializacionExitosa", false);
                result.put("serializacionError", serEx.getMessage());
                result.put("status", "SERIALIZATION_ERROR");
            }
            
            return ResponseEntity.ok(result);
            
        } catch (Exception ex) {
            result.put("status", "ERROR");
            result.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
