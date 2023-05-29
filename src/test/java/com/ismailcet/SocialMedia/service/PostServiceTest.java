package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreatePostRequest;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.PostNotFoundException;
import com.ismailcet.SocialMedia.repository.PostRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.PostDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private PostDtoConverter postDtoConverter;

    @BeforeEach
    public void setUp(){

        postRepository = mock(PostRepository.class);
        userRepository = mock(UserRepository.class);
        postDtoConverter = mock(PostDtoConverter.class);

        postService = new PostService(postRepository, userRepository, postDtoConverter);
    }

    @DisplayName("CreatePost when UserId is exist should return PostDto")
    @Test
    public void testCreatePost_whenUserIdIsExist_shouldReturnPostDto(){
        Integer userId = 1;

        CreatePostRequest givenPost =
                new CreatePostRequest("test-content",userId);

        User givenUser =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        givenUser.setId(userId);

        Post returnPost = new Post.PostBuilder()
                        .content("test-content")
                        .createdDate(LocalDateTime.now())
                        .user(givenUser)
                        .build();
        returnPost.setId(1);

        PostDto expected = new PostDto.PostDtoBuilder()
                .id(1)
                .content("test-content")
                .user(new UserDto(givenUser))
                .createdDate(LocalDateTime.now())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(givenUser));
        when(postRepository.save(returnPost)).thenReturn(Mockito.any(Post.class));
        when(postDtoConverter.convert(returnPost)).thenReturn(expected);

        PostDto actual = postService.createPost(givenPost);

        assertEquals(expected, actual);

        verify(userRepository).findById(userId);
        verify(postRepository).save(Mockito.any(Post.class));
        verify(postDtoConverter).convert(Mockito.any(Post.class));
    }
    @DisplayName("CreatePost when UserId not exist should return PostNotFoundException")
    @Test
    public void testCreatePost_whenUserIdNotExist_shouldReturnPostNotFoundException(){
        Integer userId = 1;

        CreatePostRequest givenPost =
                new CreatePostRequest("test-content",userId);

        PostNotFoundException expected =
                new PostNotFoundException("User is not exists !");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        PostNotFoundException actual = assertThrows(PostNotFoundException.class ,
                ()->postService.createPost(givenPost));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(userRepository).findById(userId);

    }
    @DisplayName("UpdatePostByPostId when PostId is exist should Updated successfully and return UserDto")
    @Test
    public void testUpdatePostByPostId_whenPostIdIsExist_shouldUpdatedSuccessfullyAndReturnUserDto(){}
}