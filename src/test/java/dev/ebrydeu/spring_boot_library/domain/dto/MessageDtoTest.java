package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class MessageDtoTest {


    @Test
    @DisplayName("MessageDto returns Message")
    void messageDtoReturnsMessage() {
        SoftAssertions soft = new SoftAssertions();
        MessageDto dto = new MessageDto(
                1L, "test", "body", Instant.now(), true,
                new UserDto(1L, "name", "name", "name", "picture", "email")
        );
        Message entity = MessageDto.map(dto);
        soft.assertThat(entity.getId()).isEqualTo(dto.id());
        soft.assertThat(entity.getTitle()).isEqualTo(dto.title());
        soft.assertThat(entity.getBody()).isEqualTo(dto.body());
        soft.assertThat(entity.getDate()).isEqualTo(dto.date());

        soft.assertThat(entity.getUser().getId()).isEqualTo(dto.user().id());
        soft.assertThat(entity.getUser().getProfileName()).isEqualTo(dto.user().profileName());

        soft.assertAll();

    }

    @Test
    @DisplayName("Message returns MessageDto")
    void messageReturnsMessageDto() {
        SoftAssertions soft = new SoftAssertions();
        Message entity = Message.builder()
                .id(1L)
                .title("test")
                .body("body")
                .date(Instant.now())
                .messagePrivate(true)
                .user(User.builder().id(1L).profileName("profile").build())
                .build();

        MessageDto dto = MessageDto.map(entity);

        soft.assertThat(dto.id()).isEqualTo(entity.getId());
        soft.assertThat(dto.title()).isEqualTo(entity.getTitle());
        soft.assertThat(dto.body()).isEqualTo(entity.getBody());
        soft.assertThat(dto.date()).isEqualTo(entity.getDate());
        soft.assertThat(dto.messagePrivate()).isEqualTo(entity.isMessagePrivate());

        soft.assertThat(dto.user().id()).isEqualTo(entity.getUser().getId());
        soft.assertThat(dto.user().profileName()).isEqualTo(entity.getUser().getProfileName());


        soft.assertAll();
    }
}