package com.jandar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.pojo.lei;

public interface leiService {
    public List<lei> query(lei lei);
    public List<lei> query1(lei lei);
    public int querycount(lei lei);

    public void insert(lei lei);
}
