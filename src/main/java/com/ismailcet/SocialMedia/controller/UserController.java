package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByUsernameResponse;
import com.ismailcet.SocialMedia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody CreateUserRequest createUserRequest){
        try{
            return userService.signup(createUserRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable("id") Integer id, @RequestBody UpdateUserRequest updateUserRequest){
        try{
            return userService.updateUserById(id,updateUserRequest);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id){
        try{
            return userService.deleteUserById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping()
    public ResponseEntity<List<GetAllUsersResponse>> getAllUsers(){
        try{
            return userService.getAllUser();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable("id") Integer id){
        try{
            return userService.getUserById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getUsername/{username}")
    public ResponseEntity<GetUserByUsernameResponse> getUserByUsername(@PathVariable("username") String username){
        try{
            return userService.getUserByUsername(username);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
