package dev.ebrydeu.spring_boot_library.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Schema(name = "Meessage")
public record MessageWithoutUserDto(
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
        boolean isPrivate
) {
}
