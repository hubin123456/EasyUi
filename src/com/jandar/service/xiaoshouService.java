package com.jandar.service;

import java.util.List;

import com.jandar.pojo.xiaoshou;

public interface xiaoshouService {
    public List<xiaoshou> query(xiaoshou xiaoshou);
    public List<xiaoshou> queryBytime(xiaoshou xiaoshou);
    public int querycount(xiaoshou xiaoshou);
    public void insert(xiaoshou xiaoshou);
    public void update(xiaoshou xiaoshou);
}
