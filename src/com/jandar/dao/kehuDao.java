package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.gongyingshang;
import com.jandar.pojo.kehu;
@Repository
public interface kehuDao {
    public List<kehu> query(kehu kehu);
    public int querycount(kehu kehu);
    public void insert(kehu kehu);
    public void update(kehu kehu);
}
