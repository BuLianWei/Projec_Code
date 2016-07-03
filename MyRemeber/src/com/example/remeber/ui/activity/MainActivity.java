package com.example.remeber.ui.activity;

import java.util.TreeSet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.DayDB;
import com.example.remeber.R;
import com.example.remeber.ui.fragment.AddRecordFrag;
import com.example.remeber.ui.fragment.ChartShowFrag;
import com.example.remeber.ui.fragment.ShowFragment;
import com.example.util.AppGlobal;
import com.example.util.DialogUtil;
import com.example.util.DialogUtil.onEventOkAfter;
import com.example.util.DragLayout;
import com.example.util.DragLayout.DragListener;
import com.example.util.Dumm;
import com.example.util.SharedUtil;
import com.example.util.ToastUtil;
//import com.google.zxing.client.android.CaptureActivity;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends BaseActivity implements OnClickListener {
	public static final int SCAN_CODE = 1;
	private static final int SHOW = 0x00;
	private static final int ADD = 0x01;
	private static final int CHART = 0x02;
	private int current = SHOW;
	private TabHost host;
	private FragmentTransaction fTransaction;
	private ShowFragment showFragment;
	private AddRecordFrag addRecordFrag;
	private ChartShowFrag chartShowFrag;
	private	ImageView backIV;
	private	TextView titleTV;
	private	TextView leftTV;
	private	TextView rightTV;
	private	LinearLayout barLayout;
	private LinearLayout showLayout, addLayout, chartLayout;
	// private SlidingMenu slidingMenu;
	private DragLayout dl;
	private LinearLayout l1, l2, l3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
//		SharedUtil.putDouble(this, "all_budget", 1000);
//		AppGlobal.ALL_BUDGET_FLOAT = 100;

		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		l1 = (LinearLayout) findViewById(R.id.ll1);
		l2 = (LinearLayout) findViewById(R.id.ll2);
		l3 = (LinearLayout) findViewById(R.id.ll3);
		l1.setOnClickListener(this);
		l2.setOnClickListener(this);
		l3.setOnClickListener(this);
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {// 打開時候要做什麼
				// 側滑欄目 位置隨機
				// lv.smoothScrollToPosition(new Random().nextInt(30));
				// backIV.setImageResource(R.drawable.icon);
			}

			@Override
			public void onClose() {
				// 主屏幕左上角頭像晃動
				shake();
			}

			@Override
			public void onDrag(float percent) {
				// 側滑背景
				ViewHelper.setAlpha(backIV, 1 - percent);

			}
		});
		initView();

		// layout.setOnClickListener(this);
		// layout1.setOnClickListener(this);
		// layout2.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		backIV = (ImageView) findViewById(R.id.bar_icon);
		titleTV = (TextView) findViewById(R.id.bar_title);
		leftTV = (TextView) findViewById(R.id.bar_left);
		rightTV = (TextView) findViewById(R.id.bar_right);
		barLayout = (LinearLayout) findViewById(R.id.bar_layout);
		// backIV.setVisibility(View.GONE);
		backIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
		backIV.setImageDrawable(getResources().getDrawable(R.drawable.iconf));
		backIV.setVisibility(View.VISIBLE);
		// barLayout.setVisibility(View.GONE);
		host = (TabHost) findViewById(R.id.tabhost);
		host.setup();
		createView();
		TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				FragmentManager manager = getFragmentManager();
				showFragment = (ShowFragment) manager
						.findFragmentByTag(ShowFragment.TAG);
				addRecordFrag = (AddRecordFrag) manager
						.findFragmentByTag(AddRecordFrag.TAG);
				chartShowFrag = (ChartShowFrag) manager
						.findFragmentByTag(ChartShowFrag.TAG);
				fTransaction = manager.beginTransaction();
				if (showFragment != null) {
					fTransaction.detach(showFragment);
				}
				if (addRecordFrag != null) {
					fTransaction.detach(addRecordFrag);
				}
				if (chartShowFrag != null) {
					fTransaction.detach(chartShowFrag);
				}
				if (tabId.equalsIgnoreCase(ShowFragment.TAG)) {
					setShow();
					current = SHOW;
				} else if (tabId.equalsIgnoreCase(AddRecordFrag.TAG)) {
					setAddRecord();
					current = ADD;
				} else if (tabId.equalsIgnoreCase(ChartShowFrag.TAG)) {
					setChartShow();
					current = CHART;
				} else {
					switch (current) {
					case SHOW:
						setShow();
						break;
					case ADD:
						setAddRecord();
						break;
					case CHART:
						setChartShow();
						break;

					}

				}
				fTransaction.commit();
			}

		};
		host.setOnTabChangedListener(listener);
		host.setCurrentTab(current);
		setTab();

	}

	private void setTab() {
		// TODO Auto-generated method stub
		TabHost.TabSpec showSpec = host.newTabSpec(ShowFragment.TAG);
		showSpec.setIndicator(showLayout);
		showSpec.setContent(new Dumm(getBaseContext()));
		host.addTab(showSpec);

		TabHost.TabSpec addSpec = host.newTabSpec(AddRecordFrag.TAG);
		addSpec.setIndicator(addLayout);
		addSpec.setContent(new Dumm(getBaseContext()));
		host.addTab(addSpec);

		TabHost.TabSpec chartSpec = host.newTabSpec(ChartShowFrag.TAG);
		chartSpec.setIndicator(chartLayout);
		chartSpec.setContent(new Dumm(getBaseContext()));
		host.addTab(chartSpec);
	}

	protected void setChartShow() {
		// TODO Auto-generated method stub
		backIV.setVisibility(View.GONE);
		barLayout.setVisibility(View.GONE);
		if (chartShowFrag == null) {
			fTransaction.add(R.id.content, new ChartShowFrag(),
					ChartShowFrag.TAG);
		} else {
			fTransaction.attach(chartShowFrag);
		}
		titleTV.setText(R.string.chart_show);
	}

	protected void setAddRecord() {
		// TODO Auto-generated method stub
		backIV.setVisibility(View.GONE);
		barLayout.setVisibility(View.GONE);
		if (addRecordFrag == null) {
			fTransaction.add(R.id.content, new AddRecordFrag(),
					AddRecordFrag.TAG);
		} else {
			fTransaction.attach(addRecordFrag);
		}
		titleTV.setText(R.string.add_record);
	}

	protected void setShow() {
		// TODO Auto-generated method stub
		// backIV.setVisibility(View.GONE);
		barLayout.setVisibility(View.GONE);
		if (showFragment == null) {
			fTransaction
					.add(R.id.content, new ShowFragment(), ShowFragment.TAG);
		} else {
			fTransaction.attach(showFragment);
		}
		titleTV.setText(R.string.show);
		leftTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				host.setCurrentTab(CHART);
			}
		});
		rightTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplication(), "right", 1).show();
			}
		});
	}

	private void createView() {
		// TODO Auto-generated method stub
		TabWidget tWidget = (TabWidget) findViewById(android.R.id.tabs);
		showLayout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_layout, tWidget, false);
		ImageView imageView = (ImageView) showLayout.getChildAt(0);
		TextView textView = (TextView) showLayout.getChildAt(1);
		imageView.setImageResource(R.drawable.main_selector);
		textView.setText(R.string.show);

		addLayout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_layout, tWidget, false);
		ImageView imageView1 = (ImageView) addLayout.getChildAt(0);
		TextView textView1 = (TextView) addLayout.getChildAt(1);
		imageView1.setImageResource(R.drawable.add_selector);
		textView1.setText(R.string.add_record);

		chartLayout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_layout, tWidget, false);
		ImageView imageView2 = (ImageView) chartLayout.getChildAt(0);
		TextView textView2 = (TextView) chartLayout.getChildAt(1);
		imageView2.setImageResource(R.drawable.chart_selector);
		textView2.setText(R.string.chart_show);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.first:
			current = SHOW;
			break;
		case R.id.sencond:
			current = ADD;
			break;
		case R.id.third:
			current = CHART;
			break;
		case R.id.ll1:
			DialogUtil dialogUtil=DialogUtil.newInstance(R.string.restore_software, R.string.restore_software_msg, new onEventOkAfter() {
				
				@Override
				public void onOkAfter() {
					// TODO Auto-generated method stub
					SharedUtil.putArrayString(MainActivity.this, "list", new TreeSet<String>());
					SharedUtil.putBoolean(MainActivity.this, AppGlobal.ALL_FLAG, false);
					SharedUtil.putBoolean(MainActivity.this, AppGlobal.WEEK_FLAG, false);
					SharedUtil.putBoolean(MainActivity.this, AppGlobal.MONTH_FLAG, false);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.ALL_AVAILABLE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.ALL_BUDGET, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.ALL_EXPENDITURE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.ALL_INCOME, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.ALL_USED, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.WEEK_AVAILABLE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.WEEK_BALANCE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.WEEK_BUDGET, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.WEEK_EXPENDITURE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.WEEK_INCOME, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.WEEK_USED, 0);
					SharedUtil.putLong(MainActivity.this, AppGlobal.WEEK_TIME, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.MONTH_AVAILABLE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.MONTH_BALANCE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.MONTH_BUDGET, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.MONTH_EXPENDITURE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.MONTH_INCOME, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.MONTH_USED, 0);
					SharedUtil.putLong(MainActivity.this, AppGlobal.MONTH_TIME, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.TODAY_EXPENDITURE, 0);
					SharedUtil.putDouble(MainActivity.this, AppGlobal.TODAY_INCOME, 0);
					SharedUtil.putLong(MainActivity.this, AppGlobal.TODAY_TIME, 0);
				DayDB dayDB=new DayDB(MainActivity.this);
				dayDB.deleteAllRows();
				if (dayDB!=null) {
					dayDB.close();
				}
				dl.close();
				host.setCurrentTab(ADD);
				host.setCurrentTab(SHOW);
				}
				
			});
			dialogUtil.show(getSupportFragmentManager(), "dialog");
			break;
		case R.id.ll2:
			Intent intent1 = new Intent(MainActivity.this,
					MipcaActivityCapture.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent1, SCAN_CODE);
			break;
		case R.id.ll3:
			Intent intent2=new Intent(MainActivity.this,AboutActivity.class);
			startActivity(intent2);
			break;

		}
	}

	private void shake() {
		backIV.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SCAN_CODE:
			if (resultCode == RESULT_OK) {
				// 显示扫描到的内容
				String result = data.getStringExtra("result");
				// 显示
				ToastUtil.shortTosat(this, result);
				if (result.equals("")) {
					return;
				}
				Log.d("test2   url--", result);
				Intent intent = new Intent(MainActivity.this,
						ScanActivity.class);
				intent.putExtra("result", result);
				startActivity(intent);
				break;
			}
		}

	}
}
