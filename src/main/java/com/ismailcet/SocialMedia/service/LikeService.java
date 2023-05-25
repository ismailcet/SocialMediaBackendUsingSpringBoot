package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.LikeDto;
import com.ismailcet.SocialMedia.exception.LikeNotFoundException;
import com.ismailcet.SocialMedia.util.converter.LikeDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateLikeRequest;
import com.ismailcet.SocialMedia.entity.Like;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.LikeRepository;
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
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeDtoConverter likeDtoConverter;

    Logger logger = LoggerFactory.getLogger(LikeService.class);
    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository, LikeDtoConverter likeDtoConverter) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeDtoConverter = likeDtoConverter;
    }

    public LikeDto createLike(CreateLikeRequest createLikeRequest) {
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
                return likeDtoConverter.convert(like);
            }
            throw new LikeNotFoundException("Post Id or User Id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new LikeNotFoundException(ex.getMessage());
        }
    }

    public void deleteLikeByLikeId(Integer id) {
        try{
            Optional<Like> like =
                    likeRepository.findById(id);

            if(like.isPresent()){
                likeRepository.deleteById(id);
            }else{
                throw new LikeNotFoundException("Like Id does not exists ! ");
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new LikeNotFoundException(ex.getMessage());
        }
    }

    public List<LikeDto> getLikesByPostId(Integer postId) {
        try{
            Optional<Post> post =
                    postRepository.findById(postId);
            if(post.isPresent()){
                List<LikeDto> likes = likeRepository
                        .findByPostId(postId)
                        .stream()
                        .map(like -> likeDtoConverter.convert(like))
                        .collect(Collectors.toList());

                return likes;
            }
            throw new LikeNotFoundException("Post Id does not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new LikeNotFoundException(ex.getMessage());
        }
    }
}
