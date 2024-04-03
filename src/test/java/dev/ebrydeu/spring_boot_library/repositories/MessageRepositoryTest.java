package dev.ebrydeu.spring_boot_library.repositories;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Message savedMessage;
    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setProfileName("svenX");
        user.setFirstName("Sven");
        user.setLastName("Svensson");
        user.setProfilePicture("profile_picture_1");
        user.setEmail("svensson@example.com");

        Message message = new Message();
        message.setTitle("Test Title");
        message.setBody("Test Body");
        message.setDate(Instant.now());
        message.setMessagePrivate(false);
        message.setUser(user);

        savedUser = entityManager.persist(user);
        savedMessage = entityManager.persist(message);

        entityManager.flush();
    }

    @Test
    void saveNewMessageToDatabaseSuccessful() {

        assertThat(entityManager.find(Message.class, savedMessage.getId())).isEqualTo(savedMessage);
    }

    @Test
    void deleteMessageFromDatabaseSuccessful() {
        messageRepository.delete(savedMessage);

        assertThat(entityManager.find(Message.class, savedMessage.getId())).isNull();
    }

    @Test
    void messageFindByTitleSuccessful() {
        List<Message> retrievedMessage = messageRepository.findByTitle("Test Title");
        assertThat(retrievedMessage).contains(savedMessage);
    }

    @Test
    void editMessageBodySuccessful() {
        String newBody = "New Body";
        Long id = savedMessage.getId();

        messageRepository.editMessageBody(newBody, id);
        entityManager.clear();
        Message updatedMessage = entityManager.find(Message.class, savedMessage.getId());

        assertThat(updatedMessage.getBody()).isEqualTo("New Body");
    }

    @Test
    void editMessageTitleSuccessful() {
        String newTitle = "New Title";
        Long id = savedMessage.getId();

        messageRepository.editMessageTitle(newTitle, id);
        entityManager.clear();
        Message updatedMessage = entityManager.find(Message.class, savedMessage.getId());

        assertThat(updatedMessage.getTitle()).isEqualTo("New Title");
    }

    @Test
    void setMessagePublicSuccessful() {
        boolean messagePublic = Boolean.parseBoolean("true");
        Long id = savedMessage.getId();

        messageRepository.setMessagePrivate(messagePublic, id);
        entityManager.clear();
        Message updatedMessage = entityManager.find(Message.class, savedMessage.getId());

        assertThat(updatedMessage.isMessagePrivate()).isTrue();
    }
}