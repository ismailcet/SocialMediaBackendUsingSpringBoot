package com.ismailcet.SocialMedia.util.converter;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto convert(User user){
        UserDto userDto =
                new UserDto(user);
        return userDto;
    }

}