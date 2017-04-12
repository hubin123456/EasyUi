package com.jandar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jandar.pojo.caozuo;

@Repository
public interface caozuoDao {
    public List<caozuo> query(caozuo caozuo);
    public List<caozuo> queryBytime(caozuo caozuo);
    public int querycount(caozuo caozuo);
    public void insert(caozuo caozuo);
    public void update(caozuo caozuo);
}
