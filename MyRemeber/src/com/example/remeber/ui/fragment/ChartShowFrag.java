package com.example.remeber.ui.fragment;


import com.example.remeber.R;
import com.example.util.AppGlobal;
import com.example.util.MyProgress;
import com.example.util.SharedUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ChartShowFrag extends Basefragment {
	public static final String TAG = "ChartShow";
	private MyProgress expenditureBar;
	private MyProgress incomeBar;
	private MyProgress budgetBar;
	private ListView real_incomeLV;
	private ListView expenditureLV;
	private TextView analyzeTV;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.chart_showl, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		budgetBar = (MyProgress) getView().findViewById(R.id.chart_budgetBar);
		incomeBar = (MyProgress) getView().findViewById(R.id.chart_incomeBar);
		expenditureBar = (MyProgress) getView().findViewById(
				R.id.chart_expenditureBar);
//		incomeTV = (TextView) getView().findViewById(R.id.chart_incomeTV);
//		expenditureTV = (TextView) getView().findViewById(
//				R.id.chart_expenditureTV);
		analyzeTV = (TextView) getView().findViewById(R.id.chart_analyze);
		toUpDateView();
	}

	private void toUpDateView() {

		double real_income =(double)(SharedUtil.getDouble(getActivity(),
				AppGlobal.ALL_INCOME));
		double real_expenditure = (double)(SharedUtil.getDouble(getActivity(),
				AppGlobal.ALL_EXPENDITURE));
		double real_budget = real_income - real_expenditure;
		if (real_budget < 0) {
//			real_budget = -real_budget;
			budgetBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_color_horizontal_red));
		}else {
			budgetBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_color_horizontal));
		}
		int msg=0;
		if (real_income==real_expenditure&&real_income==0) {
			msg=R.string.analyze_msg0;
		}else if (real_income>real_expenditure/2) {
			msg=R.string.analyze_msg1;
		} else if(real_income>=real_expenditure){
			msg=R.string.analyze_msg2;
		}else {
			msg=R.string.analyze_msg3;
		}
		analyzeTV.setText(getActivity().getString(msg));
		budgetBar.setProgress(real_budget,100);
		incomeBar.setProgress(real_income,100);
		expenditureBar.setProgress(real_expenditure,100);
		// Log.d("test2  bar", "real_budget-->" + real_budget
		// + "  real_income-->" + real_income + "  real_expendture-->"
		// + real_expenditure);
		// Log.d("test2  size", "budget-->" + budget
		// + "  income-->" + income + "  expendture-->"
		// + expenditure );
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		toUpDateView();
	}
}
