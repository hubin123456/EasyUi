package com.jandar.serviceImpl;
import java.util.List;

import javax.management.relation.Role;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.roleDao;
import com.jandar.pojo.role;
import com.jandar.service.roleService;

@Transactional
@Service
public class roleServiceImpl implements roleService{
     @Autowired
     private roleDao roleDao;
    @Override
    public List<role> select(role role) {
        // TODO Auto-generated method stub
        return roleDao.query(role);
    }

    @Override
    public int querycount(role role) {
        // TODO Auto-generated method stub
        return roleDao.querycount(role);
    }

    @Override
    public void insert(role role) {
        // TODO Auto-generated method stub
        roleDao.insert(role);
    }

    @Override
    public List<role> select1(role role) {
        // TODO Auto-generated method stub
        return roleDao.query1(role);
    }
   
}
