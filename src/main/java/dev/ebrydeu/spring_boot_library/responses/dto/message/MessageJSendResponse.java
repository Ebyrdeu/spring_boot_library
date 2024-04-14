package dev.ebrydeu.spring_boot_library.responses.dto.message;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageWithoutUserDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Message JSend Specification")
public record MessageJSendResponse(
        @Schema(example = "success")
        String status,
        MessageWithoutUserDto data
) {
}
