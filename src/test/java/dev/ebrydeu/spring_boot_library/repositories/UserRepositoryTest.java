package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static dev.ebrydeu.spring_boot_library.TestDataUtils.createUserOne;
import static dev.ebrydeu.spring_boot_library.TestDataUtils.createUserTwo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {

    private final UserRepository repository;

    @Autowired
    public UserRepositoryTest(UserRepository repository) {
        this.repository = repository;
    }

    @Test
    @DisplayName("User can be created and recalled")
    void userCanBeCreatedAndRecalled() {
        User user = createUserOne();
        repository.save(user);

        Optional<User> result = repository.findById(user.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }


    @Test
    @DisplayName("Multiply Users can be created and recalled")
    void multiplyUsersCanBeCreatedAndRecalled() {
        User userOne = createUserOne();
        repository.save(userOne);

        User userTwo = createUserTwo();
        repository.save(userTwo);

        List<User> result = repository.findAll();

        assertThat(result)
                .hasSize(2)
                .containsExactly(userOne, userTwo);
    }


    @Test
    @DisplayName("User can be updated")
    void userCanBeUpdated() {
        User userOne = createUserOne();
        repository.save(userOne);

        userOne.setEmail("updated@gmail.com");
        repository.save(userOne);

        Optional<User> result = repository.findById(userOne.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userOne);
    }


    @Test
    @DisplayName("User can be deleted")
    void userCanBeDeleted() {
        User userOne = createUserOne();
        repository.save(userOne);
        repository.deleteById(userOne.getId());

        Optional<User> result = repository.findById(userOne.getId());

        assertThat(result).isEmpty();
    }

}