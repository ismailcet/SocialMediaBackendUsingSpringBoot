package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateLikeRequest;
import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.LikeNotFoundException;
import com.ismailcet.SocialMedia.repository.LikeRepository;
import com.ismailcet.SocialMedia.repository.PostRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.LikeDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceTest {
    private LikeService likeService;
    private LikeRepository likeRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private LikeDtoConverter likeDtoConverter;

    @BeforeEach
    public void setUp(){
        likeRepository = mock(LikeRepository.class);
        userRepository = mock(UserRepository.class);
        postRepository = mock(PostRepository.class);
        likeDtoConverter = mock(LikeDtoConverter.class);

        likeService =
                new LikeService(likeRepository, userRepository, postRepository, likeDtoConverter);
    }
    @DisplayName("CreateLike when UserId and PostId are exist should CreateLike Successfully Return LikeDto")
    @Test
    public void testCreateLike_whenUserIdAndPostIdAreExist_shouldCreateLikeSuccessfullyReturnLikeDto(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer userId = 1;
        Integer postId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Like like = new Like.LikeBuilder()
                .user(user)
                .post(post)
                .createDate(date)
                .build();
        CreateLikeRequest createLikeRequest =
                new CreateLikeRequest(userId, postId, date);

        LikeDto expected = new LikeDto.LikeDtoBuilder()
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createDate(date).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(likeRepository.save(any(Like.class))).thenReturn(like);
        when(likeDtoConverter.convert(any(Like.class))).thenReturn(expected);

        LikeDto actual = likeService.createLike(createLikeRequest);


        assertEquals(expected,actual);

        verify(postRepository).findById(any(Integer.class));
        verify(userRepository).findById(any(Integer.class));
        verify(likeRepository).save(any(Like.class));
        verify(likeDtoConverter).convert(any(Like.class));
    }
    @DisplayName("CreateLike when UserId and PostId are not exist should CreateLike Failed return LikeNotFoundException")
    @Test
    public void testCreateLike_whenUserIdAndPostIdAreNotExist_shouldCreateLikeFailedReturnLikeNotFoundException(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer userId = 1;
        Integer postId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        CreateLikeRequest createLikeRequest =
                new CreateLikeRequest(userId, postId, date);

        LikeNotFoundException expected =
                new LikeNotFoundException("Post Id or User Id does not exists ! ");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        LikeNotFoundException actual = assertThrows(LikeNotFoundException.class,
                ()->likeService.createLike(createLikeRequest));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(postRepository).findById(any(Integer.class));
        verify(userRepository).findById(any(Integer.class));
    }
    @DisplayName("DeleteLikeByLikeId when LikeId is exist should Delete Like Successfully")
    @Test
    public void testDeleteLikeByLikeId_whenLikeIdIsExist_shouldDeleteLikeSuccessfully(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer likeId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Like like = new Like.LikeBuilder()
                .user(user)
                .post(post)
                .createDate(date)
                .build();
        like.setId(likeId);

        when(likeRepository.findById(likeId)).thenReturn(Optional.of(like));
        doNothing().when(likeRepository).deleteById(likeId);

        likeService.deleteLikeByLikeId(likeId);

        verify(likeRepository).findById(any(Integer.class));
        verify(likeRepository).deleteById(any(Integer.class));
    }
    @DisplayName("DeleteLikeByLikeId when LikeId is not exist should Delete Like failed return LikeNotFoundException")
    @Test
    public void testDeleteLikeByLikeId_whenLikeIdIsNotExist_shouldDeleteLikeFailedReturnLikeNotFoundException(){
        Integer likeId = 1;

        LikeNotFoundException expected =
                new LikeNotFoundException("Like Id does not exists ! ");

        when(likeRepository.findById(likeId)).thenReturn(Optional.empty());

        LikeNotFoundException actual = assertThrows(LikeNotFoundException.class,
                ()->likeService.deleteLikeByLikeId(likeId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(likeRepository).findById(any(Integer.class));
    }
    @DisplayName("GetLikesByPostId when PostId is exist should return list of LikeDto type")
    @Test
    public void testGetLikesByPostId_whenPostIdIsExist_shouldReturnListOfLikeDtoType(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer postId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Like like1 = new Like.LikeBuilder()
                .user(user)
                .post(post)
                .createDate(date)
                .build();

        Like like2 = new Like.LikeBuilder()
                .user(user)
                .post(post)
                .createDate(date)
                .build();

        LikeDto likeDto1 = new LikeDto.LikeDtoBuilder()
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createDate(date)
                .build();

        LikeDto likeDto2 = new LikeDto.LikeDtoBuilder()
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createDate(date)
                .build();

        List<Like> likes = Arrays.asList(like1, like2);
        List<LikeDto> expected = Arrays.asList(likeDto1, likeDto2);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(likeRepository.findByPostId(postId)).thenReturn(likes);
        when(likeDtoConverter.convert(any(Like.class))).thenReturn(likeDto1);
        when(likeDtoConverter.convert(any(Like.class))).thenReturn(likeDto2);

        List<LikeDto> actual = likeService.getLikesByPostId(postId);

        assertEquals(expected, actual);

        verify(postRepository).findById(any(Integer.class));
        verify(likeRepository).findByPostId(any(Integer.class));
        verify(likeDtoConverter, new Times(2)).convert(any(Like.class));

    }
    @Test
    public void testGetLikesByPostId_whenPostIdIsNotExist_shouldReturnLikeNotFoundException(){
        Integer postId = 1;

        LikeNotFoundException expected =
                new LikeNotFoundException("Post Id does not exists ! ");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        LikeNotFoundException actual = assertThrows(LikeNotFoundException.class,
                ()->likeService.getLikesByPostId(postId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(postRepository).findById(any(Integer.class));
    }
}