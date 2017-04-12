package com.jandar.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jandar.pojo.warehouse;
import com.jandar.serviceImpl.warehouseServiceImpl;
import com.jandar.util.PageUtil;
import com.jandar.util.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class warehouseAction {
    @Autowired
    private warehouseServiceImpl warehouseServiceImpl;

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
}
