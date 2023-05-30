package com.ismailcet.SocialMedia.dto.response;

import com.ismailcet.SocialMedia.dto.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "GetPostByPostIdResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetPostByPostIdResponse)) return false;
        GetPostByPostIdResponse that = (GetPostByPostIdResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getContent(), that.getContent()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getCreatedDate(), that.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getUser(), getCreatedDate());
    }
}
