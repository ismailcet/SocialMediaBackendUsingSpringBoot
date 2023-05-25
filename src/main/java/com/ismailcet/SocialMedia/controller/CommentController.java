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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CommentDto> createComment(@RequestBody CreateCommentRequest createCommentRequest){
        return ResponseEntity.ok(commentService.createComment(createCommentRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentByCommentId(@PathVariable("id") Integer id, @RequestBody UpdateCommentRequest updateCommentRequest){
        return ResponseEntity.ok(commentService.updateCommentByCommentId(id, updateCommentRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentByCommentId(@PathVariable("id") Integer id){
        commentService.deleteCommentByCommentId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") Integer postId){
       return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
