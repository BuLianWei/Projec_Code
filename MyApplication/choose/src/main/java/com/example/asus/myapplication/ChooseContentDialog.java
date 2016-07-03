package com.example.asus.myapplication;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.myapplication.utils.LineColorPicker;
import com.example.asus.myapplication.utils.OnColorChangedListener;

public class ChooseContentDialog extends DialogFragment implements View.OnClickListener{
    private TextView colorTV;
    private TextView fontTV;
    private LinearLayout colorLayout;
    private LinearLayout fontLayout;
    private Button saveBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return inflater.inflate(R.layout.choosecontent_dialog,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        WindowManager windowManager=getDialog().getWindow().getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams layoutParams=getDialog().getWindow().getAttributes();
        layoutParams.height=(int)(displayMetrics.heightPixels*0.5);
        layoutParams.width=(int)(displayMetrics.widthPixels*0.8);
        getDialog().getWindow().setAttributes(layoutParams);
        LineColorPicker colorPicker = (LineColorPicker) getView().findViewById(R.id.picker);
        final LinearLayout add = (LinearLayout) getView().findViewById(R.id.layout_add);

        // set color palette
        colorPicker.setColors(new int[]{
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.DKGRAY
        });
        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                final TextView textView = new TextView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        60, LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);

                textView.setBackgroundColor(c);
                // add.addView(textView);
                add.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        add.removeView(v);
                    }
                });
            }
        });
         colorTV= (TextView) getView().findViewById(R.id.tv_color);
         fontTV= (TextView) getView().findViewById(R.id.tv_font);
         colorLayout= (LinearLayout) getView().findViewById(R.id.layout_color);
         fontLayout= (LinearLayout) getView().findViewById(R.id.layout_font);
        colorLayout.setVisibility(View.GONE);
        fontLayout.setVisibility(View.GONE);
        saveBtn= (Button) getView().findViewById(R.id.btn_save);
        colorTV.setOnClickListener(this);
        fontTV.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_color:
                colorLayout.setVisibility(View.VISIBLE);
                fontLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_font:
                colorLayout.setVisibility(View.GONE);
                fontLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_save:
                ChooseContentDialog.this.dismiss();
                break;
        }
    }
}
