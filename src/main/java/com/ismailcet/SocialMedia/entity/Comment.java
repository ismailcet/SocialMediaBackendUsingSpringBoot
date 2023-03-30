package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="commentforpostsbyuser")
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="comment")
    private String comment;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name="byuser_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name="post_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    //NoAllArgsConstructor
    public Comment(){

    }

    //AllArgsConstructor
    public Comment(String comment, LocalDateTime createdDate, User user, Post post) {
        this.comment = comment;
        this.createdDate = createdDate;
        this.user = user;
        this.post = post;
    }

    //Getter

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    //Setter


    public void setId(Integer id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
