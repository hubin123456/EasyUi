package com.jandar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.gongyingshang;
import com.jandar.pojo.kehu;
import com.jandar.serviceImpl.kehuServiceImpl;
import com.jandar.util.ExportExcel;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class kehuAction {
    @Autowired
    private kehuServiceImpl kehuServiceImpl;

    //combobox 显示客户
    @RequestMapping(value = "/kehuselect")
    @ResponseBody
    public JSONArray kehushangselect(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        kehu kehu = new kehu();
        kehu.setRows(kehuServiceImpl.querycount(kehu));
        kehu.setCurrentnum(0);
        jsonArray = jsonArray.fromObject(kehuServiceImpl.query(kehu));
        System.out.println(jsonArray);
        return jsonArray;
    }

    //创建客户
    @RequestMapping(value = "/createKehu")
    @ResponseBody
    public String createKehu(ModelMap map, kehu kehu,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kehu.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
            System.out.println(kehu.getKehuAddress() + kehu.getKehuName()
                    + kehu.getKehuPhone() + kehu.getCreateTime());
            kehuServiceImpl.insert(kehu);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    //编辑客户
    @RequestMapping(value = "/editKehu")
    @ResponseBody
    public String editKehu(ModelMap map, kehu kehu, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kehu.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
            kehuServiceImpl.update(kehu);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    //datagrid显示客户
    @RequestMapping(value = "/kehu")
    @ResponseBody
    public JSONObject gongyingshang(ModelMap map, kehu kehu,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        kehu.setCount(kehuServiceImpl.querycount(new kehu()));
        kehu.setCurrentnum(
                PageUtil.getCurrentnum(kehu.getPage(), kehu.getRows()));
        List<kehu> list = kehuServiceImpl.query(kehu);
        json.put("rows", list);
        json.put("total", kehu.getCount());
        return json;
    }

    //客户导出
    @RequestMapping(value = "/kehuExport")
    public void gongyingshangExport(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        kehu kehu = new kehu();
        kehu.setCurrentnum(0);
        kehu.setRows(kehuServiceImpl.querycount(new kehu()));
        List<kehu> list = kehuServiceImpl.query(kehu);
        String[] header = new String[] { "客户名称", "联系方式", "地址", "创建时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (kehu kehu1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("客户名称", kehu1.getKehuName());
            map1.put("联系方式", kehu1.getKehuPhone());
            map1.put("地址", kehu1.getKehuAddress());
            map1.put("创建时间", kehu1.getCreateTime());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, "客户信息表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
