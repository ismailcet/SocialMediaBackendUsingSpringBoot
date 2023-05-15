package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostRepositoryShould {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Test
    public void createPost_WhenParametersValid_Succesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );
        postRepository.save(post);

        Post newPost =
                postRepository.findById(post.getId()).orElse(null);

        assertNotNull(newPost);
        assertEquals(user.getId(),newPost.getUser().getId());
        assertEquals(post.getId(),newPost.getId());

    }

    @Test
    public void deletePost_WhenGivenIdValid_ThenDeletedSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        postRepository.save(post);

        postRepository.deleteById(post.getId());


        Post deletedPost =
                postRepository.findById(post.getId()).orElse(null);
        assertNull(deletedPost);
    }

    @Test
    public void updatePost_WhenGivenIdValid_ThenUpdatePostAnyFieldSuccesfully(){
        User user =
                new User("ismailTest","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        postRepository.save(post);

        Post updatePost =
                postRepository.findById(post.getId()).orElse(null);

        assertNotNull(updatePost);

        Post newPost =
                new Post();

        newPost.setId(post.getId());
        newPost.setUser(post.getUser());
        newPost.setCreatedDate(post.getCreatedDate());
        newPost.setContent("COntent Degisti");

        postRepository.save(newPost);

        Post changePost =
                postRepository.findById(newPost.getId()).orElse(null);

        assertNotNull(changePost);
        assertNotEquals(updatePost.getContent(),changePost.getContent());
        assertEquals(newPost.getContent(),changePost.getContent());
        assertEquals("COntent Degisti",changePost.getContent());
    }

    @Test
    public void getAllPost_WhenUsersIsNotNull_thenSuccesfully(){
        List<Post> posts =
                postRepository.findAll();

        assertNotNull(posts);
    }

    @Test
    public void getPost_WhenTheMostLikedPost_ThenSuccesfully(){
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

        Post likedPost =
                postRepository.getPostMostLikedThreeDays(3);

        assertNotNull(likedPost);

    }

    @Test
    public void getPost_WhenGivenUserNameValid_ThenGetHasMostCommentsSuccesfully(){
        User user =
                new User("ismailTest4","passwordTest","FirstName","LastName","email@gmail.com",12);

        LocalDateTime timezone
                = LocalDateTime.now();

        Post post =
                new Post(
                        timezone,"Deneme Post",user
                );

        Comment comment =
                new Comment("Comment",timezone,user,post);

        commentRepository.save(comment);

        User user1 =
                userRepository.findByUserName(user.getUserName());

        assertNotNull(user1.getId());

        Post commentPost =
                postRepository.getPostwithUsernameMostComment(user.getUserName());

        assertNotNull(commentPost);


    }
}
