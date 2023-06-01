package com.ismailcet.SocialMedia.dto.request;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateCommentRequest {
    @NotNull(message = "")
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

    @Override
    public String toString() {
        return "UpdateCommentRequest{" +
                "comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateCommentRequest)) return false;
        UpdateCommentRequest that = (UpdateCommentRequest) o;
        return Objects.equals(getComment(), that.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComment());
    }
}
