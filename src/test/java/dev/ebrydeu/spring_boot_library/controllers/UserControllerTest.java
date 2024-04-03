package dev.ebrydeu.spring_boot_library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ebrydeu.spring_boot_library.TestDataUtils;
import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    private final UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(UserService userService, MockMvc mockMvc) {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("Http Status 200 Ok")
    class HttpStatus200 {
        @Test
        @DisplayName("On successfully Users get returns Http status 200 Ok")
        void onSuccessfullyUsersGetReturnsHttpStatus200Ok() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully User get returns Http status 200 Ok")
        void onSuccessfullyUserGetReturnsHttpStatus200Ok() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully User firstname get returns Http Status 200 Ok")
        void onSuccessfullyUserFirstnameGetReturnsHttpStatus200Ok() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/users/firstName/First One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("One successfully User lastname get returns Http Status 200 Ok")
        void oneSuccessfullyUserLastnameGetReturnsHttpStatus200Ok() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users/lastName/Last One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully user profile name get returns Http status 200 Ok")
        void onSuccessfullyUserProfileNameGetReturnsHttpStatus200Ok() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users/profileName/User One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully user email get returns Http status 200 Ok")
        void onSuccessfullyUserEmailGetReturnsHttpStatus200Ok() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.get("/users/email/one@gmail.com").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Nested
    @DisplayName("Http Status 201 Created")
    class HttpStatus201 {
        @Test
        @DisplayName("On successfully User creation returns Http status 201 Created")
        void onSuccessfullyUserCreationReturnsHttpStatus201Created() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            String userJson = objectMapper.writeValueAsString(UserDto.map(userOne));
            mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }

    @Nested
    @DisplayName("Http Status 204 No Content")
    class HttpStatus204 {
        @Test
        @DisplayName("On successfully User deletion returns Http status 204 No Content")
        void onSuccessfullyUserDeletionReturnsHttpStatus204NoContent() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Http Status 404 Not Found")
    class HttpStatus404 {
        @Test
        @DisplayName("On unsuccessfully User get returns Http status 404 Not Found")
        void onUnsuccessfullyUserGetReturnsHttpStatus404NotFound() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }


        @Test
        @DisplayName("On unsuccessfully User deletion returns Http status 404 Not Found")
        void onUnsuccessfullyUserDeletionReturnsHttpStatus404NotFound() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Http Status 409 Conflict")
    class HttpStatus409 {
        @Test
        @DisplayName("On unsuccessfully User creation with existing email returns Http status 409 Conflict")
        void onUnsuccessfullyUserCreationWithExistingEmailReturnsHttpStatus409Conflict() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));

            String userJson = objectMapper.writeValueAsString(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isConflict());
        }

    }


    @Nested
    @DisplayName("Data Verification")
    class DataVerification {
        @Test
        @DisplayName("On successfully User creation returns saved User")
        void onSuccessfullyUserCreationReturnsSavedUser() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            String userJson = objectMapper.writeValueAsString(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully Users get returns list of Users")
        void onSuccessfullyUsersGetReturnsListOfUsers() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));
        }


        @Test
        @DisplayName("On successfully User get returns User")
        void onSuccessfullyUserGetReturnsUser() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User firstname get returns list of Users")
        void onSuccessfullyUserFirstnameGetReturnsListOfUsers() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/firstName/First One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));

        }

        @Test
        @DisplayName("On successfully User lastname get returns list of Users")
        void onSuccessfullyUserLastnameGetReturnsListOfUsers() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/lastName/Last One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User profile name get returns list of Users")
        void onSuccessfullyUserProfileNameGetReturnsListOfUsers() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/profileName/User One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User email get returns list of Users")
        void onSuccessfullyUserEmailGetReturnsListOfUsers() throws Exception {
            User userOne = TestDataUtils.createUserOne();
            userOne.setId(null);

            userService.createUser(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/email/one@gmail.com").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profileName").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("one@gmail.com"));
        }
    }


}