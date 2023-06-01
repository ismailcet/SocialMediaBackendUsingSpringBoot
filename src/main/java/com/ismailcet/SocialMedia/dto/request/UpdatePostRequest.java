package com.ismailcet.SocialMedia.dto.request;

import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "UpdatePostRequest{" +
                "content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdatePostRequest)) return false;
        UpdatePostRequest that = (UpdatePostRequest) o;
        return Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
