package com.tiendamascota.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import com.tiendamascota.model.Usuario;
import com.tiendamascota.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tiendamascota.dto.AuthResponse;
import com.tiendamascota.dto.LoginRequest;
import com.tiendamascota.dto.RegistroRequest;
import com.tiendamascota.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
// @CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y registro")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.admin.init-secret:}")
    private String adminInitSecret;
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, null, null, null, null, null, e.getMessage())
            );
        }
    }
    
    @PostMapping("/registro")
    @Operation(summary = "Registrarse", description = "Crea una nueva cuenta de usuario")
    public ResponseEntity<AuthResponse> registro(@RequestBody RegistroRequest request) {
        try {
            AuthResponse response = authService.registro(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, null, null, null, null, null, e.getMessage())
            );
        }
    }
    
    @GetMapping("/verificar")
    @Operation(summary = "Verificar token JWT", description = "Valida el token y retorna los datos del usuario")
    public ResponseEntity<?> verificarToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "Token no proporcionado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
            
            String token = authHeader.substring(7);
            Map<String, Object> userData = authService.verificarToken(token);
            
            return ResponseEntity.ok(userData);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Token inválido o expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
    
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Invalida el token del usuario")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Sesión cerrada exitosamente");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token", description = "Genera un nuevo token JWT usando un token válido")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "Token no proporcionado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
            
            String token = authHeader.substring(7);
            AuthResponse response = authService.refreshToken(token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Token inválido o expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/init-admin")
    @Operation(summary = "Inicializar admin (temporal)", description = "Crea el usuario admin si no existe. Protegido por secreto de entorno.")
    public ResponseEntity<?> initAdmin(@RequestHeader(value = "X-Admin-Secret", required = false) String secret) {
        if (adminInitSecret == null || adminInitSecret.isBlank()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("mensaje", "Inicialización de admin no habilitada"));
        }
        if (secret == null || !adminInitSecret.equals(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("mensaje", "Secreto inválido"));
        }

        if (usuarioRepository.findByEmail("admin").isPresent()) {
            return ResponseEntity.ok(Map.of("mensaje", "Usuario admin ya existe"));
        }

        Usuario admin = new Usuario();
        admin.setEmail("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setNombre("Administrador");
        admin.setRol("ADMIN");
        usuarioRepository.save(admin);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario admin creado"));
    }
}
