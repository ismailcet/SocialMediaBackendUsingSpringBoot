package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void createUser(){
        User user = new User(
                "ismailcet","denemePass","İsmail","Çetin","ismailcet3@gmail.com",24
        );
        userRepository.save(user);
    }

    @Test
    public void getAllUsers(){
       List<User> userList =
               userRepository.findAll();
        System.out.println("Users : " + userList);
    }

    @Test
    public void deleteUserById(){
        userRepository.deleteById(120);
    }

    @Test
    public void updateUserById(){
        User user =
                userRepository.findById(120).orElse(null);

        User newUser =
                new User();
        if(user !=null){
            newUser.setId(user.getId());
            newUser.setUserName("İsmailUpd");
            newUser.setPassword(user.getPassword());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setAge(user.getAge());
            userRepository.save(newUser);
        }
        System.out.println("New User : " + newUser);
    }

    @Test
    public void getUserById(){
        User user =
                userRepository.findById(120).orElse(null);

        System.out.println("user : " + user.getUserName());
    }

    @Test
    public void getUserByUserName(){
        User user =
                userRepository.findByUserName("ismailcet");

        System.out.println("User : " + user.getFirstName() + " " + user.getLastName());
    }


}