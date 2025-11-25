package com.tiendamascota.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

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
                .responseTimeout(Duration.ofMillis(timeoutMs))
                .followRedirect(true)
                // opcional: setea un proxy si lo necesitas
                // .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).host("proxy.host").port(8080))
                ;

        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                // Headers tipo navegador para evitar bloqueos simples de Cloudflare
                .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36")
                .defaultHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .defaultHeader("Accept-Language", "es-CL,es;q=0.9,en;q=0.8")
                .defaultHeader("Referer", baseUrl)
                .defaultHeader("Connection", "keep-alive");

        if (apiKey != null && !apiKey.isBlank()) {
            // Ajustar header si la doc indica otro nombre
            builder.defaultHeader("Authorization", "Bearer " + apiKey);
        }

        return builder.build();
    }
}
