package dev.ebrydeu.spring_boot_library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @GetMapping("/user-profile-page")
    public String userProfilePage() {return "user-profile-page";}

    @GetMapping("/user-edit")
    public String userEditPage() {return "user-edit";}

    @GetMapping("message-create")
    public String messageCreatePage() {return "message-create";}
}
