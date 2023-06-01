package com.ismailcet.SocialMedia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateCommentRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateCommentRequest;
import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.CommentRepository;
import com.ismailcet.SocialMedia.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

@WebMvcTest(controllers = CommentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class CommentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    @Test
    public void testCreateComment_whenCreateCommentRequestIsValid_shouldCommentCreatedSuccessfullyAndReturnCommentDto() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(1);

        CreateCommentRequest request =
                new CreateCommentRequest("test-comment",date,1,1);

        CommentDto response = new CommentDto.CommentDtoBuilder()
                .id(1)
                .comment("test-comment")
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createdDate(date)
                .build();

        when(commentService.createComment(request)).thenReturn(response);

        mockMvc.perform(
                post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.comment").value(request.getComment()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.post.id").value(post.getId()))
                .andExpect(jsonPath("$.post.content").value(post.getContent()));

        verify(commentService).createComment(any(CreateCommentRequest.class));
    }
    @Test
    public void testCreateComment_whenCreateCommentRequestIsNotValid_shouldReturn400StatusCode() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(1);

        CreateCommentRequest request =
                new CreateCommentRequest();
        request.setPost_id(post.getId());
        request.setUser_id(user.getId());
        request.setCreatedDate(date);

        mockMvc.perform(
                post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateCommentByCommentId_whenCommentIdIsValidAndUpdateCommentRequestIsValid_shouldReturnCommentDto() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer commentId = 1;
        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(1);

        UpdateCommentRequest request =
                new UpdateCommentRequest("test-commentUpdated");

        CommentDto response = new CommentDto.CommentDtoBuilder()
                .id(1)
                .comment("test-commentUpdated")
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createdDate(date)
                .build();

        when(commentService.updateCommentByCommentId(commentId, request)).thenReturn(response);

        mockMvc.perform(
                        put("/comments/"+commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.comment").value(request.getComment()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.post.id").value(post.getId()))
                .andExpect(jsonPath("$.post.content").value(post.getContent()));

        verify(commentService).updateCommentByCommentId(any(Integer.class),any(UpdateCommentRequest.class));
    }
    @Test
    public void testUpdateCommentByCommentId_whenCommentIdIsNotValidAndUpdateCommentRequestIsValid_shouldReturn400() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        String commentId = "wrong";

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(1);

        UpdateCommentRequest request =
                new UpdateCommentRequest();
        request.setComment("test-commentUpdate");

        mockMvc.perform(
                put("/comments/"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isBadRequest());

    }
    @Test
    public void testUpdateCommentByCommentId_whenCommentIdIsValidAndUpdateCommentRequestIsNotValid_shouldReturn404() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer commentId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(1);

        UpdateCommentRequest request =
                new UpdateCommentRequest();

        mockMvc.perform(
                put("/comments/"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNotFound());

    }
    @Test
    public void testDeleteCommentByCommentId_whenCommentIdIsValid_shouldReturnCommentDto() throws Exception {
        Integer commentId = 1;
        doNothing().when(commentService).deleteCommentByCommentId(commentId);

        mockMvc.perform(
                delete("/comments/"+commentId))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(commentService).deleteCommentByCommentId(any(Integer.class));
    }
    @Test
    public void testGetCommentsByPostId_whenPostIdIsValid_shouldReturnListOfCommentDtoType() throws Exception {
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .age(16)
                .build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(1);

        Comment comment1 = new Comment.CommentBuilder()
                        .comment("test-comment")
                        .createdDate(date)
                        .user(user)
                        .post(post)
                        .build();
        Comment comment2 = new Comment.CommentBuilder()
                        .comment("test-comment")
                        .createdDate(date)
                        .user(user)
                        .post(post)
                        .build();

        CommentDto commentDto1 = new CommentDto.CommentDtoBuilder()
                .comment("test-comment")
                .post(new PostDto(post))
                .user(new UserDto(user))
                .createdDate(date)
                .build();
        commentDto1.setId(1);

        CommentDto commentDto2 = new CommentDto.CommentDtoBuilder()
                .comment("test-comment")
                .post(new PostDto(post))
                .user(new UserDto(user))
                .createdDate(date)
                .build();
        commentDto2.setId(2);

        List<CommentDto> response = Arrays.asList(commentDto1, commentDto2);

        when(commentService.getCommentsByPostId(post.getId())).thenReturn(response);

        mockMvc.perform(
                get("/comments/posts/"+post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(commentDto1.getId()))
                .andExpect(jsonPath("$[1].id").value(commentDto2.getId()))
                .andExpect(jsonPath("$[0].post.id").value(post.getId()))
                .andExpect(jsonPath("$[1].post.id").value(post.getId()))
                .andExpect(jsonPath("$[0].user").value(commentDto1.getUser()))
                .andExpect(jsonPath("$[1].user").value(commentDto2.getUser()));

        verify(commentService).getCommentsByPostId(any(Integer.class));
    }
    @Test
    public void testGetCommentsByPostId_whenPostIdIsNotValid_shouldReturn400StatusType() throws Exception {
        String postId = "wrong";

        mockMvc.perform(
                get("/comments/posts/"+postId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}