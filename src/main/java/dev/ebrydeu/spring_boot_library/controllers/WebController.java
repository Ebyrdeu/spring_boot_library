package dev.ebrydeu.spring_boot_library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/guest-page")
    public String guestPage() {return "guest-page";}
}

