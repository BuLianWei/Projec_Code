package com.example.remeber.ui.activity;



import com.example.remeber.R;
import com.example.util.AppGlobal;
import com.example.util.SharedUtil;

public class WeekDetailActivity extends DetailShowActivity {
	@Override
	protected void toDoView() {
		// TODO Auto-generated method stub
		titleTV.setText(R.string.week_detail);
		incomeTV.setText(R.string.week_income);
		expenditureTV.setText(R.string.week_expenditure);
		budgetTV.setText(R.string.week_budget);
		income=SharedUtil.getDouble(this, AppGlobal.WEEK_INCOME);
		expenditure=SharedUtil.getDouble(this, AppGlobal.WEEK_EXPENDITURE);
		incomeBar.setProgress(income,100);
		expenditureBar.setProgress(expenditure,100);
		 budget=SharedUtil.getDouble(this, AppGlobal.WEEK_AVAILABLE);
		budgetBar.setProgress(budget,100);
//		Log.d("test2 bu--", SharedUtil.getDouble(this, AppGlobal.WEEK_AVAILABLE)+"");
		super.toDoView();
	}
	
	@Override
	protected void getFlag() {
		// TODO Auto-generated method stub
		flag=SetBudgetActivity.WEEK;
		
	}
}
