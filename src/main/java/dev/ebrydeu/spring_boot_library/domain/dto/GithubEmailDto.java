package dev.ebrydeu.spring_boot_library.domain.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public record GithubEmailDto(
        String email,
        boolean primary,
        boolean verified,
        String visibility
) {

    public static List<GithubEmailDto> fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse email json", e);
        }
    }
}
