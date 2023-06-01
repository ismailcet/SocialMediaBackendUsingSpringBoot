package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.request.CreatePostRequest;
import com.ismailcet.SocialMedia.dto.request.UpdatePostRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllPostsResponse;
import com.ismailcet.SocialMedia.dto.response.GetPostByPostIdResponse;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequest createPostRequest){
        return new ResponseEntity<>(
                postService.createPost(createPostRequest)
                ,HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostByPostId(@Valid @PathVariable("id") Integer id ,@Valid @RequestBody UpdatePostRequest updatePostRequest){
        return ResponseEntity.ok(postService.updatePostByPostId(id,updatePostRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostByPostId(@PathVariable("id") Integer id){
        postService.deletePostByPostId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<GetAllPostsResponse>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostByPostIdResponse> getPostByPostId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(postService.getPostByPostId(id));
    }

    @GetMapping("/like/{day}")
    public ResponseEntity<PostDto> getPostMostLikedThreeDays(@PathVariable("day") Integer day){
        return ResponseEntity.ok(postService.getPostMostLikedThreeDays(day));
    }

    @GetMapping("/comment/{username}")
    public ResponseEntity<PostDto> getPostwithUsernameMostComment(@PathVariable("username") String username){
        return ResponseEntity.ok(postService.getPostWithUsernameMostComment(username));
    }
}
