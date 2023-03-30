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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    //Create a post record
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
                        timezone,"Deneme Post",user
                );
        postRepository.save(post);
    }
    //Get All Post Record
    @Test
    public void getAllPosts(){
        List<Post> posts =
                postRepository.findAll();

        System.out.println(posts);
    }
    //Delete a Post Record
    @Test
    public void deletePostById(){
        postRepository.deleteById(15);
    }

    //update a post record
    @Test
    public void updatePostById(){
        Post post =
                postRepository.findById(15).orElse(null);

        Post newPost = new Post();
        if(post != null){
            newPost.setId(post.getId());
            newPost.setCreatedDate(post.getCreatedDate());
            newPost.setUser(post.getUser());
            newPost.setContent("Content Updated");

            postRepository.save(newPost);
        }
    }

    //Get the post with username which has most comments
    @Test
    public void getPostByUsernameMostComment(){
        Post post =
                postRepository.getPostwithUsernameMostComment("ismailcet3");

        System.out.println("Post : " + post);
    }

    //Get the post which has most likes within last 3 days
    @Test
    public void getPostMostLikedThreeDays(){
        Post post =
                postRepository.getPostMostLikedThreeDays();

        System.out.println("Post : " + post);
    }

}