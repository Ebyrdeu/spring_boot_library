package dev.ebrydeu.spring_boot_library.responses.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Not Found JSend Specification")
public record NotFoundJSendResponse(
        @Schema(example = "error")
        String status,
        @Schema(example = "Entity not found with value: 1")
        String message,
        @Schema(example = "404")
        Integer code
) {
}
