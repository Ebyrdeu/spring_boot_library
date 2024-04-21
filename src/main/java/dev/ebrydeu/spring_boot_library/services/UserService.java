package dev.ebrydeu.spring_boot_library.services;

import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto save(UserDto dto);

    List<UserDto> findAll();

    UserDto findById(Long userId);
    UserDto findByGithubId(Integer githubId);

    List<UserDto> findByFirstname(String firstname);

    List<UserDto> findByLastname(String lastname);

    List<UserDto> findByUsername(String username);

    UserDto findByEmail(String email);

    boolean isExists(Long id);

    void partialUpdate(Integer id, UserDto dto);

    void delete(Long id);
}
