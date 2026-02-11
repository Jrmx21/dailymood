package com.ruis.dailymood.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OpenRouterService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper;
    private static final String URL = "https://openrouter.ai/api/v1/chat/completions";

    public OpenRouterService(RestTemplate restTemplate,
                             @Value("${spring.ai.openrouter.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Llama a OpenRouter para analizar la racha de un historial de estados
     */
    public String analyzeMoodHistory(String historyText) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "tngtech/deepseek-r1t-chimera:free"); // modelo válido
        body.put("messages", List.of(Map.of(
                "role", "user",
                "content", "Analyze this mood history and summarize if there is a bad or regular streak:\n" + historyText
        )));
        body.put("max_tokens", 500);
        body.put("stream", false); // importante: RestTemplate no soporta stream

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL, entity, String.class);
            String responseBody = response.getBody();
            System.out.println("Raw response from OpenRouter: " + responseBody);

            if (response.getStatusCode().is2xxSuccessful() && responseBody != null) {
                Map<String, Object> jsonMap = objectMapper.readValue(responseBody, Map.class);
                List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonMap.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null) {
                        return (String) message.get("content");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "No analysis available"; // fallback
    }
}