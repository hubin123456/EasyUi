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

    //datagrid显示角色列表
    @RequestMapping(value = "/role")
    @ResponseBody
    public JSONObject select(HttpServletResponse response,
            HttpServletRequest request, ModelMap map, role role) {
        role.setCurrentnum(
                PageUtil.getCurrentnum(role.getPage(), role.getRows()));

        JSONObject json = new JSONObject();
        //获取全部角色集合,移除管理员。
        List<role> list = roleServiceImpl.select1(role);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("管理员")) {
                list.remove(i);
            }
        }
        JSONArray jsonArray = new JSONArray();
        //判断最后的个数是否大于获取的角色集合 小于 循环到最后的个数  大于 循环到集合的最后
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

    //创建角色
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

     //combobox显示角色 除了管理员
    @RequestMapping(value = "/roleselect")
    @ResponseBody
    public JSONArray kucunselect(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        role role = new role();
        role.setRows(roleServiceImpl.querycount(role));
        role.setCurrentnum(0);
        List<role> list = roleServiceImpl.select(role);
        //循环移除管理员
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("管理员")) {
                list.remove(i);
            }
        }
        jsonArray = jsonArray.fromObject(list);
        System.out.println(jsonArray);
        return jsonArray;
    }

    //获取角色对应的权限集合
    @RequestMapping(value = "/quanxian1")
    @ResponseBody
    public String quanxian(ModelMap model, role role,
            HttpServletRequest request, HttpServletResponse response) {
        Navigation navigation = new Navigation();
        Navigation1 navigation1 = new Navigation1();
        JSONArray jsonArray = new JSONArray();
        //获取父导航集合
        List<Navigation> list = navigationServiceImpl.query(navigation);
        for (Navigation list1 : list) {
            //获取该角色每个父导航对应的子导航
            navigation1.setNavigationId(list1.getNavigationId());
            navigation1.setRole(role.getRole());
            List<Navigation1> list2 = navigation1ServiceImpl.query(navigation1);
            JSONObject jsonObject = new JSONObject();
            //当子导航不为空时,添加对应的父导航和子导航到jsonarray
            if (list2.size() != 0) {
                jsonObject.put("navigation", list1.getNavigation());
                jsonObject.put("navigation1", list2);
                jsonArray.add(jsonObject);
            }
        }
        System.out.println(jsonArray.toString());
        return jsonArray.toString();
    }
   
    //赋予权限
    @RequestMapping(value = "/fuyuquanxian")
    @ResponseBody
    public String fuyuquanxian(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        //获取传过来的权限数组
        JSONArray jsonArray = JSONArray
                .fromObject(request.getParameter("quanxian"));
        //先删除角色原本对应的权限
        Navigation1 Navigation2 = new Navigation1();
        Navigation2.setRole(jsonArray.getJSONObject(0).get("role").toString());
        navigation1ServiceImpl.delete(Navigation2);
        //循环权限数组,插入数据库
        for (int i = 0; i < jsonArray.size(); i++) {
            Navigation navigation = new Navigation();
            navigation.setNavigation(
                    jsonArray.getJSONObject(i).get("navigation").toString());
            //获取父导航Id,子导航,角色名
            int navigationId = navigationServiceImpl.query(navigation).get(0)
                    .getNavigationId();
            String navigation1 = jsonArray.getJSONObject(i).get("navigation1")
                    .toString();
            String role = jsonArray.getJSONObject(i).get("role").toString();
            Navigation1 Navigation1 = new Navigation1();
            //给navigation赋值
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
