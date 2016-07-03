package com.example.t;

import com.example.tool.Globle;
import com.example.tool.IsNetWork;
import com.example.tuan.R;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Global;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class BaseActivity extends Activity implements OnClickListener {
	
	private Handler handler;
	private Thread thread;


	protected void initView() {
	};
    protected void threadToDo(){
    };
	protected void threadStart() {
		thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				if (IsNetWork.isNetworkEnabled(getApplicationContext())
						&& IsNetWork
								.isNetworkConnected(getApplicationContext())) {
					Message msg4 = Message.obtain();
					msg4.what = Globle.TO1;// ÌבÊ¾
					handler.sendMessage(msg4);
					threadToDo();
				}
			}
		};
		thread.start();

	}
   
	protected void initHandler() {
		handler = new Handler() {
			@Override
			public void dispatchMessage(Message msg) {
				handlerToDo( msg);
				
			}
		};
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}
	 protected void handlerToDo(Message msg){
	    	switch (msg.what) {

			case Globle.TO1:
				Toast.makeText(getApplicationContext(),
						R.string.tishi_noconnection, 1).show();

				break;
	    	}
	    };
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		thread = null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
	}
}
