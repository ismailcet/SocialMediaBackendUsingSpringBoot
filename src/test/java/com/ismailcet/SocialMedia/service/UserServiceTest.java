package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.UserNotFoundException;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public void testUpdateUserById_whenUserIdIsNotExist_shouldReturnUserNotFoundException(){}
}