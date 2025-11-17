package com.tiendamascota.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendamascota.model.Usuario;
import com.tiendamascota.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Profile("local")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios", content = @Content(mediaType = "application/json"))
    })
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public Optional<Usuario> getById(@PathVariable Integer id) {
        return usuarioRepository.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"usuario_id\": 1, \"email\": \"cliente@correo.com\"}")))
    })
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public Usuario update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setUsuario_id(id);
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado", content = @Content)
    })
    public void delete(@PathVariable Integer id) {
        usuarioRepository.deleteById(id);
    }
}
