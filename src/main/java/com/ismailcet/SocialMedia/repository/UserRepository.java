package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserName(String username);
}
