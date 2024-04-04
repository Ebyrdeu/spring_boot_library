package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
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

import static dev.ebrydeu.spring_boot_library.TestDataUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageRepositoryTest {
    private final MessageRepository repository;

    @Autowired
    public MessageRepositoryTest(MessageRepository repository) {
        this.repository = repository;
    }

    @Test
    @DisplayName("Message can be created and recalled")
    void messageCanBeCreatedAndRecalled() {
        User userOne = createUserOne();
        Message messageOne = createMessageOne(userOne);

        repository.save(messageOne);

        Optional<Message> result = repository.findById(messageOne.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(messageOne);
    }

    @Test
    @DisplayName("Multiply Messages can be created and recalled")
    void multiplyMessagesCanBeCreatedAndRecalled() {
        User userOne = createUserOne();
        Message messageOne = createMessageOne(userOne);
        repository.save(messageOne);

        Message messageTwo = createMessageTwo(userOne);
        repository.save(messageTwo);

        List<Message> result = repository.findAll();

        assertThat(result)
                .hasSize(2)
                .containsExactly(messageOne, messageTwo);
    }

    @Test
    @DisplayName("Message can be updated")
    void messageCanBeUpdated() {
        User userOne = createUserOne();
        Message messageOne = createMessageOne(userOne);
        repository.save(messageOne);

        messageOne.setPrivate(true);
        repository.save(messageOne);

        Optional<Message> result = repository.findById(messageOne.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(messageOne);

    }

    @Test
    @DisplayName("Message can be deleted")
    void messageCanBeDeleted() {

        User userOne = createUserOne();
        Message messageOne = createMessageOne(userOne);

        repository.save(messageOne);
        repository.deleteById(messageOne.getId());

        Optional<Message> result = repository.findById(messageOne.getId());

        assertThat(result).isEmpty();
    }
}