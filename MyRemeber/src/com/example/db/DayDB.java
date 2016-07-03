package com.example.db;

import com.example.bean.AddBean;
import com.example.bean.BaseBean;
import com.example.bean.DayBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DayDB extends BaseDB {
	public static final String TABLE_NAME = "daytable";
	private AddBean bean;
	public static final String DAY_ID = "_id";
	public static final String DAY_DATE = "day_date";
	public static final String DAY_TYPE = "day_type";
	public static final String DAY_MONEY = "day_money";
	public static final String DAY_CONTENT = "day_content";
	public static final String DAY_ICONSTRING = "day_iconstring";
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  "
			+ TABLE_NAME + " ( " + DAY_ID
			+ " integer primary key autoincrement, " + DAY_CONTENT + " text ,"
			+ DAY_DATE + " long," + DAY_ICONSTRING + " text, " + DAY_MONEY
			+ " long," + DAY_TYPE + " int )";
	

	public DayDB(Context ctx) {
		super(ctx, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	public ContentValues getContentValuesByData(Object data) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		if (data != null) {
			bean = (AddBean) data;
			values.put(DAY_CONTENT, bean.getContent());
			values.put(DAY_DATE, bean.getDate());
			values.put(DAY_ICONSTRING, bean.getIconString());
			values.put(DAY_MONEY, bean.getMoney());
			values.put(DAY_TYPE, bean.getType());
		}
		return values;
	}

	@Override
	public Object getDataByCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		if (cursor==null) {
			return null;
		}
		AddBean bean=new AddBean();
		bean.setContent(cursor.getString(cursor.getColumnIndex(DAY_CONTENT)));
		bean.setDate(cursor.getLong(cursor.getColumnIndex(DAY_DATE)));
		bean.setIconString(cursor.getString(cursor.getColumnIndex(DAY_ICONSTRING)));
		bean.setMoney(cursor.getFloat(cursor.getColumnIndex(DAY_MONEY)));
		bean.setType(cursor.getInt(cursor.getColumnIndex(DAY_TYPE)));
		return bean;
	}

	@Override
	public boolean deleteRow(Object object) {
		// TODO Auto-generated method stub
		synchronized (_writeLock)
		{
			return mDBHelper.getWritableDatabase().delete(getTableName(), ""+DAY_DATE+"=?", new String[]{object.toString()}) > 0;
		}
	}

}
