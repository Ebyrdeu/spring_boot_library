package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.ebrydeu.spring_boot_library.TestDataUtils.createUserOne;

class UserDtoTest {


    @Test
    @DisplayName("UserDto returns User")
    void userDtoReturnsUser() {
        SoftAssertions soft = new SoftAssertions();
        UserDto dto = UserDto.map(createUserOne());

        User entity = UserDto.map(dto);

        soft.assertThat(entity.getId()).isEqualTo(dto.id());
        soft.assertThat(entity.getUserName()).isEqualTo(dto.username());
        soft.assertThat(entity.getFirstName()).isEqualTo(dto.firstName());
        soft.assertThat(entity.getLastName()).isEqualTo(dto.lastName());
        soft.assertThat(entity.getAvatar()).isEqualTo(dto.avatar());
        soft.assertThat(entity.getEmail()).isEqualTo(dto.email());
        soft.assertThat(entity.getRole()).isEqualTo(dto.role());
        soft.assertThat(entity.getGithubId()).isEqualTo(dto.githubId());

        soft.assertAll();
    }

    @Test
    @DisplayName("User returns UserDto")
    void userReturnsUserDto() {
        SoftAssertions soft = new SoftAssertions();
        User entity = createUserOne();

        UserDto dto = UserDto.map(entity);

        soft.assertThat(dto.id()).isEqualTo(entity.getId());
        soft.assertThat(dto.username()).isEqualTo(entity.getUserName());
        soft.assertThat(dto.firstName()).isEqualTo(entity.getFirstName());
        soft.assertThat(dto.lastName()).isEqualTo(entity.getLastName());
        soft.assertThat(dto.avatar()).isEqualTo(entity.getAvatar());
        soft.assertThat(dto.email()).isEqualTo(entity.getEmail());
        soft.assertThat(dto.role()).isEqualTo(entity.getRole());
        soft.assertThat(dto.githubId()).isEqualTo(entity.getGithubId());
        soft.assertAll();
    }
}