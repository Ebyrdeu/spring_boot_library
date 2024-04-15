package dev.ebrydeu.spring_boot_library.responses.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Internal Server JSend Specification")
public record InternalServerJSendResponse(
        @Schema(example = "error")
        String status,
        @Schema(example = "Something went wrong")
        String message,
        @Schema(example = "500")
        Integer code
) {
}
