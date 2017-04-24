package com.jandar.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.Navigation;
import com.jandar.pojo.Navigation1;
import com.jandar.pojo.User;
import com.jandar.pojo.caozuo;
import com.jandar.pojo.kucun;
import com.jandar.pojo.warehouse;
import com.jandar.service.NavigationService;
import com.jandar.serviceImpl.Navigation1ServiceImpl;
import com.jandar.serviceImpl.NavigationServiceImpl;
import com.jandar.serviceImpl.UserServiceImpl;
import com.jandar.serviceImpl.caozuoServiceImpl;
import com.jandar.serviceImpl.goodsServiceImpl;
import com.jandar.serviceImpl.warehouseServiceImpl;
import com.jandar.util.MD5;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class UserAction {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private NavigationServiceImpl navigationServiceImpl;

    @Autowired
    private Navigation1ServiceImpl navigation1ServiceImpl;
    @Autowired
    private caozuoServiceImpl caozuoserviceImpl;
    @Autowired
    private warehouseServiceImpl warehouseServiceImpl;
    @Autowired
    private goodsServiceImpl goodsServiceImpl;

    //修改密码
    @RequestMapping(value = "/xiugaimima")
    @ResponseBody
    public String xiugaimima(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        String username = request.getParameter("username");
        String jiumima = request.getParameter("jiumima");
        String xinmima = request.getParameter("xinmima");
        String xinmima1 = request.getParameter("xinmima1");
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5.getMD5String1(jiumima));
        JSONObject jsonObject = new JSONObject();
        if (userServiceImpl.querycount(user) == 0) {
            jsonObject.put("result", "1");
        } else {
            if (xinmima.equals(xinmima1)) {
                user.setPassword(MD5.getMD5String1(xinmima));
                userServiceImpl.init(user);
                jsonObject.put("result", "0");
            } else {
                jsonObject.put("result", "2");
            }
        }
        return jsonObject.toString();

    }

   

    // 人员显示
    @RequestMapping(value = "/renyuanselect")
    @ResponseBody
    public JSONObject renyuanselect(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        user.setCurrentnum(
                PageUtil.getCurrentnum(user.getPage(), user.getRows()));
        //获取全部 移除管理员
        JSONObject json = new JSONObject();
        List<User> list = userServiceImpl.query1(user);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("管理员")) {
                list.remove(i);
            }
        }
        JSONArray jsonArray = new JSONArray();
       // 判断当前结束和集合的大小
        if (user.getRows() + user.getCurrentnum() < list.size()) {
            for (int i = user.getCurrentnum(); i < user.getRows()
                    + user.getCurrentnum(); i++) {
                jsonArray.add(list.get(i));
            }
        } else {
            for (int i = user.getCurrentnum(); i < list.size(); i++) {
                jsonArray.add(list.get(i));
            }
        }
        json.put("rows", jsonArray);
        json.put("total", list.size());
        System.out.println(json);
        return json;

    }

    // combobox 启用状态人员显示
    @RequestMapping(value = "/renyuanselect1")
    @ResponseBody
    public JSONArray renyuanselect1(ModelMap model, HttpServletRequest request,
            HttpServletResponse response) {
        User user = new User();
        user.setCurrentnum(0);
        user.setRows(userServiceImpl.querycount(user));
        user.setStatus("启用");
        List<User> list = userServiceImpl.query(user);
        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray);
        return jsonArray;
    }

    // 初始化密码
    @RequestMapping(value = "/init")
    @ResponseBody
    public JSONObject init(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        user.setPassword(MD5.getMD5String1("123456"));
        userServiceImpl.init(user);
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    // 启动账户
    @RequestMapping(value = "/startUser")
    @ResponseBody
    public JSONObject startUser(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        user.setStatus("启用");
        userServiceImpl.init(user);
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    // 停用账户
    @RequestMapping(value = "/stopUser")
    @ResponseBody
    public JSONObject stopUser(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        user.setStatus("停用");
        userServiceImpl.init(user);
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    //账户状态combobox
    @RequestMapping(value = "/statusselect")
    @ResponseBody
    public JSONArray statusselect(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("status", "启用");
        jsonArray.add(jsonObject);
        jsonObject.put("status", "停用");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    // 创建用户
    @RequestMapping(value = "/createUser")
    @ResponseBody
    public String createUser(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            user.setCreatetime(TimeUtil.timeToString(TimeUtil.currentTime()));
            user.setUserImage("images/touxiang.jpg");
            user.setPassword(MD5.getMD5String1(user.getPassword()));
            user.setStatus("启用");
            userServiceImpl.create(user);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

}
