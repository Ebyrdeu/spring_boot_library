package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setProfileName("svenX");
        user.setFirstName("Sven");
        user.setLastName("Svensson");
        user.setProfilePicture("profile_picture_1");
        user.setEmail("svensson@example.com");

        savedUser = entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    void saveNewUserToDatabaseSuccessful() {

        assertThat(entityManager.find(User.class, savedUser.getId())).isEqualTo(savedUser);
    }

    @Test
    void deleteUserFromDatabaseSuccessful() {
        userRepository.delete(savedUser);

        assertThat(entityManager.find(User.class, savedUser.getId())).isNull();
    }

    @Test
    void findByProfileNameSuccessful() {
        List<User> retrievedUser = userRepository.findByProfileName("svenX");
        assertThat(retrievedUser).contains(savedUser);
    }

    @Test
    void findByFirstNameSuccessful() {
        List<User> retrievedUser = userRepository.findByFirstName("Sven");
        assertThat(retrievedUser).contains(savedUser);
    }

    @Test
    void findByLastNameSuccessful() {
        List<User> retrievedUser = userRepository.findByLastName("Svensson");
        assertThat(retrievedUser).contains(savedUser);
    }

    @Test
    void findByEmailSuccessful() {
        List<User> retrievedUser = userRepository.findByEmail("svensson@example.com");
        assertThat(retrievedUser).contains(savedUser);
    }
}