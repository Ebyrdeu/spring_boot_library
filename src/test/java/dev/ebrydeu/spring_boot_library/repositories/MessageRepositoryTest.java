package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.config.AuditingConfig;
import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.services.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.ebrydeu.spring_boot_library.TestDataUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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


    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService; // Assuming you have a service class where you use the repository

    @Test
    @DisplayName("Messages can be found by id with pagination")
    void messagesCanBeFoundByIdWithPagination() {
        // Create sample test data
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder().id(1L).title("Title 1").body("Body 1").date(Instant.now()).build());
        messages.add(Message.builder().id(2L).title("Title 2").body("Body 2").date(Instant.now()).build());
        messages.add(Message.builder().id(3L).title("Title 3").body("Body 3").date(Instant.now()).build());
        messages.add(Message.builder().id(4L).title("Title 4").body("Body 4").date(Instant.now()).build());
        messages.add(Message.builder().id(5L).title("Title 5").body("Body 5").date(Instant.now()).build());

        // Mock the behavior of the repository method
        when(messageRepository.findById(3L, 2)).thenReturn(messages.subList(1, 3)); // return 2 messages starting from index 1

        // Call the method under test
        List<Message> result = messageService.findById(3L, 2); // Assuming your service has a method to retrieve messages by id with pagination

        // Assert the results
        assertThat(result)
                .hasSize(2)
                .containsExactly(messages.get(1), messages.get(2));
    }
}