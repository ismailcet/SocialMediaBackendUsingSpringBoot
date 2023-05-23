package com.ismailcet.SocialMedia.dto.request;

import javax.validation.constraints.NotNull;

public class UpdatePostRequest {
    @NotNull(message = "Content may not be null")
    private String content;

    public UpdatePostRequest() {
    }

    public UpdatePostRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
