package com.jandar.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtil {
	
	public static Calendar calendar = Calendar.getInstance();
	public static Long currentTime(){
		calendar.setTime(new Date());
		return calendar.getTimeInMillis();
	}
	
	public static String timeToString(Long time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}
	
}
