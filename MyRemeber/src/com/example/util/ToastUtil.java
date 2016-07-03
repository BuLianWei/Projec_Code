package com.example.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;
	public static void shortTosat(Context context, String msg) {
		toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}

	public static void longTosat(Context context, String msg) {
		toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
//		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}
}
