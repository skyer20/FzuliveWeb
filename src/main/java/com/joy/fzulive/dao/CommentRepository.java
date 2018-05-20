package com.joy.fzulive.dao;

import com.joy.fzulive.bean.Comment;
import com.joy.fzulive.bean.Tiezi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    public List<Comment> findByTiezi(Tiezi tiezi);
}
