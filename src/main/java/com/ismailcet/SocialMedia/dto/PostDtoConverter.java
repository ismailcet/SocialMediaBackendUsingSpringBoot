package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {

    public PostDto convert(Post post){
        PostDto postDto = new PostDto.PostDtoBuilder()
                .id(post.getId())
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .user(new UserDto(post.getUser()))
                .build();

        return postDto;
    }
}
