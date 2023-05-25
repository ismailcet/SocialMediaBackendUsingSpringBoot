package com.ismailcet.SocialMedia.controller;


import com.ismailcet.SocialMedia.dto.FollowDto;
import com.ismailcet.SocialMedia.dto.request.CreateFollowRequest;
import com.ismailcet.SocialMedia.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping()
    public ResponseEntity<FollowDto> createFollow(@RequestBody CreateFollowRequest createFollowRequest){
       return ResponseEntity.ok(followService.createFollow(createFollowRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFollowById(@PathVariable("id") Integer id){
        followService.deleteFollowById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followerCount/{followedId}")
    public ResponseEntity<String> getFollowerCountByUserId(@PathVariable("followedId") Integer id){
        return ResponseEntity.ok(followService.getFollowerCountByUserId(id));
    }
}
