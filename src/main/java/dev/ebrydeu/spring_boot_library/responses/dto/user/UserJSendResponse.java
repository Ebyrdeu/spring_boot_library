package dev.ebrydeu.spring_boot_library.responses.dto.user;

import dev.ebrydeu.spring_boot_library.domain.dto.UserData;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "User JSend Specification")
public record UserJSendResponse(
        @Schema(example = "success")
        String status,
        UserData data
) {
}
