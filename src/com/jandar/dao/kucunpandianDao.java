package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.kucun;
import com.jandar.pojo.kucunpandian;

@Repository
public interface kucunpandianDao {
    public List<kucunpandian> query(kucunpandian goods);
    public List<kucunpandian> query1(kucunpandian goods);
    public int querycount(kucunpandian goods);

    public void insert(kucunpandian goods);
    public void delete(kucunpandian kucunpandian);
}
