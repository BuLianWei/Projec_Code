package com.example.remeber.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.remeber.R;
import com.example.util.AppGlobal;
import com.example.util.DialogUtil;
import com.example.util.DialogUtil.onEventOkAfter;
import com.example.util.SharedUtil;
import com.example.util.ToastUtil;

public class SetBudgetActivity extends BaseActivity implements OnClickListener{
	public static final int ALL=0x00;
	public static final int WEEK=0x01;
	public static final int MONTH=0x02;
	
	private TextView titleTV;
	private TextView leftTV;
	private TextView availableTV;
	private TextView usedTV;
	private TextView balanceTV;
	private EditText budgetET;
	private Button  okBtn;
	private Button  cancelBtn;
	private ImageView backIV;
	private int flag=ALL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_budget);
		flag=getIntent().getIntExtra("flag", ALL);
//		Log.d("test2 flag--", flag+"");
		initView();
		toDoView();
		
	}
	private void toDoView() {
		// TODO Auto-generated method stub
		titleTV.setText(R.string.set_budget);
		String budget;
		String available;
		String used;
		String balance;
		if (flag==ALL) {
			budget=SharedUtil.getDoubleString(SetBudgetActivity.this, AppGlobal.ALL_BUDGET);
			available=SharedUtil.getDoubleString(this, AppGlobal.ALL_AVAILABLE);
			used=SharedUtil.getDoubleString(this, AppGlobal.ALL_USED);
			balance=getString(R.string.total_balance);
		} else if(flag==WEEK){
			budget=SharedUtil.getDoubleString(SetBudgetActivity.this, AppGlobal.WEEK_BUDGET);
			available=SharedUtil.getDoubleString(this, AppGlobal.WEEK_AVAILABLE);
			used=SharedUtil.getDoubleString(this, AppGlobal.WEEK_USED);
			balance=getString(R.string.week_budget);
		}else {
			budget=SharedUtil.getDoubleString(SetBudgetActivity.this, AppGlobal.MONTH_BUDGET);
			available=SharedUtil.getDoubleString(this, AppGlobal.MONTH_AVAILABLE);
			used=SharedUtil.getDoubleString(this, AppGlobal.MONTH_USED);
			balance=getString(R.string.month_budget);
		}
		budgetET.setText(budget);
		availableTV.setText(available);
		usedTV.setText(used);
		balanceTV.setText(balance);
		Editable editable = budgetET.getEditableText();
		Selection.setSelection(editable, editable.length());
	}
	private void initView() {
		// TODO Auto-generated method stub
		titleTV=(TextView) findViewById(R.id.bar_title);
		leftTV=(TextView) findViewById(R.id.bar_left);
		backIV=(ImageView) findViewById(R.id.bar_icon);
		availableTV=(TextView) findViewById(R.id.budget_available);
		usedTV=(TextView) findViewById(R.id.budget_used);
		budgetET=(EditText) findViewById(R.id.budget_budget);
		balanceTV=(TextView) findViewById(R.id.budget_balance);
		okBtn=(Button) findViewById(R.id.ok);
		cancelBtn=(Button) findViewById(R.id.cancel);
		backIV.setVisibility(View.VISIBLE);
		leftTV.setVisibility(View.VISIBLE);
		leftTV.setText(getString(R.string.delete));
		
		backIV.setOnClickListener(this);
		leftTV.setOnClickListener(this);
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ok:
			saveData();
			SetBudgetActivity.this.finish();
			break;
		case R.id.bar_left:
		DialogUtil dialogUtil=DialogUtil.newInstance(R.string.delete_title, R.string.delete_msg, new onEventOkAfter() {
			
			@Override
			public void onOkAfter() {
				// TODO Auto-generated method stub
				if (flag==ALL) {
					SharedUtil.putBoolean(SetBudgetActivity.this, AppGlobal.ALL_FLAG, false);;
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.ALL_AVAILABLE, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.ALL_BUDGET, 0);
					SharedUtil.putInt(SetBudgetActivity.this, AppGlobal.ALL_BUDGET_MAX, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.ALL_USED, 0);
				} else if(flag==WEEK){
					SharedUtil.putBoolean(SetBudgetActivity.this, AppGlobal.WEEK_FLAG, false);;
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_AVAILABLE, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_BALANCE, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_BUDGET, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_USED, 0);
				}else {
					SharedUtil.putBoolean(SetBudgetActivity.this, AppGlobal.MONTH_FLAG, false);;
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_AVAILABLE, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_BALANCE, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_BUDGET, 0);
					SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_USED, 0);
				}
				toDoView();
			}
		});
			dialogUtil.show(getSupportFragmentManager(), "dialog");
			break;
		case R.id.cancel:
		case R.id.bar_icon:
			SetBudgetActivity.this.finish();
			break;

		default:
			break;
		}
	}
	private void saveData() {
		// TODO Auto-generated method stub
		if (budgetET.getText().toString().equals("")) {
			ToastUtil.shortTosat(SetBudgetActivity.this, getString(R.string.tishi));
			return ;
		}
		double v=Double.parseDouble(budgetET.getText().toString().trim());
		if (flag==ALL) {
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.ALL_BUDGET, v);
			SharedUtil.putInt(SetBudgetActivity.this, AppGlobal.ALL_BUDGET_MAX, (int)v);;
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.ALL_AVAILABLE, v);
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.ALL_USED, 0);
			SharedUtil.putBoolean(SetBudgetActivity.this, AppGlobal.ALL_FLAG, true);;
		} else if(flag==WEEK) {
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_BUDGET, v);
//			SharedUtil.putInt(SetBudgetActivity.this, AppGlobal.ALL_BUDGET_MAX, (int)v);;
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_AVAILABLE, v);
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.WEEK_USED, 0);
			SharedUtil.putBoolean(SetBudgetActivity.this, AppGlobal.WEEK_FLAG, true);;
		}else {
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_BUDGET, v);
//			SharedUtil.putInt(SetBudgetActivity.this, AppGlobal.ALL_BUDGET_MAX, (int)v);;
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_AVAILABLE, v);
			SharedUtil.putDouble(SetBudgetActivity.this, AppGlobal.MONTH_USED, 0);
			SharedUtil.putBoolean(SetBudgetActivity.this, AppGlobal.MONTH_FLAG, true);;
		}
		//		Log.d("test2  save--", v+"");
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	toDoView();
}
}
