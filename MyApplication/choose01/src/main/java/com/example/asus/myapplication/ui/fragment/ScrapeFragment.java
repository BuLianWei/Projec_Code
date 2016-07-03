package com.example.asus.myapplication.ui.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.asus.myapplication.R;
import com.example.asus.myapplication.utils.scrape.GuaGuaKa;

/**
 * Author:   Lianwei Bu
 * Date:     2016/5/7
 * Description:
 */
public class ScrapeFragment extends BaseFragment {
    private GuaGuaKa mGua;
private RelativeLayout mLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.scrape_fagment;
    }

    @Override
    protected void initView() {
//        mGua = (GuaGuaKa) getView().findViewById(R.id.guagua);
        mLayout= (RelativeLayout) getView().findViewById(R.id.layout);
        mLayout.removeAllViews();
        mLayout.addView(new GuaGuaKa(getActivity()));
    }

    @Override
    public void onClick(View v) {
        mLayout.removeAllViews();
        mLayout.addView(new GuaGuaKa(getActivity()));
    }
}
