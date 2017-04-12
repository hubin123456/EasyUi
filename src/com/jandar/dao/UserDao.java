package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.User;
@Repository
public interface UserDao{
    public List<User> query(User user); 
    public List<User> query1(User user); 
    public int querycount(User user);
    public void init(User user);
    public void create(User user);
}

