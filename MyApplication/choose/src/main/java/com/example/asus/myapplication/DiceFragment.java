package com.example.asus.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.example.asus.myapplication.utils.DiceSurfaceView;
import java.util.Random;

public class DiceFragment extends BaseFragment implements Runnable {
    public static final String TAG = "dice";
    private ImageView imageView;
    private int[] ints;
    private boolean flag = true;
    int i = 0;
    private Random random = new Random();
    DisplayMetrics dm = new DisplayMetrics();
    int screenW ;// 获取屏幕分辨率宽度
    int screenH ;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {

                imageView.setBackgroundResource(ints[random.nextInt(ints.length)]);
                imageView.clearAnimation();
                int padding=100;
                int toX=random.nextInt(screenW-padding);
                int toY = random.nextInt(screenH-padding);
                TranslateAnimation animation=new TranslateAnimation(0,toX,0,toY);
                animation.setDuration(500);
//                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setFillAfter(true);
                imageView.setAnimation(animation);
            } else {
                imageView.setBackgroundResource(ints[random.nextInt(ints.length)]);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.dice_fragment;
    }

    @Override
    protected void initView() {
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//         screenW = dm.widthPixels;// 获取屏幕分辨率宽度
//         screenH = dm.heightPixels;
//        imageView = (ImageView) getView().findViewById(R.id.imageView2);
//        ints = new int[]{R.mipmap.dice123, R.mipmap.dice135, R.mipmap.dice142, R.mipmap.dice154,
//                R.mipmap.dice214, R.mipmap.dice231, R.mipmap.dice246, R.mipmap.dice263,
//                R.mipmap.dice312, R.mipmap.dice326, R.mipmap.dice351, R.mipmap.dice365,
//                R.mipmap.dice421, R.mipmap.dice451, R.mipmap.dice456, R.mipmap.dice462,
//                R.mipmap.dice513, R.mipmap.dice536, R.mipmap.dice541, R.mipmap.dice564,
//                R.mipmap.dice625, R.mipmap.dice632, R.mipmap.dice645, R.mipmap.dice625};
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (i % 2 == 0) {
//                    flag = true;
//                   Thread thread= new Thread(DiceFragment.this);
//                    thread.start();
//
//                } else {
//                    flag = false;
//                }
//
//                i++;
//            }
//
//        });
    }


    @Override
    public void run() {

        while (flag) {
            mHandler.sendEmptyMessage(0);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        mGLView = new DiceSurfaceViewew(this);
//        setContentView(mGLView);
        new DiceSurfaceView(getActivity());
        return new DiceSurfaceView(getActivity());
    }
}
