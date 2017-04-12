package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.dao.kehuDao;
import com.jandar.pojo.gongyingshang;
import com.jandar.pojo.kehu;
import com.jandar.service.kehuService;
@Transactional
@Service
public class kehuServiceImpl implements kehuService{
    @Autowired
    private kehuDao kehuDao;

    @Override
    public List<kehu> query(kehu kehu) {
        // TODO Auto-generated method stub
        return kehuDao.query(kehu);
    }

    @Override
    public int querycount(kehu kehu) {
        // TODO Auto-generated method stub
        return kehuDao.querycount(kehu);
    }

    @Override
    public void insert(kehu kehu) {
        // TODO Auto-generated method stub
        kehuDao.insert(kehu);
    }

    @Override
    public int batchImport(String name, MultipartFile file) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void update(kehu kehu) {
        // TODO Auto-generated method stub
        kehuDao.update(kehu);
    }
   
}
