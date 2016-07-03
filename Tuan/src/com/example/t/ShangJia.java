package com.example.t;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyAdapter;
import com.example.tuan.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ShangJia extends Fragment implements OnClickListener{
	TextView allshang,youhui;
	ImageView alliv,youiv;
	Fragment aJia=new AllShangJia();
	Fragment yHui =new YouHui();
	ArrayList<Fragment> list=new ArrayList<Fragment>();
	
	ViewPager viewPager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =inflater.inflate(R.layout.shangjia, null);
		allshang=(TextView) view.findViewById(R.id.quanbushangjia);
		youhui=(TextView) view.findViewById(R.id.youhuishangjia);
		alliv=(ImageView) view.findViewById(R.id.quanbushang);
		youiv=(ImageView) view.findViewById(R.id.youhuishang);
		viewPager=(ViewPager) view.findViewById(R.id.viewpager);
		allshang.setOnClickListener(this);
		youhui.setOnClickListener(this);
		youiv.setBackgroundColor(Color.TRANSPARENT);
		alliv.setBackgroundColor(Color.BLACK);
		
		list.add(aJia);
		list.add(yHui);
		viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), list));
		viewPager .setCurrentItem(0);
		return view;
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
		case R.id.quanbushangjia:
			viewPager .setCurrentItem(0);
			youiv.setBackgroundColor(Color.TRANSPARENT);
			alliv.setBackgroundColor(Color.BLACK);
			break;

		case R.id.youhuishangjia:
			viewPager .setCurrentItem(1);
			alliv.setBackgroundColor(Color.TRANSPARENT);
			youiv.setBackgroundColor(Color.BLACK);
			break;
		}
	}
	public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    };
//	@Override
//    public void onDetach() {
//    	super.onDetach();
//    	try {
//    	    Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//    	    childFragmentManager.setAccessible(true);
//    	    childFragmentManager.set(this, null);
//
//    	} catch (NoSuchFieldException e) {
//    	    throw new RuntimeException(e);
//    	} catch (IllegalAccessException e) {
//    	    throw new RuntimeException(e);
//    	}
//    
//    }
}
