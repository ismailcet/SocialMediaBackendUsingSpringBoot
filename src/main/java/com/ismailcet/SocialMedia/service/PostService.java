package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.PostDtoConverter;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreatePostRequest;
import com.ismailcet.SocialMedia.dto.request.UpdatePostRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllPostsResponse;
import com.ismailcet.SocialMedia.dto.response.GetPostByPostIdResponse;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostDtoConverter postDtoConverter;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostDtoConverter postDtoConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postDtoConverter = postDtoConverter;
    }

    public ResponseEntity<PostDto> createPost(CreatePostRequest createPostRequest) {
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

                return new ResponseEntity<>(postDtoConverter.convert(post),HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<PostDto> updatePostByPostId(Integer id, UpdatePostRequest updatePostRequest) {
        try{
            Optional<Post> post =
                    postRepository.findById(id);

            if(post.isPresent()){
                post.get().setContent(updatePostRequest.getContent());
                postRepository.save(post.get());

                return new ResponseEntity<>(postDtoConverter.convert(post.get()), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deletePostByPostId(Integer id) {
        try{
            Optional<Post> post =
                    postRepository.findById(id);
            if(post.isPresent()){
                postRepository.deleteById(id);
                return new ResponseEntity<>("Post Successfully Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Post id does not exists", HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<GetAllPostsResponse>> getAllPosts() {
        try{
            List<GetAllPostsResponse> posts =
                    postRepository.findAll().stream()
                            .map(post -> new GetAllPostsResponse(
                                    post.getId(),
                                    post.getContent(),
                                    new UserDto(post.getUser()),
                                    post.getCreatedDate()))
                            .collect(Collectors.toList());
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<GetPostByPostIdResponse> getPostByPostId(Integer id) {
        try{
            Optional<Post> postOne =
                    postRepository.findById(id);

            if(postOne.isPresent()){
                GetPostByPostIdResponse post = new GetPostByPostIdResponse(
                        postOne.get().getId(),
                        postOne.get().getContent()
                        ,new UserDto(postOne.get().getUser())
                        ,postOne.get().getCreatedDate());

                return new ResponseEntity<>(post, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<PostDto> getPostMostLikedThreeDays(Integer day) {
        try{
            Post post =
                    postRepository.getPostMostLikedThreeDays(day);

            return new ResponseEntity<>(postDtoConverter.convert(post),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<PostDto> getPostwithUsernameMostComment(String username) {
        try{
            Post post =
                    postRepository.getPostwithUsernameMostComment(username);

            return new ResponseEntity<>(postDtoConverter.convert(post), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
