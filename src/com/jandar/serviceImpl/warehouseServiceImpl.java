package com.jandar.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.warehouseDao;
import com.jandar.pojo.warehouse;
import com.jandar.service.warehouseService;
@Transactional
@Service
public class warehouseServiceImpl implements warehouseService{
    @Autowired
    private warehouseDao warehouseDao;
	@Override
	public List<warehouse> query(warehouse warehouse) {
		// TODO Auto-generated method stub
		System.out.println(warehouse.getCurrentnum()+"----------"+warehouse.getRows() );
		return warehouseDao.query(warehouse);
	}

	@Override
	public int querycount(warehouse warehouse) {
		// TODO Auto-generated method stub
		return warehouseDao.querycount(warehouse);
	}

	@Override
	public void insert(warehouse warehouse) {
		// TODO Auto-generated method stub
		warehouseDao.insert(warehouse);
	}

	@Override
	public void update(warehouse warehouse) {
		// TODO Auto-generated method stub
		warehouseDao.update(warehouse);
	}

}
