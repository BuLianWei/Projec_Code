package b.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("HandlerLeak")
public class chen extends Activity {
	ImageView img1=null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.in, R.anim.out);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
	              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start);
		img1=(ImageView)findViewById(R.id.img1);
		
		Player.play(chen.this,R.raw.hanppy);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(chen.this).setTitle("提示").setMessage("还未结束，确定退出程序吗？").setIcon(R.drawable.x).setPositiveButton("取消",
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
	AnimationDrawable   anim=new AnimationDrawable();
	public  void startBtn(View v)
	{
      for(int i=0;i<8;i++)
      {
    	  anim.addFrame(this.getResources().getDrawable(R.drawable.market_1_9_01+i), 100);
      }
         new jump();
		anim.setOneShot(false);
		img1.setBackgroundDrawable(anim);
		if(anim.isRunning())
		{
			anim.stop();
		}else
		{
			anim.stop();
			anim.start();
		}
	}
    class jump {
    	public jump(){
    	new CountDownTimer(13000,1000) {

	  		public void onTick(long millisUntilFinished) {
	  		}
	  		public void onFinish() {
	  		Intent intent = new Intent();
	  		intent.setClass(chen.this, check.class);
	  		startActivity(intent);

	  		int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
	  		if(VERSION >= 5){
	  		chen.this.overridePendingTransition(R.anim.in, R.anim.out);
	  		}
	  		finish();
	  		}
	  		}.start();
	  		}
    }
	
    
}
  
	

