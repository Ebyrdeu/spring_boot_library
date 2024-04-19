package dev.ebrydeu.spring_boot_library.controllers.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {

    @GetMapping("/profile")
    public String profile(Model model) {
        userInfo(model);
        return "user-profile";
    }

    @GetMapping("/home")
    public String home(Model model) {
        userInfo(model);
        return "home";
    }

    private void userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

        if (isAuthenticated) {
            if (authentication.getPrincipal() instanceof OAuth2User oauthUser) {
                Integer id = oauthUser.getAttribute("id");
                model.addAttribute("githubId", id);
            }
        }

        model.addAttribute("isAuthenticated", isAuthenticated);
    }

}
