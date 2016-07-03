package com.example.t;

import com.example.tuan.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class XinDan extends Activity {
TextView tv02;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xindan);
		tv02=(TextView) findViewById(R.id.bar_tv02);
		tv02.setVisibility(View.GONE);
	}

}
