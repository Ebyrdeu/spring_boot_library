package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageAndUsername;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.repositories.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByPrivateMessageIsFalse() {
        return messageRepository.findAllByPrivateMessageIsFalse();
    }

    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByUserIdAndPrivateMessageIsFalse(Long id) {
        return messageRepository.findAllByUserIdAndPrivateMessageIsFalse(id);
    }
    public Optional<MessageAndUsername> getMessageById(Long id) {
        return messageRepository
                .findById(id)
                .map(message -> new MessageAndUsername(
                        message.getId(),
                        message.getDate(),
                        message.getLastChanged(),
                        message.getTitle(),
                        message.getBody(),
                        message.getUser().getUserName(),
                        message.isPrivateMessage()));
    }

    @Cacheable("messages")
    public List<MessageAndUsername> findAllMessages() {
        return messageRepository.findAll().stream()
                .map(message -> new MessageAndUsername(
                        message.getId(),
                        message.getDate(),
                        message.getLastChanged(),
                        message.getTitle(),
                        message.getBody(),
                        message.getUser().getUserName(),
                        message.isPrivateMessage()))
                .toList();
    }

    public List<MessageAndUsername> findAllMessagesByUser(User user) {
        return messageRepository.findAllByUser(user);
    }

    public List<MessageAndUsername> getMessagesByUserId(Long userId) {
        return messageRepository.findMessagesByUserId(userId);
    }
    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public void save(Message message) {
        messageRepository.save(message);
    }

    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public Message findById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent())
            return message.get();
        throw new EntityNotFoundException();
    }

    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    public Message updateMessage(Long messageId, String title, String body, boolean privateMessage) throws Exception {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setTitle(title);
            message.setBody(body);
            message.setPrivateMessage(privateMessage);
            message.setLastChanged(LocalDate.now());
            return messageRepository.save(message);
        } else {
            throw new EntityNotFoundException("No message found with ID: " + messageId);
        }
    }
}

