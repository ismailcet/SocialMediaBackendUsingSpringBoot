package com.ismailcet.SocialMedia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "share")
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="url")
    @NotNull(message = "Share may not be null")
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
    @NotNull(message = "Post Id may not be null")
    private Post post;

    //NoArgsConstructor

    public Share() {
    }

    //AllArgsConstructor

    public Share(String url, Post post) {
        this.url = url;
        this.post = post;
    }

    //Builder Pattern
    public Share(ShareBuilder shareBuilder){
        this.url = shareBuilder.url;
        this.post = shareBuilder.post;
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

    public static class ShareBuilder{
        private String url;
        private Post post;

        public ShareBuilder url(String url){
            this.url = url;
            return this;
        }
        public ShareBuilder post(Post post){
            this.post = post;
            return this;
        }

        public Share build(){
            return new Share(this);
        }
    }
}
