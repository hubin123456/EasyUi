package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.NavigationDao;
import com.jandar.pojo.Navigation;
import com.jandar.service.NavigationService;

@Transactional
@Service
public class NavigationServiceImpl implements NavigationService{
      @Autowired
      private NavigationDao navigationDao;

	@Override
	public List<Navigation> query(Navigation navigation) {
		// TODO Auto-generated method stub
		return navigationDao.query(navigation);
	}

	@Override
	public int querycount(Navigation navigation) {
		// TODO Auto-generated method stub
		return navigationDao.querycount(navigation);
	}
      
      
}
