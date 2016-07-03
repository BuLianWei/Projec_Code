package com.example.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IsNetWork {
	/**
	 * 判断Network是否开启(包括移动网络和wifi)
	 * 
	 * @return true--移动网络或wifi开启 false--移动网络都wifi没有开启
	 */
	public static boolean isNetworkEnabled(Context mContext) {
		return (isNetEnabled(mContext) || isWIFIEnabled(mContext));
	}

	/**
	 * 判断Network是否连接成功(包括移动网络和wifi)
	 * 
	 * @return true--移动网络或wifi连接成功 false--移动网络和wifi都连接失败
	 */
	public static boolean isNetworkConnected(Context mContext) {

		return (isWifiContected(mContext) || isNetContected(mContext));

	}

	/**
	 * 判断移动网络是否开启
	 * 
	 * @return true：移动网络开启 false：移动网络没有开启
	 */

	public static boolean isNetEnabled(Context context) {
		boolean enable = false;
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager != null) {
			if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
				enable = true;
				Log.i(Thread.currentThread().getName(), "isNetEnabled");
			}
		}
		return enable;
	}

	/**
	 * 判断wifi是否开启
	 * 
	 * @return true：wifi开启 false：wifi没有开启
	 */
//	public static boolean isWIFIEnabled(Context context) {
//		boolean enable = false;
//		WifiManager wifiManager = (WifiManager) context
//				.getSystemService(Context.WIFI_SERVICE);
//		if (wifiManager.isWifiEnabled()) {
//			enable = true;
//			Log.i(Thread.currentThread().getName(), "isWifiEnabled");
//		}
//		Log.i(Thread.currentThread().getName(), "isWifiDisabled");
//		return enable;
//	}
	public static boolean isWIFIEnabled(Context inContext) {   
        Context context = inContext.getApplicationContext();   
        ConnectivityManager connectivity = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity != null) {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }

	/**
	 * 判断移动网络是否连接成功！
	 * 
	 * @param context
	 * @return true：移动网络连接成功 false：移动网络连接失败
	 */
	public static boolean isNetContected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		System.out.println("mobileNetworkInfo----"+mobileNetworkInfo);
		if (mobileNetworkInfo.isConnected()) {
			Log.i(Thread.currentThread().getName(), "isNetContected");
			return true;
		}
		Log.i(Thread.currentThread().getName(), "isNetDisconnected");
		return false;
	}

	/**
	 * 判断wifi是否连接成功
	 * 
	 * @param context
	 * @return true：wifi连接成功 false：wifi连接失败
	 */
	public static boolean isWifiContected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			Log.i(Thread.currentThread().getName(), "isWifiContected");
			return true;
		}
		Log.i(Thread.currentThread().getName(), "isWifiDisconnected");
		return false;
	}
}
