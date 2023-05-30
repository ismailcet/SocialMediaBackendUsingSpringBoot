package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class LikeDto {
    private Integer id;
    @NotNull(message = "User may not be null")
    private UserDto user;
    @NotNull(message = "Post may not be null")
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

    public LikeDto(LikeDtoBuilder likeDtoBuilder){
        this.user = likeDtoBuilder.user;
        this.post = likeDtoBuilder.post;
        this.createdDate = likeDtoBuilder.createdDate;
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

    public static class LikeDtoBuilder{
        private UserDto user;
        private PostDto post;
        private LocalDateTime createdDate;

        public LikeDtoBuilder user(UserDto user){
            this.user = user;
            return this;
        }
        public LikeDtoBuilder post(PostDto post){
            this.post = post;
            return this;
        }

        public LikeDtoBuilder createDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }
        public LikeDto build(){
            return new LikeDto(this);
        }
    }

    @Override
    public String toString() {
        return "LikeDto{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeDto)) return false;
        LikeDto likeDto = (LikeDto) o;
        return Objects.equals(getId(), likeDto.getId()) && Objects.equals(getUser(), likeDto.getUser()) && Objects.equals(getPost(), likeDto.getPost()) && Objects.equals(getCreatedDate(), likeDto.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPost(), getCreatedDate());
    }
}
