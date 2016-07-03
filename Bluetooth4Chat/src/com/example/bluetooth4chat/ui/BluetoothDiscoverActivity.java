package com.example.bluetooth4chat.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.bluetooth4chat.R;
import com.example.bluetooth4chat.adapter.DeviceAdapter;
import com.example.bluetooth4chat.receiver.BluetoothReceiver;
import com.example.bluetooth4chat.server.BluetoothChatService;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class BluetoothDiscoverActivity extends BaseActivity {
	private ListView mListView;
	private BluetoothAdapter mBluetooth;
	private BluetoothReceiver mReceiver;

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
		return R.layout.discover;
	}

	@Override
	protected void initView() {
		mListView = (ListView) findViewById(R.id.list);
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText(R.string.device);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BluetoothDevice device = (BluetoothDevice) parent.getAdapter()
						.getItem(position);
				Intent intent = new Intent(BluetoothDiscoverActivity.this,
						ChatActivity.class);
				intent.putExtra("device", device);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void initData() {
		mBluetooth = BluetoothAdapter.getDefaultAdapter();

		if (!mBluetooth.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, 1);
		} else {
			initBluetoothUI();
		}
	}

	private void initBluetoothUI() {
		mBluetooth.startDiscovery();
		List<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
		DeviceAdapter adapter = new DeviceAdapter(this, list);
		mListView.setAdapter(adapter);

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mReceiver = new BluetoothReceiver(adapter);
		registerReceiver(mReceiver, filter);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			initBluetoothUI();
			break;

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
	}
}
