package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share,Integer> {
}
