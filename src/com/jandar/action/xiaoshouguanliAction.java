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

import com.jandar.pojo.caozuo;
import com.jandar.pojo.goods;
import com.jandar.pojo.kehu;
import com.jandar.pojo.kucun;
import com.jandar.pojo.xiaoshou;
import com.jandar.serviceImpl.kehuServiceImpl;
import com.jandar.serviceImpl.kucunServiceImpl;
import com.jandar.serviceImpl.xiaoshouServiceImpl;
import com.jandar.util.ExportExcel;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class xiaoshouguanliAction {
    @Autowired
    private xiaoshouServiceImpl xiaoshouServiceImpl;
    @Autowired
    private kucunServiceImpl kucunserviceImpl;
    @Autowired
    private kehuServiceImpl kehuserviceImpl;

    //客户订单
    @RequestMapping(value = "/chukudingdan")
    @ResponseBody
    public JSONObject chukudingdan(ModelMap model, xiaoshou xiaoshou,
            HttpServletRequest request, HttpServletResponse response) {

        if (xiaoshou.getStatus() != null && xiaoshou.getStatus().equals("全部")) {
            xiaoshou.setStatus("");
        }
        if (xiaoshou.getUsername().equals("admin")) {
            xiaoshou.setUsername("");
        }
        xiaoshou.setCurrentnum(
                PageUtil.getCurrentnum(xiaoshou.getPage(), xiaoshou.getRows()));
        xiaoshou.setCount(xiaoshouServiceImpl.querycount(xiaoshou));
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        System.out.println(xiaoshouServiceImpl.querycount(xiaoshou));
        List<xiaoshou> list = xiaoshouServiceImpl.query(xiaoshou);
        for (xiaoshou caozuo2 : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("xiaoshouId", caozuo2.getXiaoshouId());
            jsonObject.put("kehuName", caozuo2.getKehuName());
            jsonObject.put("kucunId", caozuo2.getKucunId());
            jsonObject.put("createTime", caozuo2.getCreateTime());
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
            jsonObject.put("status", caozuo2.getStatus());
            jsonArray.add(jsonObject);
        }

        json.put("rows", jsonArray);
        json.put("total", xiaoshou.getCount());
        System.out.println(json);
        return json;
    }

    //客户交易明细导出
    @RequestMapping(value = "/kehujiaoyiExport")
    public void kehujiaoyiExport(ModelMap map, xiaoshou kehu,
            HttpServletRequest request, HttpServletResponse response) {

        kehu.setCurrentnum(0);
        kehu.setStatus("出库");
        kehu.setRows(xiaoshouServiceImpl.querycount(kehu));
        String[] header = new String[] { "出库单编号","库存编号", "货物名称", "货物单价", "仓库名", "交易总价",
                "出库人员", "交易时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

        List<xiaoshou> goodslist = xiaoshouServiceImpl.query(kehu);
        for (xiaoshou caozuo2 : goodslist) {

            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("出库单编号", caozuo2.getXiaoshouId());
            kucun kucun = new kucun();
            kucun.setKucunId(caozuo2.getKucunId());
            map1.put("库存编号", caozuo2.getKucunId());
            map1.put("货物名称",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsName());

            map1.put("货物单价",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsPrice());
            map1.put("仓库名",
                    kucunserviceImpl.query1(kucun).get(0).getWarehouseName());
            map1.put("交易总价", caozuo2.getSumPrice());
            map1.put("出库人员", caozuo2.getUsername());

            map1.put("交易时间", caozuo2.getChukuTime());

            list1.add(map1);
        }

        // System.out.println(goods.getSupplierName()+"_____");
        try {
            ExportExcel.Export(list1, response,
                    "客户" + kehu.getKehuName() + "交易明细表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //客户订单导出
    @RequestMapping(value = "/kehudingdanExport")

    public void kehudingdanExport(ModelMap map, xiaoshou xiaoshou,
            HttpServletRequest request, HttpServletResponse response) {
        xiaoshou.setCurrentnum(0);
        if (xiaoshou.getStatus().equals("全部")) {
            xiaoshou.setStatus("");
        }
        if (xiaoshou.getUsername().equals("admin")) {
            xiaoshou.setUsername("");
        }
        xiaoshou.setRows(xiaoshouServiceImpl.querycount(xiaoshou));
        List<xiaoshou> list = xiaoshouServiceImpl.query(xiaoshou);
        String[] header = new String[] { "销售单编号","库存编号", "货物名称", "货物类别", "货物单位",
                "货物单价", "供应商", "货物数量", "货物总价", "销售对象", "订单状态", "出库人员", "创建时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (xiaoshou caozuo1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("销售单编号", caozuo1.getXiaoshouId());
            kucun kucun = new kucun();
            kucun.setKucunId(caozuo1.getKucunId());
            map1.put("库存编号", caozuo1.getKucunId());
            map1.put("货物名称",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsName());

            map1.put("货物类别",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsLei());
            map1.put("货物单位",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsUnit());
            map1.put("货物单价",
                    kucunserviceImpl.query1(kucun).get(0).getGoodsPrice());
            map1.put("供应商",
                    kucunserviceImpl.query1(kucun).get(0).getSupplierName());
            map1.put("货物数量", caozuo1.getGoodsNumber());
            map1.put("货物总价", caozuo1.getSumPrice());
            map1.put("销售对象", caozuo1.getKehuName());
            map1.put("订单状态", caozuo1.getStatus());
            map1.put("出库人员", caozuo1.getUsername());

            map1.put("创建时间", caozuo1.getCreateTime());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, xiaoshou.getStatus() + "销售单",
                    header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //客户交易明细datagrid
    @RequestMapping(value = "/kehujiaoyi1")
    @ResponseBody
    public JSONObject kehujioayi1(ModelMap map, HttpServletRequest request,
            HttpServletResponse response, xiaoshou xiaoshou) {
        xiaoshou.setStatus("出库");

        xiaoshou.setCurrentnum(
                PageUtil.getCurrentnum(xiaoshou.getPage(), xiaoshou.getRows()));
        xiaoshou.setCount(xiaoshouServiceImpl.querycount(xiaoshou));
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<xiaoshou> xiaoshoulist = xiaoshouServiceImpl.query(xiaoshou);
        for (xiaoshou caozuo2 : xiaoshoulist) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("caozuoId", caozuo2.getXiaoshouId());
            jsonObject.put("kucunId", caozuo2.getKucunId());
            jsonObject.put("createTime", caozuo2.getCreateTime());
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
            jsonObject.put("status", caozuo2.getStatus());
            jsonArray.add(jsonObject);
        }

        json.put("rows", jsonArray);
        json.put("total", xiaoshou.getCount());
        System.out.println(json);
        return json;
    }

    //提交销售订单
    @RequestMapping(value = "/chuku11")
    @ResponseBody
    public String chuku11(ModelMap model, xiaoshou xiaoshou,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            xiaoshou.setStatus("等待出库");
            xiaoshou.setCreateTime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            xiaoshouServiceImpl.insert(xiaoshou);
            jsonObject.put("result", "提交成功,等待审核");
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", "提交失败");
            return jsonObject.toString();
        }
    }

  
}
