package com.ismailcet.SocialMedia.util.converter;

import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.dto.UserDto;
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
