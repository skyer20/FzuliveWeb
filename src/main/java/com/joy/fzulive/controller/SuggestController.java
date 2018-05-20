package com.joy.fzulive.controller;

import com.joy.fzulive.bean.Suggest;
import com.joy.fzulive.bean.User;
import com.joy.fzulive.dao.SuggestRepository;
import com.joy.fzulive.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
public class SuggestController {
    @Autowired
    SuggestRepository suggestRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/AddSuggest")
    public Suggest addSuggest(@RequestBody Map<String,Object> reqMap){
        String userId = reqMap.get("userId").toString();
        String content = reqMap.get("content").toString();
        User user = userRepository.findById(Long.parseLong(userId));

        Suggest suggest = new Suggest();
        suggest.setUser(user);
        suggest.setContent(content);

        //获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        suggest.setTime(dateFormat.format(System.currentTimeMillis()));

        return suggestRepository.save(suggest);
    }
}
