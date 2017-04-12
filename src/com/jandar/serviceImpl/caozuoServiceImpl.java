package com.jandar.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.dao.caozuoDao;
import com.jandar.pojo.ReadExcel;
import com.jandar.pojo.caozuo;
import com.jandar.service.caozuoService;
@Transactional
@Service
public class caozuoServiceImpl implements caozuoService{
     @Autowired
     private  caozuoDao caozuodao;
	@Override
	public List<caozuo> query(caozuo caozuo) {
		// TODO Auto-generated method stub
		return caozuodao.query(caozuo);
	}

	@Override
	public int querycount(caozuo caozuo) {
		// TODO Auto-generated method stub
		return caozuodao.querycount(caozuo);
	}

	@Override
	public void insert(caozuo caozuo) {
		// TODO Auto-generated method stub
		 caozuodao.insert(caozuo);
	}
	
	@Override
	public int batchImport(String name,MultipartFile file) throws Exception {

        //处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        //获得解析excel方法
        int i = 0;
        List<caozuo> caozuo=readExcel.getExcelInfo(name,file);
        if(caozuo.size()==0){System.out.println("数据为空");}
        for(caozuo caozuo1:caozuo){
            caozuodao.insert(caozuo1);
            i++;
        }
        return i;
       
    }

	@Override
	public void update(caozuo caozuo) {
		// TODO Auto-generated method stub
		caozuodao.update(caozuo);
	}

    @Override
    public List<caozuo> queryBytime(caozuo caozuo) {
        // TODO Auto-generated method stub
        return caozuodao.queryBytime(caozuo);
    }
	
}
