package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="likesforpostsbyuser")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "byuser_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="post_id",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    //NoArgsConstructor
    public Like(){

    }

    //AllArgsConstructor
    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
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

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
