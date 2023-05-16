package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowDtoConverter {
    public FollowDto convert(Follow follow){
        FollowDto followDto = new FollowDto();
        followDto.setId(follow.getId());
        followDto.setFollowUser(new UserDto(follow.getFollowUser()));
        followDto.setFollowingUser(new UserDto(follow.getFollowingUser()));

        return followDto;
    }
}
