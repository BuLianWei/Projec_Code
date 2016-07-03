package com.example.asus.myapplication.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.asus.myapplication.R;
import com.example.asus.myapplication.adapter.GuideAdapter;

import me.relex.circleindicator.CircleIndicator;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/21
 * Description:
 */
public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    @Override
    protected void initData() {
        if (!getBooleanForPref("isFirst")){
            startActivity(SplashActivity.class);
            finish();
        }

    }


    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setAdapter(new GuideAdapter(this));
        indicator.setViewPager(mViewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.guide_activity;
    }

    @Override
    public void onClick(View v) {
    }
}
