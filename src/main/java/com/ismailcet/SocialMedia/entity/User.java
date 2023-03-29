package com.ismailcet.SocialMedia.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NotNull(message = "Username may not be null")
    @Column(name="username")
    private String userName;

    @NotNull(message = "Password may not be null")
    @Column(name="password")
    private String password;
    @NotNull(message = "Name may not be null")
    @Column(name = "name")
    private String firstName;
    @NotNull(message = "Surname may not be null")
    @Column(name="surname")
    private String lastName;
    @NotNull(message = "Email may not be null")
    @Column(name = "email")
    private String email;
    @Column(name="age")
    private Integer age;

    //NoArgsConstructor
    public User(){

    }
    //AllArgsConstructor
    public User(String userName, String password, String firstName, String lastName, String email, Integer age) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    //Getter
    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    //Setter
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}