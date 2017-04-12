package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.caozuo;
import com.jandar.pojo.xiaoshou;

@Repository
public interface xiaoshouDao {
    public List<xiaoshou> query(xiaoshou xiaoshou);
    public List<xiaoshou> queryBytime(xiaoshou xiaoshou);
    public int querycount(xiaoshou xiaoshou);
    public void insert(xiaoshou xiaoshou);
    public void update(xiaoshou xiaoshou);
}
