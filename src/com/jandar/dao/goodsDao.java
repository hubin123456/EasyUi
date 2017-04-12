package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.goods;
import com.jandar.pojo.kucun;

@Repository
public interface goodsDao {
   public List<goods> query(goods goods);
   public List<goods> query1(goods goods);
   public int querycount(goods goods);

   public void insert(goods goods);
   public void update(goods goods);
}
