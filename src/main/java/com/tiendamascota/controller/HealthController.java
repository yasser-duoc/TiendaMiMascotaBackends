package com.tiendamascota.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    
    /**
     * DEBUG: Endpoint que serializa manualmente y devuelve el JSON raw
     * para comparar con lo que recibe Android
     */
    @GetMapping(value = "/health/productos-raw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> productosRaw() {
        try {
            List<Producto> productos = productoRepository.findAll();
            
            System.out.println("=== [/health/productos-raw] Serializando " + productos.size() + " productos ===");
            
            // Log cada producto antes de serializar
            for (Producto p : productos) {
                String imgInfo = p.getImageUrl() == null ? "NULL" :
                    (p.getImageUrl().startsWith("data:") ? "BASE64[" + p.getImageUrl().length() + "]" : "URL");
                System.out.println("  -> ID=" + p.getId() + " | " + p.getNombre() + " | img=" + imgInfo);
            }
            
            // Serializar manualmente con el ObjectMapper configurado
            String json = objectMapper.writeValueAsString(productos);
            
            System.out.println("[/health/productos-raw] JSON generado: " + json.length() + " bytes");
            System.out.println("[/health/productos-raw] Primeros 500 chars: " + json.substring(0, Math.min(500, json.length())));
            
            // Verificar que todos los IDs estén en el JSON
            for (Producto p : productos) {
                String idCheck = "\"producto_id\":" + p.getId();
                if (!json.contains(idCheck)) {
                    System.err.println("[/health/productos-raw] ⚠️ PRODUCTO ID=" + p.getId() + " NO ENCONTRADO EN JSON!");
                }
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Total-Products", String.valueOf(productos.size()));
            headers.set("X-JSON-Length", String.valueOf(json.length()));
            
            return new ResponseEntity<>(json, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            System.err.println("[/health/productos-raw] ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }
    
    /**
     * DEBUG: Analiza cada producto individualmente para detectar cuál falla
     */
    @GetMapping("/health/productos-analisis")
    public ResponseEntity<Map<String, Object>> productosAnalisis() {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", Instant.now().toString());
        
        try {
            List<Producto> productos = productoRepository.findAll();
            result.put("totalEnBD", productos.size());
            
            List<Map<String, Object>> analisis = new ArrayList<>();
            List<Integer> idsConError = new ArrayList<>();
            List<Integer> idsConBase64 = new ArrayList<>();
            int serializadosOk = 0;
            
            for (Producto p : productos) {
                Map<String, Object> pAnalisis = new HashMap<>();
                pAnalisis.put("id", p.getId());
                pAnalisis.put("nombre", p.getNombre());
                pAnalisis.put("category", p.getCategory());
                
                boolean esBase64 = p.getImageUrl() != null && p.getImageUrl().startsWith("data:");
                pAnalisis.put("esBase64", esBase64);
                pAnalisis.put("imageUrlLength", p.getImageUrl() != null ? p.getImageUrl().length() : 0);
                
                if (esBase64) {
                    idsConBase64.add(p.getId());
                }
                
                // Intentar serializar este producto individualmente
                try {
                    String jsonIndividual = objectMapper.writeValueAsString(p);
                    pAnalisis.put("serializaOk", true);
                    pAnalisis.put("jsonLength", jsonIndividual.length());
                    serializadosOk++;
                } catch (Exception serEx) {
                    pAnalisis.put("serializaOk", false);
                    pAnalisis.put("errorSerializacion", serEx.getMessage());
                    idsConError.add(p.getId());
                }
                
                analisis.add(pAnalisis);
            }
            
            result.put("serializadosOk", serializadosOk);
            result.put("idsConError", idsConError);
            result.put("idsConBase64", idsConBase64);
            result.put("productosAnalisis", analisis);
            
            // Intentar serializar toda la lista
            try {
                String jsonCompleto = objectMapper.writeValueAsString(productos);
                result.put("listaCompletaSerializaOk", true);
                result.put("jsonCompletoLength", jsonCompleto.length());
                
                // Contar cuántos producto_id hay en el JSON
                int countIds = 0;
                int idx = 0;
                while ((idx = jsonCompleto.indexOf("\"producto_id\":", idx)) != -1) {
                    countIds++;
                    idx++;
                }
                result.put("productosEnJson", countIds);
                
            } catch (Exception listEx) {
                result.put("listaCompletaSerializaOk", false);
                result.put("errorListaCompleta", listEx.getMessage());
            }
            
            result.put("status", idsConError.isEmpty() ? "OK" : "HAY_ERRORES");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

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
