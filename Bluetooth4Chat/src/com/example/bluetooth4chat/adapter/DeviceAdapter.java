package com.example.bluetooth4chat.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.bluetooth4chat.R;

public class DeviceAdapter extends BaseAdapter {
	private Context context;
	private List<BluetoothDevice> list;

	public DeviceAdapter(Context context, List<BluetoothDevice> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BluetoothDevice device = list.get(position);
		if (TextUtils.isEmpty(device.getName())) {
			holder.name.setText(R.string.error_no_name);
		} else {
			holder.name.setText(device.getName());
		}
		holder.address.setText(device.getAddress());
		holder.state.setText(R.string.no_paired);
		switch (device.getBondState()) {
		case BluetoothDevice.BOND_BONDED:
			holder.name.setTextColor(Color.BLACK);
			holder.state.setTextColor(Color.BLACK);
			holder.state.setText(R.string.paired);
			break;
		case BluetoothDevice.BOND_NONE:
			holder.name.setTextColor(Color.RED);
			holder.state.setTextColor(Color.RED);
			holder.state.setText(R.string.no_paired);
			break;
		}
		return convertView;
	}

	public static class ViewHolder {
		private TextView name;
		private TextView state;
		private TextView address;

		public ViewHolder(View itemView) {
			name = ((TextView) itemView.findViewById(R.id.item_name));
			state = ((TextView) itemView.findViewById(R.id.item_state));
			address = ((TextView) itemView.findViewById(R.id.item_address));
		}
	}

	public void add(BluetoothDevice device) {
		if (!list.contains(device)) {
			list.add(device);
			notifyDataSetChanged();
		}
	}
}
