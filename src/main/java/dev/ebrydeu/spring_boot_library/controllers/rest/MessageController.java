package dev.ebrydeu.spring_boot_library.controllers.rest;

import dev.ebrydeu.spring_boot_library.domain.dto.MessageDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.responses.JSendResponse;
import dev.ebrydeu.spring_boot_library.responses.PaginatedResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.InternalServerJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.NotFoundJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.message.MessageJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.message.MessagesJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.user.UserJSendResponse;
import dev.ebrydeu.spring_boot_library.services.impl.MessageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "security_auth")
@Tag(name = "Message", description = "provides api to manipulate messages")

public class MessageController {

    private final MessageServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create single Message", description = "User is required")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse createMessage(@RequestBody MessageDto dto) {
        MessageDto messages = service.save(dto);
        return JSendResponse.success(messages);
    }

    @GetMapping
    @Operation(summary = "Find all Messages")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MessagesJSendResponse.class)))
    public PaginatedResponse findAll(@RequestParam(name = "page", defaultValue = "0") Integer page) {
        Page<MessageDto> messages = service.findAll(page);

        return PaginatedResponse.success(
                messages.stream().toList(),
                messages.getNumber(),
                messages.getTotalElements(),
                messages.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Message by ID", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MessageJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findById(@Parameter(description = "id", required = true, example = "1") @PathVariable Long id) {
        MessageDto message = service.findById(id);
        return JSendResponse.success(message);
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Find Message  by title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MessageJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findByTitle(@Parameter(required = true, example = "Message Title") @PathVariable String title) {
        List<MessageDto> messages = service.findByTitle(title);
        return JSendResponse.success(messages);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing Message", description = "All fields are required for update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public void fullUpdate(@Parameter(description = "id", required = true, example = "1") @PathVariable("id") Long id, @RequestBody MessageDto dto) {
        if (!service.isExists(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Message message = MessageDto.map(dto);
        message.setId(id);

        service.save(MessageDto.map(message));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public void partialUpdate(@Parameter(description = "id", required = true, example = "1") @PathVariable("id") Long id, @RequestBody MessageDto dto) {
        service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public void delete(@Parameter(required = true, example = "1") @PathVariable Long id) {
        service.delete(id);
    }
}

