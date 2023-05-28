package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

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
    public void testSignUp_whenUsernameIsNotAlreadyExist_shouldReturnUserDto(){
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

    @Test
    public void testSignUp_whenUsernameAlreadyTaken_shouldReturnUserNotFoundException(){

    }
}