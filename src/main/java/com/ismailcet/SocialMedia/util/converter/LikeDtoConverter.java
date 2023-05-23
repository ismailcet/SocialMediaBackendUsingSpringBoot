package com.ismailcet.SocialMedia.util.converter;

import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.entity.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeDtoConverter {

    public LikeDto convert(Like like){
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setCreatedDate(like.getCreatedDate());
        likeDto.setUser(new UserDto(like.getUser()));
        likeDto.setPost(new PostDto(like.getPost()));

        return likeDto;
    }

}
