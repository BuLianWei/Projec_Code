package com.example.asus.myapplication.ui.activity;

import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.example.asus.myapplication.R;
import com.example.asus.myapplication.moudle.Global;
import com.example.asus.myapplication.utils.util.NetUtils;
import com.example.asus.myapplication.utils.util.Utils;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/18
 * Description:
 */
public class LoginActivity extends BaseActivity {
    private EditText mUserNameET;
    private EditText mPasswordET;
    private TextView mLoginTV;
    private TextView mRegistTV;
    private static String mUserId = "choose01";
    private YWIMKit mIMKit;

    @Override
    public void onClick(View v) {
        String user = mUserNameET.getText().toString().trim();
        if (!Utils.stringIsUsed(user)) {
            Snackbar.make(mUserNameET, R.string.msg_user_empty, Snackbar.LENGTH_SHORT).show();
            return;
        }
        String password = mPasswordET.getText().toString().trim();
        if (!Utils.stringIsUsed(password)) {
            Snackbar.make(mPasswordET, R.string.msg_password_empty, Snackbar.LENGTH_SHORT).show();
            return;
        }
        mIMKit = YWAPI.getIMKitInstance(user, getString(R.string.app_key));
        switch (v.getId()) {
            case R.id.login_tv_login:
                if (NetUtils.isConnected(LoginActivity.this))
                login(user, password);
                else
                showSnack(v,"连网未连接");
                break;

        }
    }


    private void login(final String user, final String password) {
        mLoginTV.setEnabled(false);
        final IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(user, password);
        Global.USER_ID = loginParam.getUserId();
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                setStringToPref("userid", user);
                setStringToPref("password", password);
                mLoginTV.setEnabled(true);
//                Snackbar.make(mUserNameET, "成功" + arg0, Snackbar.LENGTH_SHORT).show();
                startActivity(ChatFrameActivity.class);
                finish();
//                try {
//                    getSupportFragmentManager().beginTransaction().add(R.id.chat, mIMKit.getContactsFragmentClass().newInstance(), null).commit();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                IYWContactService contactService = mIMKit.getContactService();
//                mIMKit.getContactService().ackAddContact("choose1", getString(R.string.app_key), false, "123", null);
//                mIMKit.getContactService().ackAddContact("choose2", getString(R.string.app_key), false, "123", null);
//                mIMKit.getContactService().ackAddContact("choose3", getString(R.string.app_key), false, "123", null);
//                mIMKit.getContactService().ackAddContact("choose4", getString(R.string.app_key), false, "123", null);


            }

            @Override
            public void onProgress(int arg0) {

            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                Log.d("blw", "123+" + errCode + description);
                Snackbar.make(mUserNameET, "123+" + errCode + description, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mLoginTV = (TextView) findViewById(R.id.login_tv_login);
        mUserNameET = (EditText) findViewById(R.id.login_et_username);
        mPasswordET = (EditText) findViewById(R.id.login_et_password);
        mLoginTV.setOnClickListener(this);
        String userid = getStringForPref("userid");
        String password = getStringForPref("password");
        if (Utils.stringIsUsed(userid)) {
            mUserNameET.setText(userid);
        }
        if (Utils.stringIsUsed(password)) {
            mPasswordET.setText(password);
        }
        mUserNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPasswordET.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }
}
