package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.repositories.MessageRepository;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import dev.ebrydeu.spring_boot_library.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.ebrydeu.spring_boot_library.exception.Exceptions.InternalServerErrorException;
import static dev.ebrydeu.spring_boot_library.exception.Exceptions.NotFoundException;
import static dev.ebrydeu.spring_boot_library.libs.Utils.isNullable;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final UserRepository userRepository;

    @Override
    public MessageDto save(MessageDto dto) {
        User exsitingUser = userRepository.findById(dto.user().id()).orElseThrow(() -> new NotFoundException("User not found with id: " + dto.user().id()));
        try {
            Message message = MessageDto.map(dto);
            message.setUser(exsitingUser);
            Message savedMessage = repository.save(message);
            return MessageDto.map(savedMessage);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<MessageDto> findAll() {
        return repository.findAll().stream()
                .map(MessageDto::map)
                .toList();
    }

    @Override
    public MessageDto findById(Long id) {
        Message message = repository.findById(id).orElseThrow(() -> new NotFoundException("Message not found with id: " + id));
        return MessageDto.map(message);
    }

    @Override
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
    public void partialUpdate(Long id, MessageDto dto) {
        Message existingMessage = repository.findById(id).orElseThrow(() -> new NotFoundException("Message not found with id: " + id));

        isNullable(existingMessage::setBody, dto.body());
        isNullable(existingMessage::setTitle, dto.title());
        isNullable(existingMessage::setPrivate, dto.isPrivate());

        try {
            repository.save(existingMessage);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Message messageToDelete = repository.findById(id).orElseThrow(() -> new NotFoundException("Message not found with id: " + id));

        try {
            repository.deleteById(messageToDelete.getId());
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }


}


