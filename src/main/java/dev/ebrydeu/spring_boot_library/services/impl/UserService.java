package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.exception.Exceptions;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Cacheable("username")
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findByGitHubId(Integer githubId) {
        return userRepository.findByGithubId(githubId);

    }
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
    }

    @CacheEvict(value = {"email", "username"}, allEntries = true)
    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}