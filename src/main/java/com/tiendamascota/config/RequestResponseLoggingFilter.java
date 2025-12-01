package com.tiendamascota.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Rutas (prefijos) que NO deben incluir bodies en los logs
    private static final List<String> EXCLUDED_PATH_PREFIXES = Arrays.asList(
        "/auth",
        "/login",
        "/registro",
        "/oauth",
        "/api/auth"
    );

    // Campos JSON sensibles que se deben enmascarar
    private static final Set<String> SENSITIVE_FIELDS = new HashSet<>(Arrays.asList(
        "password", "passwd", "token", "accessToken", "refreshToken", "authorization", "auth", "oldPassword", "newPassword"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        ContentCachingRequestWrapper wrappedRequest = request instanceof ContentCachingRequestWrapper
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper wrappedResponse = response instanceof ContentCachingResponseWrapper
                ? (ContentCachingResponseWrapper) response
                : new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            int status = wrappedResponse.getStatus();
            String queryString = request.getQueryString();
            String path = request.getRequestURI() + (queryString == null ? "" : "?" + queryString);
            String method = request.getMethod();
            String client = request.getRemoteAddr();

            boolean shouldLogBody = shouldLogBodyForPath(path) && !isBinaryOrMultipart(request.getContentType());

            String rawRequestBody = getPayload(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
            String rawResponseBody = getPayload(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

            String requestBody = shouldLogBody ? tryRedactJson(rawRequestBody) : "[excluded]";
            // para respuestas, si el tipo de contenido es binario o multipart, lo excluimos
            String responseBody = (!isBinaryOrMultipart(wrappedResponse.getContentType()) && shouldLogBody) ? tryRedactJson(rawResponseBody) : "[excluded]";

            logger.info("HTTP {} {} from {} => status={} time={}ms requestBody={} responseBody={}",
                    method, path, client, status, duration, truncate(requestBody), truncate(responseBody));

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String getPayload(byte[] buf, String encoding) {
        if (buf == null || buf.length == 0) {
            return "";
        }
        try {
            Charset cs = encoding == null ? StandardCharsets.UTF_8 : Charset.forName(encoding);
            return new String(buf, cs);
        } catch (Exception e) {
            return "[unknown]";
        }
    }

    private boolean shouldLogBodyForPath(String path) {
        if (path == null) return true;
        for (String prefix : EXCLUDED_PATH_PREFIXES) {
            if (path.startsWith(prefix) || path.contains(prefix)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBinaryOrMultipart(String contentType) {
        if (contentType == null) return false;
        String ct = contentType.toLowerCase();
        return ct.startsWith("multipart/") || ct.startsWith("image/") || ct.contains("application/octet-stream") || ct.contains("audio/") || ct.contains("video/");
    }

    private String tryRedactJson(String raw) {
        if (raw == null || raw.isBlank()) return "";
        try {
            JsonNode node = OBJECT_MAPPER.readTree(raw);
            redactNode(node);
            return OBJECT_MAPPER.writeValueAsString(node);
        } catch (Exception e) {
            // no es JSON, devolver original (limitado)
            return raw;
        }
    }

    private void redactNode(JsonNode node) {
        if (node == null) return;
        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;
            obj.fieldNames().forEachRemaining(field -> {
                JsonNode child = obj.get(field);
                if (SENSITIVE_FIELDS.contains(field)) {
                    obj.put(field, "[REDACTED]");
                } else {
                    redactNode(child);
                }
            });
        } else if (node.isArray()) {
            for (JsonNode child : node) {
                redactNode(child);
            }
        }
    }

    private String truncate(String s) {
        if (s == null) return "";
        int max = 1000;
        return s.length() <= max ? s : s.substring(0, max) + "...(truncated)";
    }
}
