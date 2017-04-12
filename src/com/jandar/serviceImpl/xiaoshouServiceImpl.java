package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.xiaoshouDao;
import com.jandar.pojo.xiaoshou;
import com.jandar.service.xiaoshouService;
@Transactional
@Service
public class xiaoshouServiceImpl implements xiaoshouService{
    @Autowired
    private xiaoshouDao xiaoshouDao;
    @Override
    public List<xiaoshou> query(xiaoshou xiaoshou) {
        // TODO Auto-generated method stub
        return xiaoshouDao.query(xiaoshou);
    }

    @Override
    public List<xiaoshou> queryBytime(xiaoshou xiaoshou) {
        // TODO Auto-generated method stub
        return xiaoshouDao.queryBytime(xiaoshou);
    }

    @Override
    public int querycount(xiaoshou xiaoshou) {
        // TODO Auto-generated method stub
        return xiaoshouDao.querycount(xiaoshou);
    }

    @Override
    public void insert(xiaoshou xiaoshou) {
        // TODO Auto-generated method stub
        xiaoshouDao.insert(xiaoshou);
    }

    @Override
    public void update(xiaoshou xiaoshou) {
        // TODO Auto-generated method stub
        xiaoshouDao.update(xiaoshou);
    }

}
