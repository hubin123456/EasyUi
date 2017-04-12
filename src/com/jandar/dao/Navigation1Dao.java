package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.Navigation1;


	@Repository
	public interface Navigation1Dao {
	     public List<Navigation1> query(Navigation1 navigation);
	     public int querycount(Navigation1 navigation);
	     public void insert(Navigation1 navigation1);
	     public void delete(Navigation1 navigation1);
	}

