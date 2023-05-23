package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    public class LikeDtoBuilder{
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
}
