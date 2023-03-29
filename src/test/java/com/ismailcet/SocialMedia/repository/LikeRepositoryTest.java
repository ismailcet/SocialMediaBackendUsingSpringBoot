package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;


    @Test
    public void createLike(){
        LocalDateTime timezone
                = LocalDateTime.now();

        User user =
                new User("ismailcet3","pass3","iso","çet","i@g.com",20);

        Post post =
                new Post(timezone,"Deneme Content" , "Url" , user);

        Like like = new Like(user,post);

        likeRepository.save(like);
    }

    @Test
    public void getAllLike(){
        List<Like> likeList =
                likeRepository.findAll();

        System.out.println("Likes : " + likeList);
    }

    @Test
    public void deleteLikeById(){
        likeRepository.deleteById(6);
    }

    @Test
    public void updateLikeById(){
        LocalDateTime timezone
                = LocalDateTime.now();

        Like like =
                likeRepository.findById(6).orElse(null);

        Like newLike =new Like();

        if(like != null) {
            Post post =
                    new Post(timezone,"Deneme Content 2 " , "Url" , like.getUser());
            newLike.setId(like.getId());
            newLike.setUser(like.getUser());
            newLike.setPost(post);

            likeRepository.save(newLike);
        }
    }
}