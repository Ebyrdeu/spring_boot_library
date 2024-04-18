package dev.ebrydeu.spring_boot_library;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.Role;
import dev.ebrydeu.spring_boot_library.domain.entities.User;

public class TestDataUtils {
    private TestDataUtils() {

    }


    public static User createUserOne() {
        return User.builder()
                .id(1L)
                .userName("User One")
                .firstName("First One")
                .lastName("Last One")
                .avatar("One picture")
                .email("one@gmail.com")
                .role(Role.ROLE_USER)
                .githubId(123123123)
                .build();
    }

    public static User createUserTwo() {
        return User.builder()
                .id(2L)
                .userName("User Two")
                .firstName("First Two")
                .lastName("Last Two")
                .avatar("Two picture")
                .role(Role.ROLE_ADMIN)
                .email("two@gmail.com")
                .githubId(32131231)
                .build();
    }

    public static Message createMessageOne(User user) {
        return Message.builder()
                .id(1L)
                .title("Title One")
                .body("Body One")
                .user(user)
                .build();
    }
    public static Message createMessageTwo(User user) {
        return Message.builder()
                .id(2L)
                .title("Title Two")
                .body("Body Two")
                .user(user)
                .build();
    }
}
