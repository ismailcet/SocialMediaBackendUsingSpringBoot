package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;

    @BeforeEach
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        userDtoConverter = Mockito.mock(UserDtoConverter.class);
        userService = new UserService(userRepository, userDtoConverter);
    }

    @Test
    public void testSignUp_whenUserIdNotExistsAndAllCreditsExists_shouldReturnUserDto(){
        CreateUserRequest createUserRequest =
                new CreateUserRequest("username", "password", "name", "surname", "email", 21);
        User user =
                new User("username", "password", "name", "surname", "email", 21);


        UserDto userDto = userDtoConverter.convert(user);
        UserDto returnUser = userService.signup(createUserRequest);


        Mockito.when(userDtoConverter.convert(user)).thenReturn(userDto);
        Mockito.when(userRepository.findByUserName(createUserRequest.getUsername())).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userService.signup(createUserRequest)).thenReturn(userDto);

        assertEquals(userDto, returnUser);
    }

}