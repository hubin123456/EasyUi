package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.leiDao;
import com.jandar.pojo.lei;
import com.jandar.service.leiService;

@Transactional
@Service
public class leiServiceImpl implements leiService{
    @Autowired
    private leiDao leiDao;
    @Override
    public List<lei> query(lei lei) {
        // TODO Auto-generated method stub
        return leiDao.query(lei);
    }

    @Override
    public List<lei> query1(lei lei) {
        // TODO Auto-generated method stub
        return leiDao.query1(lei);
    }

    @Override
    public int querycount(lei lei) {
        // TODO Auto-generated method stub
        return leiDao.querycount(lei);
    }

    @Override
    public void insert(lei lei) {
        // TODO Auto-generated method stub
        leiDao.insert(lei);
    }

}
