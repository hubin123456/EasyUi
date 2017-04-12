package com.jandar.service;

import java.util.List;

import com.jandar.pojo.goods;
import com.jandar.pojo.kucun;


public interface goodsService {
   
   public List<goods> query(goods goods);
   public List<goods> query1(goods goods);
   public int querycount(goods goods);
  
   public void insert(goods goods);
   public void update(goods goods);
}
