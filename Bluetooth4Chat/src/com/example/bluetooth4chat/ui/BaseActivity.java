package com.example.bluetooth4chat.ui;

import com.example.bluetooth4chat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity的基类，封装一些常用的方法，提供一些抽象的方法供子类实现
 * 
 * @author asus
 *
 */
public abstract class BaseActivity extends Activity implements OnClickListener {
	public static String TAG = "";
	protected TextView mTitle;

	/**
	 * 规定Activity切换的动画模式
	 * 
	 * @author asus
	 *
	 */
	public enum Transiton {
		LEFT, RIGHT, FADE, SCALE
	}

	protected int mScreenWidth = 0;
	protected int mScreenHeight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (isTransition()) {
			startJumpAnim();
		}
		super.onCreate(savedInstanceState);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		mScreenHeight = displayMetrics.heightPixels;
		mScreenWidth = displayMetrics.widthPixels;
		TAG = this.getClass().getSimpleName();
		getWindow();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (getLayoutId() == 0) {
			new Exception(getString(R.string.error_layoutid));
		} else {
			setContentView(getLayoutId());
		}
		setContentView(getLayoutId());
		initView();
		initData();
	}

	/**
	 * Activity的切换动画
	 * 
	 */
	private void startJumpAnim() {
		switch (getTranstion()) {
		case LEFT:
			overridePendingTransition(R.anim.right_out, R.anim.left_in);
			break;
		case RIGHT:
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			break;
		case FADE:
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		case SCALE:
			overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
			break;
		}
	}

	/**
	 * 是否开启Activity切换动画
	 * 
	 * @return TRUE 开启切换动画，FALSE 关闭切换动画
	 */
	protected abstract boolean isTransition();

	/**
	 * 获取Activity切换模式
	 * 
	 * @return Activity的切换模式
	 */
	protected abstract Transiton getTranstion();

	/**
	 * 获取布局资源
	 * 
	 * @return 布局Id
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化视图
	 * 
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 * 
	 */
	protected abstract void initData();

	/*
	 * 添加点击事件 (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public abstract void onClick(View v);

	/**
	 * 显示Toast信息
	 * 
	 * @param resId
	 *            消息Id
	 */
	public void showMsg(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Activity切换
	 * 
	 * @param activity
	 *            切换到的Activity
	 */
	public void toActivity(Class<?> activity) {
		startActivity(new Intent(this, activity));
	}

	/**
	 * 带有关闭当前Activity的Activity切换
	 * 
	 * @param activity
	 *            切换到的Activity
	 */
	public void toActivityWithFinish(Class<?> activity) {
		startActivity(new Intent(this, activity));
		finish();
	}

	@Override
	public void finish() {
		super.finish();
//		if (isTransition()) {
//			startJumpAnim();
//		}
	}
}
