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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostRequest createPostRequest){
        try{
            return postService.createPost(createPostRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostByPostId(@PathVariable("id") Integer id , @RequestBody UpdatePostRequest updatePostRequest){
        try{
            return postService.updatePostByPostId(id,updatePostRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostByPostId(@PathVariable("id") Integer id){
        try{
            return postService.deletePostByPostId(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping()
    public ResponseEntity<List<GetAllPostsResponse>> getAllPosts(){
        try{
            return postService.getAllPosts();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostByPostIdResponse> getPostByPostId(@PathVariable("id") Integer id){
        try{
            return postService.getPostByPostId(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/like/{day}")
    public ResponseEntity<PostDto> getPostMostLikedThreeDays(@PathVariable("day") Integer day){
        try{
            return postService.getPostMostLikedThreeDays(day);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/comment/{username}")
    public ResponseEntity<PostDto> getPostwithUsernameMostComment(@PathVariable("username") String username){
        try{
            return postService.getPostwithUsernameMostComment(username);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
