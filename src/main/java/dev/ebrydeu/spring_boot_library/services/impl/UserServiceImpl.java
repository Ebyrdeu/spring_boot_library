package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.exception.Exceptions;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import dev.ebrydeu.spring_boot_library.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static dev.ebrydeu.spring_boot_library.exception.Exceptions.EmailAlreadyExistsException;
import static dev.ebrydeu.spring_boot_library.exception.Exceptions.NotFoundException;
import static dev.ebrydeu.spring_boot_library.libs.Utils.isNullable;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    @CacheEvict("users")
    public UserDto save(UserDto dto) {
        Optional<User> existingEmail = repository.findByEmail(dto.email());

        if (existingEmail.isPresent() && !existingEmail.get().getId().equals(dto.id())) {
            throw new EmailAlreadyExistsException("Email already exists: " + dto.email());
        }


        User user = UserDto.map(dto);
        User savedUser = repository.save(user);
        return UserDto.map(savedUser);
    }


    @Override
    @Cacheable("users")
    public List<UserDto> findAll() {
        List<User> users = repository.findAll();
        return users.stream().map(UserDto::map).toList();
    }

    @Override
    @Cacheable("users")
    public UserDto findById(Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        return userOptional.map(UserDto::map).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    @Override
    @Cacheable("users")
    public List<UserDto> findByFirstname(String firstname) {
        List<User> users = repository.findByFirstName(firstname);
        return users.stream().map(UserDto::map).toList();
    }

    @Override
    @Cacheable("users")
    public List<UserDto> findByLastname(String lastname) {
        List<User> users = repository.findByLastName(lastname);
        return users.stream().map(UserDto::map).toList();
    }

    @Override
    @Cacheable("users")
    public List<UserDto> findByUsername(String userName) {
        Optional<User> users = repository.findByUserName(userName);
        return users.stream().map(UserDto::map).toList();
    }

    @Override
    @Cacheable("users")
    public UserDto findByEmail(String email) {
        User users = repository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        return UserDto.map(users);
    }

    @Override
    public boolean isExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Cacheable("users")
    public void partialUpdate(Long id, UserDto dto) {
        User exsitingUser = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        isNullable(exsitingUser::setUserName, dto.username());
        isNullable(exsitingUser::setFirstName, dto.firstName());
        isNullable(exsitingUser::setLastName, dto.lastName());
        isNullable(exsitingUser::setAvatar, dto.avatar());
        isNullable(exsitingUser::setEmail, dto.email());

        try {
            repository.save(exsitingUser);
        } catch (Exception e) {
            throw new Exceptions.InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Cacheable("users")
    public void delete(Long id) {
        User userToDelete = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        try {
            repository.deleteById(userToDelete.getId());
        } catch (Exception e) {
            throw new Exceptions.InternalServerErrorException(e.getMessage());
        }
    }
}