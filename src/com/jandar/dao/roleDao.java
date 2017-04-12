package com.jandar.dao;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.role;

@Repository
public interface roleDao {
   public List<role>  query(role role);
   public List<role>  query1(role role);
   public int querycount(role role);
   public void insert(role role);
}
