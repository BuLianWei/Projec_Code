package com.example.remeber.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.adapter.AddListAdapter;
import com.example.bean.AddBean;
import com.example.db.BaseDB.OnDatabaseHandlerListener;
import com.example.db.DayDB;
import com.example.remeber.R;
import com.example.remeber.ui.activity.AddDetailActivity;
import com.example.util.AppGlobal;
import com.example.util.GetDate;
import com.example.util.SharedUtil;

public class AddRecordFrag extends Basefragment {
	public static final String TAG = "AddRecord";
	private static final int UPDATE_ID=0x00;
	private RelativeLayout addLayout;
	private ListView addListView;
	private AddBean bean;
	private List<AddBean> list;
	private ArrayList<Object> mlist;
	private AddListAdapter adapter;
	private DayDB db;
    private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_ID:
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<AddBean>();
		db = new DayDB(getActivity());
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.add_record, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		upDate();
		addLayout = (RelativeLayout) getView()
				.findViewById(R.id.add_record_add);
		addListView = (ListView) getView().findViewById(
				R.id.add_record_listView1);
		adapter = new AddListAdapter(getActivity(), list);
		addListView.setAdapter(adapter);
		addListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						AddDetailActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("data", list.get(position));
				intent.putExtra("bundle", bundle);
				startActivity(intent);
			}
		});
		addLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						AddDetailActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
	}

	private void upDate() {
		db.loadDataFromDB(null, new OnDatabaseHandlerListener() {

			@Override
			public boolean onDatabaseHandler(final ArrayList<Object> dataList,
					boolean isSuccess) {
				// TODO Auto-generated method stub
				mlist = dataList;
				new Thread(){
					public void run() {
						notif();
						Message msg=handler.obtainMessage();
						msg.what=UPDATE_ID;
						handler.sendMessage(msg);
					};
				}.start();
				
				return isSuccess;
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			upDate();
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

	public void notif() {
		list.clear();
		if (mlist != null) {
			for (int i = 0; i < mlist.size(); i++) {
				bean = (AddBean) mlist.get(i);
				Bitmap bitmap=null;
				if (bean.getIconString()!=null) {
					String uri = bean.getIconString();
					bitmap= BitmapFactory.decodeFile(uri);
				}
				list.add(new AddBean(bitmap, bean.getContent(), bean.getDate(),
						bean.getMoney(), bean.getType(), null));
			}
		}
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		boolean is=GetDate.putOfToday(getActivity(), System.currentTimeMillis());
//		Log.d("test2 is--", is+"");
//		Log.d("test2 time--", new SimpleDateFormat("MM/dd  HH:mm ").format(SharedUtil.getLong(getActivity(), AppGlobal.TODAY_TIME))+"");
//		if (AppGlobal.ISPUTTODAY) {
//			db.deleteAllRows();
//			list.clear();
//			Log.d("test2 list--", list.size()+"");
//			
//		}
		upDate();
	}
	
}
