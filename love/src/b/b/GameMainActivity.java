package b.b;



//import b.app.Player;
//import b.app.R;
//import b.app.check;
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
        this.love = new Love(this);
        setContentView(love);
        
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(GameMainActivity.this).setTitle("��ʾ").setMessage("ȷ���˳�������").setIcon(R.drawable.ic_launcher).setPositiveButton("ȡ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {	
						}
					}).setNegativeButton("ȷ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							finish();
							//Player.stop();
						}
					}).show();		
		}
		return super.onKeyDown(keyCode, event);
		}
}
