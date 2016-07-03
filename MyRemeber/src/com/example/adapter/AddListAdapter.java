package com.example.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.bean.AddBean;
import com.example.remeber.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddListAdapter extends ArrayAdapter<AddBean> {
	private Context context;
	private LayoutInflater inflater;
	private List<AddBean> beans;
	private class ViewHolder{
		TextView content;
		TextView date;
		TextView money;
		ImageView icon;
	}
	public AddListAdapter(Context context, List<AddBean> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.beans=objects;
		this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return beans.size();
	}

	@Override
	public AddBean getItem(int position) {
		// TODO Auto-generated method stub
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		AddBean bean=beans.get(position);
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.add_record_item,parent,false);
			holder=new ViewHolder();
			holder.content=(TextView) convertView.findViewById(R.id.add_item_content);
			holder.date=(TextView) convertView.findViewById(R.id.add_item_date);
			holder.money=(TextView) convertView.findViewById(R.id.add_item_money);
			holder.icon=(ImageView) convertView.findViewById(R.id.add_item_icon);
			convertView.setTag(holder);
		} else {
			holder=(ViewHolder) convertView.getTag();
		}
		if (bean!=null) {
			BitmapDrawable bDrawable=null;
			if (bean.getType()==0) {
				bDrawable=(BitmapDrawable) context.getResources().getDrawable(R.drawable.income_icon);
			} else {
				bDrawable=(BitmapDrawable) context.getResources().getDrawable(R.drawable.expenditure_icon);
			}
			holder.icon.setImageBitmap(bDrawable.getBitmap());
			if (bean.getIcon()!=null) {
				bDrawable=new BitmapDrawable(bean.getIcon());
			}else if(bean.getType()==0){
				bDrawable=(BitmapDrawable) context.getResources().getDrawable(R.drawable.income);
			}else {
				bDrawable=(BitmapDrawable) context.getResources().getDrawable(R.drawable.expenditure);
			}
			holder.icon.setBackgroundDrawable(bDrawable);
			holder.content.setText(bean.getContent());
			SimpleDateFormat sFormat=new SimpleDateFormat("MM/dd HH:mm");
			holder.date.setText(sFormat.format(bean.getDate()));
			if (bean.getType()==0) {
				holder.money.setTextColor(Color.GREEN);
			} else {
				holder.money.setTextColor(Color.RED);
			}
			
			holder.money.setText("￥"+new DecimalFormat("#.00").format(bean.getMoney()));
		}
		return convertView;
	}

}
