package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.dto.CommentDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateCommentRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateCommentRequest;
import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.CommentRepository;
import com.ismailcet.SocialMedia.repository.PostRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final CommentDtoConverter commentDtoConverter;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, CommentDtoConverter commentDtoConverter) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentDtoConverter = commentDtoConverter;
    }

    public ResponseEntity<CommentDto> createComment(CreateCommentRequest createCommentRequest) {
        try{
            Optional <User> user = userRepository.findById(createCommentRequest.getUser_id());
            Optional <Post> post = postRepository.findById(createCommentRequest.getPost_id());

            if(user.isPresent() && post.isPresent()){
                LocalDateTime now = LocalDateTime.now();
                Comment comment = new Comment.CommentBuilder()
                        .comment(createCommentRequest.getComment())
                        .createdDate(now)
                        .user(user.get())
                        .post(post.get())
                        .build();
                System.out.println(comment);
                commentRepository.save(comment);
                return new ResponseEntity<>(commentDtoConverter.convert(comment), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<CommentDto> updateCommentByCommentId(Integer id, UpdateCommentRequest updateCommentRequest) {
        try{
            Optional<Comment> comment =
                    commentRepository.findById(id);

            if(comment.isPresent()){
                comment.get().setComment(updateCommentRequest.getComment());
                commentRepository.save(comment.get());
                return new ResponseEntity<>(commentDtoConverter.convert(comment.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteCommentByCommentId(Integer id) {
        try{
            Optional <Comment> comment =
                    commentRepository.findById(id);
            if(comment.isPresent()){
                commentRepository.deleteById(id);
                return new ResponseEntity<>("Comment Id Successfully Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Comment Id does not exists" , HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<CommentDto>> getCommentsByPostId(Integer postId){
        try{
            Optional<Post> post =
                    postRepository.findById(postId);
            if(post.isPresent()){
                List<CommentDto> comments = commentRepository.findByPostId(postId)
                        .stream()
                        .map(comment -> commentDtoConverter.convert(comment))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
