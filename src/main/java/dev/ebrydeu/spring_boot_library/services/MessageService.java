package dev.ebrydeu.spring_boot_library.services;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {

    MessageDto save(MessageDto dto);

    Page<MessageDto> findAll(Integer page);

    MessageDto findById(Long id);
    List<MessageDto> findByTitle(String title);

    boolean isExists(Long id);

    void partialUpdate(Long id, MessageDto dto);

    void delete(Long id);

}
