package dev.ebrydeu.spring_boot_library.services;
import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.exception.CustomExceptions;
import dev.ebrydeu.spring_boot_library.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    final MessageRepository messageRepository;

    @Cacheable("messages")
        public List<MessageDto> findAllMessages() {
            return messageRepository.findAll().stream()
                    .map(MessageDto::map)
                    .toList();
        }

    @Cacheable("messages")
    public List<MessageDto> findMessagesByTitle(String title) {
        return messageRepository.findByTitle(title).stream()
                    .map(MessageDto::map)
                    .toList();
        }

    @CacheEvict("messages")
    public MessageDto saveMessage(MessageDto messageDto) {
        Message message = MessageDto.map(messageDto);
        Message savedMessage = messageRepository.save(message);
        return MessageDto.map(savedMessage);
        }
    @CacheEvict("messages")
    public MessageDto editMessageBody(Long id, String body) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("Message not found with id: " + id));

        message.setBody(body);
        return MessageDto.map(messageRepository.save(message));
        }
    @CacheEvict("messages")
    public MessageDto editMessageTitle(Long id, String title) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("Message not found with id: " + id));

        message.setTitle(title);
        return MessageDto.map(messageRepository.save(message));
    }
    @CacheEvict("messages")
    public Object setMessagePrivate(boolean messagePrivate, Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("Message not found with id: " + id));
        message.setMessagePrivate(messagePrivate);
        return MessageDto.map(messageRepository.save(message));
    }
}

