package dev.ebrydeu.spring_boot_library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static dev.ebrydeu.spring_boot_library.TestDataUtils.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    private final UserService service;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(UserService service, MockMvc mockMvc) {
        this.service = service;
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
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully User firstname get returns Http Status 200 Ok")
        void onSuccessfullyUserFirstnameGetReturnsHttpStatus200Ok() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/users/firstname/First One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("One successfully User lastname get returns Http Status 200 Ok")
        void oneSuccessfullyUserLastnameGetReturnsHttpStatus200Ok() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users/lastname/Last One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully user profile name get returns Http status 200 Ok")
        void onSuccessfullyUserProfileNameGetReturnsHttpStatus200Ok() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/users/username/User One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("On successfully user email get returns Http status 200 Ok")
        void onSuccessfullyUserEmailGetReturnsHttpStatus200Ok() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));

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
            User userOne = createUserOne();
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
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        @DisplayName("On successfully User full update returns Http Status 204 No Content")
        void onSuccessfullyUserFullUpdateReturnsHttpStatus204NoContent() throws Exception {
            User userOne = createUserOne();
            service.save(UserDto.map(userOne));

            UserDto existingUser = service.findById(userOne.getId());
            String userJson = objectMapper.writeValueAsString(existingUser);

            mockMvc.perform(MockMvcRequestBuilders.put("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        @DisplayName("On successfully User partial update returns Http Status 204 No Content")
        void onSuccessfullyUserPartialUpdateReturnsHttpStatus204NoContent() throws Exception {
            User userOne = createUserOne();
            service.save(UserDto.map(userOne));

            UserDto existingUser = service.findById(userOne.getId());

            User user = UserDto.map(existingUser);
            user.setEmail("updated@gmail.com");

            String userJson = objectMapper.writeValueAsString(UserDto.map(user));

            mockMvc.perform(MockMvcRequestBuilders.patch("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson))
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

        @Test
        @DisplayName("On unsuccessfully User full update returns Http status 404 Not Found")
        void onUnsuccessfullyUserFullUpdateReturnsHttpStatus404NotFound() throws Exception {
            User user = createUserOne();
            String userJson = objectMapper.writeValueAsString(UserDto.map(user));

            mockMvc.perform(MockMvcRequestBuilders.put("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("On unsuccessfully User partial update returns Http status 404 Not Found")
        void onUnsuccessfullyUserPartialUpdateReturnsHttpStatus404NotFound() throws Exception {
            User user = createUserOne();
            String userJson = objectMapper.writeValueAsString(UserDto.map(user));

            mockMvc.perform(MockMvcRequestBuilders.patch("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Http Status 409 Conflict")
    class HttpStatus409 {
        @Test
        @DisplayName("On unsuccessfully User creation with existing email returns Http status 409 Conflict")
        void onUnsuccessfullyUserCreationWithExistingEmailReturnsHttpStatus409Conflict() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));

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
            User userOne = createUserOne();
            userOne.setId(null);

            String userJson = objectMapper.writeValueAsString(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully Users get returns list of Users")
        void onSuccessfullyUsersGetReturnsListOfUsers() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));
        }


        @Test
        @DisplayName("On successfully User get returns User")
        void onSuccessfullyUserGetReturnsUser() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));

            mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User firstname get returns list of Users")
        void onSuccessfullyUserFirstnameGetReturnsListOfUsers() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/firstname/First One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));

        }

        @Test
        @DisplayName("On successfully User lastname get returns list of Users")
        void onSuccessfullyUserLastnameGetReturnsListOfUsers() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/lastname/Last One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User profile name get returns list of Users")
        void onSuccessfullyUserProfileNameGetReturnsListOfUsers() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/username/User One").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User email get returns list of Users")
        void onSuccessfullyUserEmailGetReturnsListOfUsers() throws Exception {
            User userOne = createUserOne();
            userOne.setId(null);

            service.save(UserDto.map(userOne));


            mockMvc.perform(MockMvcRequestBuilders.get("/users/email/one@gmail.com").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("First One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Last One"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("One picture"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("one@gmail.com"));
        }

        @Test
        @DisplayName("On successfully User full update get returns fully updated User")
        void onSuccessfullyUserFullUpdateGetReturnsFullyUpdatedUser() throws Exception {
            User userOne = createUserOne();
            service.save(UserDto.map(userOne));

            User userTwo = createUserTwo();
            userTwo.setId(userOne.getId());
            service.save(UserDto.map(userTwo));

            String userJson = objectMapper.writeValueAsString(userTwo);

            mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userTwo.getUsername()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(userTwo.getFirstname()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(userTwo.getLastname()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value(userTwo.getProfilePicture()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userTwo.getEmail()));

        }

        @Test
        @DisplayName("On successfully User partial update get returns partially updated User")
        void onSuccessfullyUserPartialUpdateGetReturnsPartiallyUpdatedUser() throws Exception {
            User userOne = createUserOne();
            service.save(UserDto.map(userOne));

            User user = createUserOne();
            user.setFirstname(null);
            user.setLastname(null);
            user.setProfilePicture(null);
            user.setEmail("updated@gmail.com");

            service.save(UserDto.map(user));

            String userJson = objectMapper.writeValueAsString(user);

            mockMvc.perform(MockMvcRequestBuilders.get("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(user.getFirstname()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(user.getLastname()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value(user.getProfilePicture()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated@gmail.com"));

        }
    }


}