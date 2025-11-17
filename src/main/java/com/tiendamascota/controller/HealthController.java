package com.tiendamascota.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendamascota.repository.ProductoRepository;

@RestController
public class HealthController {

    @Autowired
    private ProductoRepository productoRepository;

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
}
