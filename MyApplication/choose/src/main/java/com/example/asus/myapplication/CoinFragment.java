package com.example.asus.myapplication;

import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.myapplication.utils.coin.TossAnimation;
import com.example.asus.myapplication.utils.coin.TossImageView;

import java.util.Random;

public class CoinFragment extends BaseFragment implements View.OnClickListener {
public static final String TAG="coin";
    private TossImageView mTossImageView;
    private RelativeLayout mRelativeLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.coin_fragment;
    }

    @Override
    protected void initView() {
        mRelativeLayout=(RelativeLayout) getView().findViewById(R.id.rLayout);
        mTossImageView=new TossImageView(getActivity());
        mTossImageView.setImageResource(R.mipmap.front);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width=400;
        layoutParams.height=400;
        mTossImageView.setLayoutParams(layoutParams);
        mRelativeLayout.addView(mTossImageView);
        TextView show= (TextView) getView().findViewById(R.id.show);
        show.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        mTossImageView.cleareOtherAnimation();

        TranslateAnimation translateAnimation0 = new TranslateAnimation(0, 0, 0, -400);
        translateAnimation0.setDuration(3000);
        TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0, 0, 400);
        translateAnimation1.setDuration(3000);
        translateAnimation1.setStartOffset(3000);
Random random=new Random();
        int i=random.nextInt(2);
        mTossImageView.setInterpolator(new DecelerateInterpolator())
                .setDuration(6000)
                .setCircleCount(40)
                .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                .setYAxisDirection(TossAnimation.DIRECTION_NONE)
                .setZAxisDirection(TossAnimation.DIRECTION_NONE)
                .setResult( i== 0 ? TossImageView.RESULT_FRONT : TossImageView.RESULT_REVERSE);
        Log.d("blw ",i+"");
        mTossImageView.addOtherAnimation(translateAnimation0);
        mTossImageView.addOtherAnimation(translateAnimation1);

        mTossImageView.startToss();
    }
}
