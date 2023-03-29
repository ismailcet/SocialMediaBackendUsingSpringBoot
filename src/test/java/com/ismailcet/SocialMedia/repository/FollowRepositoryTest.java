package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Follow;
import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createFollow(){
        User user1 =
                userRepository.findById(127).orElse(null);
        User user2 =
                userRepository.findById(129).orElse(null);

        Follow follow =
                new Follow(user1,user2);

        followRepository.save(follow);

    }

    @Test
    public void deleteFollowById(){
        followRepository.deleteById(8);
    }
    @Test
    public void updateFollowById(){
        User user =
                userRepository.findById(128).orElse(null);

        Follow follow =
                followRepository.findById(8).orElse(null);

        Follow newFollow =
                new Follow();
        if(follow != null){
            newFollow.setId(follow.getId());
            newFollow.setFollowingUser(follow.getFollowingUser());
            newFollow.setFollowUser(user);

            followRepository.save(newFollow);

        }

    }
}