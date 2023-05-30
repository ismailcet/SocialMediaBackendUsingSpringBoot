package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreatePostRequest;
import com.ismailcet.SocialMedia.dto.request.UpdatePostRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllPostsResponse;
import com.ismailcet.SocialMedia.dto.response.GetPostByPostIdResponse;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class  PostServiceTest {

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
    public void testUpdatePostByPostId_whenPostIdIsExist_shouldUpdatedSuccessfullyAndReturnUserDto(){
        Integer postId = 1;
        User givenUser =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        givenUser.setId(1);

        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,15);

        Post returnPost = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(givenUser)
                .build();
        returnPost.setId(1);

        UpdatePostRequest givenPost =
                new UpdatePostRequest("test-updated");

        Post updatedPost = new Post.PostBuilder()
                .content("test-updated")
                .createdDate(date)
                .user(givenUser)
                .build();
        updatedPost.setId(1);

        PostDto expected = new PostDto.PostDtoBuilder()
                .id(1)
                .content("test-updated")
                .user(new UserDto(givenUser))
                .createdDate(date)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(returnPost));
        when(postRepository.save(updatedPost)).thenReturn(updatedPost);
        when(postDtoConverter.convert(Mockito.any(Post.class))).thenReturn(expected);

        PostDto actual = postService.updatePostByPostId(postId, givenPost);

        assertEquals(expected, actual);
        assertEquals("test-updated", actual.getContent());

        verify(postRepository).findById(postId);
        verify(postRepository).save(any(Post.class));
        verify(postDtoConverter).convert(any(Post.class));
    }
    @DisplayName("CreatePost when UserId not exist should return PostNotFoundException")
    @Test
    public void testUpdatePostByPostId_whenPostIdNotExist_shouldUpdatedFailedAndReturnPostNotFoundException(){
        Integer postId = 1;

        UpdatePostRequest givenPost =
                new UpdatePostRequest("test-content");

        PostNotFoundException expected =
                new PostNotFoundException("Post Id does not exists ! ");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostNotFoundException actual = assertThrows(PostNotFoundException.class ,
                ()->postService.updatePostByPostId(postId , givenPost));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(postRepository).findById(postId);

    }
    @DisplayName("DeletePostByPostId when PostId is exist should delete Successful")
    @Test
    public void testDeletePostByPostId_whenPostIdIsExist_shouldDeleteSuccessful(){
        Integer postId = 1;
        User givenUser =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        givenUser.setId(1);

        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,15);

        Post returnPost = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(givenUser)
                .build();
        returnPost.setId(1);

        when(postRepository.findById(postId)).thenReturn(Optional.of(returnPost));
        doNothing().when(userRepository).deleteById(postId);

        postService.deletePostByPostId(postId);

        verify(postRepository).findById(postId);
        verify(postRepository).deleteById(postId);

    }
    @DisplayName("DeletePostByPostId when PostId is not exist should return PostNotFoundException")
    @Test
    public void testDeletePostByPostId_whenPostIdIsNotExist_shouldReturnPostNotFoundException(){
        Integer postId=1;

        PostNotFoundException expected =
                new PostNotFoundException("Post Id does not exists ! ");
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostNotFoundException actual = assertThrows(PostNotFoundException.class,
                ()->postService.deletePostByPostId(postId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(postRepository).findById(postId);


    }
    @DisplayName("GetAllPosts should return list of GetAllPostsResponse type")
    @Test
    public void testGetAllPosts_shouldReturnListOfGetAllPostsResponseType(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        User user =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        user.setId(1);

        Post post1 = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(user)
                .build();
        post1.setId(1);

        Post post2 = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(user)
                .build();
        post2.setId(2);

        GetAllPostsResponse returnPost1 =
                new GetAllPostsResponse(1,"test-content",new UserDto(user),date);
        GetAllPostsResponse returnPost2 =
                new GetAllPostsResponse(2,"test-content",new UserDto(user),date);

        List<Post> returnPosts = Arrays.asList(post1, post2);
        List<GetAllPostsResponse> expected = Arrays.asList(returnPost1, returnPost2);

        when(postRepository.findAll()).thenReturn(returnPosts);

        List<GetAllPostsResponse> actual = postService.getAllPosts();

        assertEquals(expected, actual);

        verify(postRepository).findAll();
    }
    @DisplayName("GetPostByPostId when PostId is exist should return GetPostByPostIdResponse type")
    @Test
    public void testGetPostByPostId_whenPostIdIsExist_shouldReturnGetPostByPostIdResponseType(){
        Integer postId = 1;
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        User user =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(user)
                .build();
        post.setId(postId);

        GetPostByPostIdResponse expected =
                new GetPostByPostIdResponse(1,"test-content",new UserDto(user),date);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        GetPostByPostIdResponse actual = postService.getPostByPostId(postId);

        assertEquals(expected, actual);

        verify(postRepository).findById(postId);
    }
    @DisplayName("GetPostByPostId when PostId is not exist should return PostNotFoundException")
    @Test
    public void testGetPostByPostId_whenPostIdIsNotExist_shouldReturnPostNotFoundException(){
        Integer postId = 1;
        PostNotFoundException expected =
                new PostNotFoundException("Post id does not exists ! ");
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        PostNotFoundException actual = assertThrows(PostNotFoundException.class,
                ()->postService.getPostByPostId(postId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(postRepository).findById(postId);
    }
    @DisplayName("GetPostMostLikedThreeDays should return PostDto")
    @Test
    public void testGetPostMostLikedThreeDays_shouldReturnPostDto(){
        Integer day = 3;
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        User user =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(user)
                .build();
        post.setId(1);

        PostDto expected =
                new PostDto(1,"test-content",new UserDto(user),date);

        when(postRepository.getPostMostLikedThreeDays(day)).thenReturn(post);
        when(postDtoConverter.convert(any(Post.class))).thenReturn(expected);

        PostDto actual = postService.getPostMostLikedThreeDays(day);

        assertEquals(expected, actual);

        verify(postRepository).getPostMostLikedThreeDays(day);
        verify(postDtoConverter).convert(any(Post.class));



    }
    @Test
    public void testGetPostWithUsernameMostComment_shouldReturnPostDto(){
        String username = "test-username";

        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        User user =
                new User("test-username","test-password","test-firstname","test-lastname","test-email",16);
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(date)
                .user(user)
                .build();
        post.setId(1);

        PostDto expected =
                new PostDto(1,"test-content",new UserDto(user),date);

        when(postRepository.getPostwithUsernameMostComment(username)).thenReturn(post);
        when(postDtoConverter.convert(any(Post.class))).thenReturn(expected);

        PostDto actual = postService.getPostWithUsernameMostComment(username);

        assertEquals(expected, actual);

        verify(postRepository).getPostwithUsernameMostComment(username);
        verify(postDtoConverter).convert(any(Post.class));
    }

}