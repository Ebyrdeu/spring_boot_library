package dev.ebrydeu.spring_boot_library;

import dev.ebrydeu.spring_boot_library.domain.entities.Message;
import dev.ebrydeu.spring_boot_library.domain.entities.User;

public class TestDataUtils {
    private TestDataUtils() {

    }


    public static User createUserOne() {
        return User.builder()
                .id(1L)
                .username("User One")
                .firstname("First One")
                .lastname("Last One")
                .profilePicture("One picture")
                .email("one@gmail.com")
                .build();
    }

    public static User createUserTwo() {
        return User.builder()
                .id(2L)
                .username("User Two")
                .firstname("First Two")
                .lastname("Last Two")
                .profilePicture("Two picture")
                .email("two@gmail.com")
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
