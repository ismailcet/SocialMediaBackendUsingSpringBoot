package com.ismailcet.SocialMedia.dto.request;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class CreateLikeRequest {
    @NotNull(message = "User Id may not be null")
    private Integer user_id;
    @NotNull(message = "Post Id may not be null")
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

    @Override
    public String toString() {
        return "CreateLikeRequest{" +
                "user_id=" + user_id +
                ", post_id=" + post_id +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateLikeRequest)) return false;
        CreateLikeRequest that = (CreateLikeRequest) o;
        return Objects.equals(getUser_id(), that.getUser_id()) && Objects.equals(getPost_id(), that.getPost_id()) && Objects.equals(getCreatedDate(), that.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getPost_id(), getCreatedDate());
    }
}
