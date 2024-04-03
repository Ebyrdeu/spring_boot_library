package dev.ebrydeu.spring_boot_library;

import dev.ebrydeu.spring_boot_library.domain.entities.User;

public class TestDataUtils {
    private TestDataUtils() {

    }


    public static User createUserOne() {
        return User.builder()
                .id(1L)
                .profileName("User One")
                .firstName("First One")
                .lastName("Last One")
                .profilePicture("One picture")
                .email("one@gmail.com")
                .build();
    }

    public static User createUserTwo() {
        return User.builder()
                .id(2L)
                .profileName("User Two")
                .firstName("First Two")
                .lastName("Last Two")
                .profilePicture("Two picture")
                .email("two@gmail.com")
                .build();
    }
    public static User createUserThree() {
        return User.builder()
                .id(2L)
                .profileName("User Three")
                .firstName("First Three")
                .lastName("Last Three")
                .profilePicture("Three picture")
                .email("three@gmail.com")
                .build();
    }

}
