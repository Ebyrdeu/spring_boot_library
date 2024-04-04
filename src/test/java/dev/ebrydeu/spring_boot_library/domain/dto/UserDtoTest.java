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
        soft.assertThat(entity.getUsername()).isEqualTo(dto.username());
        soft.assertThat(entity.getFirstname()).isEqualTo(dto.firstname());
        soft.assertThat(entity.getLastname()).isEqualTo(dto.lastname());
        soft.assertThat(entity.getProfilePicture()).isEqualTo(dto.profilePicture());
        soft.assertThat(entity.getEmail()).isEqualTo(dto.email());

        soft.assertAll();
    }

    @Test
    @DisplayName("User returns UserDto")
    void userReturnsUserDto() {
        SoftAssertions soft = new SoftAssertions();
        User entity = createUserOne();

        UserDto dto = UserDto.map(entity);

        soft.assertThat(dto.id()).isEqualTo(entity.getId());
        soft.assertThat(dto.username()).isEqualTo(entity.getUsername());
        soft.assertThat(dto.firstname()).isEqualTo(entity.getFirstname());
        soft.assertThat(dto.lastname()).isEqualTo(entity.getLastname());
        soft.assertThat(dto.profilePicture()).isEqualTo(entity.getProfilePicture());
        soft.assertThat(dto.email()).isEqualTo(entity.getEmail());

        soft.assertAll();
    }
}