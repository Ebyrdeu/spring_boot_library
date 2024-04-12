package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    Optional<User> findByUserName(String userName);
    Optional<User> findByGitHubId(Integer id);
    Optional<User> findByEmail(String email);


}