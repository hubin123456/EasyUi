package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.caozuo;
import com.jandar.pojo.gongyingshang;

@Repository
public interface gongyingshangDao {
	    public List<gongyingshang> query(gongyingshang gongyingshang);
	    public int querycount(gongyingshang gongyingshang);
	    public void insert(gongyingshang gongyingshang);
	    public void update(gongyingshang gongyingshang);
}
