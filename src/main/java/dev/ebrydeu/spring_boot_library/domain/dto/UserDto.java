package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;

public record UserDto(
        Long id,
        String profileName,
        String firstName,
        String lastName,
        String profilePicture,
        String email) {

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
        user.setId(dto.id);
        user.setProfileName(dto.profileName);
        user.setFirstName(dto.firstName);
        user.setLastName(dto.lastName);
        user.setProfilePicture(dto.profilePicture);
        user.setEmail(dto.email);

        return user;
    }
}
