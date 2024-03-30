package dev.ebrydeu.spring_boot_library.controllers;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Messages")
@RequiredArgsConstructor
public class MessageController{
    private final MessageService messageService;

    @GetMapping
    public List<MessageDto> getAllMessages(){
        return messageService.findAllMessages();
    }

    @GetMapping("/author/{author}")
    public List<MessageDto> getMessagesByAuthor(@PathVariable String author){
        return messageService.findMessagesByAuthor(author);
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