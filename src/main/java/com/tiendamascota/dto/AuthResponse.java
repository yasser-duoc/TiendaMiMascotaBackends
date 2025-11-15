package com.tiendamascota.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String token;
    private Integer usuario_id;
    private String email;
    private String nombre;
    private String telefono;
    private String direccion;
    private String run;
    private String mensaje;
    
    public AuthResponse() {
    }

    public AuthResponse(String token, Integer usuario_id, String email, String nombre, String telefono, String direccion, String run, String mensaje) {
        this.token = token;
        this.usuario_id = usuario_id;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.run = run;
        this.mensaje = mensaje;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
