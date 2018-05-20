package com.joy.fzulive.bean;

public class ReturnTiezi {
    private long id;
    private long user_id;
    private String userTX;
    private String userName;
    private String name;
    private String tel;
    private String qq;
    private String type;
    private String position;
    private String eventTime;
    private String textMes;
    private String[] imgMes;
    private int viewNum;
    private int goodNum;
    private String displayTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTX() {
        return userTX;
    }

    public void setUserTX(String userTX) {
        this.userTX = userTX;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImgMes() {
        return imgMes;
    }

    public void setImgMes(String[] imgMes) {
        this.imgMes = imgMes;
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
}
