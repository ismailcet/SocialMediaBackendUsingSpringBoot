package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.dto.request.CreateLikeRequest;
import com.ismailcet.SocialMedia.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping()
    public ResponseEntity<LikeDto> createLike(@RequestBody CreateLikeRequest createLikeRequest){
        try{
            return likeService.createLike(createLikeRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLikeByLikeId(@PathVariable("id") Integer id){
        try{
            return likeService.deleteLikeByLikeId(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getLikesByPostId(@PathVariable("postId") Integer postId){
        try{
            return likeService.getLikesByPostId(postId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
