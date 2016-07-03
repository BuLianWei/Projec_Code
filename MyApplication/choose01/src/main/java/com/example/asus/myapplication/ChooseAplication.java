package com.example.asus.myapplication;

import android.app.Application;
import android.content.Context;

import com.alibaba.wxlib.util.SysUtil;
import com.umeng.openim.OpenIMAgent;

/**
 * Author:   Lianwei Bu
 * Date:     2016/4/18
 * Description:
 */
public class ChooseAplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        //这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess(this)) {
//            CustomSampleHelper.initCustom();
            OpenIMAgent im = OpenIMAgent.getInstance(this);
            im.init();
        }

        //此对象获取到后，保存为全局对象，供APP使用
        //此对象跟用户相关，如果切换了用户，需要重新获取

    }
public static Context getContext(){
    return getContext();
}

}
