package dev.ebrydeu.spring_boot_library.repositrories;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface UserRepository extends ListCrudRepository<User, Long> {
    List<User> findAll();
    List<User> findUserByIdAnd(Long id);
    List<User> findByProfileName(String profileName);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmail(String email);
}