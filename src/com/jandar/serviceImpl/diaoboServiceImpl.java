package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.diaoboDao;
import com.jandar.pojo.diaobo;
import com.jandar.service.diaoboService;
@Service
@Transactional
public class diaoboServiceImpl implements  diaoboService{
    @Autowired
    private diaoboDao diaoboDao;
    @Override
    public List<diaobo> query(diaobo diaobo) {
        // TODO Auto-generated method stub
        return diaoboDao.query(diaobo);
    }

    @Override
    public List<diaobo> query1(diaobo diaobo) {
        // TODO Auto-generated method stub
        return diaoboDao.query1(diaobo);
    }

    @Override
    public int querycount(diaobo diaobo) {
        // TODO Auto-generated method stub
        return diaoboDao.querycount(diaobo);
    }

    @Override
    public void insert(diaobo diaobo) {
        // TODO Auto-generated method stub
        diaoboDao.insert(diaobo);
    }
   
}
