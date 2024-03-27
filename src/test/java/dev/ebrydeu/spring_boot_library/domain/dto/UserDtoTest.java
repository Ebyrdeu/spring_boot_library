package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserDtoTest {


    @Test
    @DisplayName("UserDto returns User")
    void userDtoReturnsUser() {
        SoftAssertions soft = new SoftAssertions();
        UserDto dto = new UserDto(
                1L,
                "profile",
                "first",
                "last",
                "picture",
                "email"
        );

        User entity = UserDto.map(dto);

        soft.assertThat(entity.getId()).isEqualTo(dto.id());
        soft.assertThat(entity.getProfileName()).isEqualTo(dto.profileName());
        soft.assertThat(entity.getFirstName()).isEqualTo(dto.firstName());
        soft.assertThat(entity.getLastName()).isEqualTo(dto.lastName());
        soft.assertThat(entity.getProfilePicture()).isEqualTo(dto.profilePicture());
        soft.assertThat(entity.getEmail()).isEqualTo(dto.email());

        soft.assertAll();
    }

    @Test
    @DisplayName("User returns UserDto")
    void userReturnsUserDto() {
        SoftAssertions soft = new SoftAssertions();
        User entity = User.builder()
                .id(1L)
                .profileName("profile")
                .firstName("first")
                .lastName("last")
                .profilePicture("picture")
                .email("email")
                .build();
        UserDto dto = UserDto.map(entity);

        soft.assertThat(dto.id()).isEqualTo(entity.getId());
        soft.assertThat(dto.profileName()).isEqualTo(entity.getProfileName());
        soft.assertThat(dto.firstName()).isEqualTo(entity.getFirstName());
        soft.assertThat(dto.lastName()).isEqualTo(entity.getLastName());
        soft.assertThat(dto.profilePicture()).isEqualTo(entity.getProfilePicture());
        soft.assertThat(dto.email()).isEqualTo(entity.getEmail());

        soft.assertAll();
    }
}