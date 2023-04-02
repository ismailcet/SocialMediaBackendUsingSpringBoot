package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.Share;
import com.ismailcet.SocialMedia.entity.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ShareRepositoryShould {


    @Autowired
    private ShareRepository shareRepository;
    @Autowired
    private  PostRepository postRepository;

    @Test
    public void createShare_WhenParametersValid_Succesfully(){
        User user =
                new User("ismailTest140","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post 01",user
                );

        Share share =
                new Share("shareurl.com",post);

        shareRepository.save(share);

        Share share1 =
                shareRepository.findById(share.getId()).orElse(null);

        assertNotNull(share1);
        assertEquals(share.getId(),share1.getId());
        assertEquals(share.getPost().getId(),post.getId());

    }

    @Test
    public void deleteShare_WhenGivenIdValid_ThenDeletedSuccesfully(){
        User user =
                new User("ismailTest140","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post 01",user
                );

        Share share =
                new Share("shareurl.com",post);

        shareRepository.save(share);

        shareRepository.deleteById(share.getId());

        Share deletedShare =
                shareRepository.findById(share.getId()).orElse(null);

        assertNull(deletedShare);
    }


}
