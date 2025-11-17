package com.tiendamascota.dto;

public class UnsplashUrls {
    private String small; // URL optimizada 400x400
    
    public UnsplashUrls() {
    }
    
    public UnsplashUrls(String small) {
        this.small = small;
    }
    
    public String getSmall() {
        return small;
    }
    
    public void setSmall(String small) {
        this.small = small;
    }
}
