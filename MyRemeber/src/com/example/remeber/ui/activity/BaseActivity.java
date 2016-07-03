package com.example.remeber.ui.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		SharedUtil.putFloat(this, "today_expenditure", 20);
//		SharedUtil.putFloat(this, "today_income", 405454554);
//		SharedUtil.putFloat(this, "week_expenditure", 80.25);
//		SharedUtil.putFloat(this, "week_income", 608844);
//		SharedUtil.putFloat(this, "month_expenditure", 30.6666);
//		SharedUtil.putFloat(this, "month_income", 50144);
//		SharedUtil.putFloat(this, "all_expenditure", 123);
//		SharedUtil.putFloat(this, "all_income", 508);
		
	}
}
