package dev.ebrydeu.spring_boot_library.responses.dto.user;

import dev.ebrydeu.spring_boot_library.domain.dto.UserData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Users JSend Specification")
public record UsersJSendResponse(
        @Schema(example = "success")
        String status,
        List<UserData> data
) {
}
