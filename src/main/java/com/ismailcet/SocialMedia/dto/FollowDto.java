package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class FollowDto {
    private Integer id;
    @NotNull(message = "Followed User Id may not be null")
    private UserDto followUser;
    @NotNull(message = "Following User Id may not be null")
    private UserDto followingUser;

    public FollowDto() {
    }

    public FollowDto(Integer id, UserDto followUser, UserDto followingUser) {
        this.id = id;
        this.followUser = followUser;
        this.followingUser = followingUser;
    }

    public FollowDto(FollowDtoBuilder followDtoBuilder){
        this.followUser = followDtoBuilder.followUser;
        this.followingUser =followDtoBuilder.followingUser;
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

    public static class FollowDtoBuilder{
        private UserDto followUser;
        private UserDto followingUser;

        public FollowDtoBuilder followUser(UserDto followUser){
            this.followUser=followUser;
            return this;
        }
        public FollowDtoBuilder followingUser(UserDto followingUser){
            this.followingUser=followingUser;
            return this;
        }
        public FollowDto build(){
            return new FollowDto(this);
        }
    }

    @Override
    public String toString() {
        return "FollowDto{" +
                "id=" + id +
                ", followUser=" + followUser +
                ", followingUser=" + followingUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowDto)) return false;
        FollowDto followDto = (FollowDto) o;
        return Objects.equals(getId(), followDto.getId()) && Objects.equals(getFollowUser(), followDto.getFollowUser()) && Objects.equals(getFollowingUser(), followDto.getFollowingUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFollowUser(), getFollowingUser());
    }
}
