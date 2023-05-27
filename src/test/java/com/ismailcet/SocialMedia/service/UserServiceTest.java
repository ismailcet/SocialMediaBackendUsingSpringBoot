package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.UserNotFoundException;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.service.UserService;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest{

    private UserService userService;
    private UserRepository userRepository;
    private UserDtoConverter userDtoConverter;

    @BeforeEach
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        userDtoConverter = new UserDtoConverter(); //  Mockito.mock(UserDtoConverter.class)

        userService = new UserService(userRepository , userDtoConverter);
    }

    @Test
    public void testSignUp_whenUserIdNotExistsAndAllCreditsExists_shouldReturnUserDto(){
        User user =
                new User("username", "password", "name", "surname", "email", 21);
        UserDto a = userDtoConverter.convert(user);
        System.out.println(a.getUserName());
    }
   /* @Test
    public void testSignUp_whenUserIdIsNotExistsAndAllCreditsAreNotExists_shouldReturnUserNotFoundException(){
        CreateUserRequest createUserRequest =
                new CreateUserRequest();
        User user =
                new User("username", "password", "name", "surname", "email", 21);

        //Mockito.when(userService.signup(createUserRequest)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,
                () ->userService.signup(createUserRequest));


    }

    @Test
    public void testSignUp_whenUserIdAlreadyExists_shouldReturnUserNotException(){
        CreateUserRequest createUserRequest =
                new CreateUserRequest("username", "password", "name", "surname", "email", 21);
        User user =
                new User("username", "password", "name", "surname", "email", 21);

        when(userRepository.findByUserName(createUserRequest.getUsername())).thenReturn(user);

        //Mockito.when(userService.signup(createUserRequest)).thenThrow(new UserNotFoundException("test-exception"));

        assertThrows(UserNotFoundException.class,
                () -> userService.signup(createUserRequest));
    }*/

}