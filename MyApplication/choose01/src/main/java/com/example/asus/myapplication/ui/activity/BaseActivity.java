package com.example.asus.myapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.asus.myapplication.moudle.Global;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/20
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() == 0) {
            new Exception("设置layout");
        } else {
            setContentView(getLayoutId());
        }
        initView();
        initData();

    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public void showSnack(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnack(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public void startActivity(Class toClass) {
        Intent intent = new Intent(this, toClass);
        startActivity(intent);
//        finish();
    }

    public String getStringForPref(String key) {
        return getSharedPreferences(Global.PREFER_FILE_NAME, 0).getString(key, "");
    }

    public boolean getBooleanForPref(String key) {
        return getSharedPreferences(Global.PREFER_FILE_NAME, 0).getBoolean(key, true);
    }

    public void setStringToPref(String key, String value) {
        getSharedPreferences(Global.PREFER_FILE_NAME, 0).edit().putString(key, value).apply();
    }

    public void setBooleanToPref(String key, boolean value) {
        getSharedPreferences(Global.PREFER_FILE_NAME, 0).edit().putBoolean(key, value).apply();
    }

}
