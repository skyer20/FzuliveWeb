package com.joy.fzulive.dao;

import com.joy.fzulive.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * 通过微信名来查找用户信息
     */
    public List<User> findUserByWxname(String wxname);
    public User findByWxname(String wxname);
    public User findUserByAccountAndPassword(String account,String password);
    public User findById(long id);
}
