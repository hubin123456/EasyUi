package com.jandar.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jandar.pojo.caozuo;
import com.jandar.pojo.gongyingshang;

public interface gongyingshangService {
	 public List<gongyingshang> query(gongyingshang gongyingshang);
	 public int querycount(gongyingshang gongyingshang);
	 public void insert(gongyingshang gongyingshang);
	 public void update(gongyingshang gongyingshang);
	 public int batchImport(String name,MultipartFile file) throws Exception;
}
