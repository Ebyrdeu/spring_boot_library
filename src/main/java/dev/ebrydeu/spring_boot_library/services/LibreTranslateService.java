package dev.ebrydeu.spring_boot_library.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.apache.logging.log4j.util.StringBuilders.escapeJson;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class LibreTranslateService {
    private final RestClient restClient;


    public LibreTranslateService(RestClient restClient) {
        this.restClient = restClient;
    }

    //https://www.baeldung.com/spring-retry
    @Retryable
    public String detectLanguage(String text) throws JsonProcessingException {
        String jsonInput = String.format("{\"q\":\"%s\"}", text);
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Getting emails from GitHub...");
        //return restClient.get()
        //HttpRequest request = HttpRequest.newBuilder()
        String jsonResponse = restClient.post()
                .uri("http://localhost:5000/detect")
                .accept(APPLICATION_JSON)
                .body(jsonInput)
                .retrieve()
                .body(String.class);

        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.get(0).get("language").asText();
    }

    public String translate(String text) throws JsonProcessingException {
        String sourceLang = detectLanguage(text);
        String targetLang;
        if (sourceLang.equals("en")) {
            targetLang = "sv";
        } else {
            targetLang = "en";
            return targetLang;
        }

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

        

