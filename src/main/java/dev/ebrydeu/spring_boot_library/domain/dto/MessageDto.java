package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record MessageDto(
        Long id,
        @NotBlank(message = "Title must not be blank")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        String title,
        @NotBlank(message = "Body must not be blank")
        String body,
        String author,
        @NotNull(message = "Date must not be null")
        @PastOrPresent(message = "Date must be in the past or present")
        Instant date,
        @NotNull(message = "User information must not be null")
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
        message.setId(dto.id());
        message.setTitle(dto.title());
        message.setBody(dto.body());
        message.setAuthor(dto.author());
        message.setDate(dto.date());
        message.setUser(UserDto.map(dto.user()));

        return message;
    }
}