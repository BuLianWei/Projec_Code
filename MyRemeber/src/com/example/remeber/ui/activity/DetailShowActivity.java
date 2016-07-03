package com.example.remeber.ui.activity;

import com.example.remeber.R;
import com.example.util.MyProgress;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class DetailShowActivity extends BaseActivity implements
		OnClickListener {
	protected TextView titleTV;
	protected TextView leftTV;
	protected ImageView backIV;
	protected TextView analyzeTV;
	protected TextView incomeTV;
	protected TextView expenditureTV;
	protected TextView budgetTV;
	protected MyProgress incomeBar;
	protected MyProgress expenditureBar;
	protected MyProgress budgetBar;
	protected int flag = 0;
	protected double income=0;
	protected double expenditure=0;
	protected double budget=0;
	protected abstract void getFlag();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detial_show);
		initView();
		toDoView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		titleTV = (TextView) findViewById(R.id.bar_title);
		leftTV = (TextView) findViewById(R.id.bar_left);
		backIV = (ImageView) findViewById(R.id.bar_icon);
		incomeTV = (TextView) findViewById(R.id.incomeTV);
		expenditureTV = (TextView) findViewById(R.id.expenditureTV);
		budgetTV = (TextView) findViewById(R.id.balanceTV);
		analyzeTV = (TextView) findViewById(R.id.chart_analyze);
		incomeBar = (MyProgress) findViewById(R.id.chart_incomeBar);
		expenditureBar = (MyProgress) findViewById(R.id.chart_expenditureBar);
		budgetBar = (MyProgress) findViewById(R.id.chart_budgetBar);
		backIV.setVisibility(View.VISIBLE);
		leftTV.setVisibility(View.VISIBLE);
		leftTV.setOnClickListener(this);
		backIV.setOnClickListener(this);
		AddToView();
	}

	protected void AddToView() {
	}

	protected void toDoView() {
		// TODO Auto-generated method stub
		leftTV.setText(R.string.set_balance);
		int  msg=0;
		if (income==expenditure&&income==0) {
			msg=R.string.analyze_msg0;
		}else if (income>expenditure/2) {
			msg=R.string.analyze_msg1;
		} else if(income>=expenditure){
			msg=R.string.analyze_msg2;
		}else {
			msg=R.string.analyze_msg3;
		}
		analyzeTV.setText(getString(msg));
		if (budget<0) {
			budgetBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_color_horizontal_red));
		} else {
			budgetBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_color_horizontal));

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bar_icon:
			DetailShowActivity.this.finish();
			break;

		case R.id.bar_left:
			setBudgetEvent();
			break;

		default:
			break;
		}
	}

	private void setBudgetEvent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(DetailShowActivity.this,
				SetBudgetActivity.class);
		getFlag();
		intent.putExtra("flag", flag);
		startActivity(intent);
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		toDoView();
	}

}
