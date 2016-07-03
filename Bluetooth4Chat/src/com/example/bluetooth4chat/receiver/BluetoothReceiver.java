package com.example.bluetooth4chat.receiver;

import com.example.bluetooth4chat.adapter.DeviceAdapter;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothReceiver extends BroadcastReceiver {
	private DeviceAdapter mAdapter;

	public BluetoothReceiver(DeviceAdapter mAdapter) {
		this.mAdapter = mAdapter;
	}

	public BluetoothReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		switch (action) {
		case BluetoothDevice.ACTION_FOUND:
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			mAdapter.add(device);
			break;

		default:
			break;
		}
	}

}
