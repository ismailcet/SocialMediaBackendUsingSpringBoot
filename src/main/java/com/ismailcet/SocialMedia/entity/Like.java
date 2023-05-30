package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="likesforpostsbyuser")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "byuser_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "User Id may not be null")
    private User user;


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name="post_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Post Id may not be null")
    private Post post;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    //NoArgsConstructor
    public Like(){

    }

    //AllArgsConstructor

    public Like(User user, Post post, LocalDateTime createdDate) {
        this.user = user;
        this.post = post;
        this.createdDate = createdDate;
    }

    //Builder Pattern
    public Like(LikeBuilder likeBuilder){
        this.user = likeBuilder.user;
        this.post = likeBuilder.post;
        this.createdDate = likeBuilder.createdDate;
    }

    //Getter

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    //Setter


    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public static class LikeBuilder{
        private User user;
        private Post post;
        private LocalDateTime createdDate;

        public LikeBuilder user(User user){
            this.user = user;
            return this;
        }
        public LikeBuilder post(Post post){
            this.post = post;
            return this;
        }

        public LikeBuilder createDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }
        public Like build(){
            return new Like(this);
        }
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Like)) return false;
        Like like = (Like) o;
        return Objects.equals(getId(), like.getId()) && Objects.equals(getUser(), like.getUser()) && Objects.equals(getPost(), like.getPost()) && Objects.equals(getCreatedDate(), like.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPost(), getCreatedDate());
    }
}
