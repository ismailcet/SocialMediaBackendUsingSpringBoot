package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetUserByUsernameResponse;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.dto.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.util.PasswordUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UserRepository userRepository, UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public ResponseEntity<UserDto> signup(CreateUserRequest createUserRequest){
        try{
            User user =
                    userRepository.findByUserName(createUserRequest.getUsername());
            if(Objects.isNull(user)){
                User newUser = new User.UserBuilder()
                                .userName(createUserRequest.getUsername())
                                .firstName(createUserRequest.getName())
                                .lastName(createUserRequest.getSurname())
                                .email(createUserRequest.getEmail())
                                .age(createUserRequest.getAge())
                                .password(PasswordUtil.hashPassword(createUserRequest.getPassword()))
                                .build();
                userRepository.save(newUser);
                return new ResponseEntity<>(userDtoConverter.convert(newUser) , HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<UserDto> updateUserById(Integer id, UpdateUserRequest updateUserRequest) {
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                user.get().setUserName(updateUserRequest.getUsername());
                user.get().setPassword(PasswordUtil.hashPassword(updateUserRequest.getPassword()));
                user.get().setFirstName(updateUserRequest.getName());
                user.get().setLastName(updateUserRequest.getSurname());
                user.get().setAge(updateUserRequest.getAge());

                userRepository.save(user.get());
                return new ResponseEntity<>(userDtoConverter.convert(user.get()),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
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

    public ResponseEntity<GetUserByUsernameResponse> getUserByUsername(String username) {
        try{
            User user =
                    userRepository.findByUserName(username);
            if(!Objects.isNull(user)){
                return new ResponseEntity<>(new GetUserByUsernameResponse(user), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
