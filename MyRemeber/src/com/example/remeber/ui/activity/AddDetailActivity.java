package com.example.remeber.ui.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bean.AddBean;
import com.example.db.DayDB;
import com.example.remeber.R;
import com.example.util.AppGlobal;
import com.example.util.DialogUtil;
import com.example.util.DialogUtil.onEventOkAfter;
import com.example.util.GetDate;
import com.example.util.SharedUtil;
import com.example.util.ToastUtil;

public class AddDetailActivity extends BaseActivity implements OnClickListener {
	private ImageView backIV;
	private TextView titleTV;
	private TextView leftTV;
	private TextView rightTV;
	private LinearLayout barLayout;
	private ImageView pictureIV;
	private Button okBtn;
	private Button cancelBtn;
	private TextView dateTV;
	private TextView typeTV;
	private EditText contentET;
	private EditText moneyET;
	private ImageView moreIV;
	private int current_type = 0;
	private Uri mUri;
	private Bitmap bitmap;
	private List<AddBean> list;
	private String iconString = null;
	private String content;
	private long date;
	private float money;
	private int type;
	private DayDB db;
	private BitmapDrawable bm;
	private Handler handler = new Handler();
	private boolean isUpDateTime = true;
	private boolean isAlert = false;
	private AddBean bean;
	private PopupWindow pop;
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (isUpDateTime) {
				dateTV.setText(GetDate.getTime(System.currentTimeMillis()));
				handler.postDelayed(runnable, 500);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_detail_set);
		list = new ArrayList<AddBean>();
		db = new DayDB(this);
//		TreeSet<String> set=new TreeSet<String>();
//		set.add("清除全部");
//		set.add("1");
//		set.add("2");
//		set.add("3");
//		SharedUtil.putArrayString(this, "list", set);
		initView();
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		if (bundle != null) {
			bean = (AddBean) bundle.get("data");
			BitmapDrawable bDrawable;
			if (bean != null) {
				dateTV.setText(GetDate.getTime(bean.getDate()) + "");
				contentET.setText(bean.getContent());
				moneyET.setText(bean.getMoney() + "");
				date=bean.getDate();
				if (bean.getIcon() != null) {
					bDrawable = new BitmapDrawable(bean.getIcon());
				} else if (bean.getType() == 0) {
					bDrawable = (BitmapDrawable) getResources().getDrawable(
							R.drawable.income);
					leftTV.callOnClick();
				} else {
					bDrawable = (BitmapDrawable) getResources().getDrawable(
							R.drawable.expenditure);
					rightTV.callOnClick();
				}
				pictureIV.setBackgroundDrawable(bDrawable);

			}
			isUpDateTime=false;
			isAlert=true;
		}
		Editable editable = moneyET.getEditableText();
		Selection.setSelection(editable, editable.length());
	}

	private void initView() {
		// TODO Auto-generated method stub
		backIV = (ImageView) findViewById(R.id.bar_icon);
		titleTV = (TextView) findViewById(R.id.bar_title);
		leftTV = (TextView) findViewById(R.id.bar_left);
		rightTV = (TextView) findViewById(R.id.bar_right);
		barLayout = (LinearLayout) findViewById(R.id.bar_layout);
		moreIV=(ImageView) findViewById(R.id.add_detail_more);
		dateTV = (TextView) findViewById(R.id.add_detail_date);
		typeTV = (TextView) findViewById(R.id.add_detail_type);
		contentET = (EditText) findViewById(R.id.add_detail_content);
		moneyET = (EditText) findViewById(R.id.add_detail_money);
		pictureIV = (ImageView) findViewById(R.id.add_detail_icon);
		okBtn = (Button) findViewById(R.id.ok);
		cancelBtn = (Button) findViewById(R.id.cancel);
		handler.postDelayed(runnable, 500);
		backIV.setVisibility(View.VISIBLE);
		barLayout.setVisibility(View.VISIBLE);
		
		contentET.setText("吃饭");
		leftTV.setVisibility(View.VISIBLE);
		rightTV.setVisibility(View.VISIBLE);
		pictureIV.setOnClickListener(this);
		moreIV.setOnClickListener(this);
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		backIV.setOnClickListener(this);
		leftTV.setOnClickListener(this);
		rightTV.setOnClickListener(this);
		dateTV.setOnClickListener(this);
		leftTV.setText(R.string.add_detail_income);
		leftTV.callOnClick();
		rightTV.setText(R.string.add_detail_expenditure);
		titleTV.setText(R.string.remember);
		showMore();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.bar_left:
			current_type = 0;
			leftTV.setTextColor(Color.GREEN);
			rightTV.setTextColor(Color.BLACK);
			typeTV.setText(R.string.add_detail_income);
			typeTV.setTextColor(Color.GREEN);
			moneyET.setTextColor(Color.GREEN);
			bm = (BitmapDrawable) getResources().getDrawable(
					R.drawable.income_icon);
			pictureIV.setImageBitmap(bm.getBitmap());
			break;
		case R.id.bar_right:
			current_type = 1;
			rightTV.setTextColor(Color.RED);
			leftTV.setTextColor(Color.BLACK);
			typeTV.setText(R.string.add_detail_expenditure);
			typeTV.setTextColor(Color.RED);
			moneyET.setTextColor(Color.RED);
			bm = (BitmapDrawable) getResources().getDrawable(
					R.drawable.expenditure_icon);
			pictureIV.setImageBitmap(bm.getBitmap());
			break;
		case R.id.add_detail_icon:
			takePictures();
			break;
		case R.id.ok:
			if (moneyET.getText().toString().trim().equals("")
					|| moneyET.getText().toString().trim().equals(".")) {
				ToastUtil.shortTosat(AddDetailActivity.this,
						getString(R.string.tishi));
				return;
			}
			if (mUri != null) {
				iconString = mUri.getPath();
			}
			content = contentET.getText().toString().trim();
			if (date == 0) {
				date = System.currentTimeMillis();
			}
			money = Float.parseFloat(moneyET.getText().toString().trim());
			type = current_type;
			saveData();
			AddDetailActivity.this.finish();
			break;
		case R.id.add_detail_date:
			isUpDateTime = false;
			initpop();
			break;
		case R.id.bar_icon:
		case R.id.cancel:
			AddDetailActivity.this.finish();
			break;
		case R.id.add_detail_more:
			pop.showAsDropDown(moreIV);
			break;
		}

	}

	private void showMore() {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(AddDetailActivity.this).inflate(R.layout.more_pop, null);
		pop=new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		ListView listView=(ListView) view.findViewById(R.id.more_list);
		final List<String> list=new ArrayList<String>();
		Set<String>  set=SharedUtil.getArrayString(AddDetailActivity.this, "list");
		for (int i = set.size()-1; i >= 0; i--) {
//			Iterator<String> iterator=set.iterator();
			list.add((String)set.toArray()[i]);
		}
//		set.add("清除全部");
		Log.d("test2 set--", "size--"+list.size()+" content--"+(Object)list);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddDetailActivity.this, R.layout.more_list_item, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (list.size()<=0) {
					ToastUtil.shortTosat(AddDetailActivity.this, getString(R.string.content_toast));
				}
				if (position<list.size()) {
					contentET.setText(list.get(position));
				}
//				if (position==list.size()-1) {
//					DialogUtil dialogUtil=DialogUtil.newInstance(R.string.content_title, R.string.content_msg, new onEventOkAfter() {
//						
//						@Override
//						public void onOkAfter() {
//							// TODO Auto-generated method stub
//							SharedUtil.putArrayString(AddDetailActivity.this, "list", new TreeSet<String>());
//						if (pop!=null) {
//							pop.dismiss();
//						}
//						}
//					});
//					dialogUtil.show(getSupportFragmentManager(), "dialg");
//				}
//				pop.dismiss();
			}
		});
		
	}

	private void saveData() {
		boolean all_flag=SharedUtil.getBoolean(this, AppGlobal.ALL_FLAG);
		boolean week_flag=SharedUtil.getBoolean(this, AppGlobal.WEEK_FLAG);
		boolean month_flag=SharedUtil.getBoolean(this, AppGlobal.MONTH_FLAG);
	Set<String> set=SharedUtil.getArrayString(AddDetailActivity.this,"list");
	set.add(content);
//		if (AppGlobal.BUDGET_FLAG) {
//			float budget = SharedUtil.getFloat(this, AppGlobal.ALL_BUDGET);
//			SharedUtil.putFloat(this, AppGlobal.ALL_BUDGET, budget - money);
//		}
		// 将收支存入总计
		if (isAlert) {
			db.deleteRow(date);
			boolean isSame=bean.getType()==current_type?true:false;
			if (isSame) {
				if (current_type==0) {
					
					float valueT=SharedUtil.getDouble(getBaseContext(), AppGlobal.TODAY_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.TODAY_INCOME, valueT-bean.getMoney()+money);
					float valueW=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_INCOME, valueW-bean.getMoney()+money);
					float valueM=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_INCOME, valueM-bean.getMoney()+money);
					float valueA=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_INCOME, valueA-bean.getMoney()+money);
				} else {
					if (all_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.ALL_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.ALL_AVAILABLE, budget +bean.getMoney()- money);
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_USED, valueU-bean.getMoney()+money);
					}
					if (week_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.WEEK_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.WEEK_AVAILABLE, budget +bean.getMoney()- money);
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_USED, valueU-bean.getMoney()+money);
					}
					if (month_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.MONTH_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.MONTH_AVAILABLE, budget +bean.getMoney()- money);
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_USED, valueU-bean.getMoney()+money);
					}
					float valueT=SharedUtil.getDouble(getBaseContext(), AppGlobal.TODAY_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.TODAY_EXPENDITURE, valueT-bean.getMoney()+money);
					float valueW=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_EXPENDITURE, valueW-bean.getMoney()+money);
					float valueM=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_EXPENDITURE, valueM-bean.getMoney()+money);
					float valueA=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_EXPENDITURE, valueA-bean.getMoney()+money);
					
					
				}
			} else {
				if (current_type==0) {
					if (all_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.ALL_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.ALL_AVAILABLE, budget + bean.getMoney());
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_USED, valueU-bean.getMoney());
					}
					if (week_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.WEEK_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.WEEK_AVAILABLE, budget + bean.getMoney());
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_USED, valueU-bean.getMoney());
					}
					if (month_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.MONTH_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.MONTH_AVAILABLE, budget + bean.getMoney());
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_USED, valueU-bean.getMoney());
					}
					float valueTI=SharedUtil.getDouble(getBaseContext(), AppGlobal.TODAY_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.TODAY_INCOME, valueTI+money);
					float valueTE=SharedUtil.getDouble(getBaseContext(), AppGlobal.TODAY_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.TODAY_EXPENDITURE, valueTE-bean.getMoney());
					float valueWI=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_INCOME, valueWI+money);
					float valueWE=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_EXPENDITURE, valueWE-bean.getMoney());
					float valueMI=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_INCOME, valueMI+money);
					float valueME=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_EXPENDITURE, valueME-bean.getMoney());
					float valueAI=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_INCOME, valueAI+money);
					float valueAE=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_EXPENDITURE, valueAE-bean.getMoney());
					
				} else {
					if (all_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.ALL_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.ALL_AVAILABLE, budget -money);
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_USED, valueU+money);
					}
					if (week_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.WEEK_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.WEEK_AVAILABLE, budget -money);
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_USED, valueU+money);
					}
					if (month_flag) {
						float budget = SharedUtil.getDouble(this, AppGlobal.MONTH_AVAILABLE);
						SharedUtil.putDouble(this, AppGlobal.MONTH_AVAILABLE, budget -money);
						float valueU=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_USED);
						SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_USED, valueU+money);
					}
					float valueTI=SharedUtil.getDouble(getBaseContext(), AppGlobal.TODAY_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.TODAY_INCOME, valueTI-bean.getMoney());
					float valueTE=SharedUtil.getDouble(getBaseContext(), AppGlobal.TODAY_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.TODAY_EXPENDITURE, valueTE+money);
					float valueWI=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_INCOME, valueWI-bean.getMoney());
					float valueWE=SharedUtil.getDouble(getBaseContext(), AppGlobal.WEEK_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.WEEK_EXPENDITURE, valueWE+money);
					float valueMI=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_INCOME, valueMI-bean.getMoney());
					float valueME=SharedUtil.getDouble(getBaseContext(), AppGlobal.MONTH_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.MONTH_EXPENDITURE, valueME+money);
					float valueAI=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_INCOME);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_INCOME, valueAI-bean.getMoney());
					float valueAE=SharedUtil.getDouble(getBaseContext(), AppGlobal.ALL_EXPENDITURE);
					SharedUtil.putDouble(getBaseContext(), AppGlobal.ALL_EXPENDITURE, valueAE+money);
				}
			}
		} else {
			if (current_type == 1){
				if (all_flag) {
					float budget = SharedUtil.getDouble(this, AppGlobal.ALL_AVAILABLE);
					SharedUtil.putDouble(this, AppGlobal.ALL_AVAILABLE, budget - money);
					SharedUtil.putDouble(this, AppGlobal.ALL_USED, money
							+ SharedUtil.getDouble(this, AppGlobal.ALL_USED));
				}
				if (week_flag) {
					float budget = SharedUtil.getDouble(this, AppGlobal.WEEK_AVAILABLE);
					SharedUtil.putDouble(this, AppGlobal.WEEK_AVAILABLE, budget - money);
					SharedUtil.putDouble(this, AppGlobal.WEEK_USED, money
							+ SharedUtil.getDouble(this, AppGlobal.WEEK_USED));
				}
				if (month_flag) {
					float budget = SharedUtil.getDouble(this, AppGlobal.MONTH_AVAILABLE);
					SharedUtil.putDouble(this, AppGlobal.MONTH_AVAILABLE, budget - money);
					SharedUtil.putDouble(this, AppGlobal.MONTH_USED, money
							+ SharedUtil.getDouble(this, AppGlobal.MONTH_USED));
				}
				SharedUtil.putDouble(this, AppGlobal.ALL_EXPENDITURE, money
						+ SharedUtil.getDouble(this, AppGlobal.ALL_EXPENDITURE));
				SharedUtil.putToday(this, AppGlobal.TODAY_EXPENDITURE, money);
				SharedUtil.putWeek(this, AppGlobal.WEEK_EXPENDITURE, money);
				SharedUtil.putMonth(this, AppGlobal.MONTH_EXPENDITURE, money);
			} else {
				SharedUtil.putDouble(this, AppGlobal.ALL_INCOME, money
						+ SharedUtil.getDouble(this, AppGlobal.ALL_INCOME));
				SharedUtil.putToday(this, AppGlobal.TODAY_INCOME, money);
				SharedUtil.putWeek(this, AppGlobal.WEEK_INCOME, money);
				SharedUtil.putMonth(this, AppGlobal.MONTH_INCOME, money);
			}
		}
		list.add(new AddBean(null, content, date, money, type, iconString));
		ArrayList<Object> dataList = new ArrayList<Object>();
		dataList.addAll(list);
		db.saveDataToDB(dataList, null);
//		Log.d("test2 budget-->", SharedUtil.getFloat(this, AppGlobal.ALL_BUDGET)+"");
	}
	

	private void takePictures() {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
		// mUri);

		try {
			// cameraIntent.putExtra("return-data", true);
			startActivityForResult(cameraIntent, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			bitmap = (Bitmap) bundle.get("data");
			File appDir = new File(Environment.getExternalStorageDirectory()
					+ "/KengDieA");

			if (!appDir.exists()) {
				appDir.mkdir();
			}

			mUri = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory() + "/KengDieA/", "kengDiePic"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(mUri.getPath());
				bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			BitmapDrawable bDrawable = new BitmapDrawable(bitmap);

			pictureIV.setBackgroundDrawable(bDrawable);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (db != null) {
			db.close();
		}
	}

	public void initpop() {
		View view = LayoutInflater.from(AddDetailActivity.this).inflate(
				R.layout.time_show, null);
		final PopupWindow popupWindow = new PopupWindow(view,
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		// popupWindow.showAsDropDown(dateET);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.datePicker1);
		final TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.timePicker1);
		((LinearLayout) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
				.getChildAt(0).setVisibility(View.GONE);
		Button ok = (Button) view.findViewById(R.id.button1);
		Button cancel = (Button) view.findViewById(R.id.button2);
		timePicker.setIs24HourView(true);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String date = (datePicker.getMonth() + 1) + "-"
						+ datePicker.getDayOfMonth() + "     "
						+ timePicker.getCurrentHour() + ":"
						+ timePicker.getCurrentMinute() + " ";
				dateTV.setText(date);
				Calendar calendar = Calendar.getInstance();
				calendar.set(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth(),
						timePicker.getCurrentHour(),
						timePicker.getCurrentMinute());
				AddDetailActivity.this.date = calendar.getTimeInMillis();
				popupWindow.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});

	}
}
