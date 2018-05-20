package com.joy.fzulive.controller;

import com.joy.fzulive.bean.Comment;
import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;
import com.joy.fzulive.dao.CommentRepository;
import com.joy.fzulive.dao.TieziRepository;
import com.joy.fzulive.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TieziRepository tieziRepository;

    @PostMapping(value = "/AddCommnet")
    public Comment addCommnet(@RequestBody Map<String,Object> reqMap){
        String userId = reqMap.get("userId").toString();
        String tieziId = reqMap.get("teiziId").toString();
        String content = reqMap.get("content").toString();
        User user = userRepository.findById(Long.parseLong(userId));
        Tiezi tiezi = tieziRepository.findById(Long.parseLong(tieziId));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTiezi(tiezi);
        comment.setContent(content);
        //获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        comment.setTime(dateFormat.format(System.currentTimeMillis()));

        return commentRepository.save(comment);
    }

    @GetMapping(value = "/GetCommentByTieziId")
    public List<Comment> getCommentByTieziId(@RequestParam(value = "tieziId") String tieziId){
        Tiezi tiezi = tieziRepository.findById(Long.parseLong(tieziId));
        List<Comment> comments = commentRepository.findByTiezi(tiezi);
        return comments;
    }
}
