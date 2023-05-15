package com.ismailcet.SocialMedia.controller;

import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<String> signup(@RequestBody Map<String, String> requestMap){
        try{
            return userService.signup(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something Went Wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable("id") Integer id, @RequestBody Map<String, String> requestMap){
        try{
            return userService.updateUserById(id,requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something Went Wrong ! " , HttpStatus.INTERNAL_SERVER_ERROR);
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

}
