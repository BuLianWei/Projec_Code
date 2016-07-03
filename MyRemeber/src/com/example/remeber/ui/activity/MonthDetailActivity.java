package com.example.remeber.ui.activity;

import android.R.integer;
import android.util.Log;

import com.example.remeber.R;
import com.example.util.AppGlobal;
import com.example.util.SharedUtil;

public class MonthDetailActivity extends DetailShowActivity {
	@Override
	protected void toDoView() {
		// TODO Auto-generated method stub
		super.toDoView();
		titleTV.setText(R.string.month_detail);
		incomeTV.setText(R.string.month_income);
		expenditureTV.setText(R.string.month_expenditure);
		budgetTV.setText(R.string.month_budget);
		income = SharedUtil.getDouble(this, AppGlobal.MONTH_INCOME);
		expenditure = SharedUtil.getDouble(this, AppGlobal.MONTH_EXPENDITURE);
		incomeBar.setProgress(income, 100);
		expenditureBar.setProgress(expenditure, 100);
		double budget = SharedUtil.getDouble(this, AppGlobal.MONTH_AVAILABLE);
		if (budget < 0) {
			budgetBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_horizontal_red));
		} else {
			budgetBar.setProgressDrawable(getResources().getDrawable(
					R.drawable.progress_color_horizontal));

		}
		budgetBar.setProgress(budget, 100);
		// Log.d("test2 bu--", SharedUtil.getDouble(this,
		// AppGlobal.WEEK_AVAILABLE)+"");
	}

	@Override
	protected void getFlag() {
		// TODO Auto-generated method stub
		flag = SetBudgetActivity.MONTH;
	}
}
