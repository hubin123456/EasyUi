package com.jandar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.caozuo;
import com.jandar.pojo.gongyingshang;
import com.jandar.pojo.goods;
import com.jandar.service.caozuoService;
import com.jandar.serviceImpl.caozuoServiceImpl;
import com.jandar.serviceImpl.gongyingshangServiceImpl;
import com.jandar.serviceImpl.goodsServiceImpl;
import com.jandar.util.ExportExcel;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class gongyingguanliAction {
    @Autowired
    private gongyingshangServiceImpl gongyingshangServiceImpl;
    @Autowired
    private caozuoServiceImpl caozuoserviceImpl;
    @Autowired
    private goodsServiceImpl goodsserviceImpl;

    //采购订单的查询
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

    //采购订单的导出
    @RequestMapping(value = "/caigoudingdanExport")
    public void caigoudingdanExport(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setCurrentnum(0);
        if (caozuo.getStatus().equals("全部")) {
            caozuo.setStatus("");
        }
        if (caozuo.getUsername().equals("admin")) {
            caozuo.setUsername("");
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

    //采购
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

    //供应商交易明细
    @RequestMapping(value = "/gongyingshangjiaoyi")
    @ResponseBody
    public JSONObject gongyingshangjioayi(ModelMap map,
            HttpServletRequest request, HttpServletResponse response,
            caozuo caozuo) {

        caozuo.setStatus("入库");
        caozuo.setCurrentnum(
                PageUtil.getCurrentnum(caozuo.getPage(), caozuo.getRows()));
        caozuo.setCount(caozuoserviceImpl.querycount(caozuo));
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<caozuo> caozuolist = caozuoserviceImpl.query(caozuo);
        for (caozuo caozuo2 : caozuolist) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("caozuoId", caozuo2.getCaozuoId());
            jsonObject.put("createTime", caozuo2.getCreateTime());
            jsonObject.put("goodsImage", caozuo2.getGoodsImage());
            jsonObject.put("goodsName", caozuo2.getGoodsName());
            jsonObject.put("goodsNumber", caozuo2.getGoodsNumber());
            jsonObject.put("goodsPrice", caozuo2.getGoodsPrice());
            jsonObject.put("sumPrice", caozuo2.getSumPrice());
            jsonObject.put("username", caozuo2.getUsername());
            jsonObject.put("warehouseName", caozuo2.getWarehouseName());
            jsonObject.put("status", caozuo2.getStatus());
            jsonArray.add(jsonObject);
        }

        json.put("rows", jsonArray);
        json.put("total", caozuo.getCount());
        System.out.println(json);
        return json;
    }

    //供应商交易明细导出
    @RequestMapping(value = "/gongyingshangyiExport")
    public void gongyingshangyiExport(ModelMap map, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        caozuo.setCurrentnum(0);
        caozuo.setRows(caozuoserviceImpl.querycount(new caozuo()));
        caozuo.setStatus("入库");
        String[] header = new String[] { "入库单编号", "货物名称", "货物数量", "货物单价", "仓库名",
                "交易总价", "入库人员", "交易时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

        List<caozuo> goodslist = caozuoserviceImpl.query(caozuo);
        for (caozuo caozuo2 : goodslist) {

            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("入库单编号", caozuo2.getCaozuoId());
            map1.put("货物名称", caozuo2.getGoodsName());
            map1.put("货物数量", caozuo2.getGoodsNumber());
            map1.put("货物单价", caozuo2.getGoodsPrice());
            map1.put("仓库名", caozuo2.getWarehouseName());
            map1.put("交易总价", caozuo2.getSumPrice());
            map1.put("入库人员", caozuo2.getUsername());
            map1.put("交易时间", caozuo2.getLukuTime());
            list1.add(map1);
        }

        // System.out.println(goods.getSupplierName()+"_____");
        try {
            ExportExcel.Export(list1, response,
                    "供应商" + caozuo.getSupplierName() + "交易明细表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
   

}
