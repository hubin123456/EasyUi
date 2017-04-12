package com.jandar.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.jandar.pojo.caozuo;


public class ExportExcel {
    public static void Export(List<Map<String, Object>> list,HttpServletResponse response,String sheetName, String[] headers) throws Exception  
    {  
    // 第一步，创建一个webbook，对应一个Excel文件  
    HSSFWorkbook wb = new HSSFWorkbook();  
    // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
   
    HSSFSheet sheet = wb.createSheet(sheetName);  
    HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
    // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
    HSSFRow row = sheet.createRow((int) 0);  
    // 第四步，创建单元格，并设置值表头 设置表头居中  
    HSSFCellStyle style = wb.createCellStyle();  
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
   
    HSSFFont font = wb.createFont();
    //      font.setColor(HSSFColor.BLACK.index);
    font.setColor(IndexedColors.BLUE.getIndex());
    font.setFontName("宋体"); //设置字体
    font.setFontHeightInPoints((short) 11); //设置字号
    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //设置字体样式 正常显示
    style.setFont(font);
    //style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
    //style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
    //style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
    //style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
   
    HSSFRow row1 = sheet.createRow(0);
    for (short i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
        HSSFCell cell = row1.createCell(i);
        cell.setCellStyle(style);
        HSSFRichTextString text = new HSSFRichTextString(headers[i]);
        cell.setCellValue(text);
    }

    // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
   
    //Field[] fields = t.getClass().getDeclaredFields();
    for(int i=0;i<list.size();i++){
        HSSFRow row2 = sheet.createRow(i+1);
        Map<String, Object> map = list.get(i);
       
        
       // cell1.setCellStyle(style);
        //拿到第一个数据
        for(int i1=0; i1<headers.length; i1++){
            HSSFCell cell = row2.createCell(i1);
            sheet.autoSizeColumn(i1);
            cell.setCellStyle(style);
            //遍历字段进行顺序赋值
            Object value = map.get(headers[i1]);
            // 判断值的类型后进行强制类型转换
            String textValue = null;
            if (value instanceof Boolean) {
                boolean bValue = (Boolean) value;
                textValue = "男";
                if (!bValue) {
                    textValue = "女";
                }
            } else if (value instanceof Date) {
                Date date = (Date) value;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                textValue = sdf.format(date);
            } else {
                // 其它数据类型都当作字符串简单处理
                textValue = value.toString();
            }
            // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
            if (textValue != null) {
                Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                Matcher matcher = p.matcher(textValue);
                if (matcher.matches()) {
                    // 是数字当作double处理
                   
                    cell.setCellValue(Double.parseDouble(textValue));
                } else {
                   
                    cell.setCellValue(textValue);
                }
            }}
        }
    // 第六步，将文件存到指定位置  
   
        response.reset();     
        response.setContentType("application/msexcel"); 
        String fileName= sheetName;
        response.setHeader("Content-Disposition","attachment;filename="+new String( fileName.getBytes("gb2312"), "ISO8859-1" )+".xls");     
         ServletOutputStream outStream = null;     
            
       try{     
           outStream = response.getOutputStream();     
           wb.write(outStream);     
          }catch(Exception e)     
          {     
            e.printStackTrace();     
          }finally
          {     
            outStream.close();
          }
    }

    public static void main(String[] args) {
        String[] header = new String[] { "标题一", "标题二", "标题三", "标题四", "标题5" };
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        caozuo caozuo = new caozuo();
        map.put("标题一", "2");
        map.put("标题二", "2");
        map.put("标题三", "2");
        map.put("标题四", "2");
        map.put("标题5", "2");
        list.add(map);
        list.add(map);
        try {
            // ExportExcel.Export(list, header, "D://aa//a.xls");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
