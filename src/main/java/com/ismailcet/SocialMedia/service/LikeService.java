package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.util.converter.LikeDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateLikeRequest;
import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.LikeRepository;
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
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeDtoConverter likeDtoConverter;
    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository, LikeDtoConverter likeDtoConverter) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeDtoConverter = likeDtoConverter;
    }

    public ResponseEntity<LikeDto> createLike(CreateLikeRequest createLikeRequest) {
        try{
            Optional<Post> post = postRepository.findById(createLikeRequest.getPost_id());
            Optional<User> user = userRepository.findById(createLikeRequest.getUser_id());

            if(post.isPresent() && user.isPresent()){
                LocalDateTime now = LocalDateTime.now();
                Like like = new Like.LikeBuilder()
                        .createDate(now)
                        .user(user.get())
                        .post(post.get())
                        .build();
                likeRepository.save(like);
                return new ResponseEntity<>(likeDtoConverter.convert(like), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteLikeByLikeId(Integer id) {
        try{
            Optional<Like> like =
                    likeRepository.findById(id);

            if(like.isPresent()){
                likeRepository.deleteById(id);
                return new ResponseEntity<>("Like Successfully Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Like Id does not exits", HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<LikeDto>> getLikesByPostId(Integer postId) {
        try{
            Optional<Post> post =
                    postRepository.findById(postId);
            if(post.isPresent()){
                List<LikeDto> likes = likeRepository
                        .findByPostId(postId)
                        .stream()
                        .map(like -> likeDtoConverter.convert(like))
                        .collect(Collectors.toList());

                return new ResponseEntity<>(likes, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
