package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        Long id,
        @NotBlank(message = "Profile name is mandatory")
        String username,
        String firstname,
        String lastname,
        String profilePicture,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email
) {
    public static UserDto map(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getProfilePicture(),
                entity.getEmail()
        );
    }

    public static User map(UserDto dto) {
        User user = new User();
        user.setId(dto.id);
        user.setUsername(dto.username);
        user.setFirstname(dto.firstname);
        user.setLastname(dto.lastname);
        user.setProfilePicture(dto.profilePicture);
        user.setEmail(dto.email);

        return user;
    }
}
