package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateFollowRequest;
import com.ismailcet.SocialMedia.entity.Follow;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.FollowNotFoundException;
import com.ismailcet.SocialMedia.repository.FollowRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.FollowDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FollowServiceTest {
    private FollowService followService;
    private FollowRepository followRepository;
    private UserRepository userRepository;
    private FollowDtoConverter followDtoConverter;

    @BeforeEach
    public void setUp(){
        followRepository = mock(FollowRepository.class);
        userRepository = mock(UserRepository.class);
        followDtoConverter = mock(FollowDtoConverter.class);

        followService = new FollowService(followRepository, userRepository, followDtoConverter);
    }
    @DisplayName("CreateFollow when FollowedId and FollowingId are exist should Follow Created Successfully and return FollowDto")
    @Test
    public void testCreateFollow_whenFollowedIdAndFollowingIdAreExist_shouldFollowCreatedSuccessfullyAndReturnFollowDto(){
        Integer followedId = 1;
        Integer followingId = 1;

        User followedUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        followedUser.setId(followedId);

        User followingUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        followingUser.setId(followingId);

        CreateFollowRequest createFollowRequest =
                new CreateFollowRequest(followedId, followingId);

        Follow follow = new Follow.FollowBuilder()
                .followUser(followedUser)
                .followingUser(followingUser)
                .build();

        FollowDto expected = new FollowDto.FollowDtoBuilder()
                .followUser(new UserDto(followedUser))
                .followingUser(new UserDto(followingUser))
                .build();

        when(userRepository.findById(followedId)).thenReturn(Optional.of(followedUser));
        when(userRepository.findById(followingId)).thenReturn(Optional.of(followingUser));
        when(followRepository.save(any(Follow.class))).thenReturn(follow);
        when(followDtoConverter.convert(any(Follow.class))).thenReturn(expected);

        FollowDto actual = followService.createFollow(createFollowRequest);

        assertEquals(expected, actual);

        verify(userRepository, new Times(2)).findById(any(Integer.class));
        verify(followRepository).save(any(Follow.class));
        verify(followDtoConverter).convert(any(Follow.class));
    }
    @DisplayName("CreateFollow when FollowedId and FollowingId are not exist should Follow Created failed and return FollowNotFoundException")
    @Test
    public void testCreateFollow_whenFollowedIdAndFollowingIdAreNotExist_shouldFollowCreatedFailedAndReturnFollowNotFoundException(){
        Integer followedId = 1;
        Integer followingId = 1;

        CreateFollowRequest createFollowRequest =
                new CreateFollowRequest(followedId, followingId);
        FollowNotFoundException expected =
                new FollowNotFoundException("Followed User Id or Following User Id does not exists");

        when(userRepository.findById(followedId)).thenReturn(Optional.empty());
        when(userRepository.findById(followingId)).thenReturn(Optional.empty());

        FollowNotFoundException actual = assertThrows(FollowNotFoundException.class,
                ()->followService.createFollow(createFollowRequest));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(userRepository, new Times(2)).findById(any(Integer.class));
    }
    @DisplayName("DeleteFollowById when FollowId Is Exist should Follow Deleted Successfully")
    @Test
    public void testDeleteFollowById_whenFollowIdIsExist_shouldFollowDeletedSuccessfully(){
        Integer followId = 1;
        User followedUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        followedUser.setId(1);

        User followingUser = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        followingUser.setId(1);

        Follow follow = new Follow.FollowBuilder()
                .followUser(followedUser)
                .followingUser(followingUser)
                .build();

        when(followRepository.findById(followId)).thenReturn(Optional.of(follow));
        doNothing().when(followRepository).deleteById(followId);

        followService.deleteFollowById(followId);

        verify(followRepository).findById(any(Integer.class));
        verify(followRepository).deleteById(any(Integer.class));

    }
    @DisplayName("DeleteFollowById when FollowId Is Not Exist should Follow Deleted Failed Return FollowNotFoundException")
    @Test
    public void testDeleteFollowById_whenFollowIdIsNotExist_shouldFollowDeletedFailedReturnFollowNotFoundException(){
        Integer followId = 1;
        FollowNotFoundException expected =
                new FollowNotFoundException("Follow Id does not exists");

        when(followRepository.findById(followId)).thenReturn(Optional.empty());

        FollowNotFoundException actual = assertThrows(FollowNotFoundException.class,
                ()->followService.deleteFollowById(followId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(followRepository).findById(any(Integer.class));
    }
    @DisplayName("GetFollowerCountByUserId when UserId Is Exist should Return String")
    @Test
    public void testGetFollowerCountByUserId_whenUserIdIsExist_shouldReturnString(){
        Integer userId = 1;
        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(userId);

        Integer count = 2;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(followRepository.getFollowersCount(userId)).thenReturn(count);

        String actual = followService.getFollowerCountByUserId(userId);

        assertTrue(actual.contains(Integer.toString(count)));

        verify(userRepository).findById(any(Integer.class));
        verify(followRepository).getFollowersCount(any(Integer.class));
    }
    @DisplayName("GetFollowerCountByUserId when UserId Is Not Exist should Return Follow Not Found Exception")
    @Test
    public void testGetFollowerCountByUserId_whenUserIdIsNotExist_shouldReturnFollowNotFoundException(){
        Integer userId = 1;
        FollowNotFoundException expected =
                new FollowNotFoundException("User Id does not exists");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        FollowNotFoundException actual = assertThrows(FollowNotFoundException.class,
                ()->followService.getFollowerCountByUserId(userId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(userRepository).findById(any(Integer.class));
    }
}