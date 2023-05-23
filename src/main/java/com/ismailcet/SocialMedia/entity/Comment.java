package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="commentforpostsbyuser")
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="comment")
    @NotNull(message = "Comment may not be null")
    private String comment;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="byuser_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "User Id may not be null")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="post_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Post Id may not be null")
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

    public Comment(CommentBuilder commentBuilder){
        this.comment = commentBuilder.comment;
        this.createdDate = commentBuilder.createdDate;
        this.user = commentBuilder.user;
        this.post = commentBuilder.post;
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

    public static class CommentBuilder{
        private String comment;
        private LocalDateTime createdDate;
        private User user;
        private Post post;

        public CommentBuilder comment(String comment){
            this.comment = comment;
            return this;
        }
        public CommentBuilder createdDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }
        public CommentBuilder user(User user){
            this.user = user;
            return this;
        }
        public CommentBuilder post(Post post){
            this.post = post;
            return this;
        }
        public Comment build(){
            return new Comment(this);
        }
    }
}
