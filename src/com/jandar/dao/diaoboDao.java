package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.diaobo;
import com.jandar.pojo.kucun;

@Repository
public interface diaoboDao {
    public List<diaobo> query(diaobo diaobo);
    public List<diaobo> query1(diaobo diaobo);
    public int querycount(diaobo diaobo);

    public void insert(diaobo diaobo);
}
