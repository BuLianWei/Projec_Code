package com.example.asus.myapplication.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.contact.IYWContactCacheUpdateListener;
import com.alibaba.mobileim.contact.IYWContactOperateNotifyListener;
import com.example.asus.myapplication.R;
import com.example.asus.myapplication.moudle.Global;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/22
 * Description:
 */
public class ChatFrameActivity extends BaseActivity {
    private FragmentTabHost mTabHost;
    private YWIMKit mYWIMKit;
    private int[] icons = new int[]{R.drawable.message_selector, R.drawable.contact_selector};
    private int[] fonts = new int[]{R.string.message, R.string.contact};
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IYWContactOperateNotifyListener mContactOperateListener;
    private IYWContactCacheUpdateListener mContactCacheListener;

    @Override
    protected void initView() {

        if (!Global.USER_ID.equals("")) {
            mYWIMKit = YWAPI.getIMKitInstance(Global.USER_ID, getString(R.string.app_key));
        }
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.content);
        mTabHost.addTab(mTabHost.newTabSpec("message").setIndicator(getTabView(0)), mYWIMKit.getConversationFragmentClass(), null);
//        mTabHost.addTab(mTabHost.newTabSpec("contact").setIndicator(getTabView(1)), mYWIMKit.getContactsFragmentClass(), null);
        setListener();
    }

    private void setListener() {
        initContactListener();
        addContactListener();


    }

    private void addContactListener() {
        if(mContactOperateListener!=null)
            mYWIMKit.getContactService().addContactOperateNotifyListener(mContactOperateListener);
        if(mContactCacheListener!=null)
            mYWIMKit.getContactService().addContactCacheUpdateListener(mContactCacheListener);
    }

    private void initContactListener() {
        mContactOperateListener = new IYWContactOperateNotifyListener() {

            /**
             * 用户请求加你为好友
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             * @param message 附带的备注
             */
            @Override
            public void onVerifyAddRequest(IYWContact contact, String message) {


            }

            /**
             * 用户接受了你的好友请求
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             */
            @Override
            public void onAcceptVerifyRequest(IYWContact contact) {
            }

            /**
             * 用户拒绝了你的好友请求
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             * @param  contact 用户的信息
             */
            @Override
            public void onDenyVerifyRequest(IYWContact contact) {
            }

            /**
             * 云旺服务端（或其它终端）进行了好友添加操作
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             */
            @Override
            public void onSyncAddOKNotify(IYWContact contact) {

            }

            /**
             * 用户从好友名单删除了您
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param contact 用户的信息
             */
            @Override
            public void onDeleteOKNotify(IYWContact contact) {
            }
        };


        mContactCacheListener = new IYWContactCacheUpdateListener() {

            /**
             * 好友缓存发生变化(联系人备注修改、联系人新增和减少等)，可以刷新使用联系人缓存的UI
             * todo 该回调在UI线程回调 ，请勿做太重的操作
             *
             * @param currentUserid                 当前登录账户
             * @param currentAppkey                 当前Appkey
             */
            @Override
            public void onFriendCacheUpdate(String currentUserid, String currentAppkey) {

            }

        };
    }


    private View getTabView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.chat_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.font);
        Drawable drawable=getResources().getDrawable(icons[i]);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight()-5);
        textView.setCompoundDrawables(null,drawable,null,null);
        textView.setText(fonts[i]);
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.chat;
    }

    @Override
    public void onClick(View v) {


    }
}
