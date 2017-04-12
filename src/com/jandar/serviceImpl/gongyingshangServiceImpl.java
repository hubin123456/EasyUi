package com.jandar.serviceImpl;

import java.util.List;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.dao.gongyingshangDao;
import com.jandar.pojo.gongyingshang;
import com.jandar.service.gongyingshangService;
@Transactional
@Service
public class gongyingshangServiceImpl implements gongyingshangService{
        @Autowired
        private gongyingshangDao gongyingshangDao;
	@Override
	public List<gongyingshang> query(gongyingshang gongyingshang) {
		// TODO Auto-generated method stub
		return gongyingshangDao.query(gongyingshang);
	}

	@Override
	public int querycount(gongyingshang gongyingshang) {
		// TODO Auto-generated method stub
		return gongyingshangDao.querycount(gongyingshang);
	}

	@Override
	public void insert(gongyingshang gongyingshang) {
		// TODO Auto-generated method stub
		gongyingshangDao.insert(gongyingshang);
	}

	@Override
	public int batchImport(String name, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

    @Override
    public void update(gongyingshang gongyingshang) {
        // TODO Auto-generated method stub
        gongyingshangDao.update(gongyingshang);
    }

}
