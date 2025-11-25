package com.tiendamascota.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendamascota.service.HuachitosService;

@RestController
@RequestMapping("/external/huachitos")
public class HuachitosController {

    private final HuachitosService huachitosService;

    public HuachitosController(HuachitosService huachitosService) {
        this.huachitosService = huachitosService;
    }

    @GetMapping("/animales/comuna/{id}")
    public ResponseEntity<?> getAnimalesPorComuna(@PathVariable String id) {
        try {
            String body = huachitosService.obtenerAnimalesPorComuna(id);
            return ResponseEntity.ok().body(body);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("mensaje", "Error al obtener datos externos", "detalle", e.getMessage()));
        }
    }
}
