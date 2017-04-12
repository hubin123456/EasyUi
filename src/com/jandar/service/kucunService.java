package com.jandar.service;

import java.util.List;

import com.jandar.pojo.kucun;

public interface kucunService {
    public List<kucun> query(kucun goods);
    public List<kucun> query1(kucun goods);
    public int querycount(kucun goods);
   
    public void insert(kucun goods);
    public void update(kucun goods);
}
