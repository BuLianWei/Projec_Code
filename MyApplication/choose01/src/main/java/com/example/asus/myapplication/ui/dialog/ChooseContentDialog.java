package com.example.asus.myapplication.ui.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.myapplication.R;
import com.example.asus.myapplication.utils.color.LineColorPicker;
import com.example.asus.myapplication.utils.color.OnColorChangedListener;
import com.example.asus.myapplication.utils.pan.MySurfaceView;

import java.util.ArrayList;
import java.util.List;

public class ChooseContentDialog extends DialogFragment implements View.OnClickListener {
    private TextView colorTV;
    private TextView fontTV;
    private LinearLayout colorLayout;
    private LinearLayout fontLayout;
    private EditText editET;
    private Button saveBtn;
    private int[] colors;
    private MySurfaceView mMySurfaceView;
    private List<Integer> mColorsList;
    int[] color = new int[1];
    private int index = 0;//0是color；1是font

    public static ChooseContentDialog newInstance(MySurfaceView mySurfaceView) {
        ChooseContentDialog chooseContentDialog = new ChooseContentDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("view", mySurfaceView);
        chooseContentDialog.setArguments(bundle);
        return chooseContentDialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("view") != null) {
                mMySurfaceView = (MySurfaceView) bundle.getSerializable("view");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mColorsList = new ArrayList<>();
        return inflater.inflate(R.layout.choosecontent_dialog, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        colors = getResources().getIntArray(R.array.choose_color);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = getDialog().getWindow().getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.height = (int) (displayMetrics.heightPixels * 0.5);
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.8);
        getDialog().getWindow().setAttributes(layoutParams);
        LineColorPicker colorPicker = (LineColorPicker) getView().findViewById(R.id.picker);
        final LinearLayout add = (LinearLayout) getView().findViewById(R.id.layout_add);

        // set color palette
        colorPicker.setColors(colors);
//        colorPicker.setColors(new int[]{
//                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.DKGRAY,
//                Color.GRAY, Color.LTGRAY,
//        });
        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(final int c) {
                final TextView textView = new TextView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        60, LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                textView.setBackgroundColor(c);
                // add.addView(textView);
                mColorsList.add(c);
                add.addView(textView);
                adColor(c);

                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        add.removeView(v);
//                        color[v.getId()]=0;
                    }
                });
            }
        });
        colorTV = (TextView) getView().findViewById(R.id.tv_color);
        fontTV = (TextView) getView().findViewById(R.id.tv_font);
        colorLayout = (LinearLayout) getView().findViewById(R.id.layout_color);
        fontLayout = (LinearLayout) getView().findViewById(R.id.layout_font);
        colorLayout.setVisibility(View.GONE);
        fontLayout.setVisibility(View.GONE);
        saveBtn = (Button) getView().findViewById(R.id.btn_save);
        editET = (EditText) getView().findViewById(R.id.et_fontcontent);
        colorTV.setOnClickListener(this);
        fontTV.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    private void adColor(int c) {
        int[] col = new int[color.length + 1];
        for (int i = 0; i < col.length; i++) {
            if (i == col.length - 1)
                col[i] = c;
            else
                col[i] = color[i];
        }
        color = col;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_color:
                index = 0;
                colorLayout.setVisibility(View.VISIBLE);
                fontLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_font:
                index = 1;
                colorLayout.setVisibility(View.GONE);
                fontLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_save:
                if (index == 0) {
                    if (mColorsList.size() == 0)
                        return;
                    int[] c = new int[mColorsList.size()];
                    for (int i = 0; i < mColorsList.size(); i++) {
                        c[i] = mColorsList.get(i);
                    }
                    mMySurfaceView.setColors(c);

                } else {
                    String s = editET.getText().toString().trim();
                    String[] strings = s.split("，");
                    if (strings.length == 0)
                        return;
                    if (strings.length > 20) {
                        String[] strings1 = new String[20];
                        for (int i = 0; i < strings1.length; i++) {
                            strings1[i] = strings[i];
                        }
                        mMySurfaceView.setStrings(strings1);
                    } else
                        mMySurfaceView.setStrings(strings);

                }
                ChooseContentDialog.this.dismiss();
                break;
        }
    }
}
