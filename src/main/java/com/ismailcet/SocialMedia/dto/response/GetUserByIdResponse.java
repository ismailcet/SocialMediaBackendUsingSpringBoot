package com.ismailcet.SocialMedia.dto.response;

import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;

public class GetUserByIdResponse {
    private Integer id;
    @NotNull(message = "Username may not be null")
    private String userName;
    @NotNull(message = "Email may not be null")
    private String email;
    @NotNull(message = "Firstname may not be null")
    private String firstName;
    @NotNull(message = "Lastname may not be null")
    private String lastName;
    @NotNull(message = "Age may not be null")
    private Integer age;

    public GetUserByIdResponse(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.age = user.getAge();
    }
    public GetUserByIdResponse(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
