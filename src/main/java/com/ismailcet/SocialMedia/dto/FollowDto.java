package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.User;

public class FollowDto {
    private Integer id;
    private UserDto followUser;
    private UserDto followingUser;

    public FollowDto() {
    }

    public FollowDto(Integer id, UserDto followUser, UserDto followingUser) {
        this.id = id;
        this.followUser = followUser;
        this.followingUser = followingUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getFollowUser() {
        return followUser;
    }

    public void setFollowUser(UserDto followUser) {
        this.followUser = followUser;
    }

    public UserDto getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(UserDto followingUser) {
        this.followingUser = followingUser;
    }
}
