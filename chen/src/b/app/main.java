package b.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class main extends Activity  {
	public static TextView show;
	private Button close;
	BufferedReader bf;
	String str=null;
	InputStream  is;
	InputStreamReader in;
	private Handler handler = new Handler() {
		@Override
		
		public void handleMessage(Message msg) {
		
		// TODO 接收消息并且去更新UI线程上的控件内容
		   show.setShadowLayer(3, 3.0f, 3.0f, Color.BLACK); 
		   show.setText(str);
		super.handleMessage(msg);
		}
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		full();
		setContentView(R.layout.main);
		Player.stop();
		Player.play(this, R.raw.mp3);
		show=(TextView)findViewById(R.id.show);
		 is=getResources().openRawResource(R.raw.show);
		
			 try {
				bf=new BufferedReader(new InputStreamReader(is,"GB2312"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Thread th=new Thread(r);
		th.start();
	}
	private void full() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
	              WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(main.this).setTitle("提示").setMessage(
					"还未结束，确定退出吗?").setIcon(R.drawable.x).setPositiveButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {	
						}
					}).setNegativeButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							finish();
							Player.stop();
						}
					}).show();		
		}
		return super.onKeyDown(keyCode, event);
	}
	Runnable r=new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
		          for (int i = 0; i < 30; i++) {
		               Thread.sleep(3500);
		               try { 
							if(str!=" "){
								str=bf.readLine();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		              
		               Message msg = new Message();
		              handler.sendMessage(msg);
		           }
		          Intent intent=new Intent(main.this,end.class);
					startActivity(intent);
					main.this.finish();
		       } catch (InterruptedException e) {
		
		           e.printStackTrace();
		       }
		}
	};
	}
