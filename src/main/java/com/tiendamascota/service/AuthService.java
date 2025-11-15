package com.tiendamascota.service;

import com.tiendamascota.dto.AuthResponse;
import com.tiendamascota.dto.LoginRequest;
import com.tiendamascota.dto.RegistroRequest;
import com.tiendamascota.model.Usuario;
import com.tiendamascota.repository.UsuarioRepository;
import com.tiendamascota.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Login de usuario
     */
    public AuthResponse login(LoginRequest request) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }
        
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getUsuario_id());
        
        return new AuthResponse(
                token,
                usuario.getUsuario_id(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getRun(),
                "Login exitoso"
        );
    }
    
    /**
     * Registro de nuevo usuario
     */
    public AuthResponse registro(RegistroRequest request) throws Exception {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new Exception("El email ya está registrado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRun(request.getRun());
        
        usuario = usuarioRepository.save(usuario);
        
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getUsuario_id());
        
        return new AuthResponse(
                token,
                usuario.getUsuario_id(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getRun(),
                "Usuario registrado exitosamente"
        );
    }
}
