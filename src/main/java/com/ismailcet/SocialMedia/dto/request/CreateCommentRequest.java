package com.ismailcet.SocialMedia.dto.request;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class CreateCommentRequest {
    @NotNull(message = "Comment may not be null")
    private String comment;
    private LocalDateTime createdDate;
    @NotNull(message = "User Id may not be null")
    private Integer user_id;
    @NotNull(message = "Post Id may not be null")
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

    @Override
    public String toString() {
        return "CreateCommentRequest{" +
                "comment='" + comment + '\'' +
                ", createdDate=" + createdDate +
                ", user_id=" + user_id +
                ", post_id=" + post_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateCommentRequest)) return false;
        CreateCommentRequest that = (CreateCommentRequest) o;
        return Objects.equals(getComment(), that.getComment()) && Objects.equals(getCreatedDate(), that.getCreatedDate()) && Objects.equals(getUser_id(), that.getUser_id()) && Objects.equals(getPost_id(), that.getPost_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComment(), getCreatedDate(), getUser_id(), getPost_id());
    }
}
