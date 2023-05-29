package com.ismailcet.SocialMedia.dto.response;

import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Objects;

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
    private GetUserByIdResponse(GetUserByIdResponseBuilder getUserByIdResponseBuilder){
        this.id = getUserByIdResponseBuilder.id;
        this.userName = getUserByIdResponseBuilder.userName;
        this.firstName = getUserByIdResponseBuilder.firstName;
        this.lastName = getUserByIdResponseBuilder.lastName;
        this.email = getUserByIdResponseBuilder.email;
        this.age = getUserByIdResponseBuilder.age;
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
        return "GetUserByIdResponse{" +
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
        if (!(o instanceof GetUserByIdResponse)) return false;
        GetUserByIdResponse that = (GetUserByIdResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getAge(), that.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getEmail(), getFirstName(), getLastName(), getAge());
    }

    public static class GetUserByIdResponseBuilder{
        private Integer id;
        private String userName;
        private String firstName;
        private String lastName;
        private String email;
        private Integer age;

        public GetUserByIdResponseBuilder id(Integer id){
            this.id = id;
            return this;
        }
        public GetUserByIdResponseBuilder userName(String userName){
            this.userName = userName;
            return this;
        }
        public GetUserByIdResponseBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public GetUserByIdResponseBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public GetUserByIdResponseBuilder email(String email){
            this.email = email;
            return this;
        }
        public GetUserByIdResponseBuilder age(Integer age){
            this.age = age;
            return this;
        }
        public GetUserByIdResponse build(){
            return new GetUserByIdResponse(this);
        }
    }
}
