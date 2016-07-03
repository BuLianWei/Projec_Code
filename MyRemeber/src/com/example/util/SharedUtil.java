package com.example.util;

import java.text.DecimalFormat;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedUtil {
	public static void putString(Context context, String id, String value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		editor.putString(id, value);
		editor.commit();
	}
	public static void putArrayString(Context context, String id, Set value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		editor.putStringSet(id, value);
		editor.commit();
	}
	public static void putBoolean(Context context, String id, Boolean value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		editor.putBoolean(id, value);
		editor.commit();
	}

	public static String getString(Context context, String id) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(id, "");
	}
	public static Set<String> getArrayString(Context context, String id) {
//		Set< String> set=new TreeSet<String>();
//		set.add("清除全部");
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getStringSet(id,new TreeSet<String>());
	}
	public static boolean getBoolean(Context context, String id) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(id,false);
	}

	public static void putInt(Context context, String id, int value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		editor.putInt(id, value);
		editor.commit();
	}

	public static int getInt(Context context, String id) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(id, 0);
	}

	 public static void putDouble(Context context, String id, double value) {
	 float valuef=(float)(value);
	 SharedPreferences sharedPreference = PreferenceManager
	 .getDefaultSharedPreferences(context);
	 Editor editor = sharedPreference.edit();
	 editor.putFloat(id, valuef);
	 editor.commit();
	 }
	 public static void putLong(Context context, String id, long value) {
		 SharedPreferences sharedPreference = PreferenceManager
				 .getDefaultSharedPreferences(context);
		 Editor editor = sharedPreference.edit();
		 editor.putLong(id, value);
		 editor.commit();
	 }
	public static boolean putToday(Context context, String id, double value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		float valuef = (float)(value);
		boolean isPut = GetDate.putOfToday(context,System.currentTimeMillis());
		if (isPut) {
			float v=SharedUtil.getDouble(context, id);
			editor.putFloat(id, valuef+v);
		} else {
			editor.putFloat(AppGlobal.TODAY_EXPENDITURE, 0);
			editor.putFloat(AppGlobal.TODAY_INCOME, 0);
			editor.putFloat(id, valuef);
		}
		editor.commit();
		return true;
	}

	public static boolean putWeek(Context context, String id, double value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		float valuef = (float)(value);
		boolean isPut=GetDate.putOfWeek(context,System.currentTimeMillis());
		if (isPut) {
			float v=SharedUtil.getDouble(context, id);
			editor.putFloat(id, valuef+v);
		}else {
			editor.putFloat(AppGlobal.WEEK_EXPENDITURE, 0);
			editor.putFloat(AppGlobal.WEEK_INCOME, 0);
			editor.putFloat(id, valuef);
		}
		editor.commit();
		return true;
	}
	public static boolean putMonth(Context context, String id, double value) {
		SharedPreferences sharedPreference = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreference.edit();
		float valuef = (float) value;
		boolean isPut=GetDate.putOfMonth(context,System.currentTimeMillis());
		if (isPut) {
			float v=SharedUtil.getDouble(context, id);
			editor.putFloat(id, valuef+v);
		}else {
			editor.putFloat(AppGlobal.MONTH_EXPENDITURE, 0);
			editor.putFloat(AppGlobal.MONTH_INCOME, 0);
			editor.putFloat(id, valuef);
		}
		editor.commit();
		return true;
	}

	public static float getDouble(Context context, String id) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getFloat(id,0);
	}
	public static String getDoubleString(Context context, String id) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return toTransform(sharedPreferences.getFloat(id, 0));
	}
	public static Long getLong(Context context, String id) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getLong(id, 0);
	}

	private static String toTransform(float float1) {
		// TODO Auto-generated method stub
		// float num=(float)(Math.round(float1*0.00));
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return decimalFormat.format(float1);
	}
}
