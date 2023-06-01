package com.ismailcet.SocialMedia.dto.request;

import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CreateFollowRequest {
    @NotNull(message = "Followed User Id may not be null")
    private Integer followUser_id;
    @NotNull(message = "Following User Id may not be null")
    private Integer followingUser_id;

    public CreateFollowRequest() {
    }

    public CreateFollowRequest(Integer followUser_id, Integer followingUser_id) {
        this.followUser_id = followUser_id;
        this.followingUser_id = followingUser_id;
    }

    public Integer getFollowUser_id() {
        return followUser_id;
    }

    public void setFollowUser_id(Integer followUser_id) {
        this.followUser_id = followUser_id;
    }

    public Integer getFollowingUser_id() {
        return followingUser_id;
    }

    public void setFollowingUser_id(Integer followingUser_id) {
        this.followingUser_id = followingUser_id;
    }

    @Override
    public String toString() {
        return "CreateFollowRequest{" +
                "followUser_id=" + followUser_id +
                ", followingUser_id=" + followingUser_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateFollowRequest)) return false;
        CreateFollowRequest that = (CreateFollowRequest) o;
        return Objects.equals(getFollowUser_id(), that.getFollowUser_id()) && Objects.equals(getFollowingUser_id(), that.getFollowingUser_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFollowUser_id(), getFollowingUser_id());
    }
}
