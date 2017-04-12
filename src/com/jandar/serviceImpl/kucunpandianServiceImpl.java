package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.kucunpandianDao;
import com.jandar.pojo.kucun;
import com.jandar.pojo.kucunpandian;
import com.jandar.service.kucunpandianService;

@Transactional
@Service
public class kucunpandianServiceImpl implements kucunpandianService{
    @Autowired
    private kucunpandianDao kucunpandianDao;
    @Override
    public List<kucunpandian> query(kucunpandian goods) {
        // TODO Auto-generated method stub
        return kucunpandianDao.query(goods);
    }

    @Override
    public List<kucunpandian> query1(kucunpandian goods) {
        // TODO Auto-generated method stub
        return kucunpandianDao.query1(goods);
    }

    @Override
    public int querycount(kucunpandian goods) {
        // TODO Auto-generated method stub
        return kucunpandianDao.querycount(goods);
    }

    @Override
    public void insert(kucunpandian goods) {
        // TODO Auto-generated method stub
        kucunpandianDao.insert(goods);
    }

    @Override
    public void delete(kucunpandian kucunpandian) {
        // TODO Auto-generated method stub
        kucunpandianDao.delete(kucunpandian);
    }

}
