package com.lb.wecharenglish.sortition;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lb.wecharenglish.R;
import com.lb.wecharenglish.ui.activity.BaseActivity;

import java.util.Random;

/**
 * Author:   Lianwei Bu
 * Date:     2016/8/29
 * Description:
 */

public class SortitionActivity extends BaseActivity {
    ImageView img_draw; // 签
    Button btn_begin; // 开始抽签
    AnimationDrawable drawAnimation; // 抽签动画
    private int[] ids = new int[]{R.drawable.draw_lots_stop_02,
            R.drawable.draw_lots_stop_03}; //抽签结果
    private Random random; //产生抽签结果随机数

    @Override
    protected View createView() {
        return View.inflate(mContext,R.layout.sortition,null);
    }


    @Override
    protected void findView() {
        img_draw = (ImageView) findViewById(R.id.img_draw);
//        img_draw.setBackgroundResource(R.drawable.drawlots);
        random = new Random();
        all.setText(R.string.txt_sortition);
        setActionBarDatas(true,getString(R.string.txt_sortition),false,false,null,true,this);
    }

    @Override
    public void onClick(View v) {
        try {
            all.setEnabled(false);
            img_draw.clearAnimation();
//            tryRecycleAnimationDrawable(drawAnimation);
            MyAnimationDrawable.animateRawManuallyFromXML(R.drawable.drawlots, img_draw, new Runnable() {
                @Override
                public void run() {

                }
            }, new Runnable() {
                @Override
                public void run() {
                    int id = ids[random.nextInt(2)];
                    img_draw.setImageResource(id);
                    all.setEnabled(true);
                }
            });


//            drawAnimation = (AnimationDrawable) img_draw.getBackground();
//            // 设置播放后重新启动
//            drawAnimation.stop();  // 结束动画播放
//
//            drawAnimation.start(); // 开始动画播放
//
//
//            int duration = 0; //抽签动画结束标志
//            for (int i = 0; i < drawAnimation.getNumberOfFrames(); i++) {
//                duration += drawAnimation.getDuration(i);
//            }
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    //动画结束后换成结果图片
//                    int id = ids[random.nextInt(2)];
//                    img_draw.setBackgroundResource(id);
////                    tryRecycleAnimationDrawable(drawAnimation);
//                    mActionButton.setEnabled(true);
//                    Log.e("blw", "is");
//                }
//            }, duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryRecycleAnimationDrawable(AnimationDrawable animationDrawable) {
        if (animationDrawable != null) {
            animationDrawable.stop();
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                Drawable frame = animationDrawable.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    ((BitmapDrawable) frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            animationDrawable.setCallback(null);
        }
    }

}
