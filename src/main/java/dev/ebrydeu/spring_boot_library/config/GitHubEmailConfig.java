package dev.ebrydeu.spring_boot_library.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;

public record GitHubEmailConfig(String email, boolean primary, boolean verified, String visibility) {


    public static List<GitHubEmailConfig> fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<List<GitHubEmailConfig>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }
}