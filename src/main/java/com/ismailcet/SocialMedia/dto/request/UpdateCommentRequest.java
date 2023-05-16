package com.ismailcet.SocialMedia.dto.request;

public class UpdateCommentRequest {
    private String comment;

    public UpdateCommentRequest() {
    }

    public UpdateCommentRequest(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
