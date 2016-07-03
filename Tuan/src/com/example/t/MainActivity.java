package com.example.t;

import com.example.tuan.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
	FragmentManager manager;
	FragmentTransaction transaction;

	RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		manager = getSupportFragmentManager();

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		((RadioButton) (radioGroup.findViewById(R.id.radio0)))
				.setClickable(true);
		transaction = manager.beginTransaction();
		TuanGou tuanGou = new TuanGou();
		transaction.replace(R.id.content, tuanGou);
		transaction.commit();
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio0:
					transaction = manager.beginTransaction();
					TuanGou tuanGou = new TuanGou();
					transaction.replace(R.id.content, tuanGou);
					transaction.commit();
					break;

				case R.id.radio1:
					transaction = manager.beginTransaction();
					ShangJia shangJia = new ShangJia();
					transaction.replace(R.id.content, shangJia);
					transaction.commit();
					break;

				case R.id.radio2:
					transaction = manager.beginTransaction();
					WoDe woDe = new WoDe();
					transaction.replace(R.id.content, woDe);
					transaction.commit();
					break;

				case R.id.radio3:
					transaction = manager.beginTransaction();
					GengDuo gengDuo = new GengDuo();

					transaction.replace(R.id.content, gengDuo);
					transaction.commit();
					break;
				}
			}
		});
	}
	
	
}
