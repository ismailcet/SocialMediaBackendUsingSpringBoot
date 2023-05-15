package com.ismailcet.SocialMedia.repository;

import com.ismailcet.SocialMedia.entity.User;
import com.ismailcet.SocialMedia.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryShould {

    //@Test
    //returnUser_ // 1 - ne yapmaıs gerektiği
                // 2- conditions
                // 2 - ne hangi şartlar altında yapması gerektiği 5N1K
                // 3 - sonuç
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser_WhenParametersValid_Succesfully(){

        User user = new User.UserBuilder()
                .userName("deneme")
                .password("deneme")
                .email("demeöe")
                .firstName("deneme")
                .lastName("deneöme")
                .age(15)
                .build();
        userRepository.save(user);

        User createdUser =
                userRepository.findById(user.getId()).orElse(null);

        assertEquals(user.getId(),createdUser.getId());

    }

    @Test
    public void deleteUser_WhenGivenIdValid_ThenDeletedSuccesfully(){
        User user =
                new User("deleteUser","deleteUser","Delete","delete","d@gmc.om",12);

        userRepository.save(user);

        userRepository.deleteById(user.getId());

        User deletedUser =
                userRepository.findById(user.getId()).orElse(null);
        assertNull(deletedUser);
    }


    @Test
    public void updateUser_WhenGivenIdValid_ThenUpdateUserAnyFieldSuccesfully(){
        User user =
                new User("Update","Update","updateName","updateSurname","updateEmail",12);
        userRepository.save(user);

        User updatedUser =
                userRepository.findById(user.getId()).orElse(null);
        assertNotNull(updatedUser);

        User newUser =
                new User();

        newUser.setId(updatedUser.getId());
        newUser.setUserName("ChangedUserName");
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setAge(user.getAge());

        userRepository.save(newUser);

        User updateUser =
                userRepository.findById(newUser.getId()).orElse(null);

        assertNotEquals(updatedUser.getUserName(),updateUser.getUserName());
        assertEquals("ChangedUserName",updateUser.getUserName());
    }

    @Test
    public void getAllUser_WhenUsersIsNotNull_thenSuccesfully(){
        List<User> users =
                userRepository.findAll();

        assertNotNull(users);
    }

    @Test
    public void findUserById_WhenGivenIdValid_ThenGetOneUserSuccesfully(){
        User user =
                new User("GetId","GetId","GetId","GetId","GetId@gmail.com",25);

        userRepository.save(user);

        User findId =
                userRepository.findById(user.getId()).orElse(null);
        assertNotNull(findId);
        assertEquals(user.getId(),findId.getId());
    }

    @Test
    public void findUserByUsername_WhenGivenUserNameValid_ThenGetOneUserSuccesfully(){
        User user =
                new User("GetUsername","GetUsername","GetUsername","GetUsername","GetUsername@gmail.com",20);

        userRepository.save(user);

        User findUsername =
                userRepository.findByUserName(user.getUserName());
        assertNotNull(findUsername);
        assertEquals(user.getUserName(),findUsername.getUserName());

    }
}
