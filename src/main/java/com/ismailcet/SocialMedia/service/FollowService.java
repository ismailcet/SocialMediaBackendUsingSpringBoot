package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.dto.FollowDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateFollowRequest;
import com.ismailcet.SocialMedia.entity.Follow;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.FollowRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowDtoConverter followDtoConverter;

    public FollowService(FollowRepository followRepository, UserRepository userRepository, FollowDtoConverter followDtoConverter) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.followDtoConverter = followDtoConverter;
    }

    public ResponseEntity<FollowDto> createFollow(CreateFollowRequest createFollowRequest) {
        try{
            Optional<User> followedUser  = userRepository.findById(createFollowRequest.getFollowUser_id());
            Optional<User> followingUser  = userRepository.findById(createFollowRequest.getFollowingUser_id());

            if(followedUser.isPresent() && followingUser.isPresent()){
                Follow follow = new Follow.FollowBuilder()
                        .followUser(followedUser.get())
                        .followingUser(followingUser.get())
                        .build();
                followRepository.save(follow);
                return new ResponseEntity<>(followDtoConverter.convert(follow), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteFollowById(Integer id) {
        try{
            Optional<Follow> follow =
                    followRepository.findById(id);

            if(follow.isPresent()){
                followRepository.deleteById(id);
                return new ResponseEntity<>("Follow Successfully Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Follow id does not exist" , HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong !" ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> getFollowerCountByUserId(Integer id) {
        try{
            Optional<User> user =
                    userRepository.findById(id);
            if(user.isPresent()){
                Integer count = followRepository.getFollowersCount(id);
                return new ResponseEntity<>(user.get().getUserName() + "follower count : " + count , HttpStatus.OK);
            }
            return new ResponseEntity<>("User id does not exist ! " , HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong ! " ,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
