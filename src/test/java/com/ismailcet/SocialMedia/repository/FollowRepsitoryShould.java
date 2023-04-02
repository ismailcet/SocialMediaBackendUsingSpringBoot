package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.Follow;
import com.ismailcet.SocialMedia.entity.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FollowRepsitoryShould {

    @Autowired
    private FollowRepository followRepository;

    @Test
    public void createComment_WhenParametersValid_Succesfully(){

        User user1 =
                new User("ismailTest123","passwordTest","FirstName","LastName","email@gmail.com",12);

        User user2 =
                new User("ismailTest123","passwordTest","FirstName","LastName","email@gmail.com",12);

        Follow follow =
                new Follow(user1,user2);

        followRepository.save(follow);

        Follow follow1 =
                followRepository.findById(follow.getId()).orElse(null);

        assertNotNull(follow1);

    }

    @Test
    public void deleteFollow_WhenGivenIdValid_ThenDeletedSuccesfully(){

        User user1 =
                new User("ismailTest123","passwordTest","FirstName","LastName","email@gmail.com",12);

        User user2 =
                new User("ismailTest123","passwordTest","FirstName","LastName","email@gmail.com",12);

        Follow follow =
                new Follow(user1,user2);

        followRepository.save(follow);

        followRepository.deleteById(follow.getId());

        Follow followDeleted =
                followRepository.findById(follow.getId()).orElse(null);

        assertNull(followDeleted);

    }

    @Test
    public void updateFollow_WhenGivenUserIdValid_ThenSuccesfully(){
        User user1 =
                new User("ismailTest123","passwordTest","FirstName","LastName","email@gmail.com",12);

        User user2 =
                new User("ismailTest123","passwordTest","FirstName","LastName","email@gmail.com",12);

        Follow follow =
                new Follow(user1,user2);

        followRepository.save(follow);

        Follow follow1 =
                followRepository.findById(follow.getId()).orElse(null);

        assertNotNull(follow1);

        Follow follow2 =
                new Follow();
        User user3 =
                new User("ismailTest123123","passwordTest","FirstName","LastName","email@gmail.com",12);

        follow2.setId(follow1.getId());
        follow2.setFollowUser(follow1.getFollowUser());
        follow2.setFollowingUser(user3);

        followRepository.save(follow2);

        Follow changeFollow =
                followRepository.findById(follow2.getId()).orElse(null);

        assertNotEquals(follow1.getFollowingUser().getId(),changeFollow.getFollowingUser().getId());
        assertEquals(follow2.getFollowingUser().getUserName(),changeFollow.getFollowingUser().getUserName());

    }

    @Test
    public void getCountFollower_WhenGivenFollowerUserId(){
        User user1 =
                new User("ismailTest1231","passwordTest","FirstName","LastName","email@gmail.com",12);

        User user2 =
                new User("ismailTest1232","passwordTest","FirstName","LastName","email@gmail.com",12);

        Follow follow =
                new Follow(user1,user2);

        followRepository.save(follow);

        Follow follow1 =
                followRepository.findById(follow.getId()).orElse(null);

        Integer count =
                followRepository.getFollowersCount(follow1.getFollowUser().getId());

        assertTrue(count >0);



    }
}
