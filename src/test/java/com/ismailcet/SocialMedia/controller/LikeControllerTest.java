package com.ismailcet.SocialMedia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateCommentRequest;
import com.ismailcet.SocialMedia.dto.request.CreateLikeRequest;
import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LikeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class LikeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private LikeService likeService;
    @Test
    public void testCreateLike_whenCreateLikeRequestIsValid_shouldReturnLikeCreatedSuccessfullyAndReturnLikeDto() throws Exception {
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

        CreateLikeRequest request =
                new CreateLikeRequest(1,1,date);

        LikeDto response = new LikeDto.LikeDtoBuilder()
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createDate(date)
                .build();

        when(likeService.createLike(request)).thenReturn(response);

        mockMvc.perform(
                        post("/likes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.post.id").value(post.getId()))
                .andExpect(jsonPath("$.post.content").value(post.getContent()));

        verify(likeService).createLike(any(CreateLikeRequest.class));
    }
    @Test
    public void testCreateLike_whenCreateLikeRequestIsNotValid_shouldReturnLikeCreatedFailedAndReturn400StatusCode() throws Exception {
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

        CreateLikeRequest request =
                new CreateLikeRequest();
        request.setPost_id(post.getId());
        request.setCreatedDate(date);

        mockMvc.perform(
                        post("/likes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void testDeleteLikeByLikeId_whenLikeIdIsValid_shouldLikeDeletedSuccessfully() throws Exception {
        Integer likeId = 1;
        doNothing().when(likeService).deleteLikeByLikeId(likeId);

        mockMvc.perform(
                        delete("/likes/"+likeId))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(likeService).deleteLikeByLikeId(any(Integer.class));
    }
    @Test
    public void testGetLikesByPostId_whenPostIdIsValid_shouldReturnListOfLikeDtoType() throws Exception {
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

        Post post = new Post.PostBuilder()
                .content("test-content")
                .user(user)
                .createdDate(date)
                .build();
        post.setId(postId);

        LikeDto like1 = new LikeDto.LikeDtoBuilder()
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createDate(date)
                .build();
        like1.setId(1);

        LikeDto like2 = new LikeDto.LikeDtoBuilder()
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createDate(date)
                .build();
        like2.setId(2);

        List<LikeDto> response = Arrays.asList(like1, like2);

        when(likeService.getLikesByPostId(postId)).thenReturn(response);

        mockMvc.perform(
                get("/likes/post/"+postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].post.id").value(postId))
                .andExpect(jsonPath("$[1].post.id").value(postId))
                .andExpect(jsonPath("$[0].id").value(like1.getId()))
                .andExpect(jsonPath("$[1].id").value(like2.getId()));

        verify(likeService).getLikesByPostId(any(Integer.class));
    }
    @Test
    public void testGetLikesByPostId_whenPostIdIsNotValid_shouldReturn400StatusCode() throws Exception {
        String postId = "wrong";

        mockMvc.perform(
                get("/likes/post/"+postId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}