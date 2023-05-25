package com.ismailcet.SocialMedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FollowNotFoundException extends RuntimeException {

    public FollowNotFoundException(String message){
        super(message);
    }

}
