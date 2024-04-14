package dev.ebrydeu.spring_boot_library.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class SwaggerController {
    @GetMapping("/swagger/login")
    public String swaggerLogin() {return "swagger-login";}
}
