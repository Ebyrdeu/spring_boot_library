//package dev.ebrydeu.spring_boot_library.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import dev.ebrydeu.spring_boot_library.config.SecurityConfigTest;
//import dev.ebrydeu.spring_boot_library.domain.dto.UserDto;
//import dev.ebrydeu.spring_boot_library.domain.entities.Message;
//import dev.ebrydeu.spring_boot_library.domain.entities.User;
//import dev.ebrydeu.spring_boot_library.services.impl.MessageService;
//import dev.ebrydeu.spring_boot_library.services.impl.UserService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static dev.ebrydeu.spring_boot_library.TestDataUtils.*;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureMockMvc(addFilters = false)
//@Import(SecurityConfigTest.class)
//class MessageControllerTest {
//
//    private final MessageService messageService;
//    private final UserService userService;
//
//    private final MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper;
//
//
//    @Autowired
//    public MessageControllerTest(MessageService messageService, UserService userService, MockMvc mockMvc) {
//        this.messageService = messageService;
//        this.userService = userService;
//        this.mockMvc = mockMvc;
//        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//    }
//
//
//    @Nested
//    @DisplayName("Http Status 200 Ok")
//    class HttpStatus200 {
//        @Test
//        @DisplayName("On successfully Messages get returns Http status 200 Ok")
//        void onSuccessfullyMessagesGetReturnsHttpStatus200Ok() throws Exception {
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk());
//        }
//
//        @Test
//        @DisplayName("On successfully Message get returns Http status 200 Ok")
//        void onSuccessfullyMessageGetReturnsHttpStatus200Ok() throws Exception {
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            MessageDto savedMessageDto = messageService.save(MessageDto.map(messageOne));
//
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/" + savedMessageDto.id())
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk());
//        }
//
//        @Test
//        @DisplayName("On successfully message title get returns Http status 200 Ok")
//        void onSuccessfullyMessageTitleGetReturnsHttpStatus200Ok() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//
//            messageOne.setUser(userOne);
//
//            MessageDto savedMessageDto = messageService.save(MessageDto.map(messageOne));
//
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/title/" + savedMessageDto.title())
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk());
//        }
//    }
//
//    @Nested
//    @DisplayName("Http Status 201 Created")
//    class HttpStatus201 {
//        @Test
//        @DisplayName("On successfully message creation returns Http status 201 Created")
//        void onSuccessfullyMessageCreationReturnsHttpStatus201Created() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            MessageDto savedMessageDto = messageService.save(MessageDto.map(messageOne));
//
//            String messageJson = objectMapper.writeValueAsString(savedMessageDto);
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(messageJson))
//                    .andExpect(MockMvcResultMatchers.status().isCreated());
//        }
//
//    }
//
//    @Nested
//    @DisplayName("Http Status 204 No Content")
//    class HttpStatus204 {
//
//        @Test
//        @DisplayName("On successfully Message deletion returns Http status 204 No Content")
//        void onSuccessfullyMessageDeletionReturnsHttpStatus204NoContent() throws Exception {
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            messageService.save(MessageDto.map(messageOne));
//
//            mockMvc.perform(MockMvcRequestBuilders.delete("/api/messages/1").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent());
//
//        }
//
//
//        @Test
//        @DisplayName("On successfully Message full update returns Http Status 204 No Content")
//        void onSuccessfullyMessageFullUpdateReturnsHttpStatus204NoContent() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            messageService.save(MessageDto.map(messageOne));
//
//            MessageDto existingMessage = messageService.findById(messageOne.getId());
//            String messageJson = objectMapper.writeValueAsString(existingMessage);
//
//            mockMvc.perform(MockMvcRequestBuilders.put("/api/messages/1").contentType(MediaType.APPLICATION_JSON).content(messageJson))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent());
//        }
//
//        @Test
//        @DisplayName("On successfully Message partial update returns Http Status 204 No Content")
//        void onSuccessfullyMessagePartialUpdateReturnsHttpStatus204NoContent() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            messageService.save(MessageDto.map(messageOne));
//
//            MessageDto existingMessage = messageService.findById(messageOne.getId());
//            Message message = MessageDto.map(existingMessage);
//            message.setPrivate(true);
//
//            String messageJson = objectMapper.writeValueAsString(message);
//
//            mockMvc.perform(MockMvcRequestBuilders.put("/api/messages/1").contentType(MediaType.APPLICATION_JSON).content(messageJson))
//                    .andExpect(MockMvcResultMatchers.status().isNoContent());
//        }
//    }
//
//    @Nested
//    @DisplayName("Http Status 404 Not Found")
//    class HttpStatus404 {
//        @Test
//        @DisplayName("On unsuccessfully Message get returns Http status 404 Not Found")
//        void onUnsuccessfullyMessageGetReturnsHttpStatus404NotFound() throws Exception {
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/1").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        @DisplayName("On unsuccessfully Message deletion returns Http status 404 Not Found")
//        void onUnsuccessfullyMessageDeletionReturnsHttpStatus404NotFound() throws Exception {
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/1").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//
//        @Test
//        @DisplayName("On unsuccessfully User full update returns Http status 404 Not Found")
//        void onUnsuccessfullyUserFullUpdateReturnsHttpStatus404NotFound() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setId(null);
//            messageOne.setUser(userOne);
//            String messageJson = objectMapper.writeValueAsString(MessageDto.map(messageOne));
//
//
//            mockMvc.perform(MockMvcRequestBuilders.put("/api/messages/1").contentType(MediaType.APPLICATION_JSON).content(messageJson))
//                    .andExpect(MockMvcResultMatchers.status().isNotFound());
//        }
//    }
//
//    @Nested
//    @DisplayName("Data Verification")
//    class DataVerification {
//        @Test
//        @DisplayName("On successfully Message creation returns saved Message")
//        void onSuccessfullyMessageCreationReturnsSavedMessage() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            String messageJson = objectMapper.writeValueAsString(MessageDto.map(messageOne));
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/messages").contentType(MediaType.APPLICATION_JSON).content(messageJson))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Title One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.body").value("Body One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.isPrivate").value(false))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").exists());
//
//        }
//
//        @Test
//        @DisplayName("On successfully Messages get returns list of Messages")
//        void onSuccessfullyMessagesGetReturnsListOfMessages() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            messageService.save(MessageDto.map(messageOne));
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").exists())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Title One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].body").value("Body One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].isPrivate").value(false))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].user.id").exists());
//        }
//
//        @Test
//        @DisplayName("On successfully Message get returns Message")
//        void onSuccessfullyMessageGetReturnsMessage() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            messageService.save(MessageDto.map(messageOne));
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/1").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Title One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.body").value("Body One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.isPrivate").value(false))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").exists());
//        }
//
//        @Test
//        @DisplayName("On successfully Message title returns list of Messages")
//        void onSuccessfullyMessageTitleReturnsListOfMessages() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//
//            messageService.save(MessageDto.map(messageOne));
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/title/Title One").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").exists())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Title One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].body").value("Body One"))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].isPrivate").value(false))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].user.id").exists());
//        }
//
//        @Test
//        @DisplayName("On successfully Message full update get  returns fully updated Message")
//        void onSuccessfullyMessageFullUpdateGetReturnsFullyUpdatedMessage() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//            messageService.save(MessageDto.map(messageOne));
//
//            Message messageTwo = createMessageTwo(UserDto.map(userDto));
//            messageTwo.setId(userOne.getId());
//            messageTwo.setUser(userOne);
//            messageService.save(MessageDto.map(messageTwo));
//
//            String messageJson = objectMapper.writeValueAsString(messageTwo);
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/1").contentType(MediaType.APPLICATION_JSON).content(messageJson))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(messageTwo.getId()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(messageTwo.getTitle()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.body").value(messageTwo.getBody()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.isPrivate").value(messageTwo.isPrivate()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(messageTwo.getUser().getId()));
//        }
//
//        @Test
//        @DisplayName("On successfully Message partial update get returns partially updated Message")
//        void onSuccessfullyMessagePartialUpdateGetReturnsPartiallyUpdatedMessage() throws Exception {
//
//            User userOne = createUserOne();
//            UserDto userDto = userService.save(UserDto.map(userOne));
//
//            Message messageOne = createMessageOne(UserDto.map(userDto));
//            messageOne.setUser(userOne);
//            messageService.save(MessageDto.map(messageOne));
//
//            Message messageTwo = createMessageTwo(UserDto.map(userDto));
//            messageTwo.setId(userOne.getId());
//            messageTwo.setPrivate(true);
//            messageTwo.setUser(userOne);
//            messageService.save(MessageDto.map(messageTwo));
//
//            String messageJson = objectMapper.writeValueAsString(messageTwo);
//
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/1").contentType(MediaType.APPLICATION_JSON).content(messageJson))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(messageTwo.getId()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(messageTwo.getTitle()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.body").value(messageTwo.getBody()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.isPrivate").value(messageTwo.isPrivate()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.user.id").value(messageTwo.getUser().getId()));
//        }
//    }
//
//}