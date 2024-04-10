package dev.ebrydeu.spring_boot_library.controllers;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.services.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDto createMessage(@RequestBody MessageDto dto) {
        return service.save(dto);
    }

    @GetMapping
    public List<MessageDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public MessageDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/title/{title}")
    public List<MessageDto> findByTitle(@PathVariable String title) {
        return service.findByTitle(title);
    }

    @GetMapping("/public")
    public List<MessageDto> findPublicMessages() {
        return service.findPublicMessages();
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fullUpdate(@PathVariable("id") Long id, @RequestBody MessageDto dto) {
        if (!service.isExists(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Message message = MessageDto.map(dto);
        message.setId(id);

        service.save(MessageDto.map(message));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialUpdate(@PathVariable("id") Long id, @RequestBody MessageDto dto) {
        service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

