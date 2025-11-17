package com.tiendamascota.dto;

import java.util.List;

public class UnsplashResponse {
    private List<UnsplashPhoto> results;
    
    public UnsplashResponse() {
    }
    
    public UnsplashResponse(List<UnsplashPhoto> results) {
        this.results = results;
    }
    
    public List<UnsplashPhoto> getResults() {
        return results;
    }
    
    public void setResults(List<UnsplashPhoto> results) {
        this.results = results;
    }
}
