package com.ismailcet.SocialMedia.dto.response;

import com.ismailcet.SocialMedia.dto.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class GetPostByPostIdResponse {
    private Integer id;
    @NotNull(message = "Content may not be null")
    private String content;
    @NotNull(message = "User Id may not be null")
    private UserDto user;
    private LocalDateTime createdDate;

    public GetPostByPostIdResponse() {
    }

    public GetPostByPostIdResponse(Integer id, String content, UserDto user, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
