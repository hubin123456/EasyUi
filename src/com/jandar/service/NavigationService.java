package com.jandar.service;

import java.util.List;

import com.jandar.pojo.Navigation;


public interface NavigationService {
	public List<Navigation> query(Navigation navigation);
    public int querycount(Navigation navigation);
}
