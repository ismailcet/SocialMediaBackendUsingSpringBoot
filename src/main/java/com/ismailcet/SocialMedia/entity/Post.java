package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "id")
    private List<Comment> comments;

    @OneToMany(mappedBy = "id")
    private List<Like> likes;
    //NoArgsConstructor
    public Post(){

    }
    //AllArgsConstructor

    public Post(LocalDateTime createdDate, String content, User user) {
        this.createdDate = createdDate;
        this.content = content;
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

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Like> getLikes() {
        return likes;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    //ToString

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", content='" + content + '\'' +
                ", user=" + user +
                '}';
    }
}
