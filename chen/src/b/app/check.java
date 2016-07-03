package b.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class check extends Activity{
	private Button login;
	private EditText ed1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
	              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.check);
		login=(Button)findViewById(R.id.login);
		ed1=(EditText)findViewById(R.id.my);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                if(ed1.getText().toString().equals("0303")){
					 Intent intent=new Intent();
			           intent.setClass(check.this,main.class);
			          check.this.startActivity(intent);
			          overridePendingTransition(R.anim.left,R.anim.left);
			         check.this.finish();
                }else{
					Toast.makeText(check.this, "密码错误！",Toast.LENGTH_SHORT).show();
                }
			}
		});
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(check.this).setTitle("提示").setMessage("还未结束，确定退出程序吗？").setIcon(R.drawable.x).setPositiveButton("取消",
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
}
