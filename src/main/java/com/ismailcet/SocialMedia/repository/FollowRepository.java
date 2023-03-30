package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {

    @Query(
            value="SELECT COUNT(*) from followuser f WHERE f.followed_userid = ?1",
            nativeQuery = true
    )
    public Integer getFollowersCount(Integer followed_userid);
}
