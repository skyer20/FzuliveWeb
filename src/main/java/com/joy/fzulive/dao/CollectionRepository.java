package com.joy.fzulive.dao;

import com.joy.fzulive.bean.Collection;
import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection,Integer> {
    public boolean existsByUserAndTiezi(User user, Tiezi tiezi);
    public Collection findByUserAndTiezi(User user,Tiezi tiezi);
    public List<Collection> findByUser(User user);
}
