package com.jandar.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;    
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;
   
    
public class FileUploadUtil {      
       
    public static FTPClient ftp;      
    /**  
     *   
     * @param path 上传到ftp服务器哪个路径下     
     * @param addr 地址  
     * @param port 端口号  
     * @param username 用户名  
     * @param password 密码  
     * @return  
     * @throws Exception  
     */    
    public  static boolean connect(String path,String addr,int port,String username,String password) throws Exception {      
        boolean result = false;      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();      
            return result;      
        }      
        ftp.changeWorkingDirectory(path);      
        result = true;      
        return result;      
    }     
    
    
    public static Boolean uploadFile(HttpServletRequest request, MultipartFile file) {  
        System.out.println("开始");  
        String path = "E://ftp";  
        String fileName = file.getOriginalFilename();  
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if (!targetFile.exists()) {  
            targetFile.mkdirs();  
        }  
        // 保存  
        try {  
            file.transferTo(targetFile);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
 
   }  
    /**  
     *   
     * @param file 上传的文件或文件夹  
     * @throws Exception  
     */    
    public static void upload(MultipartFile[] files, String currentTime, HttpServletRequest request) throws Exception{  
    	
//    	    System.out.println(currentTime);
//            ftp.makeDirectory(currentTime);                
//            ftp.changeWorkingDirectory(currentTime);  
//           for(MultipartFile file:files){
//        	   System.out.println(file.getSize());
//               ftp.storeFile(file.getOriginalFilename(), file.getInputStream());      
//            }
    }      
    
    public static void Download(String path, String path1) { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 

        try { 
            ftpClient.connect("192.68.67.124",21); 
            ftpClient.login("test", "123456"); 

            String remoteFileName = "/home/test/struts2" + path; 
            fos = new FileOutputStream(path1); 
            ftpClient.setBufferSize(1024); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.retrieveFile(remoteFileName, fos); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 

    public static InputStream getFile(String fileName)
    {
     try 
     {  
	    return ftp.retrieveFileStream(fileName);
	  }
     catch (IOException e) {
//		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
     
    }
    
    public static int getFileLength(String fileName)
    {
     String aString = fileName.substring(0,fileName.indexOf("/"));
     String bString = fileName.substring(fileName.indexOf("/")+1, fileName.length());
     System.out.println(aString+"==="+bString);
     try {
			FTPFile[] files = ftp.listFiles(aString);
	        for(FTPFile file : files){
	        	if(bString.equals(file.getName()))
	        	{System.out.println(file.getSize());
	        	return (int)file.getSize();
	        	}
	        }
		
	} catch (IOException e) {
//		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	return 0;
    }
   public static void main(String[] args) throws Exception{    
     FileUploadUtil t = new FileUploadUtil();    
      t.connect("","192.68.82.150", 21, "root", "123456");    
     // t.getFile("1481270607226/aa1.zip");
//      t.upload(file);  
//      
      //testDownload();
   }    
   
}    
