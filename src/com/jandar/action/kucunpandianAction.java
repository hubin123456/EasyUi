package com.jandar.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.kucun;
import com.jandar.pojo.kucunpandian;
import com.jandar.pojo.xiaoshou;
import com.jandar.serviceImpl.kucunServiceImpl;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class kucunpandianAction {
    @Autowired
    private com.jandar.serviceImpl.kucunpandianServiceImpl kucunpandianServiceImpl;
    @Autowired
    private kucunServiceImpl kucunserviceImpl;

    //datagrid显示库存盘点表单  只有库存数量和实际不符的表单
    @RequestMapping(value = "kucunpandian2")
    @ResponseBody
    public JSONObject kucun1(ModelMap map, kucunpandian kucun,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        kucun.setCurrentnum(
                PageUtil.getCurrentnum(kucun.getPage(), kucun.getRows()));
        List<kucunpandian> list = kucunpandianServiceImpl
                .query1(new kucunpandian());
        for (kucunpandian caozuo2 : list) {
            kucun kucun1 = new kucun();
            kucun1.setKucunId(caozuo2.getKucunId());
            if (caozuo2.getKucunpandianNumber().equals(
                    kucunserviceImpl.query1(kucun1).get(0).getKucunNumber())) {

            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kucunpandianId", caozuo2.getKucunpandianId());
                jsonObject.put("kucunId", caozuo2.getKucunId());
                jsonObject.put("createTime", caozuo2.getCreateTime());
                jsonObject.put("kucunpandianNumber",
                        caozuo2.getKucunpandianNumber());

                jsonObject.put("goodsImage",
                        kucunserviceImpl.query1(kucun1).get(0).getGoodsImage());
                jsonObject.put("goodsName",
                        kucunserviceImpl.query1(kucun1).get(0).getGoodsName());

                jsonObject.put("username", caozuo2.getUsername());
                // jsonObject.put("warehouseName", caozuo2.getWarehouseName());
                jsonObject.put("numbercha",
                        Integer.valueOf(caozuo2.getKucunpandianNumber())
                                - Integer
                                        .valueOf(kucunserviceImpl.query1(kucun1)
                                                .get(0).getKucunNumber()));
                jsonArray.add(jsonObject);
            }
        }

        JSONArray jsonArray1 = new JSONArray();
        if ((kucun.getCurrentnum() + kucun.getRows()) < jsonArray.size()) {
            for (int i = kucun.getCurrentnum(); i < kucun.getCurrentnum()
                    + kucun.getRows(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        } else {
            for (int i = kucun.getCurrentnum(); i < jsonArray.size(); i++) {
                jsonArray1.add(jsonArray.getJSONObject(i));
            }
        }
        json.put("rows", jsonArray1);
        json.put("total", jsonArray.size());
        System.out.println(json);
        return json;

    }

    //添加库存盘点表单
    @RequestMapping(value = "addkucunpandian")
    @ResponseBody
    public JSONObject addkucunpandian(ModelMap map, kucunpandian kucun,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        kucun.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
        try {
            kucunpandian kucun1 = new kucunpandian();
            kucun1.setKucunId(kucun.getKucunId());
            //覆盖已存在的库存盘点表单
            if (kucunpandianServiceImpl.query1(kucun1).size() != 0) {
                kucunpandianServiceImpl.delete(kucun1);
            }
            kucunpandianServiceImpl.insert(kucun);
            jsonObject.put("result", "提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", "提交失败");
        }
        return jsonObject;
    }

    //删除库存盘点表单
    @RequestMapping(value = "deletepandian")
    @ResponseBody
    public String deletepandian(ModelMap map, kucunpandian kucun,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kucunpandianServiceImpl.delete(kucun);
            jsonObject.put("result", "删除成功");
        } catch (Exception e) {
            jsonObject.put("result", "删除失败");
        }

        return jsonObject.toString();
    }
}
