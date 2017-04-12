package com.jandar.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jandar.dao.Navigation1Dao;
import com.jandar.pojo.Navigation1;
import com.jandar.service.Navigation1Service;


	@Transactional
	@Service
	public class Navigation1ServiceImpl implements Navigation1Service{
	      @Autowired
	      private Navigation1Dao navigation1Dao;

		@Override
		public List<Navigation1> query(Navigation1 navigation1) {
			// TODO Auto-generated method stub
			return navigation1Dao.query(navigation1);
		}

		@Override
		public int querycount(Navigation1 navigation1) {
			// TODO Auto-generated method stub
			return navigation1Dao.querycount(navigation1);
		}

        @Override
        public void insert(Navigation1 navigation1) {
            // TODO Auto-generated method stub
             navigation1Dao.insert(navigation1);
             
        }

        @Override
        public void delete(Navigation1 navigation1) {
            // TODO Auto-generated method stub
            navigation1Dao.delete(navigation1);
        }
	      
	      
	}


