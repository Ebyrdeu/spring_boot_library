package dev.ebrydeu.spring_boot_library.services;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto save(MessageDto dto);

    List<MessageDto> findAll();

    MessageDto findById(Long id);
    List<MessageDto> findByTitle(String title);

    List<MessageDto> findPublicMessages();

    boolean isExists(Long id);

    void partialUpdate(Long id, MessageDto dto);

    void delete(Long id);

}
