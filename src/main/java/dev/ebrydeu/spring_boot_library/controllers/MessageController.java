package dev.ebrydeu.spring_boot_library.controllers;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController{
    private final MessageService messageService;

    @PutMapping("/{id}/private/{messagePrivate}")
    public Object setMessagePrivate(@PathVariable Long id, @PathVariable boolean messagePrivate) {
        return messageService.setMessagePrivate(messagePrivate, id);
    }
}
