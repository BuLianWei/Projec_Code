package com.example.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteOpenHelper是一个辅助类，用来管理数据库的创建和版本他，它提供两个方面的功能
 * 第一，getReadableDatabase()、getWritableDatabase
 * ()可以获得SQLiteDatabase对象，通过该对象可以对数据库进行操作
 * 第二，提供了onCreate()、onUpgrade()两个回调函数，允许我们再创建和升级数据库时，进行自己的操作
 */
public class DBHelper extends SQLiteOpenHelper
{	
	private static final String TAG = "DBAdapter";
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "client.db";
	
	/**
	 * 在SQLiteOpenHelper的子类当中，必须有该构造函数
	 * 
	 * @param context
	 *            上下文对象
	 * @param name
	 *            数据库名称
	 * @param factory
	 * @param version
	 *            当前数据库的版本，值必须是整数并且是递增的状态
	 */
	public DBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// 该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
	@Override
	public	void onCreate(SQLiteDatabase db_db)
	{	
		try {			
			// execSQL用于执行SQL语句
			db_db.execSQL(DayDB.CREATE_TABLE);
			Log.d("test2 oncreate-- " ,DayDB.CREATE_TABLE.toString());
		} 
		catch (Exception e) {
			Log.e("DatabaseHelper " ,e.toString());
			Log.d("test2 1oncreate-- " ,e.toString());
			
			e.printStackTrace();
		}	
	}
	
	@Override
	public	void onUpgrade(SQLiteDatabase db_db, int oldVersion,int newVersion)
	{
		//数据库升级以后，删除老数据
		Log.d(TAG, "Upgrading database from version " + oldVersion+
				" to "+ newVersion + ", which will destroy all old data");			
		
		int version = oldVersion;
		if (version != DATABASE_VERSION) 
		{
			db_db.execSQL("DROP TABLE IF EXISTS " + DayDB.TABLE_NAME);
			onCreate(db_db);
		}
	}

}
