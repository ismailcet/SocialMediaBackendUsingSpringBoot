package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    @Test
    public void createComment(){
        LocalDateTime timezone
                = LocalDateTime.now();
        User user =
                userRepository.findById(127).orElse(null);

        Post post =
                postRepository.findById(17).orElse(null);

        Comment comment =
                new Comment("New Comment " , timezone , user , post);

        commentRepository.save(comment);
    }

    @Test
    public void deleteCommentById(){
        commentRepository.deleteById(7);
    }

    @Test
    public void updateCommentById(){
        Comment comment =
                commentRepository.findById(6).orElse(null);

        Comment newComment =
                new Comment();

        if(comment != null){
            newComment.setId(comment.getId());
            newComment.setUser(comment.getUser());
            newComment.setPost(comment.getPost());
            newComment.setCreatedDate(comment.getCreatedDate());
            newComment.setComment("Comment Updated");

            commentRepository.save(newComment);
        }

    }
}