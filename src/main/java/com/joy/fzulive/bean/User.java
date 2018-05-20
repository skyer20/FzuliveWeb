package com.joy.fzulive.bean;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String account;
    private String password;
    private String wxname;
    private String wxtxaddress;
    private String tel;
    private String qq;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private Set<Tiezi> tieziSet;

    public User(){

    }

    public Set<Tiezi> getTieziSet() {
        return tieziSet;
    }

    public void setTieziSet(Set<Tiezi> tieziSet) {
        this.tieziSet = tieziSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWxname() {
        return wxname;
    }

    public void setWxname(String wxname) {
        this.wxname = wxname;
    }

    public String getWxtxaddress() {
        return wxtxaddress;
    }

    public void setWxtxaddress(String wxtxaddress) {
        this.wxtxaddress = wxtxaddress;
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

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", account='" + account + '\'' +
//                ", password='" + password + '\'' +
//                ", wxname='" + wxname + '\'' +
//                ", wxtxaddress='" + wxtxaddress + '\'' +
//                ", tel='" + tel + '\'' +
//                ", qq='" + qq + '\'' +
//                ", tieziSet=" + tieziSet +
//                '}';
//    }
}
