package dev.ebrydeu.spring_boot_library.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class TranslateService {
    private final RestClient restClient;

    public TranslateService(RestClient restClient) {
        this.restClient = restClient;
    }


    public boolean detectMessageLanguage(String text) {
        String isEnOrSv = Objects.requireNonNull(restClient.post()
                .uri("http://localhost:5000/detect")
                .contentType(APPLICATION_JSON)
                .accept()
                .body(String.format("{\"q\":\"%s\"}", text))
                .retrieve()
                .body(String.class));
        return !isEnOrSv.contains("\"en\"");
    }


    public String translateMessage(String text) {
        String sourceLanguage = "en";
        String targetLanguage = "sv";

        if (detectMessageLanguage(text)) {
            sourceLanguage = "sv";
            targetLanguage = "en";
        }

        String jsonString = String
                .format("{\"q\":\"%s\",\"source\":\"%s\",\"target\":\"%s\"}", text, sourceLanguage, targetLanguage);

        return Objects.requireNonNull(restClient.post()
                        .uri("http://localhost:5000/translate")
                        .contentType(APPLICATION_JSON)
                        .accept()
                        .body(jsonString)
                        .retrieve()
                        .body(String.class))
                .split(":")[1]
                .replaceAll("[{\"}]", "");
    }

}