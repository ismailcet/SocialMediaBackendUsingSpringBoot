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
    private Long id;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "byuser_id",
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

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    //Setter

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
