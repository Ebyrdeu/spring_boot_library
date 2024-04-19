package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @NotNull
    private String userName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @URL
    private String profileImage;

    @Email
    private String email;

    public User toEntity() {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setProfileImage(profileImage);
        user.setEmail(email);
        return user;
    }
}