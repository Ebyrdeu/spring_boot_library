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
import org.springframework.data.domain.Sort;
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
import java.util.Optional;
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

    @GetMapping("/user-edit")
    public String editUserProfile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.findByGitHubId(principal.getAttribute("id"));
        Optional<User> userOptional = userService.findByUserName(user.getUserName());
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        UserData userData = new UserData(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getProfileImage());

        model.addAttribute("userData", user);
        model.addAttribute("userData", userData);
        return "user-edit";
    }



    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

        // Adding messages to the model if the user is authenticated
        if (isAuthenticated) {
            User user = userService.findByGitHubId(principal.getAttribute("id"));
            List<MessageAndUsername> messages = messageService.findAllMessages();
            model.addAttribute("messages", messages);
            model.addAttribute("isAuthenticated", isAuthenticated);
            model.addAttribute("username", user.getUserName());
            model.addAttribute("fullName", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("profileImage", user.getProfileImage());
            model.addAttribute("email", user.getEmail());
        }
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "user-profile-page";
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
        return "public";
    }

    private boolean checkIfUsernameAlreadyExists(String userName, User user) {
        return userService.findByUserName(userName).isPresent() && !userName.equals(user.getUserName());
    }


    @GetMapping("/public-page")
    public String guestPage(@RequestParam(value = "page", defaultValue = "0") String page,
                            Model model, HttpServletRequest httpServletRequest) {
        int p = Integer.parseInt(page);
        if (p < 0) p = 0;
        List<MessageAndUsername> publicMessages = messageService.findAllByPrivateMessageIsFalse(PageRequest.of(p, 5));
        int allPublicMessageCount = messageService.findAllByPrivateMessageIsFalse().size(); // Efficiently use already fetched data

        model.addAttribute("messages", publicMessages);
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("totalPublicMessages", allPublicMessageCount);
        model.addAttribute("currentPage", p);
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

    @PostMapping("/user-edit")
    public String editUserProfile(@Valid @ModelAttribute("formData") UserData userForm,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.findByGitHubId(principal.getAttribute("id"));

        if (checkIfUsernameAlreadyExists(userForm.getUserName(), user))
            bindingResult.rejectValue("userName", "duplicate", "Username needs to be unique");
        if (bindingResult.hasErrors())
            return "user-edit";

        user.setUserName(userForm.getUserName());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        user.setProfileImage(userForm.getProfileImage());
        userService.save(user);
        return "redirect:/web/profile";
    }
    @GetMapping("/messages/edit/{id}")
    public String editMessage(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Message message = messageService.findById(id);

        if (message == null) {
            model.addAttribute("errorMessage", "Message not found");
            return "errorPage";
        }
        CreateMessageFormData formData = new CreateMessageFormData(
                message.getTitle(),
                message.getBody(),
                message.isPrivateMessage()
        );
        model.addAttribute("messageId", message.getId());
        model.addAttribute("formData", formData);
        return "message-edit";
    }

    @PostMapping("/messages/edit/{id}")
    public String updateMessage(@PathVariable("id") Long messageId,
                                @ModelAttribute("formData") CreateMessageFormData formData,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal OAuth2User principal) {
        try {messageService.updateMessage(messageId, formData.getTitle(), formData.getBody(), formData.isPrivateMessage());

            redirectAttributes.addFlashAttribute("success", "Message updated successfully!");
            return "redirect:/web/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating message: " + e.getMessage());
            return "redirect:/web/messages/edit/" + messageId;
        }
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