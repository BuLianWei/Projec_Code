package com.lb.wecharenglish.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lb.wecharenglish.R;
import com.lb.wecharenglish.ui.activity.BaseActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*
 * 1、页面的主要功能是展示数据，先去获取数据（通过网络请求获取数据）
 * 注：UI线程（主线程 mainThread）不准调用网络，
 *    后台线程(自己启动的线程)不准调用UI操作
 *  （1）Android 中Activity的onCreate（）在UI线程（thread1）中执行的，
 *   UI线程的要求是不能有好时行操作（操作的时间比较长）
 *   网络请求是耗时性操作（3~5秒）
 *   所以UI线程中不能进行网络连接操作（如果UI进行网络请求操作，
 *   会导致UI界面卡顿，甚至会卡死界面）应该在后台线程中去请求网络
 *   （2）后台线程如果去操作UI界面会导致线程混乱，
 *    UI操作只能在主线程中调用，后台调用UI操作会导致崩溃
 * */

public class WeatherActivity extends BaseActivity {

    private ImageView refreshImageView;
    private ImageView searchImageView;
    private TextView netWorkErrorTextView;
    private TextView cityNameTextView;
    private TextView pmTextView;
    private TextView tempretureTextView;
    private TextView dateTextView;
    private TextView weatherTextView;
    private View pmStateColorView;
    private LinearLayout futureDayWeatherLayout;

    private AnimationDrawable refreshAnimation;
    private SharedPreferences preferences;
    private int cityId;
    //Handler对象用于处理后台线程与主线之间的通信
    private Handler netWorkFinishHandler =
            new Handler() {
                public void handleMessage(Message msg) {
                    //以下代码会在UI线程中性
                    if (msg.what == 100) {
                        //网络请求结束 动画应该停止
                        //把数据对象从消息中取出
                        WeatherinfoBean weatherBean =
                                (WeatherinfoBean) msg.obj;
                        netWorkErrorTextView.setText("");
                        cityNameTextView.
                                setText(weatherBean.getCityName());
                        pmTextView.setText("PM:" +
                                weatherBean.getPm() + " " +
                                weatherBean.getPmLevel());
                        tempretureTextView.setText(
                                weatherBean.getTemperature() + "℃");
                        dateTextView.setText(
                                weatherBean.getDateY());

                        weatherTextView.setText(
                                weatherBean.getWeather() +
                                        weatherBean.getWind() +
                                        weatherBean.getWindLevel()
                        );
                        ArrayList<FutureWeatherBean> futureWeatherList =
                                weatherBean.getFutureWeatherList();
                        //把未来天气布局中的控件清空
                        futureDayWeatherLayout.removeAllViews();
                        for (int i = 0; i < futureWeatherList.size(); i++) {
                            FutureWeatherBean futureWeatherBean =
                                    futureWeatherList.get(i);
                    /*把创建的futureWeather_layout 布局拿到
                     * 调用LayoutInflater的LayoutInflater.from(
				    		  getApplicationContext()
				    				)获取布局转化器对象
				                  在调用 布局转化器对象的inflate(R.layout.futureweather_layout, 
				       null)把一个layout布局转化为View
					 * */
                            View futureWeatherLayout =
                                    LayoutInflater.from(
                                            getApplicationContext()
                                    ).inflate(
                                            R.layout.futureweather_layout, null);
                            TextView futureWeekTextView =
                                    (TextView) futureWeatherLayout.
                                            findViewById(R.id.fu_week_tv);
                            TextView futureWeatherTextView =
                                    (TextView) futureWeatherLayout.
                                            findViewById(R.id.fu_weather_tv);
                            TextView futureTempertureTextView =
                                    (TextView) futureWeatherLayout.
                                            findViewById(R.id.fu_tem_tv);
                            futureTempertureTextView.setText(
                                    futureWeatherBean.getTemperature());
                            futureWeekTextView.setText(
                                    futureWeatherBean.getWeekString());
                            futureWeatherTextView.setText(
                                    futureWeatherBean.getWeather()
                                            + futureWeatherBean.getWind());
                            futureDayWeatherLayout.addView(
                                    futureWeatherLayout);
                        }
                        preferences.edit().putInt("code", cityId).commit();
                        refreshAnimationStop();
                    } else if (msg.what == 404) {
                        refreshAnimationStop();
                        netWorkErrorTextView.setText("网络异常");
                    }

                }

                ;
            };

    //SharedPreferences文件用于本地保存数据

    /**
     * 使用SharedPreferences保存数据方法如下：
     * //实例化SharedPreferences对象（第一步）
     * SharedPreferences mySharedPreferences= getSharedPreferences("test",
     * Activity.MODE_PRIVATE);
     * //实例化SharedPreferences.Editor对象（第二步）
     * SharedPreferences.Editor editor = mySharedPreferences.edit();
     * //用putString的方法保存数据
     * editor.putString("name", "Karl");
     * editor.putString("habit", "sleep");
     * //提交当前数据
     * editor.commit();
     * //使用toast信息提示框提示成功写入数据
     * Toast.makeText(this, "数据成功写入SharedPreferences！" , Toast.LENGTH_LONG).show();
     * 执行以上代码，SharedPreferences将会把这些数据保存在test.xml文件中，可以在File Explorer的data/data/相应的包名/test.xml 下导出该文件，并查看。
     * 2、使用SharedPreferences读取数据方法如下：
     * //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
     * SharedPreferencessharedPreferences= getSharedPreferences("test",
     * Activity.MODE_PRIVATE);
     * // 使用getString方法获得value，注意第2个参数是value的默认值
     * String name =sharedPreferences.getString("name", "");
     * String habit =sharedPreferences.getString("habit", "");
     * //使用toast信息提示框显示信息
     * Toast.makeText(this, "读取数据如下："+"\n"+"name：" + name + "\n" + "habit：" + habit,
     * Toast.LENGTH_LONG).show();
     */


    @Override
    protected View createView() {
        return View.inflate(this, R.layout.weather_main, null);
    }

    //页面回调是调用的方法（反向传值后调用的方法）
    /*requestCode 页面返回时的验证码
     * resultCode 页面返回的数据
     * */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            cityId = resultCode;
            getWeatherDataFromNetWork();
        }
    }

//    private void setListener() {
//        refreshImageView.setOnClickListener(
//                new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        getWeatherDataFromNetWork();
//                    }
//                });
//        searchImageView.setOnClickListener(
//                new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        Intent searchCityCodeIntent = new Intent(
//                                WeatherActivity.this, SearchCityCodeActivity.class);
//			    /*页面的传值
//			     * 正向 ：BMI中MainActivity――》BMIResultActivity
//			     *    传result对象（跳转到下个页面，下个页面所需的值一起传过去）
//			     * 反向 ：WeatherActivity――》SearchCityCodeActivity 页面不传之
//			     *     SearchCityCodeActivity消失时回到
//			     *     MainActivity要给MainActivity一个结果
//			     *     startActivityForResult(searchCityCodeIntent, 300);
//			     *     参数1：要跳转的Intent 参数2：用于区分那个页面的返回数据
//			     * */
//                        startActivityForResult(searchCityCodeIntent, 300);
//                    }
//                });
//    }


    @Override
    protected void findView() {

        setActionBarDatas(true,"天气早知道",false,false,null,false,null);
        preferences = getSharedPreferences("city", 0);
        cityId = preferences.getInt("code", 0);



        refreshImageView = (ImageView)
                findViewById(R.id.refresh_img);
        searchImageView = (ImageView)
                findViewById(R.id.search_img);
        netWorkErrorTextView = (TextView)
                findViewById(R.id.network_error_tv);
        cityNameTextView = (TextView)
                findViewById(R.id.city_tv);
        pmTextView = (TextView)
                findViewById(R.id.pm_tv);
        tempretureTextView = (TextView)
                findViewById(R.id.temperature_tv);
        dateTextView = (TextView)
                findViewById(R.id.date_tv);
        weatherTextView = (TextView)
                findViewById(R.id.weather_tv);
        pmStateColorView =
                findViewById(R.id.pm_state_color_v);
        futureDayWeatherLayout =
                (LinearLayout) findViewById(
                        R.id.futureday_weather_layout);

        refreshImageView.setOnClickListener(this);
        searchImageView.setOnClickListener(this);
        getWeatherDataFromNetWork();
    }


    private void getWeatherDataFromNetWork() {
        //异常处理
        //获取网络状态 ConnectivityManager连接管理类
        //获取ConnectivityManager对象用
        //getSystemService(
        //  Context.CONNECTIVITY_SERVICE)
        //获取网络状态要加权限
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE
                );
        //通过连接管理类去拿到网络状态
        NetworkInfo netWorkInfo =
                manager.getActiveNetworkInfo();

        if (netWorkInfo == null) {
            netWorkErrorTextView.setText("网络异常，请检查网络");
            return;
        }
        refreshAnimationStart();
        new Thread(new Runnable() {
            //后台操作写到run()方法中
            @Override
            public void run() {

                //开始网络请求
                String urlString = "http://weather.123.duba.net/static/weather_info/" + cityId + ".html";
                StringBuffer stringBuffer = new StringBuffer();
                try {
                    //把网址变为网络路径
                    //进行网络请求要加权限
                    URL url = new URL(urlString);
                    //打开路径获取连接
                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();
                    //设置网络的超时时间
                    connection.setConnectTimeout(10 * 1000);
                    connection.setReadTimeout(10 * 1000);
                    //读取连接中的数据
                    InputStream inputStream =
                            connection.getInputStream();
                    //把输入流换出字节缓存
                    InputStreamReader inputStreamReader = new
                            InputStreamReader(inputStream);
                    //把字节缓存转化字符缓存便于读取数据
                    BufferedReader bufferedReader =
                            new BufferedReader(inputStreamReader);
                    String readLineString = bufferedReader.readLine();
                    while (!TextUtils.isEmpty(readLineString)) {
                        stringBuffer.append(readLineString);
                        readLineString = bufferedReader.readLine();
                    }
                    String resultString = stringBuffer.toString();
                    if (TextUtils.isEmpty(resultString)) {
                        dealWithNetWorkError();
                        return;
                    }
                    if (!resultString.contains("weather_callback")) {
                        dealWithNetWorkError();
                        return;
                    }
                    int startIndex = resultString.indexOf("(") + 1;
                    int endIndex = resultString.indexOf(")");
                    String resultJsonString = resultString.
                            substring(startIndex, endIndex);
                    //将标准的JsonString转化为JSONObject 键值对 键对值
                    //Json 对象不建议直接拿来使用应转化为JavaBean对象
                    JSONObject resultJson =
                            new JSONObject(resultJsonString);
                    JSONObject weatherInfoJson =
                            resultJson.getJSONObject("weatherinfo");
                    //创建一个JavaBean类去保存 weatherInfoJson 
                    WeatherinfoBean weatherBean =
                            new WeatherinfoBean(weatherInfoJson);
                    System.out.println("");
                    //进行线程之间的通信，通知主线程（UI线程）去展示请求到的数据
                    //Message消息，用于线程之间传递信息
                    Message message = new Message();
                    //message.what 是消息的标示，用于区分何种消息
                    message.what = 100;
                    //message.obj 是传递的数据对象
                    message.obj = weatherBean;
                    //handler调用sendMessage(message)向主线程发送消息
                    netWorkFinishHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void dealWithNetWorkError() {
                Message message = new Message();
                //message.what 是消息的标示，用于区分何种消息
                message.what = 404;
                //handler调用sendMessage(message)向主线程发送消息
                netWorkFinishHandler.sendMessage(message);
            }
        }).start();
    }

    private void refreshAnimationStart() {
        //把动画背景设置到refreshImageView上
        refreshImageView.setBackgroundResource(
                R.drawable.pb_bg);
        //从refreshImageView把背景动画取到
        refreshAnimation = (AnimationDrawable)
                refreshImageView.getBackground();
        //开启动画
        refreshAnimation.start();
        //保证动画转动过程中refreshImageView 不能被点击
        refreshImageView.setEnabled(false);
    }

    private void refreshAnimationStop() {
        //停止动画
        refreshAnimation.stop();
        //更换refreshImageView背景
        refreshImageView.setBackgroundResource(
                R.drawable.refresh);
        //refreshImageView恢复可点击
        refreshImageView.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh_img:
                getWeatherDataFromNetWork();
                break;
            case R.id.search_img:
                Intent searchCityCodeIntent = new Intent(
                        WeatherActivity.this, SearchCityCodeActivity.class);
                startActivityForResult(searchCityCodeIntent, 300);
                break;
        }
    }
}
