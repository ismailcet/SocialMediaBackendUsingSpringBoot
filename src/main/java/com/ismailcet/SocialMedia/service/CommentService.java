package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.exception.CommentNotFoundException;
import com.ismailcet.SocialMedia.util.converter.CommentDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateCommentRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateCommentRequest;
import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.CommentRepository;
import com.ismailcet.SocialMedia.repository.PostRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, CommentDtoConverter commentDtoConverter) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentDtoConverter = commentDtoConverter;
    }

    public CommentDto createComment(CreateCommentRequest createCommentRequest) {
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

                commentRepository.save(comment);
                return commentDtoConverter.convert(comment);
            }
            throw new CommentNotFoundException("User Id or Post Id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new CommentNotFoundException(ex.getMessage());
        }
    }

    public CommentDto updateCommentByCommentId(Integer id, UpdateCommentRequest updateCommentRequest) {
        try{
            Optional<Comment> comment =
                    commentRepository.findById(id);

            if(comment.isPresent()){
                comment.get().setComment(updateCommentRequest.getComment());
                commentRepository.save(comment.get());
                return commentDtoConverter.convert(comment.get());
            }
            throw new CommentNotFoundException("Comment Id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new CommentNotFoundException(ex.getMessage());
        }
    }

    public void deleteCommentByCommentId(Integer id) {
        try{
            Optional <Comment> comment =
                    commentRepository.findById(id);
            if(comment.isPresent()){
                commentRepository.deleteById(id);
            }else{
                throw new CommentNotFoundException("Comment Id does not exists ! ");
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new CommentNotFoundException(ex.getMessage());
        }
    }

    public List<CommentDto> getCommentsByPostId(Integer postId){
        try{
            Optional<Post> post =
                    postRepository.findById(postId);
            if(post.isPresent()){
                List<CommentDto> comments = commentRepository.findByPostId(postId)
                        .stream()
                        .map(comment -> commentDtoConverter.convert(comment))
                        .collect(Collectors.toList());
                return comments;
            }
            throw new CommentNotFoundException("Post Id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new CommentNotFoundException(ex.getMessage());
        }
    }
}
