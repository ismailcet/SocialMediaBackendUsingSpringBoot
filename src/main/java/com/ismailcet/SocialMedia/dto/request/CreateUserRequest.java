package com.ismailcet.SocialMedia.dto.request;

import javax.validation.constraints.NotNull;

public class CreateUserRequest {
    @NotNull(message = "Username may not be null")
    private String username;
    @NotNull(message = "Password may not be null")
    private String password;
    @NotNull(message = "Name may not be null")
    private String name;
    @NotNull(message = "Surname may not be null")
    private String surname;
    @NotNull(message = "Email may not be null")
    private String email;
    @NotNull(message = "Age may not be null")
    private Integer age;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password, String name, String surname, String email, Integer age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
