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

    @PatchMapping("/{id}/private/{messagePrivate}")
    public Object setMessagePrivate(@PathVariable Long id, @PathVariable boolean messagePrivate) {
        return messageService.setMessagePrivate(messagePrivate, id);
    }
  
    @GetMapping
    public List<MessageDto> getAllMessages(){
        return messageService.findAllMessages();
    }


    @GetMapping("/title/{title}")
    public List<MessageDto> getMessagesByTitle(@PathVariable String title){
        return messageService.findMessagesByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDto createMessage(@RequestBody MessageDto messageDto){
        return messageService.saveMessage(messageDto);
    }

    @PutMapping("/{id}/body")
    public MessageDto editMessageBody(@PathVariable Long id, @RequestBody String body){
        return messageService.editMessageBody(id, body);
    }

    @PutMapping("/{id}/title")
    public MessageDto editMessageTitle(@PathVariable Long id, @RequestBody String title){
        return messageService.editMessageTitle(id, title);
    }
}

