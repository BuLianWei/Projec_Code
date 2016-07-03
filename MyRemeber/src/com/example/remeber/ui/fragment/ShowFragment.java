package com.example.remeber.ui.fragment;

import java.text.DecimalFormat;

import com.example.db.DayDB;
import com.example.remeber.R;
import com.example.remeber.ui.activity.MonthDetailActivity;
import com.example.remeber.ui.activity.SetBudgetActivity;
import com.example.remeber.ui.activity.WeekDetailActivity;
import com.example.util.AppGlobal;
import com.example.util.GetDate;
import com.example.util.GetProportionData;
import com.example.util.MyProgress;
import com.example.util.SharedUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowFragment extends Basefragment implements OnClickListener {
	public static final String TAG = "Show";
	private TextView all_income;
	private TextView all_expenditure;
	private TextView budgetTV;
	private ProgressBar budgetBar;
	private TextView today_time, current_week_time, current_month_time;
	private RelativeLayout todayLayout, weekLayout, monthLayout;
	private TextView today_income;
	private TextView today_expenditure;
	private TextView week_income;
	private TextView week_expenditure;
	private TextView month_income;
	private TextView month_expenditure;
	private boolean barFlag = true;
	private boolean flag = true;
	private DayDB db;
	private MyProgress weekBar;
	private MyProgress monthBar;
	private float budgetInt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db = new DayDB(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.show, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		intView();
	}

	private void intView() {
		// TODO Auto-generated method stub
		all_income = (TextView) getView().findViewById(R.id.show_all_income);
		all_expenditure = (TextView) getView().findViewById(
				R.id.show_all_expenditure);
		budgetTV = (TextView) getView().findViewById(R.id.show_budget);
		budgetBar = (ProgressBar) getView().findViewById(R.id.show_budget_bar);
		today_expenditure = (TextView) getView().findViewById(
				R.id.show_taday_expenditure);
		today_income = (TextView) getView()
				.findViewById(R.id.show_taday_income);
		today_time = (TextView) getView().findViewById(R.id.show_taday);
		week_expenditure = (TextView) getView().findViewById(
				R.id.show_week_expenditure);
		week_income = (TextView) getView().findViewById(R.id.show_week_income);
		current_week_time = (TextView) getView().findViewById(R.id.show_week);
		month_expenditure = (TextView) getView().findViewById(
				R.id.show_month_expenditure);
		month_income = (TextView) getView()
				.findViewById(R.id.show_month_income);
		current_month_time = (TextView) getView().findViewById(R.id.show_month);
		todayLayout = (RelativeLayout) getView().findViewById(
				R.id.show_todayLayout);
		weekLayout = (RelativeLayout) getView().findViewById(
				R.id.show_weekLayout);
		monthLayout = (RelativeLayout) getView().findViewById(
				R.id.show_monthLayout);
		weekBar = (MyProgress) getView().findViewById(R.id.show_week_budget);
		monthBar = (MyProgress) getView().findViewById(R.id.show_month_budget);
		budgetTV.setOnClickListener(this);
		todayLayout.setOnClickListener(this);
		weekLayout.setOnClickListener(this);
		monthLayout.setOnClickListener(this);
		// toDoView();
	}

	private void toDoView() {
		double week = SharedUtil.getDouble(getActivity(),
				AppGlobal.WEEK_AVAILABLE);
		double month = SharedUtil.getDouble(getActivity(),
				AppGlobal.MONTH_AVAILABLE);
		if (week < 0) {
			weekBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_horizontal_red));
		} else {
			weekBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_horizontal));

		}
		if (month < 0) {
			monthBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_horizontal_red));
		} else {
			monthBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_horizontal));

		}

		weekBar.setProgress(week, 100);
		monthBar.setProgress(month, 100);
		// TODO Auto-generated method stub
		all_income.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.ALL_INCOME));
		all_expenditure.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.ALL_EXPENDITURE));
		
		today_time.setText(GetDate.getTodaty());
		current_week_time.setText(GetDate.getWeek());
		current_month_time.setText(GetDate.getMonth());
		if (!GetDate.putOfToday(getActivity(), System.currentTimeMillis())) {
			SharedUtil.putDouble(getActivity(), AppGlobal.TODAY_EXPENDITURE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.TODAY_INCOME, 0);
			db.deleteAllRows();
		}
		if (!GetDate.putOfWeek(getActivity(), System.currentTimeMillis())) {
			SharedUtil.putDouble(getActivity(), AppGlobal.WEEK_EXPENDITURE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.WEEK_INCOME, 0);
			SharedUtil.putBoolean(getActivity(), AppGlobal.WEEK_FLAG, false);
			SharedUtil.putDouble(getActivity(), AppGlobal.WEEK_AVAILABLE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.WEEK_BALANCE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.WEEK_BUDGET, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.WEEK_USED, 0);
		}
		if (!GetDate.putOfMonth(getActivity(), System.currentTimeMillis())) {
			SharedUtil.putDouble(getActivity(), AppGlobal.MONTH_EXPENDITURE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.MONTH_INCOME, 0);
			SharedUtil.putBoolean(getActivity(), AppGlobal.MONTH_FLAG, false);
			SharedUtil.putDouble(getActivity(), AppGlobal.MONTH_AVAILABLE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.MONTH_BALANCE, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.MONTH_BUDGET, 0);
			SharedUtil.putDouble(getActivity(), AppGlobal.MONTH_USED, 0);
		}
		today_expenditure.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.TODAY_EXPENDITURE));
		today_income.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.TODAY_INCOME));
		week_expenditure.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.WEEK_EXPENDITURE));
		week_income.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.WEEK_INCOME));
		month_expenditure.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.MONTH_EXPENDITURE));
		month_income.setText("￥"
				+ SharedUtil.getDoubleString(getActivity(),
						AppGlobal.MONTH_INCOME));
		budgetInt = SharedUtil
				.getDouble(getActivity(), AppGlobal.ALL_AVAILABLE);
		budgetTV.setText("￥" + new DecimalFormat("0.00").format(budgetInt));
		if (SharedUtil.getBoolean(getActivity(), AppGlobal.ALL_FLAG)) {
			upDateBar();
		}else {
			budgetBar.setProgress(0);
		}
	}

	private void upDateBar() {
		if (budgetInt < 0) {
			budgetInt = -budgetInt;
			budgetBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_vertical_red));
			budgetInt = (int) GetProportionData.getData(budgetInt, 100);
		} else {
			budgetBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_vertical));

		}

		new Thread() {
			public void run() {
				while (barFlag) {
					if (flag) {
						if (budgetBar.getProgress() >= budgetBar.getMax()) {
							flag = false;
							 Log.d("test2  ruu--", 1 + "");

						} else {
							budgetBar.incrementProgressBy(2);
							Log.d("test2  ruu--", 2 + "");
						}
					} else {
//						if (SharedUtil.getInt(getActivity(),
//								AppGlobal.ALL_BUDGET_MAX) == 0) {
//							SharedUtil.putInt(getActivity(),
//									AppGlobal.ALL_BUDGET_MAX, 100);
//						}
//						if ((float) budgetBar.getProgress() <= budgetBar
//								.getMax()
//								* budgetInt
//								/ (float) SharedUtil.getInt(getActivity(),
//										AppGlobal.ALL_BUDGET_MAX)) {
//							barFlag = false;
							if ( budgetBar.getProgress() <= budgetInt
									) {
								barFlag = false;
						} else {
							budgetBar.incrementProgressBy(-2);
							Log.d("test2  ruu--", 4 + "");
						}
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
		// Log.d("test2  all--", SharedUtil.getInt(getActivity(),
		// AppGlobal.ALL_BUDGET_MAX)+"");
		// Log.d("test2 bar-->", "budget-->" + budgetInt + "  current-->"
		// + budgetBar.getProgress() + "  max  " + budgetBar.getMax()
		// * budgetInt
		// / SharedUtil.getInt(getActivity(),
		// AppGlobal.ALL_BUDGET_MAX));
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		barFlag = true;
		flag = true;
		toDoView();
//		Log.d("test2 p-->", budgetBar.getProgress()+"");
//		Log.d("test2 f-->", "all--"+AppGlobal.ALL_FLAG);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.show_budget:
			Intent intent = new Intent(getActivity(), SetBudgetActivity.class);
			intent.putExtra("flag", SetBudgetActivity.ALL);
			startActivity(intent);
			break;
		case R.id.show_weekLayout:
			Intent intent1 = new Intent(getActivity(), WeekDetailActivity.class);
			startActivity(intent1);
			break;
		case R.id.show_monthLayout:
			Intent intent2 = new Intent(getActivity(), MonthDetailActivity.class);
			startActivity(intent2);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (db != null) {
			db.close();
		}
	}
}
