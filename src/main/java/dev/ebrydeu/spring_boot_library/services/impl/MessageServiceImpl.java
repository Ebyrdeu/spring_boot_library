package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.repositories.MessageRepository;
import dev.ebrydeu.spring_boot_library.services.MessageService;
import dev.ebrydeu.spring_boot_library.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static dev.ebrydeu.spring_boot_library.exception.Exceptions.NotFoundException;
import static dev.ebrydeu.spring_boot_library.libs.Utils.isNullable;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final UserService userService;


    @Override
    @CacheEvict("messages")
    public MessageDto save(MessageDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto currentUser = userService.findByEmail(authentication.getName());

        Message message = MessageDto.map(dto);
        message.setUser(UserDto.map(currentUser));
        Message savedMessage = repository.save(message);
        return MessageDto.map(savedMessage);
    }

    @Override
    @Cacheable("messages")
    public List<MessageDto> findAll() {
        return repository.findAll().stream()
                .map(message -> {
                    MessageDto messageDto = MessageDto.map(message); // Map the message entity to MessageDto
                    messageDto.setUser(UserDto.map(message.getUser())); // Map the associated user entity to UserDto
                    return messageDto;
                })
                .toList();
    }

    @Override
    public MessageDto findById(Long id) {
        Message message = repository.findById(id).orElseThrow(() -> new NotFoundException("Message not found with id: " + id));
        return MessageDto.map(message);
    }

    @Override
    @Cacheable("messages")
    public List<MessageDto> findByTitle(String title) {
        return repository.findByTitle(title).stream()
                .map(MessageDto::map)
                .toList();
    }

    @Override
    public boolean isExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    @CacheEvict("messages")
    public void partialUpdate(Long id, MessageDto dto) {
        Message existingMessage = repository.findById(id).orElseThrow(() -> new NotFoundException("Message not found with id: " + id));

        isNullable(existingMessage::setBody, dto.body());
        isNullable(existingMessage::setTitle, dto.title());
        isNullable(existingMessage::setPrivate, dto.isPrivate());

        repository.save(existingMessage);
    }

    @Override
    @CacheEvict("messages")
    public void delete(Long id) {
        Message messageToDelete = repository.findById(id).orElseThrow(() -> new NotFoundException("Message not found with id: " + id));
        repository.deleteById(messageToDelete.getId());
    }
    @Override
    @Cacheable("publicMessages")
    public List<MessageDto> findPublicMessages() {
        return repository.findMessageByIsPrivateFalse().stream()
                .map(MessageDto::map)
                .collect(Collectors.toList());
    }

}


