package com.example.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public abstract class BaseDB 
{
	private static final int LOAD_DB_MAG_ID = 0x01;
	private static final int SAVE_DB_MAG_ID = 0x02;
	public static final int CUSTOM_MSG_START = SAVE_DB_MAG_ID+1;
	
	protected DBHelper mDBHelper; 
	protected DBHandler mHandler = null;
	private OnDatabaseHandlerListener mLoadDBListener = null;
	private OnDatabaseHandlerListener mSaveDBListener = null;

	protected final static byte[] _singleLock = new byte[0];
	protected final static byte[] _writeLock = new byte[0];
	
	protected abstract boolean deleteRow(Object object);
	protected abstract String getTableName();
	public abstract ContentValues getContentValuesByData(Object data);
	public abstract Object getDataByCursor(Cursor cursor);

	public BaseDB(Context ctx, boolean isCreateFromUiThread)
	{ 
		synchronized (_singleLock)
        {
			if(isCreateFromUiThread)
			{
				mHandler = new DBHandler();
			}
    		if(mDBHelper == null)
    		{
    		    mDBHelper = new DBHelper(ctx); 		
    		}
    		open();
        }
	}   
	
	// 只有调用了DatabaseHelper的getWritableDatabase()方法或者getReadableDatabase()方法之后，才会创建或打开一个连接
	private void open()
	{	
	    synchronized (_writeLock)
        {
            try
            {
                mDBHelper.getReadableDatabase();
            }
            catch (Exception e)
            {

            }
            
            try
            {
                mDBHelper.getWritableDatabase();
            }
            catch (Exception e)
            {
            }
        }		
	}
	
	public void close()
	{
	    synchronized (_writeLock)
        {
	        mDBHelper.close();
        }
	}
	
	public boolean deleteAllRows( )
    {    
	    synchronized (_writeLock)
        {
	        return mDBHelper.getWritableDatabase().delete(getTableName(), null, null) > 0;
        }
    }
	
	
	public Cursor getAllRows() throws SQLException
	{
		Cursor mCursor =mDBHelper.getReadableDatabase().query(true, 
				getTableName(),
				null,
				null,
				null,
				null,
				null,
				null,
				null);			
		return mCursor;	
	}
	
	public long insertRow(Object data)
	{
		long ret = -1;
		if(data != null)
		{
			ContentValues initialValues =  getContentValuesByData(data);
//			if(mDBHelper.getWritableDatabase().insert(getTableName(), null, initialValues)>0)
			SQLiteDatabase db=mDBHelper.getWritableDatabase();
			int a=db.insert(getTableName(), null, initialValues)>0?1:0;
			if(a>0)
	    	{
				ret = 1;	
	    	}
	    	else 
	    	{
				ret = -1;
			}
		}
    	return ret;
	}
	
	private boolean loadDataFormDBEnter(ArrayList<Object> list, String selection)
	{
		boolean ret = true;
		onLoadDataFormDBbefore();
		if(list != null)
		{
			Cursor contactCursor  =  null;
	    	try 
	    	{
	    		 contactCursor  = mDBHelper.getReadableDatabase().query(true, 
						getTableName(),
						null,
						selection,
						null,
						null,
						null,
						DayDB.DAY_DATE+" desc ",
						null);
	    		if(contactCursor != null)
	    		{
		    		if(contactCursor.moveToFirst())
		    		{
		    			do 
		    			{
		    				Object data = getDataByCursor(contactCursor);
							if(data != null)
							{
								list.add(data);
							}
						} while (contactCursor.moveToNext());
		    		}
	    		}
			} catch (Exception e) 
			{
				ret = false;
				e.printStackTrace();
			}
	    	finally
	    	{
	    		if(contactCursor != null){
	    			contactCursor.close();
	    		}
	    	}
		}
		onLoadDataFormDBAfter();
		return ret;
	}
	
	public void loadDataFromDB(String selection, OnDatabaseHandlerListener listener)
	{
		mLoadDBListener = listener;
		if(mHandler != null)
		{
			final String fSelection = selection;
			new Thread()
			{
				@Override
				public void run() 
				{
					super.run();
					ArrayList<Object> list = new ArrayList<Object>();
					boolean isSuccess = loadDataFormDBEnter(list, fSelection);
					
		    		Message msg = Message.obtain();
		    		msg.what = LOAD_DB_MAG_ID;
		    		msg.obj = list;
		    		msg.arg1 = isSuccess?1:0;
					mHandler.sendMessage(msg);
				}
			}.start();
		}
		else
		{
			ArrayList<Object> list = new ArrayList<Object>();
			boolean isSuccess = loadDataFormDBEnter(list, selection);
			if(listener != null)
			{
				listener.onDatabaseHandler(list, isSuccess);
			}
		}
	}
	
	private boolean saveDataToDBEnter(ArrayList<Object> list)
	{
		boolean ret = true;
		onSaveDataToDBbefore();
		if(list != null)
		{
			try 
			{
				for(int i=0; i<list.size(); i++)
				{
					insertRow(list.get(i));
				}
			}
			catch (Exception e) 
			{
				ret = false;
				e.printStackTrace();
			}
		}
		onSaveDataToDBAfter();
		return ret;
	}
	
	//将一组对象数据保存到数据库，异步方法，操作结束以后通过listener接口通知调用者
	public void saveDataToDB(ArrayList<Object> list, OnDatabaseHandlerListener listener)
	{
		if(list!=null && list.size()>0)
		{
			mSaveDBListener = listener;
			final ArrayList<Object> fList = list;
			if(mHandler != null)
			{
				new Thread()
				{
					@Override
					public void run() {
						super.run();
						boolean isSuccess = true;
						isSuccess = saveDataToDBEnter(fList);
						
						Message msg = Message.obtain();
			    		msg.what = SAVE_DB_MAG_ID;
			    		msg.obj = null;
			    		msg.arg1 = isSuccess?1:0;
						mHandler.sendMessage(msg);
					}
				}.start();
			}
			else 
			{
				boolean isSuccess = true;
				isSuccess = saveDataToDBEnter(fList);
				if(listener!=null)
				{
					listener.onDatabaseHandler(null, isSuccess);
				}
			}
		}
	}
	
	protected void onDBHandleMessage(Message msg)
	{
	}
	
	protected void onLoadDataFormDBbefore()
	{
	}
	protected void onLoadDataFormDBAfter()
	{
	}
	protected void onSaveDataToDBbefore()
	{
	}
	protected void onSaveDataToDBAfter()
	{
	}
	
	class DBHandler extends Handler
	{
		@Override
		public void dispatchMessage(Message msg) 
		{
			switch (msg.what) 
			{
			case LOAD_DB_MAG_ID:
				if(mLoadDBListener != null)
				{
					boolean isSuccess = (msg.arg1>=1)?true:false;
					ArrayList<Object> list = (ArrayList<Object>)(msg.obj);
					mLoadDBListener.onDatabaseHandler(list, isSuccess);
				}
				break;
			case SAVE_DB_MAG_ID:
				if(mSaveDBListener != null)
				{
					boolean isSuccess = (msg.arg1>=1)?true:false;
					mSaveDBListener.onDatabaseHandler(null, isSuccess);
				}
				break;

			default:
				onDBHandleMessage(msg);
				break;
			}
			
			super.handleMessage(msg);
		}
	}
	
	public interface OnDatabaseHandlerListener
	{
		boolean onDatabaseHandler(ArrayList<Object> dataList, boolean isSuccess);
	}
}
