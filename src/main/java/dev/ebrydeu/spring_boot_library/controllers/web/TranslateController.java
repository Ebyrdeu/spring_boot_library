package dev.ebrydeu.spring_boot_library.controllers.web;

import dev.ebrydeu.spring_boot_library.services.impl.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslateController {

    private final TranslateService translateService;

    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @PostMapping("/translate")
    public String translate(@RequestParam String lang, @RequestParam String text) {
        if ("en".equals(lang) || "sv".equals(lang)) {
            return translateService.translateMessage(text);
        }
        return "Invalid language";
    }

}