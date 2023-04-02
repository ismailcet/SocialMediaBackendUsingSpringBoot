package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "share")
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="url")
    private String url;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    //NoArgsConstructor

    public Share() {
    }

    //AllArgsConstructor

    public Share(String url, Post post) {
        this.url = url;
        this.post = post;
    }

    //Getter

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Post getPost() {
        return post;
    }

    //Setter

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    //ToString

    @Override
    public String toString() {
        return "Share{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", post=" + post +
                '}';
    }
}
