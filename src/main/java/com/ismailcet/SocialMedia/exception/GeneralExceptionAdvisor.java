package com.ismailcet.SocialMedia.exception;

import com.ismailcet.SocialMedia.entity.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handlerUserNotFoundException(UserNotFoundException ex){

        List<String> details = new ArrayList<String>();

        details.add(ex.getMessage());

        ExceptionResponse err = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                details);
        return new ResponseEntity<>(err,err.getStatus());

    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlerPostNotFoundException(PostNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse err = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                details);

        return new ResponseEntity<>(err, err.getStatus());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handlerPostNotFoundException(CommentNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse err = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                details);

        return new ResponseEntity<>(err, err.getStatus());
    }
    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<Object> handlerPostNotFoundException(LikeNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse err = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                details);

        return new ResponseEntity<>(err, err.getStatus());
    }
    @ExceptionHandler(FollowNotFoundException.class)
    public ResponseEntity<Object> handlerPostNotFoundException(FollowNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse err = new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                details);

        return new ResponseEntity<>(err, err.getStatus());
    }


}
