package com.ismailcet.SocialMedia.dto;

import com.ismailcet.SocialMedia.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserDto {
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

    public UserDto(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.age = user.getAge();
    }

    public UserDto() {
    }

    private UserDto(UserDtoBuilder userDtoBuilder){
        this.userName = userDtoBuilder.userName;
        this.email=userDtoBuilder.email;
        this.firstName=userDtoBuilder.firstName;
        this.lastName=userDtoBuilder.lastName;
        this.age=userDtoBuilder.age;
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
        return "UserDto{" +
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
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId()) && Objects.equals(getUserName(), userDto.getUserName()) && Objects.equals(getEmail(), userDto.getEmail()) && Objects.equals(getFirstName(), userDto.getFirstName()) && Objects.equals(getLastName(), userDto.getLastName()) && Objects.equals(getAge(), userDto.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getEmail(), getFirstName(), getLastName(), getAge());
    }

    public static class UserDtoBuilder{
        private String userName;
        private String email;
        private String firstName;
        private String lastName;
        private Integer age;

        public UserDtoBuilder userName(String userName){
            this.userName = userName;
            return this;
        }
        public UserDtoBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserDtoBuilder firstName(String firstName){
            this.firstName= firstName;
            return this;
        }
        public UserDtoBuilder lastName(String lastName){
            this.lastName= lastName;
            return this;
        }
        public UserDtoBuilder age(Integer age){
            this.age = age;
            return this;
        }
        public UserDto build(){
            return new UserDto(this);
        }
    }
}
