package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByUsernameResponse;
import com.ismailcet.SocialMedia.exception.GeneralExceptionAdvisor;
import com.ismailcet.SocialMedia.exception.UserNotFoundException;
import com.ismailcet.SocialMedia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody CreateUserRequest createUserRequest){
        return ResponseEntity.ok(userService.signup(createUserRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable("id") Integer id, @RequestBody UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(userService.updateUserById(id, updateUserRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<GetAllUsersResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable("id") Integer id){

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getUsername/{username}")
    public ResponseEntity<GetUserByUsernameResponse> getUserByUsername(@PathVariable("username") String username){

        return ResponseEntity.ok(userService.getUserByUsername(username));

    }

}
