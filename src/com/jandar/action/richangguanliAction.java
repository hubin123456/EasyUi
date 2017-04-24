package com.jandar.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jandar.pojo.Navigation;
import com.jandar.pojo.Navigation1;
import com.jandar.pojo.User;
import com.jandar.pojo.gongyingshang;
import com.jandar.pojo.goods;
import com.jandar.pojo.kehu;
import com.jandar.pojo.lei;
import com.jandar.pojo.role;
import com.jandar.pojo.warehouse;
import com.jandar.serviceImpl.Navigation1ServiceImpl;
import com.jandar.serviceImpl.NavigationServiceImpl;
import com.jandar.serviceImpl.UserServiceImpl;
import com.jandar.serviceImpl.caozuoServiceImpl;
import com.jandar.serviceImpl.goodsServiceImpl;
import com.jandar.serviceImpl.kehuServiceImpl;
import com.jandar.serviceImpl.leiServiceImpl;
import com.jandar.serviceImpl.roleServiceImpl;
import com.jandar.serviceImpl.warehouseServiceImpl;
import com.jandar.util.ExportExcel;
import com.jandar.util.MD5;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class richangguanliAction {
    @Autowired
    private warehouseServiceImpl warehouseServiceImpl;
    @Autowired
    private roleServiceImpl roleServiceImpl;
    @Autowired
    private NavigationServiceImpl navigationServiceImpl;
    @Autowired
    private Navigation1ServiceImpl navigation1ServiceImpl;
    @Autowired
    private leiServiceImpl leiServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private com.jandar.serviceImpl.gongyingshangServiceImpl gongyingshangServiceImpl;
    @Autowired
    private goodsServiceImpl goodsServiceImpl;
    @Autowired
    private kehuServiceImpl kehuServiceImpl;

  //datagrid显示仓库
    @RequestMapping(value = "/warehouse")
    @ResponseBody
    public JSONObject warehouse(ModelMap map, warehouse warehouse,
            HttpServletRequest request, HttpServletResponse response) {

        warehouse.setCurrentnum(PageUtil.getCurrentnum(warehouse.getPage(),
                warehouse.getRows()));
        JSONObject json = new JSONObject();
        List<warehouse> list = warehouseServiceImpl.query(warehouse);
        json.put("rows", list);
        json.put("total", warehouseServiceImpl.querycount(warehouse));
        return json;
    }

    //combobox仓库
    @RequestMapping(value = "/cangkuselect")
    @ResponseBody
    public JSONArray cangkuselect(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        warehouse warehouse = new warehouse();
        warehouse.setRows(warehouseServiceImpl.querycount(warehouse));
        warehouse.setCurrentnum(0);
        jsonArray = jsonArray.fromObject(warehouseServiceImpl.query(warehouse));
        return jsonArray;
    }

    //添加仓库
    @RequestMapping(value = "/addwarehouse")
    public String addwarehouse(warehouse warehouse,
            HttpServletResponse response, HttpServletRequest request) {
        warehouse.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
        try {
            warehouseServiceImpl.insert(warehouse);
            return "/neirong/richangguanli/cangku";
        } catch (Exception e) {
            return "/neirong/richangguanli/cangku";
        }
    }

    //编辑仓库
    @RequestMapping(value = "/editwarehouse")
    public String editwarehouse(warehouse warehouse,
            HttpServletResponse response, HttpServletRequest request) {
        warehouse.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
        System.out.println(warehouse.getWarehouseCompany() + "--------------");
        try {
            warehouseServiceImpl.update(warehouse);
            return "/neirong/richangguanli/cangku";
        } catch (Exception e) {
            return "/neirong/richangguanli/cangku";
        }
    }
    
    @RequestMapping("/leiselect")
    @ResponseBody
    public JSONArray leiselect(HttpServletRequest request,
            HttpServletResponse response, ModelMap map, lei lei) {

        JSONArray jsonArray = JSONArray
                .fromObject(leiServiceImpl.query1(new lei()));
        return jsonArray;

    }
    

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
            String url = jsonArray.getJSONObject(i).get("url").toString();
         
            if(navigation1.equals("")){
            }else{
            Navigation1 Navigation1 = new Navigation1();
            //给navigation赋值
            Navigation1.setNavigation1(navigation1);
            Navigation1.setRole(role);
            Navigation1.setNavigationId(navigationId);
            Navigation1.setCreatetime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            Navigation1.setId("");
            Navigation1.setImage("icon-zidaohang");
            Navigation1.setUrl(url);
            navigation1ServiceImpl.insert(Navigation1);
            }
        }
        jsonObject.put("result", "success");
        return jsonObject.toString();
    }
    
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
  //供应商查询
    @RequestMapping(value = "/gongyingshangselect")
    @ResponseBody
    public JSONArray gongyingshangselect(ModelMap map,
            HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        gongyingshang gongyingshang = new gongyingshang();
        gongyingshang
                .setRows(gongyingshangServiceImpl.querycount(gongyingshang));
        gongyingshang.setCurrentnum(0);
        jsonArray = jsonArray
                .fromObject(gongyingshangServiceImpl.query(gongyingshang));
        System.out.println(jsonArray);
        return jsonArray;
    }

    //创建供应商
    @RequestMapping(value = "/createSupplier")
    @ResponseBody
    public String createSupplier(ModelMap map, gongyingshang gongyingshang,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            gongyingshang.setCreateTime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            gongyingshangServiceImpl.insert(gongyingshang);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    //编辑供应商
    @RequestMapping(value = "/editSupplier")
    @ResponseBody
    public String editSupplier(ModelMap map, gongyingshang gongyingshang,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            gongyingshang.setCreateTime(
                    TimeUtil.timeToString(TimeUtil.currentTime()));
            gongyingshangServiceImpl.update(gongyingshang);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    //datagrid供应商
    @RequestMapping(value = "/gongyingshang")
    @ResponseBody
    public JSONObject gongyingshang(ModelMap map, gongyingshang gongyingshang,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        gongyingshang.setCount(
                gongyingshangServiceImpl.querycount(new gongyingshang()));
        gongyingshang.setCurrentnum(PageUtil.getCurrentnum(
                gongyingshang.getPage(), gongyingshang.getRows()));
        List<gongyingshang> list = gongyingshangServiceImpl
                .query(gongyingshang);
        json.put("rows", list);
        json.put("total", gongyingshang.getCount());
        return json;
    }
    
    //供应商导出
    @RequestMapping(value = "/ gongyingshangExport")
    public void gongyingshangExport(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        gongyingshang gongyingshang = new gongyingshang();
        gongyingshang.setCurrentnum(0);
        gongyingshang.setRows(
                gongyingshangServiceImpl.querycount(new gongyingshang()));
        List<gongyingshang> list = gongyingshangServiceImpl
                .query(gongyingshang);
        String[] header = new String[] { "供应商名称", "联系方式", "地址", "创建时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (gongyingshang gongyingshang1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("供应商名称", gongyingshang1.getSupplierName());
            map1.put("联系方式", gongyingshang1.getSupplierPhone());
            map1.put("地址", gongyingshang1.getSupplierAddress());
            map1.put("创建时间", gongyingshang1.getCreateTime());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, "供应商信息表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
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

    
    //combobox 显示客户
    @RequestMapping(value = "/kehuselect")
    @ResponseBody
    public JSONArray kehushangselect(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        kehu kehu = new kehu();
        kehu.setRows(kehuServiceImpl.querycount(kehu));
        kehu.setCurrentnum(0);
        jsonArray = jsonArray.fromObject(kehuServiceImpl.query(kehu));
        System.out.println(jsonArray);
        return jsonArray;
    }

    //创建客户
    @RequestMapping(value = "/createKehu")
    @ResponseBody
    public String createKehu(ModelMap map, kehu kehu,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kehu.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
            System.out.println(kehu.getKehuAddress() + kehu.getKehuName()
                    + kehu.getKehuPhone() + kehu.getCreateTime());
            kehuServiceImpl.insert(kehu);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    //编辑客户
    @RequestMapping(value = "/editKehu")
    @ResponseBody
    public String editKehu(ModelMap map, kehu kehu, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            kehu.setCreateTime(TimeUtil.timeToString(TimeUtil.currentTime()));
            kehuServiceImpl.update(kehu);
            jsonObject.put("result", 0);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result", 1);
            return jsonObject.toString();
        }
    }

    //datagrid显示客户
    @RequestMapping(value = "/kehu")
    @ResponseBody
    public JSONObject gongyingshang(ModelMap map, kehu kehu,
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        kehu.setCount(kehuServiceImpl.querycount(new kehu()));
        kehu.setCurrentnum(
                PageUtil.getCurrentnum(kehu.getPage(), kehu.getRows()));
        List<kehu> list = kehuServiceImpl.query(kehu);
        json.put("rows", list);
        json.put("total", kehu.getCount());
        return json;
    }

    //客户导出
    @RequestMapping(value = "/kehuExport")
    public void kehuExport(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) {
        kehu kehu = new kehu();
        kehu.setCurrentnum(0);
        kehu.setRows(kehuServiceImpl.querycount(new kehu()));
        List<kehu> list = kehuServiceImpl.query(kehu);
        String[] header = new String[] { "客户名称", "联系方式", "地址", "创建时间" };
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        for (kehu kehu1 : list) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("客户名称", kehu1.getKehuName());
            map1.put("联系方式", kehu1.getKehuPhone());
            map1.put("地址", kehu1.getKehuAddress());
            map1.put("创建时间", kehu1.getCreateTime());
            list1.add(map1);
        }
        try {
            ExportExcel.Export(list1, response, "客户信息表", header);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
