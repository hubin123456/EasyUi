package com.jandar.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.caozuo;
import com.jandar.pojo.kucun;
import com.jandar.pojo.xiaoshou;
import com.jandar.serviceImpl.caozuoServiceImpl;
import com.jandar.serviceImpl.kucunServiceImpl;
import com.jandar.util.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.org.mozilla.javascript.internal.IdFunctionCall;

@Controller
public class baobiaoAction {
    @Autowired
    private caozuoServiceImpl caozuoServiceImpl;
    @Autowired
    private com.jandar.serviceImpl.xiaoshouServiceImpl xiaoshouServiceImpl;
    @Autowired
    private kucunServiceImpl kucunserviceImpl;
    // 年份下拉框
    @RequestMapping("/yearselect")
    @ResponseBody
    public JSONArray  yearselect(HttpServletResponse response,
            HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        Calendar a = Calendar.getInstance();
        int year = a.get(Calendar.YEAR);
        for (int i = year; i > 2000; i--) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("year", i);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    // 月份下拉框
    @RequestMapping("/monthselect")
    @ResponseBody
    public JSONArray monthselect(HttpServletResponse response,
            HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 1; i < 13; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("month", i);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    // 财务折线图
    @RequestMapping("/caiwuzhexiantu")
    @ResponseBody
    public String caiwuzhexiantu(HttpServletResponse response,
            HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String renyuan = request.getParameter("renyuan");
        jsonObject.put("name", "支出");
        jsonObject.put("data", list("入库", year, month, renyuan));
        jsonObject1.put("name", "收入");
        jsonObject1.put("data", list1("出库", year, month, renyuan));
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonObject2.put("x", listx("入库", year, month, renyuan));
        jsonObject2.put("data", jsonArray);
        if (month.equals("")) {
            year = year + "年";
            if (renyuan.equals("")) {
                jsonObject2.put("year", year);
            } else {
                jsonObject2.put("year", year + renyuan);
            }
        } else {
            year = year + "年" + month + "月";
            if (renyuan.equals("")) {
                jsonObject2.put("year", year);
            } else {
                jsonObject2.put("year", year + renyuan);
            }
        }
        return jsonObject2.toString();
    }

    // x轴
    public List<String> listx(String status, String year, String month,
            String renyuan) {
        List<String> list1 = new ArrayList<String>();
        if (month.equals("")) {
            for (int i = 1; i <= 12; i++) {
                list1.add(String.valueOf(i) + "月");
            }
        } else {
            Calendar c = Calendar.getInstance();
            c.set(Integer.valueOf(year), Integer.valueOf(month), 0); // 输入类型为int类型
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

            for (int i = 1; i <= dayOfMonth; i++) {
                list1.add(String.valueOf(i) + "号");
            }
        }
        return list1;
    }

    // 入库财务
    public List<Integer> list(String status, String year, String month,
            String renyuan) {
        List<Integer> list = new ArrayList<Integer>();
        caozuo caozuo = new caozuo();
        caozuo.setStatus(status);
        caozuo.setCurrentnum(0);
        caozuo.setRows(caozuoServiceImpl.querycount(new caozuo()));
        if (month.equals("")) {
            for (int i = 1; i <= 12; i++) {
                if (i <= 9) {
                    caozuo.setLukuTime(year + "-0" + String.valueOf(i));
                } else {
                    caozuo.setLukuTime(year + "-" + String.valueOf(i));
                }
                caozuo.setUsername(renyuan);
                List<caozuo> list2 = caozuoServiceImpl.queryBytime(caozuo);
                int sum = 0;
                System.out.println(list2.size() + "----------");
                for (caozuo caozuo2 : list2) {
                    sum = sum + Integer.valueOf(caozuo2.getSumPrice());
                }
                list.add(sum);

            }
        } else {
            Calendar c = Calendar.getInstance();
            c.set(Integer.valueOf(year), Integer.valueOf(month), 0); // 输入类型为int类型
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            String nianyue = "";
            if (Integer.valueOf(month) <= 9) {
                nianyue = year + "-0" + month;
            } else {
                nianyue = year + "-" + month;
            }
            for (int i = 1; i <= dayOfMonth; i++) {
                if (i <= 9) {
                    caozuo.setLukuTime(nianyue + "-0" + String.valueOf(i));
                } else {
                    caozuo.setLukuTime(nianyue + "-" + String.valueOf(i));
                }
                caozuo.setUsername(renyuan);
                List<caozuo> list2 = caozuoServiceImpl.queryBytime(caozuo);
                int sum = 0;
                System.out.println(list2.size() + "----------");
                for (caozuo caozuo2 : list2) {

                    sum = sum + Integer.valueOf(caozuo2.getSumPrice());
                }
                list.add(sum);

            }
        }
        return list;
    }

    // 销售财务
    public List<Integer> list1(String status, String year, String month,
            String renyuan) {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list1 = new ArrayList<Integer>();
        xiaoshou caozuo = new xiaoshou();
        caozuo.setStatus(status);
        caozuo.setCurrentnum(0);
        caozuo.setRows(xiaoshouServiceImpl.querycount(caozuo));
        if (month.equals("")) {
            for (int i = 1; i <= 12; i++) {
                if (i <= 9) {
                    caozuo.setChukuTime(year + "-0" + String.valueOf(i));
                } else {
                    caozuo.setChukuTime(year + "-" + String.valueOf(i));
                }
                caozuo.setUsername(renyuan);
                List<xiaoshou> list2 = xiaoshouServiceImpl.queryBytime(caozuo);
                int sum = 0;
                System.out.println(list2.size() + "----------");
                for (xiaoshou caozuo2 : list2) {
                    sum = sum + Integer.valueOf(caozuo2.getSumPrice());
                }
                list.add(sum);
                list1.add(i);
            }
        } else {
            Calendar c = Calendar.getInstance();
            c.set(Integer.valueOf(year), Integer.valueOf(month), 0); // 输入类型为int类型
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            String nianyue = "";
            if (Integer.valueOf(month) <= 9) {
                nianyue = year + "-0" + month;
            } else {
                nianyue = year + "-" + month;
            }
            for (int i = 1; i <= dayOfMonth; i++) {
                if (i <= 9) {
                    caozuo.setChukuTime(nianyue + "-0" + String.valueOf(i));
                } else {
                    caozuo.setChukuTime(nianyue + "-" + String.valueOf(i));
                }
                caozuo.setUsername(renyuan);
                List<xiaoshou> list2 = xiaoshouServiceImpl.queryBytime(caozuo);
                int sum = 0;
                System.out.println(list2.size() + "----------");
                for (xiaoshou caozuo2 : list2) {

                    sum = sum + Integer.valueOf(caozuo2.getSumPrice());
                }
                list.add(sum);
                list1.add(i);
            }
        }
        return list;
    }
    
    
  //财务折线图点击具体的点查看具体的datagrid数据
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
        //如果month为空   x+1就为当前月份  判断小于9 前面加-
        if (month == "") {
            if (Integer.valueOf(x) <= 8) {
                time = year + "-0" + String.valueOf(Integer.valueOf(x) + 1);
            } else {
                time = year + "-" + String.valueOf(Integer.valueOf(x) + 1);
            }
        } 
        //month不为空 x+1就是当前号数，判断month小于9  号小于9
        else {
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
        //上段代码算出时间,下段判断是收入还是支出
        if (tiaojian.equals("收入")) {
            xiaoshou xiaoshou = new xiaoshou();
            System.out.println(time);
            xiaoshou.setChukuTime(time);
            xiaoshou.setUsername(renyuan);
            List<xiaoshou> list = xiaoshouServiceImpl.queryBytime(xiaoshou);

            shourucaiwuxiangxi(list, caozuo, json);
        } else if (tiaojian.equals("支出")) {
            System.out.println(time);
            caozuo caozuo1 = new caozuo();
            caozuo1.setUsername(renyuan);
            caozuo1.setLukuTime(time);
            List<caozuo> list = caozuoServiceImpl.queryBytime(caozuo1);
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

    
  //货物统计报表
    @RequestMapping(value = "/goods1")
    @ResponseBody
    public JSONArray goods1(HttpServletResponse response,
            HttpServletRequest request) {
        kucun kucun = new kucun();
        kucun.setWarehouseName(request.getParameter("warehouseName"));
        kucun.setCurrentnum(0);
        kucun.setRows(kucunserviceImpl.querycount(kucun));
        JSONArray jsonArray = new JSONArray();
        double sum = 0;
        int sum1 = 0;
        List<kucun> list = kucunserviceImpl.query(kucun);
        for (kucun kucun1 : list) {
            sum = sum + Integer.valueOf(kucun1.getKucunNumber());
        }
        for (kucun kucun1 : list) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", kucun1.getKucunId());

            double y = Double.valueOf(kucun1.getKucunNumber()) / sum * 100;
            jsonObject.put("y", y);
            jsonArray.add(jsonObject);
            sum1 = 0;
        }

        return jsonArray;
    }

}
