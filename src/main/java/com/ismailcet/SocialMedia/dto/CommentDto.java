package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
//deleted
public class CommentDto {
    private Integer id;
    @NotNull(message = "Comment may not be null")
    private String comment;
    private LocalDateTime createdDate;
    @NotNull(message = "User may not be null")
    private UserDto user;
    @NotNull(message = "Post may not be null")
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

    public CommentDto(CommentDtoBuilder commentDtoBuilder){

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

    public class CommentDtoBuilder{
        private String comment;
        private LocalDateTime createdDate;
        private UserDto user;
        private PostDto post;

        public CommentDtoBuilder comment(String comment){
            this.comment = comment;
            return this;
        }
        public CommentDtoBuilder createdDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }
        public CommentDtoBuilder user(UserDto user){
            this.user = user;
            return this;
        }
        public CommentDtoBuilder post(PostDto post){
            this.post = post;
            return this;
        }
        public CommentDto build(){
            return new CommentDto(this);
        }
    }
}
