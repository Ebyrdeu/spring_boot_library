package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        Long id,
        @NotBlank(message = "Profile name is mandatory")
        String profileName,
        @NotBlank(message = "First name is mandatory")
        String firstName,
        @NotBlank(message = "Last name is mandatory")
        String lastName,
        String profilePicture,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email
) {
    public static UserDto map(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getProfileName(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getProfilePicture(),
                entity.getEmail()
        );
    }

    public static User map(UserDto dto) {
        User user = new User();
        user.setId(dto.id());
        user.setProfileName(dto.profileName());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setProfilePicture(dto.profilePicture());
        user.setEmail(dto.email());

        return user;
    }
}
