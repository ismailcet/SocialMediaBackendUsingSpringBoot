package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.dto.request.CreateLikeRequest;
import com.ismailcet.SocialMedia.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping()
    public ResponseEntity<LikeDto> createLike(@Valid @RequestBody CreateLikeRequest createLikeRequest){
        return new ResponseEntity<>(
                likeService.createLike(createLikeRequest),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLikeByLikeId(@PathVariable("id") Integer id){
        likeService.deleteLikeByLikeId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getLikesByPostId(@PathVariable("postId") Integer postId){

        return ResponseEntity.ok(likeService.getLikesByPostId(postId));

    }
}
