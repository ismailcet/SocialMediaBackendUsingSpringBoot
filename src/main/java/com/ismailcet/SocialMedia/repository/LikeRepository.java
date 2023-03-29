package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
}
