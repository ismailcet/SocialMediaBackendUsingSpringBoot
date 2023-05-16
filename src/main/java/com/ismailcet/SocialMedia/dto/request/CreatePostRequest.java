package com.ismailcet.SocialMedia.dto.request;

public class CreatePostRequest {
    private String content;
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
}
