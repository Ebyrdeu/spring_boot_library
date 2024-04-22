package dev.ebrydeu.spring_boot_library.domain.dto;

import dev.ebrydeu.spring_boot_library.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 50)
    private String userName;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @Email
    private String email;

    @URL
    private String profileImage;


    public User toEntity() {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setProfileImage(profileImage);
        return user;
    }
}