package com.example.asus.myapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.myapplication.ui.activity.BaseActivity;
import com.example.asus.myapplication.R;
import com.example.asus.myapplication.ui.activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/21
 * Description:
 */
public class GuideAdapter extends PagerAdapter {
    private List mList;
    private BaseActivity mContext;

    public GuideAdapter(BaseActivity context) {
        mContext = context;
        mList = new ArrayList();
        mList.add(R.mipmap.one);
        mList.add(R.mipmap.two);
        mList.add(R.mipmap.three);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.guide_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.item);
        TextView textView = (TextView) view.findViewById(R.id.jump);
        imageView.setBackgroundResource((Integer) mList.get(position));
        if (position == mList.size() - 1)
            textView.setVisibility(View.VISIBLE);
        else
            textView.setVisibility(View.GONE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.setBooleanToPref("isFirst", false);
                mContext.startActivity(SplashActivity.class);
                mContext.finish();
            }
        });
        ((ViewPager) container).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
