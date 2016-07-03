package com.example.t;

import com.example.tool.Globle;
import com.example.tool.IsNetWork;
import com.example.tuan.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BaseFragment extends Fragment implements OnClickListener {
	private Handler handler;
	private Thread thread;

	protected void initView() {
	};

	protected void threadToDo() {
	};

	protected void threadStart() {
		thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				if (IsNetWork.isNetworkEnabled(getActivity())
						&& IsNetWork
								.isNetworkConnected(getActivity())) {
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
				handlerToDo(msg);

			}
		};
	}

	@SuppressLint("NewApi")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getChildFragmentManager().beginTransaction().addToBackStack(null);
		}
		return true;
	}

	protected void handlerToDo(Message msg) {
		switch (msg.what) {

		case Globle.TO1:
			Toast.makeText(getActivity(),
					R.string.tishi_noconnection, 1).show();

			break;
		}
	};

	

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		thread=null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
