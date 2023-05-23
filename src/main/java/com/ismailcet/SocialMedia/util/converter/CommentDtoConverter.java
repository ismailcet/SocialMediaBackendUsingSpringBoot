package com.ismailcet.SocialMedia.util.converter;

import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter {

    public CommentDto convert(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setComment(comment.getComment());
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setUser(new UserDto(comment.getUser()));
        commentDto.setPost(new PostDto(comment.getPost()));

        return commentDto;
    }
}
