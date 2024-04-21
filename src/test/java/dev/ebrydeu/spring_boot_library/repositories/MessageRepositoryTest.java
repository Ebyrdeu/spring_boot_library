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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({AuditingConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageRepositoryTest {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;


    @Autowired
    public MessageRepositoryTest(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Message can be created and recalled")
    void messageCanBeCreatedAndRecalled() {
        User userOne = createUserOne();

        User user = userRepository.save(userOne);

        Message messageOne = createMessageOne(user);

        messageOne = messageRepository.save(messageOne);

        Optional<Message> result = messageRepository.findById(messageOne.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(messageOne);
    }

    @Test
    @DisplayName("Multiply Messages can be created and recalled")
    void multiplyMessagesCanBeCreatedAndRecalled() {
        User userOne = createUserOne();

        User user = userRepository.save(userOne);

        Message messageOne = createMessageOne(user);
        messageRepository.save(messageOne);

        Message messageTwo = createMessageTwo(userOne);
        messageRepository.save(messageTwo);

        List<Message> result = messageRepository.findAll();

        assertThat(result)
                .hasSize(2)
                .containsExactly(messageOne, messageTwo);
    }

    @Test
    @DisplayName("Message can be updated")
    void messageCanBeUpdated() {
        User userOne = createUserOne();

        User user = userRepository.save(userOne);

        Message messageOne = createMessageOne(user);
        messageOne = messageRepository.save(messageOne);

        messageOne.setPrivate(true);
        messageRepository.save(messageOne);

        Optional<Message> result = messageRepository.findById(messageOne.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(messageOne);

    }

    @Test
    @DisplayName("Message can be deleted")
    void messageCanBeDeleted() {

        User userOne = createUserOne();

        User user = userRepository.save(userOne);

        Message messageOne = createMessageOne(user);

        messageOne = messageRepository.save(messageOne);

        messageRepository.deleteById(messageOne.getId());

        Optional<Message> result = messageRepository.findById(messageOne.getId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Auditing fields are populated for Message")
    void auditingFieldsArePopulatedForMessage() {
        User userOne = createUserOne();
        User user = userRepository.save(userOne);

        Message message = createMessageOne(user);

        message = messageRepository.save(message);

        Optional<Message> fetchedMessage = messageRepository.findById(message.getId());
        assertThat(fetchedMessage).isPresent();

        Message persistedMessage = fetchedMessage.get();
        assertNotNull(persistedMessage.getCreationDate());
        assertNotNull(persistedMessage.getLastModifiedDate());
    }
}