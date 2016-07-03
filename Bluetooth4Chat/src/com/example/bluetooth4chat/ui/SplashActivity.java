package com.example.bluetooth4chat.ui;

import android.os.Handler;
import android.view.View;

import com.example.bluetooth4chat.R;

/**SplashActivity 
 * @author asus
 *
 */
public class SplashActivity extends BaseActivity implements Runnable{
	private Handler mHandler;
	
	@Override
	protected boolean isTransition() {
		return true;
	}
	@Override
	protected Transiton getTranstion() {
		return Transiton.SCALE;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.splash;
	}


	@Override
	protected void initView() {
		mHandler = new Handler();
		//延时切换Activity
		mHandler.postDelayed(this, 1000*3);
	}
	@Override
	protected void initData() {
		
		
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void run() {
		toActivityWithFinish(BluetoothDiscoverActivity.class);
	}

}
