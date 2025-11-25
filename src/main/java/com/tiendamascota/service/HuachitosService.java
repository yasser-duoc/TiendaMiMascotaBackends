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
            // Loguear body para debugging (puede contener HTML challenge)
            String body = e.getResponseBodyAsString();
            if (e.getRawStatusCode() == 403 && body != null && body.contains("Just a moment")) {
                throw new RuntimeException("Huachitos bloqueó la petición con Cloudflare JS challenge (403). Contacta a huachitos.cl para acceso API o whitelist. HTML: " + (body.length() > 1000 ? body.substring(0,1000) + "..." : body), e);
            }
            throw new RuntimeException("Huachitos error: " + e.getRawStatusCode() + " - " + body, e);
        } catch (Exception e) {
            throw new RuntimeException("Error conectando a Huachitos", e);
        }
    }
}
