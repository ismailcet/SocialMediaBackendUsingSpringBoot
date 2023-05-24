package com.ismailcet.SocialMedia.service;

import com.ismailcet.SocialMedia.dto.UserDto;
import com.ismailcet.SocialMedia.exception.GeneralExceptionAdvisor;
import com.ismailcet.SocialMedia.exception.UserNotFoundException;
import com.ismailcet.SocialMedia.util.converter.UserDtoConverter;
import com.ismailcet.SocialMedia.dto.request.CreateUserRequest;
import com.ismailcet.SocialMedia.dto.request.UpdateUserRequest;
import com.ismailcet.SocialMedia.dto.response.GetUserByUsernameResponse;
import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.repository.UserRepository;
import com.ismailcet.SocialMedia.dto.response.GetAllUsersResponse;
import com.ismailcet.SocialMedia.dto.response.GetUserByIdResponse;
import com.ismailcet.SocialMedia.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, UserDtoConverter userDtoConverter) {
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public UserDto signup(CreateUserRequest createUserRequest){
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
                return userDtoConverter.convert(newUser);
            }else{
                throw new UserNotFoundException("Username already is taken ! ");
            }
        }catch(Exception ex){
            logger.info(ex.getMessage());
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    public UserDto updateUserById(Integer id, UpdateUserRequest updateUserRequest) {
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                user.get().setUserName(updateUserRequest.getUsername());
                user.get().setPassword(PasswordUtil.hashPassword(updateUserRequest.getPassword()));
                user.get().setFirstName(updateUserRequest.getName());
                user.get().setLastName(updateUserRequest.getSurname());
                user.get().setAge(updateUserRequest.getAge());

                userRepository.save(user.get());
                return userDtoConverter.convert(user.get());
            }else{
                throw new UserNotFoundException("User Id is not valid ! ");
            }
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new UserNotFoundException(ex.getMessage());
        }
    }


    public Void deleteUserById(Integer id) {
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                userRepository.deleteById(user.get().getId());
            }
            throw new UserNotFoundException("User id does not exist");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    public List<GetAllUsersResponse> getAllUser() {
        try{
            List<GetAllUsersResponse> users =
                    userRepository.findAll().stream()
                            .map(p-> new GetAllUsersResponse(p))
                            .collect(Collectors.toList());

            return users;
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    public GetUserByIdResponse getUserById(Integer id) {
        try{
            Optional<User> user =
                    userRepository.findById(id);
            if(user.isPresent()){
                return new GetUserByIdResponse(user.get());
            }
            throw new UserNotFoundException("User Id is not exists ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    public GetUserByUsernameResponse getUserByUsername(String username) {
        try{
            User user =
                    userRepository.findByUserName(username);
            if(!Objects.isNull(user)){
                return new GetUserByUsernameResponse(user);
            }
            throw new UserNotFoundException("Username is not exist ! ");
        }catch (Exception ex){
            logger.info(ex.getMessage());
            throw new UserNotFoundException(ex.getMessage());
        }
    }
}
