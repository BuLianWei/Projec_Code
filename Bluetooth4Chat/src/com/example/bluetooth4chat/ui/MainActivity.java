package com.example.bluetooth4chat.ui;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.bluetooth4chat.R;

/**
 * 文件传递和聊天模块的选择类
 * 
 * @author asus
 *
 */
public class MainActivity extends BaseActivity {
	private ImageView mLeftIV;
	private ImageView mRightIV;
	LayoutParams pLeft;
	LayoutParams pRight;

	public interface onThreadAfter {
		void onAfter();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mLeftIV.setLayoutParams(pLeft);
			mRightIV.setLayoutParams(pRight);

		};
	};

	@Override
	protected boolean isTransition() {
		return true;
	}

	@Override
	protected Transiton getTranstion() {
		return Transiton.FADE;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.main;
	}

	@Override
	protected void initView() {
		mLeftIV = (ImageView) findViewById(R.id.left);
		mRightIV = (ImageView) findViewById(R.id.right);
		mTitle=(TextView) findViewById(R.id.title);
		mTitle.setText(R.string.main);
		mLeftIV.setOnClickListener(this);
		mRightIV.setOnClickListener(this);
		pLeft = (LayoutParams) mLeftIV.getLayoutParams();
		pRight = (LayoutParams) mRightIV.getLayoutParams();
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		mLeftIV.setEnabled(false);
		mRightIV.setEnabled(false);
		switch (v.getId()) {
		case R.id.left:
			startAnimation(pLeft, pRight, new onThreadAfter() {

				@Override
				public void onAfter() {
					toActivityWithFinish(BluetoothDiscoverActivity.class);
				}
			});
			break;
		case R.id.right:
			startAnimation(pRight, pLeft, new onThreadAfter() {

				@Override
				public void onAfter() {
					// toActivityWithFinish(BluetoothDiscoverActivity.class);
				}
			});
			break;

		}
	}

	private void startAnimation(final LayoutParams p1, final LayoutParams p2,
			final onThreadAfter oAfter) {
		new Thread() {
			public void run() {
				while (p1.width < mScreenWidth) {
					p1.width += 10;
					p2.width -= 10;
					mHandler.sendEmptyMessage(0);
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				oAfter.onAfter();
			};
		}.start();
	}

}
