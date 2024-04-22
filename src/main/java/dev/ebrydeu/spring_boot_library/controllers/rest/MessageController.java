package dev.ebrydeu.spring_boot_library.controllers.rest;

import dev.ebrydeu.spring_boot_library.domain.dto.CreateMessageFormData;
import dev.ebrydeu.spring_boot_library.domain.dto.MessageAndUsername;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.services.impl.MessageService;
import dev.ebrydeu.spring_boot_library.services.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<MessageAndUsername>> getAllMessages() {
        List<MessageAndUsername> messages = messageService.findAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<List<MessageAndUsername>> getPublicMessages() {
        List<MessageAndUsername> messages = messageService.findAllByPrivateMessageIsFalse();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageAndUsername> getMessageById(@PathVariable Long id) {
        Optional<MessageAndUsername> message = messageService.getMessageById(id);
        return message.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<Message> createMessage(@Valid @RequestBody CreateMessageFormData messageData, @RequestParam("userId") Long userId) {
        User user = userService.findById(userId);
        Message newMessage = messageService.save(messageData.toEntity(user));
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }



}