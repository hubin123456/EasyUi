package com.jandar.service;

import java.util.List;

import com.jandar.pojo.warehouse;

public interface warehouseService {
	public List<warehouse> query(warehouse warehouse);
	  public int querycount(warehouse warehouse);
	  public void insert(warehouse warehouse);
	  public void update(warehouse warehouse);
}
