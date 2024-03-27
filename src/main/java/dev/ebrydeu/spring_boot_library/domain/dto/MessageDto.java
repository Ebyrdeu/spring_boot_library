package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;

import java.time.Instant;

public record MessageDto(
        Long id,
        String title,
        String body,
        String author,
        Instant date,
        UserDto user) {

    public static MessageDto map(Message entity) {
        return new MessageDto(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getAuthor(),
                entity.getDate(),
                UserDto.map(entity.getUser())
        );
    }

    public static Message map(MessageDto dto) {
        Message message = new Message();
        message.setId(dto.id);
        message.setTitle(dto.title);
        message.setBody(dto.body);
        message.setAuthor(dto.author);
        message.setDate(dto.date);
        message.setUser(UserDto.map(dto.user));

        return message;
    }
}
