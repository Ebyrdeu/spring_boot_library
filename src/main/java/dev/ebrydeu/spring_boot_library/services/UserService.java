package dev.ebrydeu.spring_boot_library.services;
import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.exception.CustomExceptions;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    @Cacheable("users")
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
    @Cacheable("users")
    public UserDto getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(UserDto::map)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("User not found with id: " + userId));
    }
    @CacheEvict("users")
    public UserDto createUser(UserDto userDto) {
        userRepository.findByEmail(userDto.email())
                .ifPresent(existingUser -> {
                    throw new CustomExceptions.EmailAlreadyExistsException("Email already exists: " + existingUser.getEmail());
                });

        User user = UserDto.map(userDto);
        User savedUser = userRepository.save(user);
        return UserDto.map(savedUser);
    }
    @CacheEvict("users")
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions.NotFoundException("User not found with id: " + userId));
        userRepository.deleteById(userId);
    }
    @Cacheable("users")
    public List<UserDto> findByFirstName(String firstName) {
        List<User> users = userRepository.findByFirstName(firstName);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
    @Cacheable("users")
    public List<UserDto> findByLastName(String lastName) {
        List<User> users = userRepository.findByLastName(lastName);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
    @Cacheable("users")
    public List<UserDto> findByProfileName(String profileName) {
        List<User> users = userRepository.findByProfileName(profileName);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }
    @Cacheable("users")
    public List<UserDto> findByEmail(String email) {
        Optional<User> users = userRepository.findByEmail(email);
        return users.stream()
                .map(UserDto::map)
                .collect(Collectors.toList());
    }

}