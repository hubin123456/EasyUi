package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.warehouse;

@Repository
public interface warehouseDao {
  public List<warehouse> query(warehouse warehouse);
  public int querycount(warehouse warehouse);
  public void insert(warehouse warehouse);
  public void update(warehouse warehouse);
}
