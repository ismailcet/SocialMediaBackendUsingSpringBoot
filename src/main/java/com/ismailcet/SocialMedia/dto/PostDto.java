package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import java.time.LocalDateTime;

public class PostDto {

    private Integer id;
    private String content;
    private UserDto user;
    private LocalDateTime createdDate;

    public PostDto(Integer id, String content, UserDto user, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
    }

    public PostDto() {
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

    public PostDto(PostDtoBuilder postDtoBuilder){
        this.id = postDtoBuilder.id;
        this.content = postDtoBuilder.content;
        this.user = postDtoBuilder.user;
        this.createdDate = postDtoBuilder.createdDate;
    }
    public PostDto(Post post){
        this.id = post.getId();
        this.content = post.getContent();
        this.user = new UserDto(post.getUser());
        this.createdDate = post.getCreatedDate();
    }
    public static class PostDtoBuilder{
        private Integer id;
        private String content;
        private UserDto user;
        private LocalDateTime createdDate;

        public PostDtoBuilder id(Integer id){
            this.id = id;
            return this;
        }

        public PostDtoBuilder createdDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }

        public PostDtoBuilder content(String content){
            this.content = content;
            return this;
        }
        public PostDtoBuilder user(UserDto user){
            this.user = user;
            return this;
        }
        public PostDto build(){
            return new PostDto(this);
        }
    }

}
