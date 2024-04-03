package dev.ebrydeu.spring_boot_library.controllers;

import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Validated @RequestBody UserDto dto) {
        return service.save(dto);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/firstname/{firstname}")
    public List<UserDto> findByFirstname(@PathVariable String firstname) {
        return service.findByFirstname(firstname);
    }

    @GetMapping("/lastname/{lastname}")
    public List<UserDto> findByLastname(@PathVariable String lastname) {
        return service.findByLastname(lastname);
    }

    @GetMapping("/username/{username}")
    public List<UserDto> findByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @GetMapping("/email/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fullUpdate(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        if (!service.isExists(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User user = UserDto.map(dto);
        user.setId(id);

        service.save(UserDto.map(user));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialUpdate(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
