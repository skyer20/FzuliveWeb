package com.joy.fzulive.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tiezi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    private User user;

    private String name;
    private String tel;
    private String qq;
    private String type;
    private String position;
    private String eventTime;
    private String textMes;
    private String imgMes;
    private int viewNum;
    private int goodNum;
    private String displayTime;

    public Tiezi() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getTextMes() {
        return textMes;
    }

    public void setTextMes(String textMes) {
        this.textMes = textMes;
    }

    public String getImgMes() {
        return imgMes;
    }

    public void setImgMes(String imgMes) {
        this.imgMes = imgMes;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonBackReference
    public User getUser() {
        return user;
    }

    @JsonBackReference
    public void setUser(User user) {
        this.user = user;
    }

//    @Override
//    public String toString() {
//        return "Tiezi{" +
//                "id=" + id +
//                ", user=" + user +
//                ", name='" + name + '\'' +
//                ", tel='" + tel + '\'' +
//                ", qq='" + qq + '\'' +
//                ", type='" + type + '\'' +
//                ", position='" + position + '\'' +
//                ", eventTime='" + eventTime + '\'' +
//                ", textMes='" + textMes + '\'' +
//                ", imgMes='" + imgMes + '\'' +
//                ", viewNum=" + viewNum +
//                ", goodNum=" + goodNum +
//                ", displayTime=" + displayTime +
//                '}';
//    }
}
