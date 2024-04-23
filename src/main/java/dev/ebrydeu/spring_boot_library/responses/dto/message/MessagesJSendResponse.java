package dev.ebrydeu.spring_boot_library.responses.dto.message;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageAndUsername;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Messages JSend Specification")
public record MessagesJSendResponse(
        @Schema(example = "success")
        String status,
        List<MessageAndUsername> data
) {
}

