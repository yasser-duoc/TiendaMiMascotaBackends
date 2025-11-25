package com.tiendamascota.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LoginRequest {
    @JsonAlias({"username", "user", "email"})
    private String email;
    @JsonAlias({"password", "pass"})
    private String password;
    
    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
