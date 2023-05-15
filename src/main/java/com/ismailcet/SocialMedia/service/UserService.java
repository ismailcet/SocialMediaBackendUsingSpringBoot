package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> signup(Map<String, String> requestMap){
        try{

            if(validateSignUp(requestMap)){
                User user =
                        userRepository.findByUserName(requestMap.get("username"));
                if(Objects.isNull(user)){
                    userRepository.save(setUserFromMap(requestMap));
                    return new ResponseEntity<String>("Successfully Registered" , HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("Username Already Exists",HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>("Invalid Data",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something Went Wrong ! " , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateUserById(Integer id, Map<String, String> requestMap) {
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                User findUser = user.get();
                userRepository.save(updateUserFromMap(requestMap, findUser));
                return new ResponseEntity<String>("Successfuly Updated",HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("Something Went Wrong",HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUp(Map<String, String> requestMap) {

        if(requestMap.containsKey("username")
                && requestMap.containsKey("password")
                && requestMap.containsKey("email")
                && requestMap.containsKey("name")
                && requestMap.containsKey("surname")
                && requestMap.containsKey("age")){
            return true;
        }
        return false;
    }
    private User setUserFromMap(Map<String, String> requestMap) {

        User user = new User.UserBuilder()
                .userName(requestMap.get("username"))
                .password(PasswordUtil.hashPassword(requestMap.get("password")))
                .email(requestMap.get("email"))
                .firstName(requestMap.get("name"))
                .lastName(requestMap.get("surname"))
                .age(Integer.parseInt(requestMap.get("age")))
                .build();
        System.out.println(user);
        return user;
    }

    private User updateUserFromMap(Map<String, String> requestMap, User user){
        user.setUserName(requestMap.get("username"));
        user.setFirstName(requestMap.get("name"));
        user.setAge(Integer.parseInt(requestMap.get("age")));
        user.setLastName(requestMap.get("surname"));
        user.setPassword(PasswordUtil.hashPassword(requestMap.get("password")));

        return user;
    }

    public ResponseEntity<String> deleteUserById(Integer id) {
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                userRepository.deleteById(user.get().getId());
                return new ResponseEntity<String>("Successfully Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<String>("User id doesn not exist", HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<GetAllUsersResponse>> getAllUser() {
        try{
            List<GetAllUsersResponse> users =
                    userRepository.findAll().stream()
                            .map(p-> new GetAllUsersResponse(p))
                            .collect(Collectors.toList());

            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<GetUserByIdResponse> getUserById(Integer id) {
        try{
            Optional<User> user =
                    userRepository.findById(id);
            if(user.isPresent()){
                return new ResponseEntity<>(new GetUserByIdResponse(user.get()),HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
