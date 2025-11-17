package com.tiendamascota.dto;

public class UnsplashPhoto {
    private UnsplashUrls urls;
    
    public UnsplashPhoto() {
    }
    
    public UnsplashPhoto(UnsplashUrls urls) {
        this.urls = urls;
    }
    
    public UnsplashUrls getUrls() {
        return urls;
    }
    
    public void setUrls(UnsplashUrls urls) {
        this.urls = urls;
    }
}
