package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CommentRepositoryShould {

    @Autowired
    private CommentRepository commentRepository;


    @Test
    public void createComment_WhenParametersValid_Succesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Comment comment =
                new Comment("Comment Deneme" , timezone,user,post);

        commentRepository.save(comment);

        Comment comment1 =
                commentRepository.findById(comment.getId()).orElse(null);

        assertEquals(comment1.getId(),comment.getId());
        assertEquals(comment1.getUser().getId(),comment.getUser().getId());
    }

    @Test
    public void deleteComment_WhenGivenIdValid_ThenDeletedSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Comment comment =
                new Comment("Comment Deneme" , timezone,user,post);

        commentRepository.save(comment);

        commentRepository.deleteById(comment.getId());


        Comment deletedComment =
                commentRepository.findById(comment.getId()).orElse(null);
        assertNull(deletedComment);
    }

    @Test
    public void updateComment_WhenGivenIdValid_ThenUpdateCommentAnyFieldSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Comment comment =
                new Comment("Comment Deneme" , timezone,user,post);

        commentRepository.save(comment);

        Comment checkComment =
                commentRepository.findById(comment.getId()).orElse(null);

        assertNotNull(checkComment);

        Comment newComment =
                new Comment();

        newComment.setId(checkComment.getId());
        newComment.setUser(checkComment.getUser());
        newComment.setPost(checkComment.getPost());
        newComment.setCreatedDate(checkComment.getCreatedDate());
        newComment.setComment("New Comment");

        commentRepository.save(newComment);

        Comment changeComment =
                commentRepository.findById(newComment.getId()).orElse(null);

        assertNotNull(changeComment);
        assertNotEquals(checkComment.getComment(),changeComment.getComment());
        assertEquals(newComment.getComment(),changeComment.getComment());
        assertEquals("New Comment",changeComment.getComment());

    }

    @Test
    public void getAllComment_WhenIs_thenSuccesfully(){
        List<Comment> comments =
                commentRepository.findAll();

        assertNotNull(comments);
    }

    @Test
    public void getComment_WhenGivenPostIdValid_ThenGetSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Comment comment =
                new Comment("Comment Deneme" , timezone,user,post);

        commentRepository.save(comment);

        List<Comment> comments =
                commentRepository.findByPostId(post.getId());

        assertNotNull(comments);
    }

}
