package com.ismailcet.SocialMedia.util.converter;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto convert(User user){
        UserDto userDto =
                new UserDto.UserDtoBuilder()
                        .userName(user.getUserName())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .age(user.getAge())
                        .build();
        return userDto;
    }

}
