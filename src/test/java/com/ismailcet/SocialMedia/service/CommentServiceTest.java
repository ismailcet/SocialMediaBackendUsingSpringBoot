package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.CommentDto;
import com.ismailcet.SocialMedia.dto.PostDto;
import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateCommentRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateCommentRequest;
import com.ismailcet.SocialMedia.entity.Comment;
import com.ismailcet.SocialMedia.entity.Post;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.exception.CommentNotFoundException;
import com.ismailcet.SocialMedia.repository.CommentRepository;
import com.ismailcet.SocialMedia.repository.PostRepository;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.util.converter.CommentDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {
    private CommentService commentService;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentDtoConverter commentDtoConverter;

    @BeforeEach
    public void setUp(){
        commentRepository = mock(CommentRepository.class);
        postRepository = mock(PostRepository.class);
        userRepository = mock(UserRepository.class);
        commentDtoConverter = mock(CommentDtoConverter.class);

        commentService =
                new CommentService(commentRepository, postRepository, userRepository, commentDtoConverter);
    }
    @DisplayName("CreateComment when UserId and PostId are exist should CreateComment successfully return CommentDto")
    @Test
    public void testCreateComment_whenUserIdAndPostIdAreExist_shouldCreateCommentSuccessfullyReturnCommentDto(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer userId = 1;
        Integer postId = 1;

        User user = new User.UserBuilder()
                        .userName("test-username")
                        .firstName("test-firstname")
                        .lastName("test-lastname")
                        .email("test-email")
                        .password("test-password")
                        .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Comment comment = new Comment.CommentBuilder()
                        .comment("test-comment")
                        .user(user)
                        .post(post)
                        .createdDate(date)
                        .build();
        CreateCommentRequest createCommentRequest =
                new CreateCommentRequest("test-comment",date,userId,postId);
        CommentDto expected =
                new CommentDto(1, "test-comment",date,new UserDto(user),new PostDto(post));


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentDtoConverter.convert(any(Comment.class))).thenReturn(expected);

        CommentDto actual = commentService.createComment(createCommentRequest);

        assertEquals(expected, actual);

        verify(userRepository).findById(any(Integer.class));
        verify(postRepository).findById(any(Integer.class));
        verify(commentRepository).save(any(Comment.class));
        verify(commentDtoConverter).convert(any(Comment.class));

    }
    @DisplayName("CreateComment when UserId and PostId are not exist should return CommentNotFoundException")
    @Test
    public void testCreateComment_whenUserIdAndPostIdAreNotExist_shouldReturnCommentNotFoundException(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        Integer userId = 1;
        Integer postId = 1;

        CreateCommentRequest createCommentRequest =
                new CreateCommentRequest("test-comment",date,userId,postId);

        CommentNotFoundException expected =
                new CommentNotFoundException("User Id or Post Id does not exists ! ");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        CommentNotFoundException actual = assertThrows(CommentNotFoundException.class,
                ()->commentService.createComment(createCommentRequest));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(userRepository).findById(any(Integer.class));
        verify(postRepository).findById(any(Integer.class));
    }
    @DisplayName("UpdateCommentByCommentId when CommentId is exist should Updated successfully and return CommentDto")
    @Test
    public void testUpdateCommentByCommentId_whenCommentIdIsExist_shouldUpdatedSuccessfullyAndReturnCommentDto(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        Integer commentId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Comment comment = new Comment.CommentBuilder()
                .comment("test-comment")
                .user(user)
                .post(post)
                .createdDate(date)
                .build();

        UpdateCommentRequest updateCommentRequest =
                new UpdateCommentRequest("test-comment");

        CommentDto expected = new CommentDto.CommentDtoBuilder()
                .comment("test-comment")
                .user(new UserDto(user))
                .post(new PostDto(post))
                .createdDate(date)
                .build();
         when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
         when(commentRepository.save(any(Comment.class))).thenReturn(comment);
         when(commentDtoConverter.convert(any(Comment.class))).thenReturn(expected);

         CommentDto actual = commentService.updateCommentByCommentId(commentId, updateCommentRequest);

         assertEquals(expected, actual);

         verify(commentRepository).findById(any(Integer.class));
         verify(commentRepository).save(any(Comment.class));
         verify(commentDtoConverter).convert(any(Comment.class));
    }
    @DisplayName("UpdateCommentByCommentId when CommentId is not exist should return CommentNotFoundException")
    @Test
    public void testUpdateCommentByCommentId_whenCommentIdIsNotExist_shouldReturnCommentNotFoundException(){
        Integer commentId = 1;
        UpdateCommentRequest updateCommentRequest =
                new UpdateCommentRequest("test-comment");
        CommentNotFoundException expected =
                new CommentNotFoundException("Comment Id does not exists ! ");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        CommentNotFoundException actual = assertThrows(CommentNotFoundException.class,
                ()->commentService.updateCommentByCommentId(commentId, updateCommentRequest));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(commentRepository).findById(any(Integer.class));
    }
    @DisplayName("DeleteCommentByCommentId when CommentId is exist should deleted successfully")
    @Test
    public void testDeleteCommentByCommentId_whenCommentIdIsExist_shouldDeletedSuccessfully(){
        Integer commentId = 1;
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Comment comment = new Comment.CommentBuilder()
                .comment("test-comment")
                .user(user)
                .post(post)
                .createdDate(date)
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteCommentByCommentId(commentId);

        verify(commentRepository).findById(any(Integer.class));
        verify(commentRepository).deleteById(any(Integer.class));
    }
    @DisplayName("DeleteCommentByCommentId when CommentId is not exist should return CommentNotFoundException")
    @Test
    public void testDeleteCommentByCommentId_whenCommentIdIsNotExist_shouldReturnCommentNotFoundException(){
        Integer commentId = 1;

        CommentNotFoundException expected =
                new CommentNotFoundException("Comment Id does not exists ! ");

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        CommentNotFoundException actual = assertThrows(CommentNotFoundException.class,
                ()->commentService.deleteCommentByCommentId(commentId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(commentRepository).findById(any(Integer.class));
    }
    @DisplayName("GetCommentsByPostId when PostId is exist should Return list of CommentDtoType")
    @Test
    public void testGetCommentsByPostId_whenPostIdIsExist_shouldReturnListOfCommentDtoType(){
        LocalDateTime date = LocalDateTime.of(2023,5,30,15,28,59);
        Integer userId = 1;
        Integer postId = 1;

        User user = new User.UserBuilder()
                .userName("test-username")
                .firstName("test-firstname")
                .lastName("test-lastname")
                .email("test-email")
                .password("test-password")
                .age(16).build();
        user.setId(1);

        Post post = new Post.PostBuilder()
                .content("test-content")
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        post.setId(1);

        Comment comment1 = new Comment.CommentBuilder()
                .comment("test-comment")
                .user(user)
                .post(post)
                .createdDate(date)
                .build();
        comment1.setId(1);

        Comment comment2 = new Comment.CommentBuilder()
                .comment("test-comment")
                .user(user)
                .post(post)
                .createdDate(date)
                .build();
        comment2.setId(2);

        CommentDto commentDto1 = new CommentDto.CommentDtoBuilder()
                .comment("test-comment")
                .post(new PostDto(post))
                .user(new UserDto(user))
                .createdDate(date)
                .build();
        commentDto1.setId(1);

        CommentDto commentDto2 = new CommentDto.CommentDtoBuilder()
                .comment("test-comment")
                .post(new PostDto(post))
                .user(new UserDto(user))
                .createdDate(date)
                .build();
        commentDto2.setId(2);
        List<Comment> comments = Arrays.asList(comment1, comment2);
        List<CommentDto> expected = Arrays.asList(commentDto1 , commentDto2);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findByPostId(postId)).thenReturn(comments);
        when(commentDtoConverter.convert(comment1)).thenReturn(commentDto1);
        when(commentDtoConverter.convert(comment2)).thenReturn(commentDto2);

        List<CommentDto> actual = commentService.getCommentsByPostId(postId);

        assertEquals(expected, actual);

        verify(postRepository).findById(any(Integer.class));
        verify(commentRepository).findByPostId(any(Integer.class));
        verify(commentDtoConverter, new Times(2)).convert(any(Comment.class));
    }
    @Test
    public void testGetCommentsByPostId_whenPostIdIsNotExist_shouldReturnCommentNotFoundException(){
        Integer postId = 1;
        CommentNotFoundException expected =
                new CommentNotFoundException("Post Id does not exists ! ");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        CommentNotFoundException actual = assertThrows(CommentNotFoundException.class,
                ()->commentService.getCommentsByPostId(postId));

        assertEquals(expected.getMessage(), actual.getMessage());

        verify(postRepository).findById(any(Integer.class));
    }
}