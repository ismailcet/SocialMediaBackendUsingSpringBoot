package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(
            value = "SELECT posts.id ,posts.user_id,posts.created_date, posts.content FROM posts INNER JOIN commentforpostsbyuser comment ON posts.id = comment.post_id INNER JOIN users ON posts.user_id = users.id WHERE users.username = ?1 GROUP BY posts.id ORDER BY COUNT(*) DESC LIMIT 1",
            nativeQuery = true
    )
    public Post getPostwithUsernameMostComment(String username);

    @Query(
            value = "SELECT posts.id ,posts.user_id,posts.created_date, posts.content from posts INNER JOIN likesforpostsbyuser l ON posts.id = l.post_id WHERE l.created_Date >= (CURRENT_DATE - ?1) GROUP BY posts.id ORDER BY COUNT(l) DESC LIMIT 1",  //>= (CURRENT_DATE - INTERVAL '3 days')
            nativeQuery = true
    )
    @Transactional
    public Post getPostMostLikedThreeDays(Integer day);
}
