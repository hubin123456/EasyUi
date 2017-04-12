package com.jandar.service;

import java.util.List;

import javax.management.relation.Role;

import com.jandar.pojo.role;

public interface roleService {
    public List<role>  select(role role);
    public int querycount(role role);
    public void insert(role role);
    public List<role>  select1(role role);
}
