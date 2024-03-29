package dev.ebrydeu.spring_boot_library.services;
import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.exception.CustomExceptions;
import dev.ebrydeu.spring_boot_library.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
    @Transactional
    public class MessageService {
        MessageRepository messageRepository;

        @Autowired
        public MessageService(MessageRepository messageRepository) {
            this.messageRepository = messageRepository;
        }
        @Cacheable("messages")
        public List<MessageDto> findAllMessages() {
            return messageRepository.findAll().stream()
                    .map(MessageDto::map)
                    .collect(Collectors.toList());
        }

        @Cacheable("messages")
        public List<MessageDto> findMessagesByAuthor(String author) {
            return messageRepository.findByAuthor(author).stream()
                    .map(MessageDto::map)
                    .collect(Collectors.toList());
        }

        @Cacheable("messages")
        public List<MessageDto> findMessagesByTitle(String title) {
            return messageRepository.findByTitle(title).stream()
                    .map(MessageDto::map)
                    .collect(Collectors.toList());
        }

        public MessageDto saveMessage(MessageDto messageDto) {
            Message message = MessageDto.map(messageDto);
            Message savedMessage = messageRepository.save(message);
            return MessageDto.map(savedMessage);
        }

        public MessageDto editMessageBody(Long id, String body) {
            Message message = messageRepository.findById(id)
                    .orElseThrow(() -> new CustomExceptions.NotFoundException("Message not found with id: " + id));

            message.setBody(body);
            return MessageDto.map(messageRepository.save(message));
        }

    public MessageDto editMessageTitle(Long id, String title) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("Message not found with id: " + id));

        message.setTitle(title);
        return MessageDto.map(messageRepository.save(message));
    }
}

