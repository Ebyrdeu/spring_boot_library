package dev.ebrydeu.spring_boot_library.responses.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "No Content JSend Specification")
public record NoContentJSendResponse(
        @Schema(example = "success")
        String status,
        @Schema(example = "null")
        Object data
) {
}
