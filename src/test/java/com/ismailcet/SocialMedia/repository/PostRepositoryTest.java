package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void createPost(){
        LocalDateTime timezone
                = LocalDateTime.now();

        User user =
            new User(
                    "ismail","denemePass","ismail","Çetin","is@gmail.com",25
            );

        Post post =
                new Post(
                        timezone,"Deneme Post ","url",user
                );
        postRepository.save(post);
    }


}