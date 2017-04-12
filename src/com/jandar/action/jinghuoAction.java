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
public class jinghuoAction {
    @Autowired
    private caozuoServiceImpl caozuoserviceImpl;
    @Autowired
    private kucunServiceImpl kucunserviceImpl;
    @Autowired
    private goodsServiceImpl goodsserviceImpl;
    @Autowired
    private xiaoshouServiceImpl xiaoshouserviceImpl;

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

    @RequestMapping(value = "/caozuozhexiantu")
    @ResponseBody
    public JSONObject caozuozhexiantu(ModelMap model, xiaoshou caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setCurrentnum(
                PageUtil.getCurrentnum(caozuo.getPage(), caozuo.getRows()));
        System.out.println(caozuo.getCurrentnum() + "--------------------");
        String x = request.getParameter("x");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String renyuan = request.getParameter("renyuan");
        String tiaojian = request.getParameter("tiaojian");
        JSONObject json = new JSONObject();
        String time = "";
        if (month == "") {
            if (Integer.valueOf(x) <= 8) {
                time = year + "-0" + String.valueOf(Integer.valueOf(x) + 1);
            } else {
                time = year + "-" + String.valueOf(Integer.valueOf(x) + 1);
            }
        } else {
            if (Integer.valueOf(month) <= 8) {
                if (Integer.valueOf(x) <= 8) {
                    time = year + "-0" + month + "-0"
                            + String.valueOf(Integer.valueOf(x) + 1);
                } else {
                    time = year + "-0" + month + "-"
                            + String.valueOf(Integer.valueOf(x) + 1);
                }
            } else {
                if (Integer.valueOf(x) <= 8) {
                    time = year + "-" + month + "-0"
                            + String.valueOf(Integer.valueOf(x) + 1);
                } else {
                    time = year + "-" + month + "-"
                            + String.valueOf(Integer.valueOf(x) + 1);
                }
            }
        }
        if (tiaojian.equals("收入")) {
            xiaoshou xiaoshou = new xiaoshou();
            System.out.println(time);
            xiaoshou.setChukuTime(time);
            xiaoshou.setUsername(renyuan);
            List<xiaoshou> list = xiaoshouserviceImpl.queryBytime(xiaoshou);

            shourucaiwuxiangxi(list, caozuo, json);
        } else if (tiaojian.equals("支出")) {
            System.out.println(time);
            caozuo caozuo1 = new caozuo();
            caozuo1.setUsername(renyuan);
            caozuo1.setLukuTime(time);
            List<caozuo> list = caozuoserviceImpl.queryBytime(caozuo1);
            zhichucaiwuxiangxi(list, caozuo, json);

        }
        return json;

    }

    public JSONObject zhichucaiwuxiangxi(List<caozuo> list, xiaoshou xiaoshou,
            JSONObject json) {
        JSONArray jsonArray = new JSONArray();
        for (caozuo caozuo2 : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("xiaoshouId", caozuo2.getCaozuoId());
            jsonObject.put("kehuName", caozuo2.getSupplierName());
            jsonObject.put("goodsImage", caozuo2.getGoodsImage());
            jsonObject.put("goodsName", caozuo2.getGoodsName());
            jsonObject.put("goodsNumber", caozuo2.getGoodsNumber());
            // jsonObject.put("goodsPrice", goods2.getGoodsPrice());
            jsonObject.put("sumPrice", caozuo2.getSumPrice());
            jsonObject.put("username", caozuo2.getUsername());
            // jsonObject.put("warehouseName", caozuo2.getWarehouseName());
            jsonObject.put("createTime", caozuo2.getCreateTime());
            jsonArray.add(jsonObject);
        }

        JSONArray jsonArray1 = new JSONArray();
        if ((xiaoshou.getCurrentnum() + xiaoshou.getRows()) < jsonArray
                .size()) {
            for (int i = xiaoshou.getCurrentnum(); i < xiaoshou.getCurrentnum()
                    + xiaoshou.getRows(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        } else {
            for (int i = xiaoshou.getCurrentnum(); i < jsonArray.size(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        }
        json.put("rows", jsonArray1);
        json.put("total", jsonArray.size());
        return json;
    }

    public JSONObject shourucaiwuxiangxi(List<xiaoshou> list, xiaoshou xiaoshou,
            JSONObject json) {
        JSONArray jsonArray = new JSONArray();
        for (xiaoshou caozuo2 : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("xiaoshouId", caozuo2.getXiaoshouId());
            jsonObject.put("kehuName", caozuo2.getKehuName());
            // jsonObject.put("kucunId", caozuo2.getKucunId());
            // jsonObject.put("createTime", caozuo2.getCreateTime());
            kucun kucun = new kucun();
            kucun.setKucunId(caozuo2.getKucunId());
            jsonObject.put("goodsImage",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsImage());
            jsonObject.put("goodsName",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsName());
            jsonObject.put("goodsNumber", caozuo2.getGoodsNumber());
            // jsonObject.put("goodsPrice", goods2.getGoodsPrice());
            jsonObject.put("sumPrice", caozuo2.getSumPrice());
            jsonObject.put("username", caozuo2.getUsername());
            // jsonObject.put("warehouseName", caozuo2.getWarehouseName());
            jsonObject.put("createTime", caozuo2.getCreateTime());
            jsonArray.add(jsonObject);
        }

        JSONArray jsonArray1 = new JSONArray();
        if ((xiaoshou.getCurrentnum() + xiaoshou.getRows()) < jsonArray
                .size()) {
            for (int i = xiaoshou.getCurrentnum(); i < xiaoshou.getCurrentnum()
                    + xiaoshou.getRows(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        } else {
            for (int i = xiaoshou.getCurrentnum(); i < jsonArray.size(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        }
        json.put("rows", jsonArray1);
        json.put("total", jsonArray.size());
        return json;
    }

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

    @RequestMapping(value = "/caigouruku")
    @ResponseBody
    public JSONObject caigouruku(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        if (caozuo.getStatus() != null && caozuo.getStatus().equals("全部")) {
            caozuo.setStatus("");
        }
        if (caozuo.getUsername().equals("admin")) {
            caozuo.setUsername("");
        }
        caozuo.setCurrentnum(
                PageUtil.getCurrentnum(caozuo.getPage(), caozuo.getRows()));
        caozuo.setCount(caozuoserviceImpl.querycount(caozuo));
        JSONObject json = new JSONObject();
        List<caozuo> list = caozuoserviceImpl.query(caozuo);
        json.put("rows", list);
        json.put("total", caozuo.getCount());
        return json;

    }

    @RequestMapping(value = "/caigoudingdanExport")
    public void caigoudingdanExport(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setCurrentnum(0);
        if (caozuo.getStatus().equals("全部")) {
            caozuo.setStatus("");
        }
        caozuo.setRows(caozuoserviceImpl.querycount(caozuo));
        List<caozuo> list = caozuoserviceImpl.query(caozuo);
        String[] header = new String[] { "采购单编号", "货物名称", "货物类别", "货物单位",
                "货物单价", "供应商", "货物数量", "货物总价", "仓库名", "入库人员", "状态", "创建时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (caozuo caozuo1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("采购单编号", caozuo1.getCaozuoId());

            map1.put("货物名称", caozuo1.getGoodsName());

            map1.put("货物类别", caozuo1.getGoodsLei());
            map1.put("货物单位", caozuo1.getGoodsUnit());
            map1.put("货物单价", caozuo1.getGoodsPrice());
            map1.put("供应商", caozuo1.getSupplierName());
            map1.put("货物数量", caozuo1.getGoodsNumber());
            map1.put("货物总价", caozuo1.getSumPrice());
            map1.put("仓库名", caozuo1.getWarehouseName());
            map1.put("入库人员", caozuo1.getUsername());
            map1.put("状态", caozuo1.getStatus());
            map1.put("创建时间", caozuo1.getCreateTime());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, caozuo.getStatus() + "采购单",
                    header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/jinghuo3")
    @ResponseBody
    public String createcaigoudan(ModelMap map, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
        caozuo.setStatus("等待入库");
        JSONObject jsonObject = new JSONObject();
        try {

            caozuoserviceImpl.insert(caozuo);
            jsonObject.put("result", "1");
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            jsonObject.put("result", "0");
        }
        return jsonObject.toString();
    }

}
