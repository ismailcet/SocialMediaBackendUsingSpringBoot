package com.ismailcet.SocialMedia.dto;

import java.time.LocalDateTime;

public class LikeDto {
    private Integer id;
    private UserDto user;
    private PostDto post;
    private LocalDateTime createdDate;

    public LikeDto(){

    }

    public LikeDto(Integer id, UserDto user, PostDto post, LocalDateTime createdDate) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
