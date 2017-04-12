package com.jandar.service;

import java.util.List;

import com.jandar.pojo.Navigation1;

public interface Navigation1Service {
	public List<Navigation1> query(Navigation1 navigation1);
    public int querycount(Navigation1 navigation1);
    public void insert(Navigation1 navigation1);
    public void delete(Navigation1 navigation1);
}
