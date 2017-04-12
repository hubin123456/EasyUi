package com.jandar.action;

import java.io.UnsupportedEncodingException;
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

import com.jandar.pojo.diaobo;
import com.jandar.pojo.goods;
import com.jandar.pojo.kucun;
import com.jandar.service.diaoboService;
import com.jandar.serviceImpl.goodsServiceImpl;
import com.jandar.serviceImpl.kucunServiceImpl;
import com.jandar.util.ExportExcel;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class kucunAction {
    @Autowired
    private kucunServiceImpl kucunServiceImpl;
    @Autowired
    private goodsServiceImpl goodsServiceImpl;
    @Autowired
    private com.jandar.serviceImpl.diaoboServiceImpl diaoboServiceImpl;

    @RequestMapping(value = "kucunyujing1")
    @ResponseBody
    public JSONObject kucunyujing1(ModelMap map, kucun kucun,
            HttpServletRequest request, HttpServletResponse response) {
        kucun.setCount(kucunServiceImpl.querycount(new kucun()));
        kucun.setCurrentnum(
                PageUtil.getCurrentnum(kucun.getPage(), kucun.getRows()));
        List<kucun> list = kucunServiceImpl.query1(kucun);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        for (int i = 0; i < list.size(); i++) {

            if (Integer.valueOf(list.get(i).getKucunNumber()) < Integer
                    .valueOf(list.get(i).getKucunyujing())) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("kucunId", list.get(i).getKucunId());

                jsonObject.put("goodsName", list.get(i).getGoodsName());
                jsonObject.put("goodsImage", list.get(i).getGoodsImage());
                jsonObject.put("goodsLei", list.get(i).getGoodsLei());
                jsonObject.put("goodsUnit", list.get(i).getGoodsUnit());
                jsonObject.put("goodsPrice", list.get(i).getGoodsPrice());
                jsonObject.put("supplierName", list.get(i).getSupplierName());
                jsonObject.put("warehouseName", list.get(i).getWarehouseName());
                jsonObject.put("goodsNumber", list.get(i).getKucunNumber());
                jsonObject.put("supplier", list.get(i).getSupplierName());
                jsonObject.put("goodsyujing", list.get(i).getKucunyujing());
                jsonObject.put("goodsyujing1", list.get(i).getKucunyujing1());
                jsonObject.put("status", "超出最小库存");
                jsonArray.add(jsonObject);
            } else if (Integer.valueOf(list.get(i).getKucunNumber()) > Integer
                    .valueOf(list.get(i).getKucunyujing1())) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("kucunId", list.get(i).getKucunId());

                jsonObject.put("goodsName", list.get(i).getGoodsName());
                jsonObject.put("goodsImage", list.get(i).getGoodsImage());
                jsonObject.put("goodsLei", list.get(i).getGoodsLei());
                jsonObject.put("goodsUnit", list.get(i).getGoodsUnit());
                jsonObject.put("goodsPrice", list.get(i).getGoodsPrice());
                jsonObject.put("supplierName", list.get(i).getSupplierName());
                jsonObject.put("warehouseName", list.get(i).getWarehouseName());
                jsonObject.put("goodsNumber", list.get(i).getKucunNumber());
                jsonObject.put("supplier", list.get(i).getSupplierName());
                jsonObject.put("goodsyujing", list.get(i).getKucunyujing());
                jsonObject.put("goodsyujing1", list.get(i).getKucunyujing1());
                jsonObject.put("status", "超出最大库存");
                jsonArray.add(jsonObject);
            }
        }
        int b = 0;
        for (int a = 0; a < list.size(); a++) {
            if (Integer.valueOf(list.get(a).getKucunNumber()) < Integer
                    .valueOf(list.get(a).getKucunyujing())
                    || Integer.valueOf(list.get(a).getKucunNumber()) > Integer
                            .valueOf(list.get(a).getKucunyujing1())) {
                b++;
            }

        }
        JSONArray jsonArray1 = new JSONArray();
        if ((kucun.getCurrentnum() + kucun.getRows()) < b) {
            for (int i = kucun.getCurrentnum(); i < kucun.getCurrentnum()
                    + kucun.getRows(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        } else {
            for (int i = kucun.getCurrentnum(); i < b; i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        }
        jsonObject1.put("rows", jsonArray1);
        jsonObject1.put("total", b);
        return jsonObject1;
    }

    @RequestMapping(value = "kucun1")
    @ResponseBody
    public JSONObject kucun1(ModelMap map, kucun kucun,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        kucun.setCurrentnum(
                PageUtil.getCurrentnum(kucun.getPage(), kucun.getRows()));
        jsonObject.put("rows", kucunServiceImpl.query(kucun));
        jsonObject.put("total", kucunServiceImpl.querycount(kucun));
        return jsonObject;
    }

    @RequestMapping(value = "/kucunExport")
    public void kucunExport(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        kucun kucun = new kucun();
        kucun.setCurrentnum(0);
        kucun.setRows(kucunServiceImpl.querycount(new kucun()));
        List<kucun> list = kucunServiceImpl.query(kucun);
        String[] header = new String[] { "库存编号", "仓库名", "供应商", "货物名称", "货物单价",
                "货物类别", "货物单位", "货物数量", "入库时间", "最小库存预警", "最大库存预警" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (kucun kucun1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("库存编号", kucun1.getKucunId());
            map1.put("仓库名", kucun1.getWarehouseName());
            map1.put("供应商", kucun1.getSupplierName());
            map1.put("货物名称", kucun1.getGoodsName());
            map1.put("货物单价", kucun1.getGoodsPrice());
            map1.put("货物类别", kucun1.getGoodsLei());
            map1.put("货物单位", kucun1.getGoodsUnit());
            map1.put("货物数量", kucun1.getKucunNumber());
            map1.put("入库时间", kucun1.getCreateTime());
            map1.put("最小库存预警", kucun1.getKucunyujing());
            map1.put("最大库存预警", kucun1.getKucunyujing1());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, "库存表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/kucunyujingExport")
    public void kucunyujinfExport(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        kucun kucun = new kucun();
        kucun.setCurrentnum(0);
        kucun.setRows(kucunServiceImpl.querycount(new kucun()));
        List<kucun> list = kucunServiceImpl.query(kucun);
        String[] header = new String[] { "库存编号", "仓库名", "供应商", "货物名称", "货物单价",
                "货物类别", "货物单位", "货物数量", "入库时间", "最小库存预警", "最大库存预警", "库存状态" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {

            if (Integer.valueOf(list.get(i).getKucunNumber()) < Integer
                    .valueOf(list.get(i).getKucunyujing())) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("库存编号", list.get(i).getKucunId());
                map1.put("仓库名", list.get(i).getWarehouseName());
                map1.put("供应商", list.get(i).getSupplierName());
                map1.put("货物名称", list.get(i).getGoodsName());
                map1.put("货物单价", list.get(i).getGoodsPrice());
                map1.put("货物类别", list.get(i).getGoodsLei());
                map1.put("货物单位", list.get(i).getGoodsUnit());
                map1.put("货物数量", list.get(i).getKucunNumber());
                map1.put("入库时间", list.get(i).getCreateTime());
                map1.put("最小库存预警", list.get(i).getKucunyujing());
                map1.put("最大库存预警", list.get(i).getKucunyujing1());
                map1.put("库存状态", "超出最小库存");
                list1.add(map1);
            } else if (Integer.valueOf(list.get(i).getKucunNumber()) > Integer
                    .valueOf(list.get(i).getKucunyujing1())) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("库存编号", list.get(i).getKucunId());
                map1.put("仓库名", list.get(i).getWarehouseName());
                map1.put("供应商", list.get(i).getSupplierName());
                map1.put("货物名称", list.get(i).getGoodsName());
                map1.put("货物单价", list.get(i).getGoodsPrice());
                map1.put("货物类别", list.get(i).getGoodsLei());
                map1.put("货物单位", list.get(i).getGoodsUnit());
                map1.put("货物数量", list.get(i).getKucunNumber());
                map1.put("入库时间", list.get(i).getCreateTime());
                map1.put("最小库存预警", list.get(i).getKucunyujing());
                map1.put("最大库存预警", list.get(i).getKucunyujing1());
                map1.put("库存状态", "超出最大库存");
                list1.add(map1);
            }
        }
        try {
            ExportExcel.Export(list1, response, "库存预警表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/kucunselect")
    @ResponseBody
    public JSONArray kucunselect(ModelMap map, kucun kucun,
            HttpServletRequest request, HttpServletResponse response) {

        JSONArray jsonArray = new JSONArray();
        kucun.setRows(kucunServiceImpl.querycount(new kucun()));
        kucun.setCurrentnum(0);
        jsonArray = jsonArray.fromObject(kucunServiceImpl.query(kucun));
        System.out.println(jsonArray);
        return jsonArray;
    }

    @RequestMapping(value = "/kucunselect1")
    @ResponseBody
    public JSONArray kucunselect1(ModelMap map, kucun kucun,
            HttpServletRequest request, HttpServletResponse response) {

        JSONArray jsonArray = new JSONArray();
        kucun.setRows(kucunServiceImpl.querycount(new kucun()));
        kucun.setCurrentnum(0);
        try {
            String warehouseName = new String(
                    kucun.getWarehouseName().getBytes("iso-8859-1"), "utf-8");
            kucun.setWarehouseName(warehouseName);
            jsonArray = jsonArray.fromObject(kucunServiceImpl.query(kucun));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonArray;
    }

    @RequestMapping(value = "/editkucunyujing")
    @ResponseBody
    public String editkucunyujing(ModelMap map, kucun kucun,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println(kucun.getKucunId());
        System.out.println(kucun.getKucunyujing());
        JSONObject jsonObject = new JSONObject();
        try {
            kucunServiceImpl.update(kucun);
            jsonObject.put("result", 0);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
        }
        return jsonObject.toString();

    }

    @RequestMapping(value = "/editkucunyujing1")
    @ResponseBody
    public String editkucunyujing1(ModelMap map, kucun kucun,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kucunServiceImpl.update(kucun);
            jsonObject.put("result", 0);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
        }
        return jsonObject.toString();

    }

    @RequestMapping(value = "/kucundiaobo")
    @ResponseBody
    public String kucundiaobo(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        String warehouseNamediaochu = request
                .getParameter("warehouseNamediaochu");
        String goodsNamediaochu = request.getParameter("goodsNamediaochu");
        String warehouseNamediaoru = request
                .getParameter("warehouseNamediaoru");
        String diaoruNumber = request.getParameter("diaoruNumber");
        System.out.println(goodsNamediaochu + "---");
        JSONObject jsonObject = new JSONObject();
        kucun kucun = new kucun();
        kucun.setKucunId(Integer.valueOf(goodsNamediaochu));
        List<kucun> list = kucunServiceImpl.query1(kucun);
        if (Integer.valueOf(kucunServiceImpl.query1(kucun).get(0)
                .getKucunNumber()) > Integer.valueOf(diaoruNumber)) {
            kucun.setKucunNumber(String.valueOf(Integer
                    .valueOf(kucunServiceImpl.query1(kucun).get(0)
                            .getKucunNumber())
                    - Integer.valueOf(diaoruNumber)));
            kucunServiceImpl.update(kucun);
            kucun kucun1 = new kucun();
            kucun1.setGoodsName(list.get(0).getGoodsName());
            kucun1.setGoodsImage(list.get(0).getGoodsImage());
            kucun1.setGoodsLei(list.get(0).getGoodsLei());
            kucun1.setGoodsPrice(list.get(0).getGoodsPrice());
            kucun1.setGoodsUnit(list.get(0).getGoodsUnit());
            kucun1.setGoodsbeizhu(list.get(0).getGoodsbeizhu());
            kucun1.setSupplierName(list.get(0).getSupplierName());
            kucun1.setWarehouseName(warehouseNamediaoru);
            List<kucun> list1 = kucunServiceImpl.query1(kucun1);

            try {
                if (list1.size() == 0) {

                    String time = TimeUtil.timeToString(TimeUtil.currentTime());
                    kucun1.setCreateTime(time);
                    kucun1.setKucunyujing("50");
                    kucun1.setKucunyujing1("100");
                    kucun1.setKucunNumber(diaoruNumber);
                    kucunServiceImpl.insert(kucun1);
                    jsonObject.put("result", "调入成功");
                    diaobo diaobo = new diaobo();
                    diaobo.setWarehouseName(warehouseNamediaochu);
                    diaobo.setWarehouseName1(warehouseNamediaoru);
                    diaobo.setKucunId(Integer.valueOf(goodsNamediaochu));
                    diaobo.setDiaoboNumber(diaoruNumber);
                    diaobo.setCreateTime(time);
                    diaoboServiceImpl.insert(diaobo);
                } else {
                    kucun kucun2 = new kucun();
                    kucun2.setKucunId(list1.get(0).getKucunId());
                    kucun2.setKucunNumber(String.valueOf(
                            Integer.valueOf(list1.get(0).getKucunNumber())
                                    + Integer.valueOf(diaoruNumber)));
                    kucunServiceImpl.update(kucun2);
                    jsonObject.put("result", "调入成功");
                    diaobo diaobo = new diaobo();
                    diaobo.setWarehouseName(warehouseNamediaochu);
                    diaobo.setWarehouseName1(warehouseNamediaoru);
                    diaobo.setKucunId(Integer.valueOf(goodsNamediaochu));
                    diaobo.setDiaoboNumber(diaoruNumber);
                    diaobo.setCreateTime(
                            TimeUtil.timeToString(TimeUtil.currentTime()));
                    diaoboServiceImpl.insert(diaobo);
                }

            } catch (Exception e) {
                e.printStackTrace();
                jsonObject.put("result", "调入失败");
            }
        } else {
            jsonObject.put("result", "库存不足");
        }
        return jsonObject.toString();

    }

    @RequestMapping(value = "/kucunyujingselect")
    @ResponseBody
    public JSONArray kucunyujingselect(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        kucun kucun = new kucun();
        kucun.setRows(kucunServiceImpl.querycount(kucun));
        kucun.setCurrentnum(0);
        List<kucun> list = kucunServiceImpl.query(kucun);
        for (int i = 0; i < list.size(); i++) {

            if (Integer.valueOf(list.get(i).getKucunNumber()) < Integer
                    .valueOf(list.get(i).getKucunyujing())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kucunId", list.get(i).getKucunId());

                jsonObject.put("goodsName", list.get(i).getGoodsName());
                jsonObject.put("goodsImage", list.get(i).getGoodsImage());
                jsonObject.put("warehouseName", list.get(i).getWarehouseName());
                jsonObject.put("goodsNumber", list.get(i).getKucunNumber());
                jsonObject.put("goodsyujing", list.get(i).getKucunyujing());
                jsonObject.put("supplier", list.get(i).getSupplierName());
                jsonObject.put("goodsyujing1", list.get(i).getKucunyujing1());
                jsonObject.put("status", "超出最小库存");
                jsonArray.add(jsonObject);
            } else if (Integer.valueOf(list.get(i).getKucunNumber()) > Integer
                    .valueOf(list.get(i).getKucunyujing1())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kucunId", list.get(i).getKucunId());

                jsonObject.put("goodsName", list.get(i).getGoodsName());
                jsonObject.put("goodsImage", list.get(i).getGoodsImage());
                jsonObject.put("warehouseName", list.get(i).getWarehouseName());
                jsonObject.put("goodsNumber", list.get(i).getKucunNumber());
                jsonObject.put("goodsyujing", list.get(i).getKucunyujing());
                jsonObject.put("supplier", list.get(i).getSupplierName());
                jsonObject.put("goodsyujing1", list.get(i).getKucunyujing1());
                jsonObject.put("status", "超出最大库存");
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
