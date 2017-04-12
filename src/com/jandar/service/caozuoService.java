package com.jandar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.pojo.caozuo;

public interface caozuoService {
	 public List<caozuo> query(caozuo caozuo);
	 public List<caozuo> queryBytime(caozuo caozuo);
	 public int querycount(caozuo caozuo);
	 public void insert(caozuo caozuo);
	 public int batchImport(String name,MultipartFile file) throws Exception;
	 public void update(caozuo caozuo);
}
