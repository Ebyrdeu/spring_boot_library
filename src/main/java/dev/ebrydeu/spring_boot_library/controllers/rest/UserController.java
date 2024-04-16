package dev.ebrydeu.spring_boot_library.controllers.rest;

import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.responses.JSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.ConflictJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.InternalServerJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.NoContentJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.NotFoundJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.user.UserJSendResponse;
import dev.ebrydeu.spring_boot_library.responses.dto.user.UsersJSendResponse;
import dev.ebrydeu.spring_boot_library.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "security_auth")
@Tag(name = "User", description = "provides api to manipulate users")
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create User", description = "Make sure that email and username is unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ConflictJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse createUser(@Validated @RequestBody UserDto dto) {
        UserDto user = service.save(dto);
        return JSendResponse.success(user);
    }

    @GetMapping
    @Operation(summary = "Get All Users")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UsersJSendResponse.class)))
    public JSendResponse findAll() {
        List<UserDto> users = service.findAll();
        return JSendResponse.success(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find single User by ID", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findById(@Parameter(required = true, example = "1") @PathVariable Long id) {
        UserDto user = service.findById(id);
        return JSendResponse.success(user);
    }

    @GetMapping("/firstname/{firstname}")
    @Operation(summary = "Find single User by Firstname", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findByFirstname(@Parameter(description = "firstname", required = true, example = "Sven") @PathVariable String firstname) {
        List<UserDto> user = service.findByFirstname(firstname);
        return JSendResponse.success(user);
    }

    @GetMapping("/lastname/{lastname}")
    @Operation(summary = "Find User by Lastname", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findByLastname(@Parameter(required = true, example = "Svenson") @PathVariable String lastname) {
        List<UserDto> user = service.findByLastname(lastname);
        return JSendResponse.success(user);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Find User by Username", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findByUsername(@Parameter(required = true, example = "SvenSvenson") @PathVariable String username) {
        List<UserDto> user = service.findByUsername(username);
        return JSendResponse.success(user);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Find User by Email", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public JSendResponse findByEmail(@Parameter(required = true, example = "sven.svenson@gmail.com") @PathVariable String email) {
        UserDto user = service.findByEmail(email);
        return JSendResponse.success(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing User", description = "All fields are required for update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = NoContentJSendResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public void fullUpdate(@Parameter(required = true, example = "1") @PathVariable("id") Long id, @RequestBody UserDto dto) {
        if (!service.isExists(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User user = UserDto.map(dto);
        user.setId(id);

        service.save(UserDto.map(user));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public void partialUpdate(@Parameter(required = true, example = "1") @PathVariable("id") Long id, @RequestBody UserDto dto) {
        service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove single User by ID", description = "User Must Exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = NotFoundJSendResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = InternalServerJSendResponse.class)))
    })
    public void delete(@Parameter(required = true, example = "1") @PathVariable Long id) {
        service.delete(id);
    }
}
