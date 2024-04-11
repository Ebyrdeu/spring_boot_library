package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.ebrydeu.spring_boot_library.TestDataUtils.createMessageOne;
import static dev.ebrydeu.spring_boot_library.TestDataUtils.createUserOne;

class MessageDtoTest {

    @Test
    @DisplayName("MessageDto returns Message")
    void messageDtoReturnsMessage() {
        SoftAssertions soft = new SoftAssertions();
        MessageDto dto = MessageDto.map(createMessageOne(createUserOne()));

        Message entity = MessageDto.map(dto);
        soft.assertThat(entity.getId()).isEqualTo(dto.id());
        soft.assertThat(entity.getTitle()).isEqualTo(dto.title());
        soft.assertThat(entity.getBody()).isEqualTo(dto.body());
        soft.assertThat(entity.getDate()).isEqualTo(dto.date());

        soft.assertThat(entity.getUser().getId()).isEqualTo(dto.user().id());
        soft.assertThat(entity.getUser().getUserName()).isEqualTo(dto.user().userName());

        soft.assertAll();

    }

    @Test
    @DisplayName("Message returns MessageDto")
    void messageReturnsMessageDto() {
        SoftAssertions soft = new SoftAssertions();
        Message entity = createMessageOne(createUserOne());

        MessageDto dto = MessageDto.map(entity);

        soft.assertThat(dto.id()).isEqualTo(entity.getId());
        soft.assertThat(dto.title()).isEqualTo(entity.getTitle());
        soft.assertThat(dto.body()).isEqualTo(entity.getBody());
        soft.assertThat(dto.date()).isEqualTo(entity.getDate());
        soft.assertThat(dto.isPrivate()).isEqualTo(entity.isPrivate());

        soft.assertThat(dto.user().id()).isEqualTo(entity.getUser().getId());
        soft.assertThat(dto.user().userName()).isEqualTo(entity.getUser().getUserName());

        soft.assertAll();
    }
}