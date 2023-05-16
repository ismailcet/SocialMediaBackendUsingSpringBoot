package com.ismailcet.SocialMedia.dto.request;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import java.time.LocalDateTime;

public class CreateLikeRequest {
    private Integer user_id;
    private Integer post_id;
    private LocalDateTime createdDate;

    public CreateLikeRequest() {
    }

    public CreateLikeRequest(Integer user_id, Integer post_id, LocalDateTime createdDate) {
        this.user_id = user_id;
        this.post_id = post_id;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
