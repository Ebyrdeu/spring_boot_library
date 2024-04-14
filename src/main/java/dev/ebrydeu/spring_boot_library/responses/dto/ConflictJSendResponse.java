package dev.ebrydeu.spring_boot_library.responses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "Conflict JSend Specification")
public record ConflictJSendResponse(
        @Schema(example = "error")
        String status,
        @Schema(example = "Email already exist")
        String message,
        @Schema(example = "409")
        Integer code

) {
}
