package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="followuser")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="followed_userid",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followUser;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="following_userid",
            referencedColumnName = "id",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User followingUser;

    //NoArgsConstructor
    public Follow(){

    }

    //AllArgsConstructor

    public Follow(User followUser, User followingUser) {
        this.followUser = followUser;
        this.followingUser = followingUser;
    }

    //Getter

    public Integer getId() {
        return id;
    }

    public User getFollowUser() {
        return followUser;
    }

    public User getFollowingUser() {
        return followingUser;
    }

    //Setter

    public void setId(Integer id) {
        this.id = id;
    }
    public void setFollowUser(User followUser) {
        this.followUser = followUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", followUser=" + followUser +
                ", followingUser=" + followingUser +
                '}';
    }
}
