package com.joy.fzulive.bean;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "comment_user_fk")
    private User user;

    @OneToOne
    @JoinColumn(name = "comment_tiezi_fk")
    private Tiezi tiezi;

    private String time;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tiezi getTiezi() {
        return tiezi;
    }

    public void setTiezi(Tiezi tiezi) {
        this.tiezi = tiezi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
