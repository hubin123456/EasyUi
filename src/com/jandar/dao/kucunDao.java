package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.kucun;

@Repository
public interface kucunDao {
   public List<kucun> query(kucun goods);
   public List<kucun> query1(kucun goods);
   public int querycount(kucun goods);

   public void insert(kucun goods);
   public void update(kucun goods);
}
