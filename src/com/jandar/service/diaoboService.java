package com.jandar.service;

import java.util.List;

import com.jandar.pojo.diaobo;

public interface diaoboService {
    public List<diaobo> query(diaobo diaobo);
    public List<diaobo> query1(diaobo diaobo);
    public int querycount(diaobo diaobo);

    public void insert(diaobo diaobo);
}
