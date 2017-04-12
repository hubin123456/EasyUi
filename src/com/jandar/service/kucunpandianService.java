package com.jandar.service;

import java.util.List;

import com.jandar.pojo.kucun;
import com.jandar.pojo.kucunpandian;

public interface kucunpandianService {
    public List<kucunpandian> query(kucunpandian goods);
    public List<kucunpandian> query1(kucunpandian goods);
    public int querycount(kucunpandian goods);

    public void insert(kucunpandian goods);
    public void delete(kucunpandian kucunpandian);
}
