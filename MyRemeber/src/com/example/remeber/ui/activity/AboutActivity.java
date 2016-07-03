package com.example.remeber.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.remeber.R;

public class AboutActivity extends BaseActivity {
	private TextView titleTV;
	private TextView versionTV;
	private ImageView backIV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		titleTV = (TextView) findViewById(R.id.bar_title);
		versionTV = (TextView) findViewById(R.id.about_version);
		backIV=(ImageView) findViewById(R.id.bar_icon);
		titleTV.setText(R.string.about);
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			versionTV.setText(getString(R.string.version)+info.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backIV.setVisibility(View.VISIBLE);
		backIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
		
	}
}
