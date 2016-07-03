package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class GetDate {
	private static SimpleDateFormat sFormat ;

	public static String getTodaty() {
		Calendar calendar = Calendar.getInstance();
		sFormat = new SimpleDateFormat(
				"yyyy年MM月dd日");
		return sFormat.format(calendar.getTime());
	}
	public static String getTime(long milliseconds) {
		sFormat = new SimpleDateFormat(
				"MM/dd    HH:mm");
		return sFormat.format(new Date(milliseconds));
	}
	public static String getWeek() {
		Calendar calendar = Calendar.getInstance();
		sFormat = new SimpleDateFormat(
				"MM月dd日");
		calendar.setTimeInMillis(System.currentTimeMillis());
		  int s = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		  if (s == 0)
		   s = 7;
		  calendar.add(Calendar.DATE, -s + 1);
		long start = calendar.getTime().getTime();
		
		int e = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		  if (e == 0)
		   e = 7;
		  calendar.add(Calendar.DATE, -e + 7);
		long end = calendar.getTime().getTime();
//		Log.d("test2 time--", sFormat.format(start)+"  m "+sFormat.format(end)+ " s ");
		return (sFormat.format(start)+"-"+sFormat.format(end));
	}
	public static long getLastDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		long sunday=0;
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.WEEK_OF_MONTH, 0);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return sunday;
	}
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		sFormat = new SimpleDateFormat(
				"MM月dd日");
		String start;
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		start=sFormat.format(calendar.getTime());
		String end;
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		end=sFormat.format(calendar.getTime());
		return (start+"-"+end);
	}
	public static boolean putOfToday(Context context,long time) {
		Calendar calendar = Calendar.getInstance();
		if (SharedUtil.getLong(context, AppGlobal.TODAY_TIME)==0) {
			SharedUtil.putLong(context, AppGlobal.TODAY_TIME, time);
		}
		calendar.setTimeInMillis(SharedUtil.getLong(context, AppGlobal.TODAY_TIME));
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	     
	    Date start = calendar.getTime();
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    calendar.add(Calendar.SECOND, -1);
	     
	    Date end = calendar.getTime();
//	    Log.d("test2 time--t", sFormat.format(start)+"  s "+sFormat.format(end)+ " e "+sFormat.format(SharedUtil.getLong(context, AppGlobal.TODAY_TIME)));
	    if (time>=start.getTime()&&time<=end.getTime()) {
			return true;
		} else {
			SharedUtil.putLong(context, AppGlobal.TODAY_TIME, time);
			return false;
		}
	}
	public static boolean putOfWeek(Context context,long time) {
		Calendar calendar =Calendar.getInstance();
		if (SharedUtil.getLong(context, AppGlobal.WEEK_TIME)==0) {
			SharedUtil.putLong(context, AppGlobal.WEEK_TIME, time);
		}
		calendar.setTimeInMillis(SharedUtil.getLong(context, AppGlobal.WEEK_TIME));
//		calendar.add(Calendar.WEEK_OF_MONTH, 0);
//		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		long start = calendar.getTime().getTime();
//		
//		calendar.add(Calendar.WEEK_OF_MONTH, 1);
//		calendar.add(Calendar.MILLISECOND, -1);
//		long end = calendar.getTime().getTime();
		
		  int s = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		  if (s == 0)
		   s = 7;
		  calendar.add(Calendar.DATE, -s + 1);
		  calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		long start = calendar.getTime().getTime();
		
		int e = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		  if (e == 0)
		   e = 7;
		  calendar.add(Calendar.DATE, -e + 8);
		  calendar.add(Calendar.MILLISECOND, -1);
		long end = calendar.getTime().getTime();
		
		
		
//		Log.d("test2 time--w", sFormat.format(start)+"  m "+sFormat.format(end)+ " s "+sFormat.format(SharedUtil.getLong(context, AppGlobal.WEEK_TIME)));
		if (time>=start&&time<=end) {
			return true;
		}else {
			SharedUtil.putLong(context, AppGlobal.WEEK_TIME, time);
			return false;
		} 
	}
	public static boolean putOfMonth(Context context,long time) {
		Calendar calendar = Calendar.getInstance();
		if (SharedUtil.getLong(context, AppGlobal.MONTH_TIME)==0) {
			SharedUtil.putLong(context, AppGlobal.MONTH_TIME, time);
		}
		calendar.setTimeInMillis(SharedUtil.getLong(context, AppGlobal.MONTH_TIME));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		//将分钟至0
		calendar.set(Calendar.MINUTE, 0);
		//将秒至0
		calendar.set(Calendar.SECOND,0);
		//将毫秒至0
		calendar.set(Calendar.MILLISECOND, 0);
		//获得当前月第一天
		long start = calendar.getTime().getTime();
		//将当前月加1；
		calendar.add(Calendar.MONTH, 1);
		//在当前月的下一月基础上减去1毫秒
		calendar.add(Calendar.MILLISECOND, -1);
		//获得当前月最后一天
		long end = calendar.getTime().getTime();
//		Log.d("test2 time--m ", sFormat.format(start)+"  s "+sFormat.format(end)+ " e "+sFormat.format(SharedUtil.getLong(context, AppGlobal.MONTH_TIME)));
		if (time>=start&&time<=end) {
			return true;
		}else {
			SharedUtil.putLong(context, AppGlobal.MONTH_TIME, time);
			return false;
		}
	}
}
