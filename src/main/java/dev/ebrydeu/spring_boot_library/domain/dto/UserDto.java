package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.Role;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "User")
public record UserDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
        Long id,

        @NotBlank(message = "Profile name is mandatory")
        @Schema(example = "SvenSvenson")
        String username,

        @Schema(example = "Sven")
        String firstName,

        @Schema(example = "Svenson")
        String lastName,

        @Schema(example = "https://profile-picutre.png")
        String avatar,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        @Schema(example = "sven.svenson@gmail.com")
        String email,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Role role,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "123123")
        Integer githubId
) {
    public static UserDto map(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getUserName(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAvatar(),
                entity.getEmail(),
                entity.getRole(),
                entity.getGithubId()
        );
    }

    public static User map(UserDto dto) {
        User user = new User();
        user.setId(dto.id);
        user.setUserName(dto.username);
        user.setFirstName(dto.firstName);
        user.setLastName(dto.lastName);
        user.setAvatar(dto.avatar);
        user.setEmail(dto.email);
        user.setRole(dto.role);
        user.setGithubId(dto.githubId);
        return user;
    }
}
