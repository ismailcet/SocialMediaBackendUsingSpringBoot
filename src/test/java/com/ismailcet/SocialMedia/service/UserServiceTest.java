package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByUsernameResponse;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.UserNotFoundException;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;
    private AutoCloseable closeable;
    @BeforeEach
    public void setUp(){
        userRepository = mock(UserRepository.class);
        userDtoConverter = mock(UserDtoConverter.class);

        userService = new UserService(userRepository, userDtoConverter);
    }
    @DisplayName("When Username Is Not Already Taken Should Return UserDto")
    @Test
    public void testSignUp_whenUsernameIsNotExist_shouldReturnUserDto(){
        String username = "test-username";
        CreateUserRequest givenUser =
                new CreateUserRequest("test-username", "test-password", "test-name", "test-surname", "test-email",16);

        User expectedUser =
                new User.UserBuilder().userName("test-username").firstName("test-name").lastName("test-surname").email("test-email").password("test-password").age(16).build();

        UserDto exceptedResult = new UserDto.UserDtoBuilder()
                .userName(expectedUser.getUserName())
                .firstName(expectedUser.getFirstName())
                .lastName(expectedUser.getLastName())
                .email(expectedUser.getEmail())
                .age(expectedUser.getAge())
                .build();

        when(userRepository.findByUserName(username)).thenReturn(null);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedUser);
        when(userDtoConverter.convert(Mockito.any(User.class))).thenReturn(exceptedResult);

        UserDto convertedUser = userDtoConverter.convert(expectedUser);
        UserDto result = userService.signup(givenUser);

        assertEquals(convertedUser , exceptedResult);
        assertEquals(result , exceptedResult);

        verify(userRepository).findByUserName(username);
        verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(userDtoConverter,new Times(2)).convert(Mockito.any(User.class));
    }
    @DisplayName("When Username Already Taken Should Return UserNotFoundException")
    @Test
    public void testSignUp_whenUsernameAlreadyTaken_shouldReturnUserNotFoundException(){
        String username = "test-username";
        CreateUserRequest givenUser =
                new CreateUserRequest("test-username", "test-password", "test-name", "test-surname", "test-email",16);
        User expectedUser =
                new User.UserBuilder().userName("test-username").firstName("test-name").lastName("test-surname").email("test-email").password("test-password").age(16).build();

        UserNotFoundException exceptedException =
                new UserNotFoundException("Username already is taken ! ");

        when(userRepository.findByUserName(username)).thenReturn(expectedUser);

        UserNotFoundException resultException =
                assertThrows(UserNotFoundException.class,
                        ()->userService.signup(givenUser));

        assertEquals(exceptedException.getMessage() , resultException.getMessage());

        verify(userRepository).findByUserName(username);

    }
    @DisplayName("Update UserById when UserId is exist should return UserDto")
    @Test
    public void testUpdateUserById_whenUserIdIsExist_shouldReturnUserDto(){
        Integer userId = 1;

        User optionalUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-name")
                .lastName("test-surname")
                .email("test-email")
                .password("test-password")
                .age(16)
                .build();

        UpdateUserRequest updateUser =
                new UpdateUserRequest("test-username-updated","test-password","test-name","test-surname",16);
        User updatedUser =
                new User("test-username-updated","test-password","test-name","test-surname","test-email",16);

        UserDto actual = new UserDto(updatedUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(optionalUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userDtoConverter.convert(Mockito.any(User.class))).thenReturn(actual);


        UserDto result = userService.updateUserById(userId, updateUser);

        assertEquals(actual, result);

        verify(userRepository).findById(userId);
        verify(userRepository).save(Mockito.any(User.class));
        verify(userDtoConverter).convert(Mockito.any(User.class));
    }
    @DisplayName("Update UserById when UserId is not exist should Return UserNotFoundException")
    @Test
    public void testUpdateUserById_whenUserIdIsNotExist_shouldReturnUserNotFoundException(){
        Integer userId = 1;
        UpdateUserRequest updateUser =
                new UpdateUserRequest("test-username-updated","test-password","test-name","test-surname",16);

        UserNotFoundException actualException =
                new UserNotFoundException("User Id is not valid ! ");

        when(userRepository.findById(userId)).thenReturn(null);


        UserNotFoundException exceptedException =
                assertThrows(UserNotFoundException.class,
                        ()-> userService.updateUserById(userId, updateUser));

        assertEquals(exceptedException.getMessage(), actualException.getMessage());
    }
    @DisplayName("Delete User By UserId when UserId is exist should delete successful !")
    @Test
    public void testDeleteUserById_whenUserIdIsExist_shouldDeleteSuccessful(){
        Integer userId = 1;

        User returnUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-name")
                .lastName("test-surname")
                .email("test-email")
                .password("test-password")
                .age(16)
                .build();
        returnUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(returnUser));
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUserById(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }
    @DisplayName("DeleteUserById when userId is not exist should return UserNotFoundException")
    @Test
    public void testDeleteUserById_whenUserIdIsNotExist_shouldReturnUserNotFoundException(){
        Integer userId = 1;

        UserNotFoundException actualException =
                new UserNotFoundException("User Id is not valid !");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException expectedException =
                assertThrows(UserNotFoundException.class ,
                        ()->userService.deleteUserById(userId));

        assertEquals(expectedException.getMessage(), actualException.getMessage());

        verify(userRepository).findById(userId);
        verify(userRepository).findById(userId);


    }
    @DisplayName("GetAllUser should return list of GetAllUsersResponseType")
    @Test
    public void testGetAllUser_shouldReturnListOfGetAllUsersResponseType(){
        User user1 = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-name")
                .lastName("surname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        User user2 = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-name")
                .lastName("surname")
                .email("test-email")
                .password("test-password")
                .age(16).build();

        GetAllUsersResponse returnUser1 = new GetAllUsersResponse(user1);
        GetAllUsersResponse returnUser2 = new GetAllUsersResponse(user2);

        List<User> returnUsers = new ArrayList<>();
        returnUsers.add(user1);
        returnUsers.add(user2);
        List<GetAllUsersResponse> expected = Arrays.asList(returnUser1, returnUser2);

        when(userRepository.findAll()).thenReturn(returnUsers);

        List<GetAllUsersResponse> actual = userService.getAllUser();

        for(int i = 0;i<actual.size();i++){
            assertEquals(expected.get(i), actual.get(i));
        }

        verify(userRepository).findAll();
    }
    @DisplayName("GetAllUser should return list of GetAllUsersResponseType")
    @Test
    public void testGetUserById_whenUserIdIsExist_shouldReturnGetUserByIdResponse(){
        Integer userId = 1;

        User returnUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-name")
                .lastName("test-surname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        returnUser.setId(1);

        GetUserByIdResponse expected = new GetUserByIdResponse
                .GetUserByIdResponseBuilder()
                .id(1)
                .userName("test-username")
                .firstName("test-name")
                .lastName("test-surname")
                .email("test-email")
                .age(16).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(returnUser));

        GetUserByIdResponse actual = userService.getUserById(userId);

        assertEquals(expected, actual);

        verify(userRepository).findById(userId);

    }
    @DisplayName("GetUserById when UserId not exist should return UserNotFoundException")
    @Test
    public void testGetUserById_whenUserIdNotExist_shouldReturnUserNotFoundException(){

        Integer userId = 1;

        UserNotFoundException expected =
                new UserNotFoundException("User Id is not exists ! ");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException actual = assertThrows(UserNotFoundException.class,
                ()->userService.getUserById(userId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(userRepository).findById(userId);
    }
    @DisplayName("GetAllUser should return list of GetAllUsersResponseType")
    @Test
    public void testGetUserById_whenUsernameIsExist_shouldReturnGetUserByUsernameResponse(){
        String username = "test-username";

        User returnUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-name")
                .lastName("test-surname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        returnUser.setId(1);

        GetUserByUsernameResponse expected = new GetUserByUsernameResponse
                .GetUserByUsernameResponseBuilder()
                .id(1)
                .userName("test-username")
                .firstName("test-name")
                .lastName("test-surname")
                .email("test-email")
                .age(16).build();

        when(userRepository.findByUserName(username)).thenReturn(returnUser);

        GetUserByUsernameResponse actual = userService.getUserByUsername(username);

        assertEquals(expected, actual);

        verify(userRepository).findByUserName(username);

    }
    @DisplayName("GetUserById when UserId not exist should return UserNotFoundException")
    @Test
    public void testGetUserById_whenUsernameNotExist_shouldReturnUserNotFoundException(){

        String username = "test-name";

        UserNotFoundException expected =
                new UserNotFoundException("Username is not exist ! ");

        when(userRepository.findByUserName(username)).thenReturn(null);

        UserNotFoundException actual = assertThrows(UserNotFoundException.class,
                ()->userService.getUserByUsername(username));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(userRepository).findByUserName(username);
    }
}