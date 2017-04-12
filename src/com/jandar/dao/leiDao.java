package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.goods;
import com.jandar.pojo.lei;

@Repository
public interface leiDao {
    public List<lei> query(lei lei);
    public List<lei> query1(lei lei);
    public int querycount(lei lei);

    public void insert(lei lei);
}
