package com.jandar.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jandar.util.WDWUtil;

public class ReadExcel {
    
    //总行数
    private int totalRows = 0;  
   
    //总条数
    private int totalCells = 0; 
    
    //错误信息接收器
    private String errorMsg;
          
    //构造方法
    public ReadExcel(){}
    
    //得到总行数
    public int getTotalRows()  { return totalRows;} 
    
    //得到总列数
    public int getTotalCells() {  return totalCells;} 
    
    public String getErrorInfo() { return errorMsg; }  
    
  /**
   * 描述：验证EXCEL文件
   * @param filePath
   * @return
   */
  public boolean validateExcel(String filePath){
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
            errorMsg = "文件名不是excel格式";  
            return false;  
        }  
        return true;
  }
  public List<caozuo> getExcelInfo(String fileName,MultipartFile Mfile){
      
      //把spring文件上传的MultipartFile转换成File
       CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; 
       DiskFileItem fi = (DiskFileItem)cf.getFileItem();
       File file = fi.getStoreLocation(); 
       
      List<caozuo> caozuo=new ArrayList<caozuo>();
      InputStream is = null;  
      try{
          //验证文件名是否合格
          if(!validateExcel(fileName)){
              return null;
          }
          //判断文件时2003版本还是2007版本
          boolean isExcel2003 = true; 
          if(WDWUtil.isExcel2007(fileName)){
              isExcel2003 = false;  
          }
          is = new FileInputStream(file);
           caozuo = getExcelInfo(is, isExcel2003); 
          is.close();
      }catch(Exception e){
          e.printStackTrace();
      }
      finally{
          if(is !=null)
          {
              try{
                  is.close();
              }catch(IOException e){
                  is = null;    
                  e.printStackTrace();  
              }
          }
      }
      return caozuo;
  }
  /**
   * 此方法两个参数InputStream是字节流。isExcel2003是excel是2003还是2007版本
   * @param is
   * @param isExcel2003
   * @return
   * @throws IOException
   */
  public  List<caozuo> getExcelInfo(InputStream is,boolean isExcel2003){
      
       List<caozuo> caozuo=null;
       try{
           /** 根据版本选择创建Workbook的方式 */
           Workbook wb = null;
           //当excel是2003时
           if(isExcel2003){
               wb = new HSSFWorkbook(is); 
           }
           else{
        	  // wb = new XSSFWorkbook(is); 
           }
           caozuo = readExcelValue(wb);
           System.out.println( caozuo.size()+"-----------------");
       }
       catch (IOException e)  {  
           e.printStackTrace();  
       }  
       return caozuo;
  }
  /**
   * 读取Excel里面的信息
   * @param wb
   * @return
   */
  private List<caozuo> readExcelValue(Workbook wb){ 
       //得到第一个shell  
       Sheet sheet=wb.getSheetAt(0);
       
       //得到Excel的行数
       this.totalRows=sheet.getPhysicalNumberOfRows();
       
       //得到Excel的列数(前提是有行数)
       if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
       }
       
       List<caozuo> caozuo=new ArrayList<caozuo>();
       caozuo caozuo1;            //用户bean（组成：UserInfo+UserLogin）
       
       //循环Excel行数,从第二行开始。标题不入库
       for(int r=1;r<totalRows;r++)
       {
           Row row = sheet.getRow(r);
           if (row == null) continue;
           
           caozuo1 = new caozuo();
           //循环Excel的列
           for(int c = 0; c <this.totalCells; c++)
           {    
               Cell cell = row.getCell(c); 
               if (null != cell)  
               {
            	   
            	   //if(c==0){cell.setCellType(Cell.CELL_TYPE_STRING);System.out.println(cell.getStringCellValue());caozuo1.setGoodsId(Integer.valueOf(cell.getStringCellValue()));}
            	   //else if(c==1){cell.setCellType(Cell.CELL_TYPE_STRING);caozuo1.setWarehouseName(cell.getStringCellValue());}
            	   //else if(c==2){cell.setCellType(Cell.CELL_TYPE_STRING);caozuo1.setCaozuoId(Integer.valueOf(cell.getStringCellValue()));}
            	  // else if(c==3){cell.setCellType(Cell.CELL_TYPE_STRING);caozuo1.setCaozuoNumber(cell.getStringCellValue());}
            	   
               }
                   
       }
           caozuo.add(caozuo1); 
       }
       return caozuo;
  }
}