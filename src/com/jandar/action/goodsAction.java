package com.jandar.action;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.pojo.goods;
import com.jandar.pojo.kucun;
import com.jandar.serviceImpl.goodsServiceImpl;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;
import com.sun.accessibility.internal.resources.accessibility;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class goodsAction {
    @Autowired
    private com.jandar.serviceImpl.kucunServiceImpl kucunServiceImpl;
    @Autowired
    private goodsServiceImpl goodsServiceImpl;

    @RequestMapping(value = "/goodschaxun")
    @ResponseBody
    public JSONObject gooodschaxun(HttpServletRequest request,
            HttpServletResponse response, goods goods, ModelMap map) {
        goods.setCurrentnum(
                PageUtil.getCurrentnum(goods.getPage(), goods.getRows()));

        System.out.println(goods.getGoodsId());
        goods.setCount(goodsServiceImpl.querycount(goods));

        List<goods> list = goodsServiceImpl.query(goods);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows", list);
        jsonObject.put("total", goods.getCount());
        System.out.println(jsonObject);
        return jsonObject;

    }

    @RequestMapping(value = "/editgoods")

    public String editgoods(HttpServletRequest request,
            HttpServletResponse response, goods goods, ModelMap map,
            @RequestParam("file1") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/") + "images/"
                        + file.getOriginalFilename();
                System.out.println(request.getSession().getServletContext()
                        .getRealPath("/") + file.getOriginalFilename());
                // 转存文件
                file.transferTo(new File(filePath));
                goods.setGoodsImage("images/" + file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        goods.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));

        try {
            goodsServiceImpl.update(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/neirong/richangguanli/shangpingguanli";

    }

    @RequestMapping(value = "/addgoods")

    public String addgoods(HttpServletRequest request,
            HttpServletResponse response, goods goods, ModelMap map,
            @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/") + "images/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        goods.setGoodsImage("images/" + file.getOriginalFilename());
        goods.setGoodsbeizhu("1");
        goods.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
        goods.setStatus("上架");
        try {
            goodsServiceImpl.insert(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/neirong/richangguanli/shangpingguanli";

    }

    @RequestMapping(value = "/goodsxiajia")
    @ResponseBody
    public String gooodsxiajia(HttpServletRequest request,
            HttpServletResponse response, goods goods, ModelMap map) {
        JSONObject jsonObject = new JSONObject();
        try {
            goodsServiceImpl.update(goods);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }

    }

    @RequestMapping(value = "/goodschaxun1")
    @ResponseBody
    public String gooodschaxun1(HttpServletRequest request,
            HttpServletResponse response, goods goods, ModelMap map) {
        goods.setCurrentnum(0);
        goods.setRows(goodsServiceImpl.querycount(goods));
        List<goods> list = goodsServiceImpl.query(goods);
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();

    }

    @RequestMapping(value = "/goodsselect")
    @ResponseBody
    public JSONArray goodsselect(HttpServletRequest request,
            HttpServletResponse response, goods goods, ModelMap map) {
        goods.setCurrentnum(0);
        goods.setRows(goodsServiceImpl.querycount(goods));
        JSONArray jsonArray = JSONArray
                .fromObject(goodsServiceImpl.query(goods));
        return jsonArray;
    }

    @RequestMapping(value = "/goods")
    @ResponseBody
    public JSONArray select(HttpServletResponse response, kucun kucun,
            HttpServletRequest request) {
        kucun.setCurrentnum(0);
        kucun.setRows(kucunServiceImpl.querycount(kucun));
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String[] goodsName = new String[kucunServiceImpl.query(kucun).size()];
        String[] goodsNumber = new String[kucunServiceImpl.query(kucun).size()];
        for (int i = 0; i < kucunServiceImpl.query(kucun).size(); i++) {
            goodsName[i] = kucunServiceImpl.query(kucun).get(i).getGoodsName();
            goodsNumber[i] = kucunServiceImpl.query(kucun).get(i)
                    .getKucunNumber();
        }
        jsonObject.put("goodsName", goodsName);
        jsonObject1.put("goodsNumber", goodsNumber);
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        System.out.println(jsonArray);
        return jsonArray;
    }

    @RequestMapping(value = "/goods1")
    @ResponseBody
    public JSONArray goods1(HttpServletResponse response,
            HttpServletRequest request) {
        kucun kucun = new kucun();
        kucun.setWarehouseName(request.getParameter("warehouseName"));
        kucun.setCurrentnum(0);
        kucun.setRows(kucunServiceImpl.querycount(kucun));
        JSONArray jsonArray = new JSONArray();
        double sum = 0;
        int sum1 = 0;
        List<kucun> list = kucunServiceImpl.query(kucun);
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

    @RequestMapping(value = "/goods2")
    @ResponseBody
    public JSONArray goods2(HttpServletResponse response,
            HttpServletRequest request) {
        kucun kucun = new kucun();
        kucun.setWarehouseName(request.getParameter("warehouseName"));
        kucun.setCurrentnum(0);
        kucun.setRows(kucunServiceImpl.querycount(kucun));
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        List<Integer> arraylist = new ArrayList<Integer>();
        List<String> namelist = new ArrayList<String>();
        double sum = 0;
        int sum1 = 0;
        List<kucun> list = kucunServiceImpl.query(kucun);
        for (kucun kucun1 : list) {
            kucun kucun2 = new kucun();
            kucun2.setGoodsName(kucun1.getGoodsName());
            kucun2.setCurrentnum(0);
            kucun2.setRows(kucunServiceImpl.querycount(new kucun()));
            JSONObject jsonObject = new JSONObject();
            namelist.add(kucun1.getGoodsName());
            for (kucun kucun3 : list) {
                if (Integer.valueOf(kucun1.getKucunId()) == Integer
                        .valueOf(kucun3.getKucunId())) {
                    arraylist.add(Integer.valueOf(kucun3.getKucunNumber()));
                } else {
                    arraylist.add(0);
                }
            }
            jsonObject.put("data", arraylist);
            jsonArray.add(jsonObject);
            sum1 = 0;
            arraylist.clear();
            System.out.println(jsonArray);

        }
        jsonArray1.add(namelist);
        namelist.clear();
        jsonArray1.add(jsonArray);
        return jsonArray1;
    }
}
