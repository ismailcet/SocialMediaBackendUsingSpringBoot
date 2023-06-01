package com.ismailcet.SocialMedia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreatePostRequest;
import com.ismailcet.SocialMedia.dto.request.UpdatePostRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllPostsResponse;
import com.ismailcet.SocialMedia.dto.response.GetPostByPostIdResponse;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class PostControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private PostService postService;

    @Test
    public void testCreatePost_whenCreatePostRequestIsValid_shouldPostCreatedSuccessfullyAndReturnPostDto() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                        .userName("test-username")
                        .firstName("test-firstname")
                        .lastName("test-lastname")
                        .email("test-email")
                        .age(16)
                        .build();
        user.setId(1);


        CreatePostRequest request =
                new CreatePostRequest("test-content",1);

        PostDto response = new PostDto.PostDtoBuilder()
                        .id(1)
                        .content("test-content")
                        .user(new UserDto(user))
                        .createdDate(date)
                        .build();
        when(postService.createPost(request)).thenReturn(response);

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(request.getContent()))
                .andExpect(jsonPath("$.user.id").value(request.getUser_id()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()));

        verify(postService).createPost(any(CreatePostRequest.class));
    }
    @Test
    public void testCreatePost_whenCreatePostRequestIsNotValid_shouldPostCreatedFailedAndReturn400StatusCode() throws Exception {

        CreatePostRequest request = new CreatePostRequest();
        request.setContent("test-content");

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testUpdatePostByPostId_whenPostIdIsValidAndUpdatePostRequestIsValid_shouldPostUpdatedSuccessfullyAndReturnPostDto() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer postId = 1;
        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);


        UpdatePostRequest request =
                new UpdatePostRequest("test-contentUpdated");

        PostDto response = new PostDto.PostDtoBuilder()
                        .id(postId)
                        .user(new UserDto(user))
                        .createdDate(date)
                        .content("test-contentUpdated")
                        .build();

        when(postService.updatePostByPostId(postId, request)).thenReturn(response);

        mockMvc.perform(
                put("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(request.getContent()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()));

        verify(postService).updatePostByPostId(any(Integer.class), any(UpdatePostRequest.class));

    }
    @Test
    public void testUpdatePostByPostId_whenPostIdIsNotValid_shouldPostUpdatedFailedAndreturn400StatusCode() throws Exception {

        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        String postId = "deneme";
        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);


        UpdatePostRequest request =
                new UpdatePostRequest("test-contentUpdated");
        mockMvc.perform(
                put("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdatePostByPostId_whenUpdatePostRequestIsNotValid_shouldPostUpdatedFailedAndreturn400StatusCode() throws Exception {

        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer postId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);


        UpdatePostRequest request =
                new UpdatePostRequest();
        mockMvc.perform(
                        put("/posts/" + postId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testDeletePostByPostId_whenPostIdIsValid_shouldPostDeletedSuccessfullyAndReturnNoContentStatusCode() throws Exception {
        Integer postId = 1;

        doNothing().when(postService).deletePostByPostId(postId);

        mockMvc.perform(
                delete("/posts/"+postId)
                ).andDo(print())
                .andExpect(status().isNoContent());
        verify(postService).deletePostByPostId(any(Integer.class));
    }
    @Test
    public void testDeletePostByPostId_whenPostIdIsNotValid_shouldPostDeletedFailedAndReturn400StatusCode() throws Exception {
        String postId = "abc";

        mockMvc.perform(
                delete("/posts/"+postId)
                ).andDo(print())
                .andExpect(status().isBadRequest());

    }
    @Test
    public void testGetAllPosts_shouldReturnListOfGetAllPostsResponseTypeAnd200StatusCode() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        GetAllPostsResponse post1 =
                new GetAllPostsResponse(1,"test-content",new UserDto(user),date);
        GetAllPostsResponse post2 =
                new GetAllPostsResponse(2,"test-content",new UserDto(user),date);

        List<GetAllPostsResponse> response = Arrays.asList(post1, post2);

        when(postService.getAllPosts()).thenReturn(response);

        mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[1].id").value(post2.getId()));

        verify(postService).getAllPosts();
    }
    @Test
    public void testGetPostByPostId_whenPostIdIsValid_shouldReturnPostDtoAnd200StatusCode() throws Exception {
        Integer postId = 1;
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        GetPostByPostIdResponse response =
                new GetPostByPostIdResponse(postId,"test-content",new UserDto(user),date);

        when(postService.getPostByPostId(postId)).thenReturn(response);

        mockMvc.perform(
                get("/posts/"+postId)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()));

        verify(postService).getPostByPostId(any(Integer.class));

    }
    @Test
    public void testGetPostByPostId_whenPostIdIsNotValid_shouldReturn404StatusCode() throws Exception {
        String postId = "wrong";

        mockMvc.perform(
                get("/posts"+postId)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testGetPostMostLikedThreeDays_whenDayIsValid_shoulReturnPostDtoAnd200StatusCode() throws Exception {
        Integer day = 3;
        Integer postId = 1;
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        PostDto response =
                new PostDto(1,"text-content",new UserDto(user),date);

        when(postService.getPostMostLikedThreeDays(day)).thenReturn(response);

        mockMvc.perform(get("/posts/like/"+day))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.content").value(response.getContent()));

        verify(postService).getPostMostLikedThreeDays(day);
    }
    @Test
    public void testGetPostMostLikedThreeDays_whenDayIsNotValid_shoulReturn400StatusCode() throws Exception {
        String postId = "wrong";

        mockMvc.perform(
                        get("/posts/like/"+postId)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testGetPostWithUsernameMostComment_whenUsernameIsValid_shouldReturnPostDtoAnd200StatusCode() throws Exception {
        String username = "test-username";
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        PostDto response =
                new PostDto(1,"test-content",new UserDto(user),date);
        when(postService.getPostWithUsernameMostComment(username)).thenReturn(response);

        mockMvc.perform(get("/posts/comment/"+username))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.content").value(response.getContent()));

        verify(postService).getPostWithUsernameMostComment(username);
    }
}