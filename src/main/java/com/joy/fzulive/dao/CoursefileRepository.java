package com.joy.fzulive.dao;

import com.joy.fzulive.bean.Coursefile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursefileRepository extends JpaRepository<Coursefile,Integer> {
    //分页查询
    public Page<Coursefile> findBy(Pageable pageable);
    public int countByCollegeAndAndMajorAndAndCourseAndAndTeather(String college,String major,String course,String teather);
    public List<Coursefile> findByFlag(int flag);
    public List<Coursefile> findByPath(String path);
    public Coursefile findById(Long id);
}
