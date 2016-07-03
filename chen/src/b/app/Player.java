package b.app;

import android.content.Context;
import android.media.MediaPlayer;

public class Player {
	public static MediaPlayer mp=null;
	public static void play(Context context,int resourcs){
	 mp=MediaPlayer.create(context, resourcs);
     mp.start();
     mp.setLooping(true);
	}
	public static void stop(){
		mp.stop();
	}
}
