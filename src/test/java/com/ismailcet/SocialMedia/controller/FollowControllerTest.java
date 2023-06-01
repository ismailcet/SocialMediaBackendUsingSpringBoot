package com.ismailcet.SocialMedia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateFollowRequest;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.service.FollowService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FollowController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class FollowControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FollowService followService;
    @Test
    public void testCreateFollow_whenCreateFollowRequestIsValid_shouldFollowCreatedSuccessfullyAndReturnFollowDto() throws Exception {
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
        followingUser.setId(2);

        CreateFollowRequest request =
                new CreateFollowRequest(1,1);

        FollowDto response = new FollowDto.FollowDtoBuilder()
                .followUser(new UserDto(followedUser))
                .followingUser(new UserDto(followingUser))
                .build();
        when(followService.createFollow(request)).thenReturn(response);

        mockMvc.perform(
                post("/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.followUser").value(new UserDto(followedUser)))
                .andExpect(jsonPath("$.followingUser").value(new UserDto(followingUser)));

        verify(followService).createFollow(any(CreateFollowRequest.class));
    }
    @Test
    public void testCreateFollow_whenCreateFollowRequestIsNotValid_shouldFollowCreatedFailedAndReturn400StatusCode() throws Exception {
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
        followingUser.setId(2);

        CreateFollowRequest request =
                new CreateFollowRequest();

        request.setFollowUser_id(followedUser.getId());

        mockMvc.perform(
                post("/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isNotFound());

    }
    @Test
    public void testDeleteFollowById_whenFollowIdIsValid_shouldFollowDeletedSuccessfully() throws Exception {
        Integer followId = 1;

        doNothing().when(followService).deleteFollowById(followId);

        mockMvc.perform(
                        delete("/follows/"+followId))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(followService).deleteFollowById(any(Integer.class));
    }
    @Test
    public void testGetFollowerCountByUserId_whenFollowedIdIsValid_shouldReturnString() throws Exception {
        Integer followedId = 1;
        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16)
                .build();
        Integer count = 3;
        String response = user.getUserName()+" follower count : " + count;
        when(followService.getFollowerCountByUserId(followedId)).thenReturn(response);

        MvcResult result =  mockMvc.perform(
                get("/follows/followerCount/"+followedId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(response, content);

        verify(followService).getFollowerCountByUserId(any(Integer.class));
    }
}