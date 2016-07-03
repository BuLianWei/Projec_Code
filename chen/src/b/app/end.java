package b.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.EditText;
import android.widget.TextView;

public class end extends Activity {
		TextView end;
		private Button close;
		BufferedReader bf;
		String str;
		private Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				end.setShadowLayer(4, 4.0f, 4.0f, Color.LTGRAY);
				end.setText(str);
			}
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
	              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.end);
		end=(TextView)findViewById(R.id.end);
		//close=(Button)findViewById(R.id.close);
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent=new Intent(end.this,GameMainActivity.class);
				startActivity(intent);
				Player.stop();
				end.this.finish();
			}
		});
		th.start();
    StringBuffer buffer = new StringBuffer();
      try {
    	  Resources re=this.getResources();
InputStreamReader isr = new InputStreamReader(re.openRawResource(R.raw.end),"GB2312");
//文件编码Unicode,UTF-8,ASCII,GB2312,Big5
      Reader in = new BufferedReader(isr);
      int ch;
      while ((ch = in.read()) > -1) {
          buffer.append((char)ch);
                            }
      in.close();
      end.setText(buffer.toString());  //buffer.toString())就是读出的内容字符 
      } catch (IOException e) {
    	end.setText("文件不存在!");
      }
} 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(end.this).setTitle("提示").setMessage(
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
}
