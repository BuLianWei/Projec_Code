package com.example.bluetooth4chat.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluetooth4chat.R;
import com.example.bluetooth4chat.adapter.ChatAdapter;
import com.example.bluetooth4chat.mode.Constants;
import com.example.bluetooth4chat.mode.Msg;
import com.example.bluetooth4chat.server.BluetoothChatService;

public class ChatActivity extends BaseActivity {

	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;
	private String mConnectedDeviceName = null;
	private StringBuffer mOutStringBuffer;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothChatService mChatService = null;

	BluetoothDevice device;
	RecyclerView recyclerView;
	EditText editText;
	Button button;
	ChatAdapter mAdapter;
	RecyclerView.LayoutManager mLayoutManager;
	int icon_left = R.drawable.head_left;
	int icon_right = R.drawable.head_right;
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					mTitle.setTextColor(Color.WHITE);
					break;
				case BluetoothChatService.STATE_CONNECTING:
					mTitle.setTextColor(Color.WHITE);
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					mTitle.setTextColor(Color.RED);
					showMsg(R.string.not_connected);
					break;
				}
				break;
			case Constants.MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				Msg writeMsg = new Msg(writeMessage, Msg.TYPE_SENT, icon_right);
				mAdapter.addMsg(writeMsg);
				recyclerView
						.smoothScrollToPosition(mAdapter.getItemCount() - 1);
				// mConversationArrayAdapter.add("Me:  " + writeMessage);
				break;
			case Constants.MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
				Msg readMsg = new Msg(readMessage, Msg.TYPE_RECEIVED, icon_left);
				mAdapter.addMsg(readMsg);
				recyclerView
						.smoothScrollToPosition(mAdapter.getItemCount() - 1);
				break;
			case Constants.MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(
						Constants.DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case Constants.MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(Constants.TOAST),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected boolean isTransition() {
		return false;
	}

	@Override
	protected Transiton getTranstion() {
		return Transiton.LEFT;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.chat;
	}

	@Override
	protected void initView() {
		device = getIntent().getParcelableExtra("device");
		recyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

		editText = (EditText) findViewById(R.id.input_text);
		button = (Button) findViewById(R.id.send);
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText(device.getName());
		mTitle.setOnClickListener(this);
		button.setOnClickListener(this);
		mAdapter = new ChatAdapter(this);

		recyclerView.setAdapter(mAdapter);
		mLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(mLayoutManager);
		initMsgs();
		mChatService = new BluetoothChatService(this, mHandler);
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	protected void initData() {
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.send) {
			String content = editText.getText().toString();
			// int imageId = R.drawable.ic_launcher;
			// Msg msg = new Msg(content, Msg.TYPE_SENT, imageId);
			// mAdapter.addMsg(msg);
			if (content.equals("")) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
				startActivityForResult(intent, 1);
			}
			sendMessage(content);
			editText.setText("");

		} else if (v.getId() == R.id.title) {
			// if (mChatService.getState()==BluetoothChatService.STATE_NONE) {
			mChatService.connect(device, true);
			// }
		}
	}

	private void initMsgs() {
		Msg msg1 = new Msg("Hello", Msg.TYPE_RECEIVED, icon_left);
		mAdapter.addMsg(msg1);
		Msg msg2 = new Msg("Hello", Msg.TYPE_SENT, icon_right);
		mAdapter.addMsg(msg2);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mChatService != null) {
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				mChatService.start();
			}
		}
	}

	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	private void sendMessage(String message) {
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(getApplicationContext(), R.string.not_connected,
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (message.length() > 0) {
			byte[] send = message.getBytes();
			mChatService.write(send);

			mOutStringBuffer.setLength(0);
		}
	}

	private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId,
				KeyEvent event) {
			// message
			if (actionId == EditorInfo.IME_NULL
					&& event.getAction() == KeyEvent.ACTION_UP) {
				String message = view.getText().toString();
				sendMessage(message);
			}
			return true;
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_CONNECT_DEVICE_INSECURE:
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, false);
			}
			break;
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
			} else {
				Log.d(TAG, "BT not enabled");
				Toast.makeText(getApplicationContext(),
						R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private void connectDevice(Intent data, boolean secure) {
		mChatService.connect(device, secure);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mChatService != null) {
			mChatService.stop();
		}
	}

}
