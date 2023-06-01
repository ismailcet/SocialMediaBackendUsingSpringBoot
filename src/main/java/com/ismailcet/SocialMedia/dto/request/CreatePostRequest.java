package com.ismailcet.SocialMedia.dto.request;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CreatePostRequest {
    @NotNull(message = "Content may not be null")
    private String content;
    @NotNull(message = "User Id may not be null")
    private Integer user_id;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String content, Integer user_id) {
        this.content = content;
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "CreatePostRequest{" +
                "content='" + content + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreatePostRequest)) return false;
        CreatePostRequest that = (CreatePostRequest) o;
        return Objects.equals(getContent(), that.getContent()) && Objects.equals(getUser_id(), that.getUser_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getUser_id());
    }
}
