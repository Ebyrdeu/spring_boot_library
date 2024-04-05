package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.config.AuditingConfig;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static dev.ebrydeu.spring_boot_library.TestDataUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({AuditingConfig.class})
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

        messageOne = repository.save(messageOne);

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
        messageOne = repository.save(messageOne);

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

        messageOne = repository.save(messageOne);

        repository.deleteById(messageOne.getId());

        Optional<Message> result = repository.findById(messageOne.getId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Auditing fields are populated for Message")
    void auditingFieldsArePopulatedForMessage() {
        User user = createUserOne();

        Message message = createMessageOne(user);

        message = repository.save(message);

        Optional<Message> fetchedMessage = repository.findById(message.getId());
        assertThat(fetchedMessage).isPresent();

        Message persistedMessage = fetchedMessage.get();
        assertNotNull(persistedMessage.getCreationDate());
        assertEquals("testUser", persistedMessage.getCreatedBy());
        assertNotNull(persistedMessage.getLastModifiedDate());
        assertEquals("testUser", persistedMessage.getLastModifiedBy());
    }
}