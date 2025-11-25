package com.tiendamascota.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class HuachitosService {

    private final WebClient webClient;

    public HuachitosService(WebClient huachitosWebClient) {
        this.webClient = huachitosWebClient;
    }

    public String obtenerAnimalesPorComuna(String comunaId) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/animales/comuna/{id}").build(comunaId))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Huachitos error: " + e.getRawStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error conectando a Huachitos", e);
        }
    }
}
