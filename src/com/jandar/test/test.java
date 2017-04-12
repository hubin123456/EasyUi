package com.jandar.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.sun.accessibility.internal.resources.accessibility;
//set list map arrays.sort collections.sort
public class test {
      public static void main(String[] args){
    	  int[] a = new int[]{2,1,3,5,4,7,6};
    	  List<String> arraylist = new ArrayList<String>();
//    	  List<Object> linklist = new LinkedList<Object>();
//    	  Set<Object> treeset = new TreeSet<Object>();
//    	  Set<Object> hashset = new HashSet<Object>();
//    	  Map<String, Object>  hashmap = new HashMap<String, Object>();
//    	  Map<String, Object>  treemap = new TreeMap<String, Object>();
    	  
    	  arraylist.add(0, "222");
    	  arraylist.add(1,"aaa");
    	  arraylist.add(2,"2222");
    	  arraylist.remove(0);
          Arrays.sort(a);
          Collections.sort(arraylist);
    	  Iterator<String>  iterator = arraylist.iterator();
    	  while(iterator.hasNext()){
    		  System.out.println(iterator.next());
    	  }
    	  for(int i = 0; i<a.length; i++){
    		  System.out.println(a[i]);
    	  }
    	 
      }
}
