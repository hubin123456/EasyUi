package com.jandar.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jandar.pojo.gongyingshang;
import com.jandar.pojo.kehu;

public interface kehuService {
    public List<kehu> query(kehu kehu);
    public int querycount(kehu kehu);
    public void insert(kehu kehu);
    public void update(kehu kehu);
    public int batchImport(String name,MultipartFile file) throws Exception;
}
