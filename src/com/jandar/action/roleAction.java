package com.jandar.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.Navigation;
import com.jandar.pojo.Navigation1;
import com.jandar.pojo.User;
import com.jandar.pojo.caozuo;
import com.jandar.pojo.role;
import com.jandar.serviceImpl.Navigation1ServiceImpl;
import com.jandar.serviceImpl.NavigationServiceImpl;
import com.jandar.serviceImpl.roleServiceImpl;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.org.mozilla.javascript.internal.LazilyLoadedCtor;

@Controller
public class roleAction {
    @Autowired
    private roleServiceImpl roleServiceImpl;
    @Autowired
    private NavigationServiceImpl navigationServiceImpl;
    @Autowired
    private Navigation1ServiceImpl navigation1ServiceImpl;

    @RequestMapping(value = "/role")
    @ResponseBody
    public JSONObject select(HttpServletResponse response,
            HttpServletRequest request, ModelMap map, role role) {
        role.setCurrentnum(
                PageUtil.getCurrentnum(role.getPage(), role.getRows()));

        JSONObject json = new JSONObject();
        List<role> list = roleServiceImpl.select1(role);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("管理员")) {
                list.remove(i);
            }
        }
        JSONArray jsonArray = new JSONArray();
        if (role.getRows() + role.getCurrentnum() < list.size()) {
            for (int i = role.getCurrentnum(); i < role.getRows()
                    + role.getCurrentnum(); i++) {
                jsonArray.add(list.get(i));
            }
        } else {
            for (int i = role.getCurrentnum(); i < list.size(); i++) {
                jsonArray.add(list.get(i));
            }
        }
        json.put("rows", jsonArray);
        json.put("total", list.size());
        System.out.println(json);
        return json;
    }

    @RequestMapping(value = "/createRole")
    @ResponseBody
    public String createRole(HttpServletResponse response,
            HttpServletRequest request, ModelMap map, role role) {
        JSONObject jsonObject = new JSONObject();
        try {
            roleServiceImpl.insert(role);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/roleselect")
    @ResponseBody
    public JSONArray kucunselect(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        role role = new role();
        role.setRows(roleServiceImpl.querycount(role));
        role.setCurrentnum(0);
        List<role> list = roleServiceImpl.select(role);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("管理员")) {
                list.remove(i);
            }
        }
        jsonArray = jsonArray.fromObject(list);
        System.out.println(jsonArray);
        return jsonArray;
    }

    @RequestMapping(value = "/quanxian1")
    @ResponseBody
    public String quanxian(ModelMap model, role role,
            HttpServletRequest request, HttpServletResponse response) {
        Navigation navigation = new Navigation();
        Navigation1 navigation1 = new Navigation1();
        JSONArray jsonArray = new JSONArray();
        List<Navigation> list = navigationServiceImpl.query(navigation);
        for (Navigation list1 : list) {
            navigation1.setNavigationId(list1.getNavigationId());
            navigation1.setRole(role.getRole());
            List<Navigation1> list2 = navigation1ServiceImpl.query(navigation1);
            JSONObject jsonObject = new JSONObject();
            if (list2.size() != 0) {
                jsonObject.put("navigation", list1.getNavigation());
                jsonObject.put("navigation1", list2);
                jsonArray.add(jsonObject);
            }
        }
        System.out.println(jsonArray.toString());
        return jsonArray.toString();
    }

    @RequestMapping(value = "/fuyuquanxian")
    @ResponseBody
    public String fuyuquanxian(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONArray
                .fromObject(request.getParameter("quanxian"));
        Navigation1 Navigation2 = new Navigation1();
        Navigation2.setRole(jsonArray.getJSONObject(0).get("role").toString());
        navigation1ServiceImpl.delete(Navigation2);
        for (int i = 0; i < jsonArray.size(); i++) {
            Navigation navigation = new Navigation();
            System.out.println(
                    jsonArray.getJSONObject(i).get("navigation").toString());
            navigation.setNavigation(
                    jsonArray.getJSONObject(i).get("navigation").toString());
            int navigationId = navigationServiceImpl.query(navigation).get(0)
                    .getNavigationId();
            String navigation1 = jsonArray.getJSONObject(i).get("navigation1")
                    .toString();
            String role = jsonArray.getJSONObject(i).get("role").toString();
            Navigation1 Navigation1 = new Navigation1();
            System.out.println(navigationId + "----------");
            Navigation1.setNavigation1(navigation1);
            Navigation1.setRole(role);
            Navigation1.setNavigationId(navigationId);
            Navigation1.setCreatetime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            Navigation1.setId("");
            Navigation1.setImage("icon-zidaohang");
            Navigation1.setUrl("");
            navigation1ServiceImpl.insert(Navigation1);

        }
        jsonObject.put("result", "success");
        return jsonObject.toString();
    }
}
