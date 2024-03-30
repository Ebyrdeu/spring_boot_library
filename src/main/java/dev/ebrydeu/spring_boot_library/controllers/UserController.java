package dev.ebrydeu.spring_boot_library.controllers;

import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable Long id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            return null;
      }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/firstName/{firstName}")
    public List<UserDto> findByFirstName(@PathVariable String firstName) {
        return userService.findByFirstName(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    public List<UserDto> findByLastName(@PathVariable String lastName) {
        return userService.findByLastName(lastName);
    }

    @GetMapping("/profileName/{profileName}")
    public List<UserDto> findByProfileName(@PathVariable String profileName) {
        return userService.findByProfileName(profileName);
    }

    @GetMapping("/email/{email}")
    public List<UserDto> findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
}
