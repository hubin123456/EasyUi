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
public class gongyingshangAction {
    @Autowired
    private gongyingshangServiceImpl gongyingshangServiceImpl;
    @Autowired
    private caozuoServiceImpl caozuoserviceImpl;
    @Autowired
    private goodsServiceImpl goodsserviceImpl;

    @RequestMapping(value = "/gongyingshangselect")
    @ResponseBody
    public JSONArray gongyingshangselect(ModelMap map,
            HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        gongyingshang gongyingshang = new gongyingshang();
        gongyingshang
                .setRows(gongyingshangServiceImpl.querycount(gongyingshang));
        gongyingshang.setCurrentnum(0);
        jsonArray = jsonArray
                .fromObject(gongyingshangServiceImpl.query(gongyingshang));
        System.out.println(jsonArray);
        return jsonArray;
    }

    @RequestMapping(value = "/createSupplier")
    @ResponseBody
    public String createSupplier(ModelMap map, gongyingshang gongyingshang,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            gongyingshang.setCreateTime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            gongyingshangServiceImpl.insert(gongyingshang);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/editSupplier")
    @ResponseBody
    public String editSupplier(ModelMap map, gongyingshang gongyingshang,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            gongyingshang.setCreateTime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            gongyingshangServiceImpl.update(gongyingshang);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/gongyingshang")
    @ResponseBody
    public JSONObject gongyingshang(ModelMap map, gongyingshang gongyingshang,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        gongyingshang.setCount(
                gongyingshangServiceImpl.querycount(new gongyingshang()));
        gongyingshang.setCurrentnum(PageUtil.getCurrentnum(
                gongyingshang.getPage(), gongyingshang.getRows()));
        List<gongyingshang> list = gongyingshangServiceImpl
                .query(gongyingshang);
        json.put("rows", list);
        json.put("total", gongyingshang.getCount());
        return json;
    }

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

    @RequestMapping(value = "/ gongyingshangExport")
    public void gongyingshangExport(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        gongyingshang gongyingshang = new gongyingshang();
        gongyingshang.setCurrentnum(0);
        gongyingshang.setRows(
                gongyingshangServiceImpl.querycount(new gongyingshang()));
        List<gongyingshang> list = gongyingshangServiceImpl
                .query(gongyingshang);
        String[] header = new String[] { "供应商名称", "联系方式", "地址", "创建时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (gongyingshang gongyingshang1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("供应商名称", gongyingshang1.getSupplierName());
            map1.put("联系方式", gongyingshang1.getSupplierPhone());
            map1.put("地址", gongyingshang1.getSupplierAddress());
            map1.put("创建时间", gongyingshang1.getCreateTime());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, "供应商信息表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
