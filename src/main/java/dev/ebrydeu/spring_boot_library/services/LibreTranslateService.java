package dev.ebrydeu.spring_boot_library.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LibreTranslateService {
    String detectLanguage(String text) throws JsonProcessingException;

    String translate(String text) throws JsonProcessingException;
}
