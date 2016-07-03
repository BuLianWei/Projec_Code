package com.example.t;

import java.lang.reflect.Field;

import com.example.tool.IsNetWork;
import com.example.tuan.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TuanGou extends Fragment implements OnClickListener {
	ImageView meishi, dianying, jiudian, ktv, liren, menpiao, xindan, quanbu;
	LinearLayout layout09,layout;
	DisplayMetrics dm = new DisplayMetrics();
	// »ñÈ¡ÆÁÄ»¸ß¿í
	int screenWidth, screenHeigh;
	LayoutParams para;
    final static int TO1=1;
    Handler handler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tuangou, null);
		initView(view);
		return view;
	}

	public void initView(View view) {

		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeigh = dm.heightPixels;
		meishi = (ImageView) view.findViewById(R.id.meishi);
		dianying = (ImageView) view.findViewById(R.id.dianying);
		jiudian = (ImageView) view.findViewById(R.id.jiudian);
		ktv = (ImageView) view.findViewById(R.id.ktv);
		liren = (ImageView) view.findViewById(R.id.liren);
		menpiao = (ImageView) view.findViewById(R.id.menpiao);
		xindan = (ImageView) view.findViewById(R.id.xindan);
		quanbu = (ImageView) view.findViewById(R.id.quanbu);
		layout09 = (LinearLayout) view.findViewById(R.id.layout09);
		layout=(LinearLayout) view.findViewById(R.id.layout);
		para = meishi.getLayoutParams();
		para.height = (int) (screenWidth * 0.15);
		para.width = (int) (screenWidth * 0.25);
		meishi.setLayoutParams(para);
		dianying.setLayoutParams(para);
		jiudian.setLayoutParams(para);
		ktv.setLayoutParams(para);
		liren.setLayoutParams(para);
		menpiao.setLayoutParams(para);
		xindan.setLayoutParams(para);
		quanbu.setLayoutParams(para);
		
		meishi.setOnClickListener(this);
		dianying.setOnClickListener(this);
		jiudian.setOnClickListener(this);
		ktv.setOnClickListener(this);
		liren.setOnClickListener(this);
		meishi.setOnClickListener(this);
		menpiao.setOnClickListener(this);
		xindan.setOnClickListener(this);
		quanbu.setOnClickListener(this);
		layout.setOnClickListener(this);
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.meishi:
     Intent intent=new Intent(getActivity(),MeiShi.class);
     startActivity(intent);
			break;

		case R.id.dianying:
			Intent intent1=new Intent(getActivity(),DianYing.class);
		     startActivity(intent1);
			break;
		case R.id.jiudian:
			Intent intent2=new Intent(getActivity(),JiuDian.class);
		     startActivity(intent2);
			break;

		case R.id.ktv:
			Intent intent3=new Intent(getActivity(),KTV.class);
		     startActivity(intent3);
			break;
		case R.id.liren:
			Intent intent4=new Intent(getActivity(),LiRen.class);
		     startActivity(intent4);
			break;

		case R.id.menpiao:
			Intent intent5=new Intent(getActivity(),MenPiao.class);
		     startActivity(intent5);
			break;
		case R.id.xindan:
			Intent intent6=new Intent(getActivity(),XinDan.class);
		     startActivity(intent6);
			break;

		case R.id.quanbu:
			Intent intent7=new Intent(getActivity(),MeiShi.class);
		     startActivity(intent7);
			break;
			case R.id.layout:
				layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_city_area));
				break;
		}
	}
	
}
