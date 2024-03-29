package dev.ebrydeu.spring_boot_library.services;
import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.exception.CustomExceptions;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserService  {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
    public UserDto getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(UserDto::map)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("User not found with id: " + userId));
    }
    public UserDto createUser(UserDto userDto) {
        if (!userRepository.findByEmail(userDto.email()).isEmpty()) {
            throw new CustomExceptions.EmailAlreadyExistsException("Email already exists: " + userDto.email());
        }
        User user = UserDto.map(userDto);
        User savedUser = userRepository.save(user);
        return UserDto.map(savedUser);
    }
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("User not found with id: " + userId));
        userRepository.deleteById(userId);
    }

    public List<UserDto> findByFirstName(String firstName) {
        List<User> users = userRepository.findByFirstName(firstName);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByLastName(String lastName) {
        List<User> users = userRepository.findByLastName(lastName);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByProfileName(String profileName) {
        List<User> users = userRepository.findByProfileName(profileName);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
    public List<UserDto> findByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
}