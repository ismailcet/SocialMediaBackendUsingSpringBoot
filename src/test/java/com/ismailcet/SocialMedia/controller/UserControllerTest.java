package com.ismailcet.SocialMedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByUsernameResponse;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(controllers = UserController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerTestIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @DisplayName("SignUp method CreateUserRequest is valid should User Created successfully and Return UserDto")
    @Test
    public void testSignUp_CreateUserRequestIsValid_shouldCreateUserSuccessfullyAndReturnUserDto() throws Exception {
        CreateUserRequest request =
                new CreateUserRequest("test-username","test-password","test-firstname","test-lastname","test-email",16);

        UserDto response =
                new UserDto.UserDtoBuilder()
                        .userName("test-username")
                        .firstName("test-firstname")
                        .lastName("test-lastname")
                        .email("test-email")
                        .age(16)
                        .build();
        when(userService.signup(request)).thenReturn(response);
        mockMvc.perform(
                post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.userName").value(request.getUsername()))
            .andExpect(jsonPath("$.email").value(request.getEmail()))
            .andExpect(jsonPath("$.firstName").value(request.getName()))
            .andExpect(jsonPath("$.lastName").value(request.getSurname()))
            .andExpect(jsonPath("$.age").value(request.getAge()));

        verify(userService).signup(any(CreateUserRequest.class));
    }
    @DisplayName("SignUp CreateUserRequest Is Not Valid should User Failed And Return 404 StatusCode")
    @Test
    public void testSignUp_CreateUserRequestIsNotValid_shouldUserFailedAndReturn404StatusCode() throws Exception {
        CreateUserRequest request =
                new CreateUserRequest();
        request.setUsername("test-username");
        request.setEmail("test-email");
        request.setSurname("test-surname");
        request.setPassword("test-password");
        request.setAge(16);

        mockMvc.perform(
                post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateUserById_whenUserIdIsValidAndUpdateUserRequestIsValid_shouldUserUpdatedSuccessfullyAndReturnUserDto() throws Exception {
        Integer userId = 1;

        UpdateUserRequest request =
                new UpdateUserRequest("test-usernameUpdate","test-password","test-firstname","test-lastname",16);

        UserDto response =
                new UserDto.UserDtoBuilder()
                        .userName("test-usernameUpdate")
                        .firstName("test-firstname")
                        .lastName("test-lastname")
                        .email("test-email")
                        .age(16)
                        .build();

        when(userService.updateUserById(userId, request)).thenReturn(response);

        mockMvc.perform(
                        put("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(request.getUsername()))
                .andExpect(jsonPath("$.firstName").value(request.getName()))
                .andExpect(jsonPath("$.lastName").value(request.getSurname()))
                .andExpect(jsonPath("$.age").value(request.getAge()));

        verify(userService).updateUserById(any(Integer.class), any(UpdateUserRequest.class));

    }
    @Test
    public void testUpdateUserById_whenUpdateUserRequestIsNotValid_shouldUserUpdatedFailedAndReturn404StatusCode() throws Exception {
        Integer userId = 1;
        UpdateUserRequest request =
                new UpdateUserRequest();
        request.setUsername("test-username");
        request.setSurname("test-surname");
        request.setPassword("test-password");
        request.setAge(16);

        mockMvc.perform(
                        put("/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testDeleteUserById_shouldUserDeletedSuccessfullyReturnNoContentStatusCode() throws Exception {
        Integer userId = 1;

        doNothing().when(userService).deleteUserById(userId);

        mockMvc.perform(
                delete("/users/"+userId))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById(any(Integer.class));
    }
    @Test
    public void testGetAllUsers_shouldeReturnListOfGetAllUsersResponseType() throws Exception {
        User newUser =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        newUser.setId(1);

        User newUser2 =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        newUser2.setId(2);

        GetAllUsersResponse user1 =
                new GetAllUsersResponse(newUser);
        GetAllUsersResponse user2 =
                new GetAllUsersResponse(newUser2);

        List<GetAllUsersResponse> users =
                Arrays.asList(user1, user2);
        when(userService.getAllUser()).thenReturn(users);


        mockMvc.perform(
                get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(user1))
                .andExpect(jsonPath("$[1]").value(user2));

        verify(userService).getAllUser();
    }
    @Test
    public void testGetUserById_shouldReturnGetUserByIdResponseType() throws Exception {
        Integer userId = 1;

        User newUser =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        newUser.setId(userId);

        GetUserByIdResponse user =
                new GetUserByIdResponse(newUser);

        when(userService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(
                get("/users/"+userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$").value(user));

        verify(userService).getUserById(any(Integer.class));
    }
    @Test
    public void testGetUserById_whenUserIdIsNotValid_shouldReturn400StatusCode() throws Exception {
        String userId="test-wrong";

        mockMvc.perform(
                get("/users/"+userId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testGetUserByUsername_shouldReturnGetUserByIdResponseType() throws Exception {
        String username = "test-username";

        User newUser =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);

        GetUserByUsernameResponse user =
                new GetUserByUsernameResponse(newUser);

        when(userService.getUserByUsername(username)).thenReturn(user);

        mockMvc.perform(
                        get("/users/getUsername/"+username))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$").value(user));

        verify(userService).getUserByUsername(any(String.class));
    }
}