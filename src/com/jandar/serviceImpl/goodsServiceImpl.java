package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.goodsDao;
import com.jandar.pojo.goods;
import com.jandar.pojo.kucun;
import com.jandar.service.goodsService;
@Transactional
@Service
public class goodsServiceImpl implements goodsService{
	@Autowired
	 private goodsDao goodsDao;
	

    @Override
    public List<goods> query(goods goods) {
        // TODO Auto-generated method stub
        return goodsDao.query(goods);
    }

    @Override
    public List<goods> query1(goods goods) {
        // TODO Auto-generated method stub
        return goodsDao.query1(goods);
    }

    @Override
    public int querycount(goods goods) {
        // TODO Auto-generated method stub
        return goodsDao.querycount(goods);
    }

    @Override
    public void insert(goods goods) {
        // TODO Auto-generated method stub
        goodsDao.insert(goods);
    }

    @Override
    public void update(goods goods) {
        // TODO Auto-generated method stub
        goodsDao.update(goods);
    }

 

}
