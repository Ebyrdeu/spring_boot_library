package dev.ebrydeu.spring_boot_library.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class LibreTranslateService {
    private final RestClient restClient;


    @Retryable
    public String detectLanguage(String text) throws JsonProcessingException {
        String jsonInput = String.format("{\"q\":\"%s\"}", text);
        ObjectMapper mapper = new ObjectMapper();

        String jsonResponse = restClient.post()
                .uri("http://localhost:5000/detect")
                .contentType(APPLICATION_JSON)
                .accept()
                .body(jsonInput)
                .retrieve()
                .body(String.class);

        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.get(0).get("language").asText();
    }


    @Retryable
    public String translate(String text) throws JsonProcessingException {
        String sourceLang = detectLanguage(text);
        String targetLang = sourceLang.equals("en") ? "sv" : "en";
        String jsonString = String
                .format("{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}", text, sourceLang, targetLang);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = restClient.post()
                .uri("http://localhost:5000/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(jsonString)
                .retrieve()
                .body(String.class);

        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.get("translatedText").asText();
    }
}





        

