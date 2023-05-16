package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.dto.request.CreateCommentRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateCommentRequest;
import com.ismailcet.SocialMedia.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CommentDto> createComment(@RequestBody CreateCommentRequest createCommentRequest){
        try{
            return commentService.createComment(createCommentRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentByCommentId(@PathVariable("id") Integer id, @RequestBody UpdateCommentRequest updateCommentRequest){
        try{
            return commentService.updateCommentByCommentId(id, updateCommentRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentByCommentId(@PathVariable("id") Integer id){
        try{
            return commentService.deleteCommentByCommentId(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") Integer postId){
        try{
            return commentService.getCommentsByPostId(postId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
