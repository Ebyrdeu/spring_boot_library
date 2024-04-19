package dev.ebrydeu.spring_boot_library.controllers.web;


import dev.ebrydeu.spring_boot_library.domain.dto.CreateMessageFormData;
import dev.ebrydeu.spring_boot_library.domain.dto.MessageAndUsername;
import dev.ebrydeu.spring_boot_library.domain.dto.UserData;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.services.impl.MessageService;
import dev.ebrydeu.spring_boot_library.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web")
public class WebController {

    private final MessageService messageService;
    private final UserService userService;
    //private final LibreTranslateService libreTranslateService;

    public WebController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
        //this.libreTranslateService = libreTranslateService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

        // Adding messages to the model if the user is authenticated
        if (isAuthenticated) {
            List<MessageAndUsername> messages = messageService.findAllMessages();
            model.addAttribute("messages", messages);
        }

        model.addAttribute("isAuthenticated", isAuthenticated);
        return "user-profile-page"; // Name of your Thymeleaf template
    }

    @GetMapping("/messages")
    public String messagePage(@RequestParam(value = "page", defaultValue = "0") String page,
                              Model model,
                              HttpServletRequest httpServletRequest) {
        int p = Integer.parseInt(page);
        if (p < 0) p = 0;
        List<MessageAndUsername> messages = messageService.findAllMessages();
        List<String> distinctUserNames = userService.findAll().stream().map(User::getUserName).toList();
        int allMessageCount = messageService.findAllMessages().size();

        model.addAttribute("userList", distinctUserNames);
        model.addAttribute("messages", messages);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("currentPage", p);
        model.addAttribute("totalPublicMessages", allMessageCount);
        return "messages";
    }

    @GetMapping("/myprofile")
    public String userProfile(@RequestParam(value = "page", defaultValue = "0") String page,
                              Model model,
                              @AuthenticationPrincipal OAuth2User principal,
                              HttpServletRequest httpServletRequest) {
        int p = Integer.parseInt(page);
        if (p < 0) p = 0;
        User user = userService.findByGitHubId(principal.getAttribute("id"));
        List<MessageAndUsername> messages = messageService.findAllMessagesByUser(user);
        int allMessageCount = messageService.findAllMessagesByUser(user).size();

        model.addAttribute("messages", messages);
        model.addAttribute("currentPage", p);
        model.addAttribute("totalMessages", allMessageCount);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("profilepic", user.getProfileImage());
        model.addAttribute("email", user.getEmail());
        return "userprofile";
    }

    @GetMapping("/myprofile/edit")
    public String editUserProfile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.findByGitHubId(principal.getAttribute("id"));

        model.addAttribute("formData", new UserData(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getProfileImage()));
        return "edituser";
    }

    private boolean checkIfUsernameAlreadyExists(String userName, User user) {
        return userService.findByUserName(userName).isPresent() && !userName.equals(user.getUserName());
    }

    @PostMapping("/myprofile/edit")
    public String editUserProfile(@Valid @ModelAttribute("formData") UserData userForm,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.findByGitHubId(principal.getAttribute("id"));

        if (checkIfUsernameAlreadyExists(userForm.getUserName(), user))
            bindingResult.rejectValue("userName", "duplicate", "Username needs to be unique");
        if (bindingResult.hasErrors())
            return "edituser";

        user.setUserName(userForm.getUserName());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setProfileImage(userForm.getProfileImage());
        userService.save(user);
        return "redirect:/web/myprofile";
    }


    @GetMapping("/myprofile/editmessage")
    public String editMessage(Model model, @RequestParam("id") Long id, @AuthenticationPrincipal OAuth2User principal) {
        Message message = messageService.findById(id);
        User currentUser = userService.findByGitHubId(principal.getAttribute("id"));

        if (!message.getUser().getId().equals(currentUser.getId()))
            return "redirect:/web/myprofile";

        model.addAttribute("messageId", message.getId());
        model.addAttribute("formData", new CreateMessageFormData(
                message.getTitle(),
                message.getBody(),
                message.isPrivateMessage()));
        return "editmessage";
    }

    @GetMapping("/myprofile/deletemessage")
    public String deleteMessage(@RequestParam("id") Long id, @AuthenticationPrincipal OAuth2User principal) {
        Message message = messageService.findById(id);
        User currentUser = userService.findByGitHubId(principal.getAttribute("id"));

        if (!message.getUser().getId().equals(currentUser.getId()))
            return "redirect:/web/myprofile";

        messageService.delete(message);
        return "redirect:/web/myprofile";
    }

    @PostMapping("/myprofile/editmessage")
    public String editMessage(@Valid @ModelAttribute("formData") CreateMessageFormData messageForm,
                              BindingResult bindingResult,
                              @RequestParam("id") Long id,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("id", id);
            return "redirect:/web/myprofile/editmessage";
        }

        Message message = messageService.findById(id);
        message.setBody(messageForm.getBody());
        message.setTitle(messageForm.getTitle());
        message.setPrivateMessage(messageForm.isPrivateMessage());
        message.setLastChanged(LocalDate.now());
        messageService.save(message);
        return "redirect:/web/myprofile";
    }

    @GetMapping("/public-page")
    public String guestPage(Model model, HttpServletRequest httpServletRequest) {
        List<MessageAndUsername> publicMessages = messageService.findAllByPrivateMessageIsFalse();
        int allPublicMessageCount = publicMessages.size(); // Efficiently use already fetched data

        model.addAttribute("messages", publicMessages);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("totalPublicMessages", allPublicMessageCount);
        return "public";
    }
    @GetMapping("/message-create")
    public String createMessage(Model model) {
        model.addAttribute("formData", new CreateMessageFormData());
        return "message-create";
    }

    @PostMapping("/message/new")
    public String createMessage(@Valid @ModelAttribute("formData") CreateMessageFormData messageForm,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal OAuth2User principal) {
        if (bindingResult.hasErrors())
            return "message-create";

        User user = userService.findByGitHubId(principal.getAttribute("id"));
        messageService.save(messageForm.toEntity(user));
        return "redirect:/web/profile";
    }

    //GetMapping("/messages/translate")
    //ublic String translateMessage(Model model, @RequestParam("id") Long id) {
    //   Message message = messageService.findById(id);
    //   String translatedTitle = libreTranslateService.translateTitle(message.getTitle());
    //   String translatedMessage = libreTranslateService.translateMessage(message.getBody());
    //
    //   model.addAttribute("title", translatedTitle);
    //   model.addAttribute("message", translatedMessage);
    //   model.addAttribute("userName", message.getUser().getUserName());
    //   model.addAttribute("date", message.getDate());
    //   model.addAttribute("lastChanged", message.getLastChanged());
    //   return "translatemessage";
    //

}