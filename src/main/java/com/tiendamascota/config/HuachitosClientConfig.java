package com.tiendamascota.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class HuachitosClientConfig {

    @Value("${huachitos.base-url}")
    private String baseUrl;

    @Value("${huachitos.api-key:}")
    private String apiKey;

    @Value("${huachitos.timeout-ms:5000}")
    private int timeoutMs;

    @Bean
    public WebClient huachitosWebClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(timeoutMs));

        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build());

        if (apiKey != null && !apiKey.isBlank()) {
            // Ajustar header si la doc indica otro nombre (X-Api-Key, etc.)
            builder.defaultHeader("Authorization", "Bearer " + apiKey);
        }

        return builder.build();
    }
}
