package com.jandar.util;

public class PageUtil {
       public static int getCurrentnum(int currentpage, int pagenum){
    	   int currentnum = (currentpage - 1) * pagenum ;
    	   return currentnum;
       }
}
