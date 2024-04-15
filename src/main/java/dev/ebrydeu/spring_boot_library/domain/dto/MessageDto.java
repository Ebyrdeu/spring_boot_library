package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Schema(name = "Meessage")
public record MessageDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
        Long id,

        @NotBlank(message = "Title must not be blank")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        @Schema(example = "Message Title")
        String title,

        @NotBlank(message = "Body must not be blank")
        @Schema(example = "Important Body")
        String body,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Instant date,

        @Schema(example = "false")
        boolean isPrivate,

        @NotNull(message = "User information must not be null")
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        UserDto user
) {

    public static MessageDto map(Message entity) {
        return new MessageDto(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getDate(),
                entity.isPrivate(),
                UserDto.map(entity.getUser())
        );
    }

    public static Message map(MessageDto dto) {
        Message message = new Message();
        message.setId(dto.id);
        message.setTitle(dto.title);
        message.setBody(dto.body);
        message.setDate(dto.date);
        message.setPrivate(dto.isPrivate);
        message.setUser(UserDto.map(dto.user));

        return message;
    }
}