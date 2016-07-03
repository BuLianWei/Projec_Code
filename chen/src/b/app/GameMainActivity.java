package b.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class GameMainActivity  extends Activity
{
    private Love love;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Player p=new Player();
       // p.play(GameMainActivity.this, );
        
        this.love = new Love(this);
        setContentView(love);
        Player p1=new Player();
        p1.play(GameMainActivity.this, R.raw.rain);
        
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(GameMainActivity.this).setTitle("��ʾ").setMessage(
					"ȷ���˳���?").setIcon(R.drawable.x).setPositiveButton("ȡ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {	
						}
					}).setNegativeButton("ȷ��",
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
