package com.joy.fzulive.dao;

import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TieziRepository extends JpaRepository<Tiezi, Integer> {

    public Tiezi findById(Long id);
    //分页
    public Page<Tiezi> findBy(Pageable pageable);
    //分类查询  按id倒序
    public List<Tiezi> findByType(String type, Sort sort);
    //按照用户查询 按id倒序
    public List<Tiezi> findByUser(User user,Sort sort);
    public List<Tiezi> findByName(String name);
    public List<Tiezi> findByQq(String qq);
    public List<Tiezi> findByTel(String tel);

    //sql语句中Tiezi要按照项目中的写法，不能按照数据库中的写法
    //?1 表示方法中的第一个参数
    @Query(value = "SELECT t FROM Tiezi t WHERE t.textMes LIKE %?1%")
    List<Tiezi> searchTieziByLike(String string);
}
