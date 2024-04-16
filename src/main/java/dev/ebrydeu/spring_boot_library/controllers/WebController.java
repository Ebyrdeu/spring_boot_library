package dev.ebrydeu.spring_boot_library.controllers;

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
    public String userProfilePage() {return "user-profile-page";}
    @GetMapping("/guest-page")
    public String guestPage() {return "guest-page";}
    @GetMapping("/messages-page")
    public String messagesPage() {return "messages-page";}
    @GetMapping("/user-edit")
    public String userEdit() {return "user-edit";}
    @GetMapping("message-create")
    public String messageCreate() {return "message-create";}
    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") UserDto dto) {
        userService.save(dto);
        return "redirect:/web/users";// can be any other pathway of our choice
    }

    @GetMapping("/myprofile/editmessage")
    public String showMessageEditPage(@RequestParam("id") Long messageId, Model model) {
        MessageDto message = messageService.findById(messageId);
        model.addAttribute("message", message);
        return "message-edit"; // Assuming you have a message-edit.html template
    }

    @PatchMapping("/myprofile/editmessage/{id}")
    public String updateMessage(@PathVariable("id") Long id, @ModelAttribute("message") MessageDto dto) {
        messageService.partialUpdate(id, dto);
        return "redirect:/web/messages"; // Redirect to the messages page after editing
    }

    @PatchMapping("/userprofile/")
    public String partialUpdateUser(@PathVariable Long id, @ModelAttribute("user") UserDto dto) {
        userService.partialUpdate(id, dto);
        return "redirect:/web/users";
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
    @GetMapping("/public/messages")
    public String findPublicMessages(Model model) {
        List<MessageDto> publicMessages = messageService.findPublicMessages();
        model.addAttribute("publicMessages", publicMessages);
        return "public-messages"; //
    }

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
