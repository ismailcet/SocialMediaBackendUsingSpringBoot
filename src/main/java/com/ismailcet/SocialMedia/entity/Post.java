package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name="created_Date")
    private LocalDateTime createdDate;

    @Column(name="content")
    private String content;

    @Column(name="url")
    private String url;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    //NoArgsConstructor
    public Post(){

    }
    //AllArgsConstructor

    public Post(LocalDateTime createdDate, String content, String url, User user) {
        this.createdDate = createdDate;
        this.content = content;
        this.url = url;
        this.user = user;
    }

    //Getter

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public User getUser() {
        return user;
    }

    //Setter

    public void setId(Integer id) {
        this.id = id;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
