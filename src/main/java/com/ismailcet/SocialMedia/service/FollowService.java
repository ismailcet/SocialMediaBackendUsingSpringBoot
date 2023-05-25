package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.exception.FollowNotFoundException;
import com.ismailcet.SocialMedia.util.converter.FollowDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateFollowRequest;
import com.ismailcet.SocialMedia.entity.Follow;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.FollowRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowDtoConverter followDtoConverter;

    Logger logger = LoggerFactory.getLogger(FollowService.class);

    public FollowService(FollowRepository followRepository, UserRepository userRepository, FollowDtoConverter followDtoConverter) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.followDtoConverter = followDtoConverter;
    }

    public FollowDto createFollow(CreateFollowRequest createFollowRequest) {
        try{
            Optional<User> followedUser  = userRepository.findById(createFollowRequest.getFollowUser_id());
            Optional<User> followingUser  = userRepository.findById(createFollowRequest.getFollowingUser_id());

            if(followedUser.isPresent() && followingUser.isPresent()){
                Follow follow = new Follow.FollowBuilder()
                        .followUser(followedUser.get())
                        .followingUser(followingUser.get())
                        .build();
                followRepository.save(follow);
                return followDtoConverter.convert(follow);
            }
            throw new FollowNotFoundException("Followed User Id or Following User Id does not exists");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new FollowNotFoundException(ex.getMessage());
        }
    }

    public void deleteFollowById(Integer id) {
        try{
            Optional<Follow> follow =
                    followRepository.findById(id);

            if(follow.isPresent()){
                followRepository.deleteById(id);
            }else{
                throw new FollowNotFoundException("Follow Id does not exists");
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new FollowNotFoundException(ex.getMessage());
        }
    }

    public String getFollowerCountByUserId(Integer id) {
        try{
            Optional<User> user =
                    userRepository.findById(id);
            if(user.isPresent()){
                Integer count = followRepository.getFollowersCount(id);
                return user.get().getUserName() + "follower count : " + count;
            }
            throw new FollowNotFoundException("User Id does not exists");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new FollowNotFoundException(ex.getMessage());
        }
    }
}
