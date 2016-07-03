package com.example.asus.myapplication;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.asus.myapplication.utils.SlyderView;

import java.util.Random;

public class TransferFragment extends BaseFragment {
    public static final String TAG = "transfer";
    private SlyderView slyderView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            slyderView.stop(new Random().nextInt(5));
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.tansfer_fragment;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FloatingActionButton fab = ((MainActivity) getActivity()).fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "888", Snackbar.LENGTH_SHORT).show();
                DialogFragment dialogFragment = new ChooseContentDialog();
                dialogFragment.show(getFragmentManager(), "dialog");
            }
        });
    }

    @Override
    protected void initView() {

    }


}

