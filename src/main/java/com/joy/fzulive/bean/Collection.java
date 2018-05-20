package com.joy.fzulive.bean;

import javax.persistence.*;

@Entity
@Table(name = "collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "collection_user_fk")
    private User user;

    @OneToOne
    @JoinColumn(name = "collection_tiezi_fk")
    private Tiezi tiezi;

    private String collectTime;

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

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }
}
