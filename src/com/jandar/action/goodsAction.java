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

    //datagrid分页
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

    //编辑商品
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

    //添加 上传
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

    //下架
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

    //查询
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
   
    //combobox 货物
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

  
 //货物饼图
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

    
}
