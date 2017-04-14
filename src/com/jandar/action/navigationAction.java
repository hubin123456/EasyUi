package com.jandar.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.Navigation;
import com.jandar.pojo.Navigation1;
import com.jandar.pojo.User;
import com.jandar.pojo.caozuo;
import com.jandar.pojo.kucun;
import com.jandar.serviceImpl.Navigation1ServiceImpl;
import com.jandar.serviceImpl.NavigationServiceImpl;
import com.jandar.serviceImpl.UserServiceImpl;
import com.jandar.util.MD5;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class navigationAction {
    @Autowired
    private NavigationServiceImpl navigationServiceImpl;
    @Autowired
    private Navigation1ServiceImpl navigation1ServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    // 退出
    @RequestMapping(value = "/exit")
    public String exit(ModelMap model, User user, HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(ModelMap model, User user, HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("=======================");

        user.setCurrentnum(0);

        user.setPassword(MD5.getMD5String1(user.getPassword()));
        JSONObject jsonObject = new JSONObject();
        user.setRows(userServiceImpl.querycount(user));
        System.out.println(
                user.getPassword() + "--" + userServiceImpl.query(user).size());
        if (userServiceImpl.query(user).size() == 1) {
            if (userServiceImpl.query(user).get(0).getStatus().equals("启用")) {
                model.put("user", userServiceImpl.query(user).get(0));
                HttpSession session = request.getSession();
                session.setAttribute("user",
                        userServiceImpl.query(user).get(0).getUsername());
                session.setAttribute("role",
                        userServiceImpl.query(user).get(0).getRole());
                return "/index/index";
            } else {
                jsonObject.put("result", "该账户已被禁用");
                model.put("result", jsonObject);
                return "/login";
            }
        } else {
            jsonObject.put("result", "用户名密码错误");
            model.put("result", jsonObject);
            return "login";
        }
    }

    @RequestMapping(value = "/treeinit")
    @ResponseBody
    public JSONArray treeinit(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {

        Navigation navigation = new Navigation();
        Navigation1 navigation1 = new Navigation1();

        user.setCurrentnum(0);
        user.setRows(userServiceImpl.querycount(user));
        user.setRole(
                new String(user.getRole().getBytes("ISO-8859-1"), "utf-8"));
        List<Navigation> list = navigationServiceImpl.query(new Navigation());
        JSONArray jsonArray1 = new JSONArray();
        navigation1.setRole(user.getRole());
        for (Navigation list1 : list) {
            JSONArray jsonArray = new JSONArray();
            navigation1.setNavigationId(list1.getNavigationId());
            List<Navigation1> list2 = navigation1ServiceImpl.query(navigation1);
            JSONObject jsonObject = new JSONObject();

            if (list2.size() != 0) {
                jsonObject.put("text", list1.getNavigation());
                for (Navigation1 list3 : list2) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("text", list3.getNavigation1());
                    jsonObject1.put("iconCls", list3.getImage());
                  
                    jsonArray.add(jsonObject1);
                }
                jsonObject.put("children", jsonArray);
                jsonObject.put("state", "closed");
                jsonObject.put("iconCls", list1.getImage());
                jsonArray1.add(jsonObject);
            }

        }

        System.out.println(jsonArray1);
        return jsonArray1;
    }

    // 入库管理
    @RequestMapping(value = "/jinghuo")
    public String jinghuo(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/kucunguanli/jinghuo";
    }

    // 出库管理
    @RequestMapping(value = "/chuhuo11")
    public String chuhuo(ModelMap model, caozuo caozuo,
            HttpServletRequest request, HttpServletResponse response) {
        return "/neirong/kucunguanli/chuhuo";
    }

    // 库存管理
    @RequestMapping(value = "/kucun")
    public String kucun(ModelMap model, kucun goods, HttpServletRequest request,
            HttpServletResponse response) {

        return "/neirong/kucunguanli/kucun";
    }

    // 仓库管理
    @RequestMapping(value = "/cangku")
    public String guanli(ModelMap model, User user, HttpServletRequest request,
            HttpServletResponse response) {

        return "/neirong/richangguanli/cangku";
    }

    // 人员管理
    @RequestMapping(value = "/renyuan")
    public String renyuan(ModelMap model, User user, HttpServletRequest request,
            HttpServletResponse response) {

        return "/neirong/richangguanli/renyuan";
    }

    // 采购订单
    @RequestMapping(value = "/caigoudingdan")
    public String cangoudingdan(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/gongyingguanli/caigoudingdan";
    }

    // 采购入库单
    @RequestMapping(value = "/caigoulukudan")
    public String caigoulukudan(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/gongyingguanli/caigoulukudan";
    }

    // 采购退货单
    @RequestMapping(value = "/caigoutuihuodan")
    public String caigoutuihuodan(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/gongyingguanli/caigoutuihuodan";
    }

    // 权限管理
    @RequestMapping(value = "/gongyingshangjiaoyimingxi")
    public String gongyingshangjiaoyimingxi(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/gongyingguanli/gongyingshangjiaoyi";
    }

    @RequestMapping(value = "/gongyingshangguanli")
    public String gongyingshangguanli(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/gongyingguanli/gongyingshangguanli";
    }

    @RequestMapping(value = "/baobiao")
    public String baobiao(ModelMap model, User user, HttpServletRequest request,
            HttpServletResponse response) {

        return "/neirong/baobiao";
    }

    @RequestMapping(value = "/canshu")
    public String canshu(ModelMap model, User user, HttpServletRequest request,
            HttpServletResponse response) {

        return "/neirong/canshu";
    }

    @RequestMapping(value = "/kehuguanli")
    public String kehuguanli(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/richangguanli/kehu";
    }

    @RequestMapping(value = "kucunyujing")
    public String kucunyujing(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/kucunguanli/kucunyujing";
    }

    @RequestMapping(value = "caiwutongjibaobiao")
    public String caiwutongjibaobiao(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/caiwutongjibaobiao";
    }

    @RequestMapping(value = "kehudingdan")
    public String kehudingdan(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/xiaoshouguanli/kehudingdan";
    }

    @RequestMapping(value = "kehujiaoyimingxi")
    public String kehujiaoyimingxi(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/xiaoshouguanli/kehujiaoyimingxi";
    }

    @RequestMapping(value = "tijiaodingdan")
    public String tijiaodingdan(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {

        return "/neirong/xiaoshouguanli/tijiaodingdan";
    }

    @RequestMapping(value = "kucunpandian1")
    public String kucunpandian(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println("====");
        return "/neirong/kucunguanli/kucunpandian";
    }

    @RequestMapping(value = "shangpingguanli")
    public String shangpingguanli(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println("====");
        return "/neirong/richangguanli/shangpingguanli";
    }

    @RequestMapping(value = "leiguanli")
    public String leiguanli(ModelMap model, User user,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println("====");
        return "/neirong/richangguanli/leiguanli";
    }
}
