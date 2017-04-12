package com.jandar.service;

import java.util.List;

import com.jandar.pojo.User;

public interface UserService {
        
	public List<User> query(User user);
	public List<User> query1(User user);
	public int querycount(User user);
	public void init(User user);
	public void create(User user);
}
