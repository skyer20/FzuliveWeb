package com.joy.fzulive.controller;

import com.joy.fzulive.bean.User;
import com.joy.fzulive.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.awt.*;
import java.util.List;
import java.util.Map;

@RestController
public class UserController  {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/GetUserInfo")
    public List<User> userList(){
        return userRepository.findAll();
    }


    /**
     * 增加一个用户
     */
    @PostMapping(value = "/Register")
    public User saveOneuser(@RequestBody Map<String,Object> reqMap){
        String account = reqMap.get("account").toString();
        String password = reqMap.get("password").toString();
        String tel = reqMap.get("tel").toString();
        String qq = reqMap.get("qq").toString();
        String wxname = reqMap.get("wxname").toString();
        String wxtxaddress = reqMap.get("wxtxaddress").toString();
        //System.out.println(reqMap);
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setTel(tel);
        user.setQq(qq);
        user.setWxname(wxname);
        user.setWxtxaddress(wxtxaddress);
        //System.out.println(user);
        return userRepository.save(user);
    }


    /**
     * 通过微信名查询用户信息
     */
    @GetMapping(value = "/GetUserInfo/WXName/{name}")
    public List<User> listUserByName(@PathVariable("name") String name){
        return userRepository.findUserByWxname(name);
    }

    /**
     * 通过账号密码来登录
     */
    @GetMapping(value = "/Load/{account}&{password}")
    public User load(@PathVariable("account") String account,
                     @PathVariable("password") String password){
        return userRepository.findUserByAccountAndPassword(account,password);
    }
}
