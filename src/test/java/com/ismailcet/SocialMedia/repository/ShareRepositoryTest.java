package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.Share;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShareRepositoryTest {

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private PostRepository postRepository;

    //Create a share record
    @Test
    public void createShareLink(){
        Post post =
                postRepository.findById(17).orElse(null);
        Share share =
                new Share("shareurl.com",post);

        shareRepository.save(share);
    }

    //Delete a share record
    @Test
    public void deleteShareLinkById(){
        shareRepository.deleteById(1);
    }
}