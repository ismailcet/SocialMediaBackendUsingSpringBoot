package com.ismailcet.SocialMedia.dto.request;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import java.time.LocalDateTime;

public class CreateCommentRequest {
    private String comment;
    private LocalDateTime createdDate;
    private Integer user_id;
    private Integer post_id;

    public CreateCommentRequest() {
    }

    public CreateCommentRequest(String comment, LocalDateTime createdDate, Integer user_id, Integer post_id) {
        this.comment = comment;
        this.createdDate = createdDate;
        this.user_id = user_id;
        this.post_id = post_id;
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }
}
