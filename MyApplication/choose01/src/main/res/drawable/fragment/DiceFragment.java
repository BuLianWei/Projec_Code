package drawable.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.asus.myapplication.R;

import java.util.Random;

public class DiceFragment extends BaseFragment implements Runnable {
    public static final String TAG = "dice";
    private ImageView imageView;
    private int[] ints = {R.mipmap.dice154, R.mipmap.dice142, R.mipmap.dice135, R.mipmap.dice123,
            R.mipmap.dice214, R.mipmap.dice231, R.mipmap.dice246, R.mipmap.dice263,
            R.mipmap.dice365, R.mipmap.dice351, R.mipmap.dice326, R.mipmap.dice312,
            R.mipmap.dice421, R.mipmap.dice451, R.mipmap.dice456, R.mipmap.dice462,
            R.mipmap.dice513, R.mipmap.dice536, R.mipmap.dice541, R.mipmap.dice564,
            R.mipmap.dice625, R.mipmap.dice645, R.mipmap.dice632, R.mipmap.dice653,
    };
    private Random random = new Random();
    private Thread mThread;
    private boolean flag = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setBackgroundResource(ints[random.nextInt(ints.length)]);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.dice_fragment;
    }

    @Override
    protected void initView() {
        imageView = (ImageView) getView().findViewById(R.id.imageView2);
//        imageView.setImageResource(ints[0]);
        mActionButton.setOnClickListener(this);
    }


    @Override
    public void run() {
        while (flag) {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        imageView.clearAnimation();
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, screenH - screenH / 3 - 100);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
        imageView.setAnimation(animation);
    }

    @Override
    public void onResume() {
        super.onResume();
        mThread = new Thread(this);
        flag=true;
        mThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        flag=false;
        mThread=null;
    }

}
