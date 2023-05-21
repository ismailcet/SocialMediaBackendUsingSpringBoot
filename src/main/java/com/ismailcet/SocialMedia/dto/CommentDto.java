package com.ismailcet.SocialMedia.dto;

import java.time.LocalDateTime;
//deleted
public class CommentDto {
    private Integer id;
    private String comment;
    private LocalDateTime createdDate;
    private UserDto user;
    private PostDto post;

    public CommentDto() {
    }

    public CommentDto(Integer id, String comment, LocalDateTime createdDate, UserDto user, PostDto post) {
        this.id = id;
        this.comment = comment;
        this.createdDate = createdDate;
        this.user = user;
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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
}
