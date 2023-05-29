package com.ismailcet.SocialMedia.dto.response;

import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class GetAllUsersResponse {
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
    public GetAllUsersResponse(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.age = user.getAge();
    }

    public GetAllUsersResponse() {
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

    @Override
    public String toString() {
        return "GetAllUsersResponse{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetAllUsersResponse)) return false;
        GetAllUsersResponse that = (GetAllUsersResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getAge(), that.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getEmail(), getFirstName(), getLastName(), getAge());
    }
}
