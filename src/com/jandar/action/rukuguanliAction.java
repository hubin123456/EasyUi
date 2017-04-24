package com.jandar.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.dao.caozuoDao;
import com.jandar.pojo.caozuo;
import com.jandar.pojo.goods;
import com.jandar.pojo.kucun;
import com.jandar.pojo.xiaoshou;
import com.jandar.service.caozuoService;
import com.jandar.serviceImpl.caozuoServiceImpl;
import com.jandar.serviceImpl.goodsServiceImpl;
import com.jandar.serviceImpl.kucunServiceImpl;
import com.jandar.serviceImpl.xiaoshouServiceImpl;
import com.jandar.util.ExportExcel;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;
import com.sun.org.apache.regexp.internal.recompile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class rukuguanliAction {
    @Autowired
    private caozuoServiceImpl caozuoserviceImpl;
    @Autowired
    private kucunServiceImpl kucunserviceImpl;
    @Autowired
    private goodsServiceImpl goodsserviceImpl;
    @Autowired
    private xiaoshouServiceImpl xiaoshouserviceImpl;

    //入库管理datagrid
    @RequestMapping(value = "/jinghuo1")
    @ResponseBody
    public JSONObject jinghuo1(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setStatus("等待入库");
        caozuo.setCurrentnum(
                PageUtil.getCurrentnum(caozuo.getPage(), caozuo.getRows()));
        caozuo.setCount(caozuoserviceImpl.querycount(caozuo));
        JSONObject json = new JSONObject();
        List<caozuo> list = caozuoserviceImpl.query(caozuo);
        json.put("rows", list);
        json.put("total", caozuo.getCount());
        System.out.println(json);
        return json;
    }

    
    //combobox等待入库的集合
    @RequestMapping(value = "/jinghuoselect")
    @ResponseBody
    public JSONArray jinghuoselect(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setStatus("等待入库");
        caozuo.setCurrentnum(0);
        caozuo.setRows(caozuoserviceImpl.querycount(caozuo));
        List<caozuo> list = caozuoserviceImpl.query(caozuo);

        return JSONArray.fromObject(list);
    }

    //撤销
    @RequestMapping(value = "/tuihuo")
    @ResponseBody
    public JSONObject tuihuo(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        caozuo.setStatus("撤销");
        try {
            caozuoserviceImpl.update(caozuo);
            jsonObject.put("result", "撤销成功");
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put("result", "撤销失败");
            return jsonObject;
        }
    }

    //入库
    @RequestMapping(value = "/ruku")
    @ResponseBody
    public JSONObject ruku(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        caozuo.setCurrentnum(0);
        caozuo.setRows(caozuoserviceImpl.querycount(caozuo));
        try {
            kucun kucun = new kucun();
            kucun.setGoodsName(
                    caozuoserviceImpl.query(caozuo).get(0).getGoodsName());
            kucun.setGoodsImage(
                    caozuoserviceImpl.query(caozuo).get(0).getGoodsImage());
            kucun.setGoodsLei(
                    caozuoserviceImpl.query(caozuo).get(0).getGoodsLei());
            kucun.setGoodsUnit(
                    caozuoserviceImpl.query(caozuo).get(0).getGoodsUnit());
            kucun.setGoodsPrice(
                    caozuoserviceImpl.query(caozuo).get(0).getGoodsPrice());
            kucun.setGoodsbeizhu(
                    caozuoserviceImpl.query(caozuo).get(0).getGoodsbeizhu());
            kucun.setSupplierName(
                    caozuoserviceImpl.query(caozuo).get(0).getSupplierName());
            kucun.setWarehouseName(caozuo.getWarehouseName());
            kucun.setCurrentnum(0);
            kucun.setRows(kucunserviceImpl.querycount(new kucun()));
            String time = TimeUtil.timeToString(TimeUtil.currentTime());
            int num = 0;
            //查看库存存不存在
            if (kucunserviceImpl.query(kucun).size() == 0) {
                kucun.setCreateTime(time);
                kucun.setKucunyujing("50");
                kucun.setKucunyujing1("100");
                kucun.setKucunNumber(caozuo.getGoodsNumber());
                kucunserviceImpl.insert(kucun);
                caozuo.setLukuTime(time);
                caozuo.setStatus("入库");
                caozuoserviceImpl.update(caozuo);
            } else {

                num = Integer
                        .valueOf(kucunserviceImpl.query(kucun).get(0)
                                .getKucunNumber())
                        + Integer.valueOf(caozuo.getGoodsNumber());

                kucun.setKucunId(
                        kucunserviceImpl.query(kucun).get(0).getKucunId());
                kucun.setKucunNumber(String.valueOf(num));
                kucun.setCreateTime(
                        TimeUtil.timeToString(TimeUtil.currentTime()));
                kucunserviceImpl.update(kucun);
                caozuo.setLukuTime(time);
                caozuo.setStatus("入库");
                caozuoserviceImpl.update(caozuo);
            }
            jsonObject.put("result", "入库成功");
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", "入库失败");
            return jsonObject;
        }
    }

   

}
