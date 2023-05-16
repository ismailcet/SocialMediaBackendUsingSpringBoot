package com.ismailcet.SocialMedia.dto.request;

import com.ismailcet.SocialMedia.entity.User;

public class CreateFollowRequest {
    private Integer followUser_id;
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
}
