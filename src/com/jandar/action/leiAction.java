package com.jandar.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.lei;
import com.jandar.pojo.role;
import com.jandar.serviceImpl.leiServiceImpl;
import com.jandar.util.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class leiAction {
    @Autowired
    private leiServiceImpl leiServiceImpl;

    @RequestMapping("/leiselect")
    @ResponseBody
    public JSONArray leiselect(HttpServletRequest request,
            HttpServletResponse response, ModelMap map, lei lei) {

        JSONArray jsonArray = JSONArray
                .fromObject(leiServiceImpl.query1(new lei()));
        return jsonArray;

    }

    @RequestMapping(value = "/createLei")
    @ResponseBody
    public String createLei(HttpServletResponse response,
            HttpServletRequest request, ModelMap map, lei lei) {
        JSONObject jsonObject = new JSONObject();
        try {
            leiServiceImpl.insert(lei);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    @RequestMapping("/leiselect1")
    @ResponseBody
    public JSONObject leiselect1(HttpServletRequest request, lei lei,
            HttpServletResponse response, ModelMap map) {
        lei.setCurrentnum(PageUtil.getCurrentnum(lei.getPage(), lei.getRows()));

        JSONObject json = new JSONObject();
        List<lei> list = leiServiceImpl.query(lei);
        json.put("rows", list);
        json.put("total", leiServiceImpl.querycount(new lei()));
        System.out.println(json);
        return json;
    }
}
