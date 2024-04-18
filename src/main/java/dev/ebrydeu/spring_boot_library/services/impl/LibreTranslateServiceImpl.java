package dev.ebrydeu.spring_boot_library.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ebrydeu.spring_boot_library.services.LibreTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class LibreTranslateServiceImpl implements LibreTranslateService {
    private final RestClient restClient;

    @Override
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public String detectLanguage(String text) throws JsonProcessingException {
        String jsonInput = String.format("{\"q\":\"%s\"}", text);
        ObjectMapper mapper = new ObjectMapper();

        String jsonResponse = restClient.post()
                .uri("http://localhost:5000/detect")
                .accept(APPLICATION_JSON)
                .body(jsonInput)
                .retrieve()
                .body(String.class);

        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.get(0).get("language").asText();
    }

    @Override
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public String translate(String text) throws JsonProcessingException {
        String sourceLang = detectLanguage(text);
        String targetLang = sourceLang.equals("en") ? "sv" : "en";
        String jsonInput = String.
                format("{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}", text, sourceLang, targetLang);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = restClient.post()
                .uri("http://localhost:5000/translate")
                .accept(APPLICATION_JSON)
                .body(jsonInput)
                .retrieve()
                .body(String.class);

        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.get("translation").asText();
    }
}





        

