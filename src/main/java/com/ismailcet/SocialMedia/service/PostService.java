package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.exception.PostNotFoundException;
import com.ismailcet.SocialMedia.util.converter.PostDtoConverter;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreatePostRequest;
import com.ismailcet.SocialMedia.dto.request.UpdatePostRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllPostsResponse;
import com.ismailcet.SocialMedia.dto.response.GetPostByPostIdResponse;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostDtoConverter postDtoConverter;

    Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostService(PostRepository postRepository, UserRepository userRepository, PostDtoConverter postDtoConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postDtoConverter = postDtoConverter;
    }

    public PostDto createPost(CreatePostRequest createPostRequest) {
        try{
            Optional<User> user =
                    userRepository.findById(createPostRequest.getUser_id());
            if(user.isPresent()){
                LocalDateTime now = LocalDateTime.now();

                Post post = new Post.PostBuilder()
                        .content(createPostRequest.getContent())
                        .createdDate(now)
                        .user(user.get())
                        .build();
                postRepository.save(post);

                return postDtoConverter.convert(post);
            }
            throw new PostNotFoundException("Post id already exists ! ");

        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException(ex.getMessage());
        }
    }

    public PostDto updatePostByPostId(Integer id, UpdatePostRequest updatePostRequest) {
        try{
            Optional<Post> post =
                    postRepository.findById(id);

            if(post.isPresent()){
                post.get().setContent(updatePostRequest.getContent());
                postRepository.save(post.get());

                return postDtoConverter.convert(post.get());
            }
            throw new PostNotFoundException("Post Id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException(ex.getMessage());
        }
    }

    public void deletePostByPostId(Integer id) {
        try{
            Optional<Post> post =
                    postRepository.findById(id);
            if(post.isPresent()){
                postRepository.deleteById(id);
            }else{
                throw new PostNotFoundException("Post Id does not exists ! ");
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException(ex.getMessage());
        }
    }

    public List<GetAllPostsResponse> getAllPosts() {
        try{
            List<GetAllPostsResponse> posts =
                    postRepository.findAll().stream()
                            .map(post -> new GetAllPostsResponse(
                                    post.getId(),
                                    post.getContent(),
                                    new UserDto(post.getUser()),
                                    post.getCreatedDate()))
                            .collect(Collectors.toList());
            return posts;
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException("Something Went Wrong ! ");
        }
    }

    public GetPostByPostIdResponse getPostByPostId(Integer id) {
        try{
            Optional<Post> postOne =
                    postRepository.findById(id);

            if(postOne.isPresent()){
                GetPostByPostIdResponse post = new GetPostByPostIdResponse(
                        postOne.get().getId(),
                        postOne.get().getContent()
                        ,new UserDto(postOne.get().getUser())
                        ,postOne.get().getCreatedDate());

                return post;
            }
            throw new PostNotFoundException("Post id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException(ex.getMessage());
        }
    }

    public PostDto getPostMostLikedThreeDays(Integer day) {
        try{
            Post post =
                    postRepository.getPostMostLikedThreeDays(day);

            return postDtoConverter.convert(post);
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException(ex.getMessage());
        }
    }

    public PostDto getPostWithUsernameMostComment(String username) {
        try{
            Post post =
                    postRepository.getPostwithUsernameMostComment(username);

            return postDtoConverter.convert(post);
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new PostNotFoundException(ex.getMessage());
        }
    }
}
