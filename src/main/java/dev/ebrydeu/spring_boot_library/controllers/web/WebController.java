package dev.ebrydeu.spring_boot_library.controllers.web;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.services.MessageService;
import dev.ebrydeu.spring_boot_library.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {
    private final UserService userService;
    private final MessageService messageService;

    public WebController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/user-profile-page")
    public String userProfilePage() {
        return "user-profile-page";
    }

    @GetMapping("/guest-page")
    public String guestPage() {
        return "guest-page";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") UserDto dto) {
        userService.save(dto);
        return "redirect:/web/users";// can be any other pathway of our choice
    }

    @PatchMapping("/userprofile/")
    public String partialUpdateUser(@PathVariable Long id, @ModelAttribute("user") UserDto dto) {
        userService.partialUpdate(id, dto);
        return "redirect:/web/users";
    }

    @GetMapping("/message-create")
    public String createMessage() {
        return "message-create";
    }

    @GetMapping("/user-edit")
    public String editUser() {
        return "user-edit";
    }

    @PostMapping("/messages/new")
    public String createMessage(@ModelAttribute("message") MessageDto messageDto) {
        messageService.save(messageDto);
        return "redirect:/web/messages";
    }

    @GetMapping("/messages")
    public String findAllMessages(Model model) {
        List<MessageDto> messages = messageService.findAll();
        model.addAttribute("messages", messages);
        return "messages"; //web page for all messages accessible for logged users
    }

    //method from 40-guest-login-page
/*    @GetMapping("/public/messages")
    public String findPublicMessages(Model model) {
        List<MessageDto> publicMessages = messageService.findPublicMessages();
        model.addAttribute("publicMessages", publicMessages);
        return "public-messages"; // "public-messages.html" is only for not-logged users
    }*/

    @PatchMapping("/messages/edit")
    public String partialUpdateMessage(@PathVariable Long id, @ModelAttribute("message") MessageDto dto) {
        messageService.partialUpdate(id, dto);
        return "redirect:/web/messages";
    }

    @DeleteMapping("/messages/delete")
    public String deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
        return "redirect:/web/messages";
    }

}
