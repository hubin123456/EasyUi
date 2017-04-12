package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.Navigation;



@Repository
public interface NavigationDao {
     public List<Navigation> query(Navigation navigation);
     public int querycount(Navigation navigation);
}
