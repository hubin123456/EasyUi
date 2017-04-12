package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jandar.dao.kucunDao;
import com.jandar.pojo.kucun;
import com.jandar.service.kucunService;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class kucunServiceImpl implements kucunService{
    @Autowired
    private kucunDao kucunDao;

    @Override
    public List<kucun> query(kucun goods) {
        // TODO Auto-generated method stub
        return kucunDao.query(goods);
    }

    @Override
    public List<kucun> query1(kucun goods) {
        // TODO Auto-generated method stub
        return kucunDao.query1(goods);
    }

    @Override
    public int querycount(kucun goods) {
        // TODO Auto-generated method stub
        return kucunDao.querycount(goods);
    }

    @Override
    public void insert(kucun goods) {
        // TODO Auto-generated method stub
        kucunDao.insert(goods);
    }

    @Override
    public void update(kucun goods) {
        // TODO Auto-generated method stub
        System.out.println(goods.getKucunId());
        System.out.println(goods.getKucunyujing());
        kucunDao.update(goods);
    }
    
    

}
