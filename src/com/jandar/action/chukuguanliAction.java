package com.jandar.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.kucun;
import com.jandar.pojo.xiaoshou;
import com.jandar.serviceImpl.kucunServiceImpl;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class chukuguanliAction {
    @Autowired
    private kucunServiceImpl kucunserviceImpl;
    @Autowired
    private com.jandar.serviceImpl.xiaoshouServiceImpl xiaoshouServiceImpl;
  //出库管理datagrid
    @RequestMapping(value = "/dengdaichuhuo")
    @ResponseBody
    public JSONObject dengdaichuhuo(ModelMap model, xiaoshou xiaoshou,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println(xiaoshou.getUsername() + "---");
        xiaoshou.setStatus("等待出库");
        xiaoshou.setCurrentnum(
                PageUtil.getCurrentnum(xiaoshou.getPage(), xiaoshou.getRows()));
        xiaoshou.setCount(xiaoshouServiceImpl.querycount(xiaoshou));
        JSONObject json = new JSONObject();
        List<xiaoshou> list = xiaoshouServiceImpl.query(xiaoshou);
        JSONArray jsonArray = new JSONArray();
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
            jsonObject.put("sumPrice", caozuo2.getSumPrice());
            jsonObject.put("username", caozuo2.getUsername());
            jsonObject.put("status", caozuo2.getStatus());
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
        json.put("rows", jsonArray.toList(jsonArray1));
        json.put("total", jsonArray.size());
        System.out.println(json);
        return json;
    }
    
    //出库撤销
    @RequestMapping(value = "/chuhuochexiao")
    @ResponseBody
    public JSONObject chuhuochexiao(ModelMap model, xiaoshou caozuo,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        caozuo.setStatus("撤销");
        try {
            xiaoshouServiceImpl.update(caozuo);
            jsonObject.put("result", "撤销成功");
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put("result", "撤销失败");
            return jsonObject;
        }
    }
    
    //出库
    @RequestMapping(value = "/chuku")
    @ResponseBody
    public String chuku(ModelMap model, xiaoshou xiaoshou,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kucun kucun = new kucun();
            kucun.setKucunId(xiaoshou.getKucunId());
            kucun.setCurrentnum(0);
            kucun.setRows(kucunserviceImpl.querycount(new kucun()));
            String time = TimeUtil.timeToString(TimeUtil.currentTime());
            int num = 0;
            List<kucun> list = kucunserviceImpl.query(kucun);
            if (list.size() != 0) {
                if (Integer.valueOf(list.get(0).getKucunNumber()) > Integer
                        .valueOf(xiaoshou.getGoodsNumber())) {
                    kucun.setKucunNumber(String.valueOf(Integer
                            .valueOf(list.get(0).getKucunNumber())
                            - Integer.valueOf(xiaoshou.getGoodsNumber())));
                    kucunserviceImpl.update(kucun);
                    xiaoshou.setStatus("出库");
                    xiaoshou.setChukuTime(time);
                    xiaoshouServiceImpl.update(xiaoshou);
                    jsonObject.put("result", "出库成功");
                } else {
                    jsonObject.put("result", "出库失败，库存不足");
                }
            } else {
                jsonObject.put("result", "出库失败，库存不足");
            }
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", "出库失败");
            return jsonObject.toString();
        }
    }
}
