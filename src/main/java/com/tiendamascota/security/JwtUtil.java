package com.tiendamascota.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:mi-secreto-super-seguro-para-tienda-mascota-2025}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:604800000}") // 7 días en milisegundos
    private long jwtExpiration;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    /**
     * Genera un JWT token con todos los claims
     */
    public String generateToken(String email, Integer usuarioId, String nombre, String rol) {
        return Jwts.builder()
                .subject(email)
                .claim("usuario_id", usuarioId)
                .claim("email", email)
                .claim("nombre", nombre)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }
    
    /**
     * Genera un JWT token (versión simplificada para compatibilidad)
     */
    public String generateToken(String email, Integer usuarioId) {
        return generateToken(email, usuarioId, "", "cliente");
    }
    
    /**
     * Obtiene el email del token
     */
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    /**
     * Obtiene el ID del usuario del token
     */
    public Integer getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("usuario_id", Integer.class);
    }
    
    /**
     * Obtiene el nombre del usuario del token
     */
    public String getNombreFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("nombre", String.class);
    }
    
    /**
     * Obtiene el rol del usuario del token
     */
    public String getRolFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("rol", String.class);
    }
    
    /**
     * Obtiene todos los claims del token
     */
    public java.util.Map<String, Object> getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
     * Valida el token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
