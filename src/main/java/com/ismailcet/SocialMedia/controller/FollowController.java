package com.ismailcet.SocialMedia.controller;


import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.dto.request.CreateFollowRequest;
import com.ismailcet.SocialMedia.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping()
    public ResponseEntity<FollowDto> createFollow(@RequestBody CreateFollowRequest createFollowRequest){
        try{
            return followService.createFollow(createFollowRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFollowById(@PathVariable("id") Integer id){
        try{
            return followService.deleteFollowById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong ! " , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/followerCount/{followedId}")
    public ResponseEntity<String> getFollowerCountByUserId(@PathVariable("followedId") Integer id){
        try{
            return followService.getFollowerCountByUserId(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong ! ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
