package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.UserDao;
import com.jandar.pojo.User;
import com.jandar.service.UserService;
@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    public UserDao userDao;
	@Override
	public List<User> query(User user) {
		// TODO Auto-generated method stub
		return userDao.query(user);
	}
	@Override
	public int querycount(User user) {
		// TODO Auto-generated method stub
		return userDao.querycount(user);
	}
	@Override
	public void init(User user) {
		// TODO Auto-generated method stub
		userDao.init(user);
	}
	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		userDao.create(user);
	}
    @Override
    public List<User> query1(User user) {
        // TODO Auto-generated method stub
        return userDao.query1(user);
    }

}
