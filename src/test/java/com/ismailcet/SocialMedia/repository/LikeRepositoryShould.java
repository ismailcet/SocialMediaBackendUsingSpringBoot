package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LikeRepositoryShould {

    @Autowired
    private LikeRepository likeRepository;

    @Test
    public void createLike_WhenParametersValid_Succesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Like like =
                new Like(user,post,timezone);

        likeRepository.save(like);

        Like like1 =
                likeRepository.findById(like.getId()).orElse(null);

        assertEquals(like1.getId(),like.getId());
        assertEquals(like1.getUser().getId(),like.getUser().getId());
    }

    @Test
    public void deleteLike_WhenGivenIdValid_ThenDeletedSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Like like =
                new Like(user,post,timezone);

        likeRepository.save(like);

        likeRepository.deleteById(like.getId());


        Like deletedLike =
                likeRepository.findById(like.getId()).orElse(null);
        assertNull(deletedLike);
    }

    @Test
    public void updateLike_WhenGivenIdValid_ThenUpdateLikeAnyFieldSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Like like =
                new Like(user,post,timezone);

        likeRepository.save(like);

        Like checkLike =
                likeRepository.findById(like.getId()).orElse(null);

        assertNotNull(checkLike);

        User user1 =
                new User("ismailTest11","passwordTest","FirstName","LastName","email@gmail.com",12);

        Like newLike =
                new Like();

        newLike.setId(checkLike.getId());
        newLike.setUser(user1);
        newLike.setPost(checkLike.getPost());
        newLike.setCreatedDate(checkLike.getCreatedDate());

        likeRepository.save(newLike);

        Like changeLike =
                likeRepository.findById(newLike.getId()).orElse(null);

        assertNotNull(changeLike);
        assertNotEquals(checkLike.getUser(),changeLike.getUser());
        assertEquals(newLike.getUser().getUserName(),changeLike.getUser().getUserName());
        assertEquals(user1.getUserName(),changeLike.getUser().getUserName());

    }

    @Test
    public void getLike_WhenGivenPostIdValid_ThenGetSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Like like =
                new Like(user,post,timezone);

        likeRepository.save(like);

        List<Like> likes =
                likeRepository.findByPostId(post.getId());

        assertNotNull(likes);
    }
}
