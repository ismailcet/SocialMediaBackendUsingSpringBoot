package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {
}
