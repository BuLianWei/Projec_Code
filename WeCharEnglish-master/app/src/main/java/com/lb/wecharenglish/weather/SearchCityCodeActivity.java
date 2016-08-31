package com.lb.wecharenglish.weather;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.lb.wecharenglish.R;
import com.lb.wecharenglish.ui.activity.BaseActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;
/*
 * 1、获取到city_code.xml中的数据
 * 2、把获取到的所有的城市名字配置到AutoCompeleTextView中
 * 3、AutoCompeleTextView中的内容，查找到相应的cityID
 * 4、返回MainActivity
 */

public class SearchCityCodeActivity extends BaseActivity {

    private ImageView finishImageView;
    private AutoCompleteTextView autoCompleteTextView;
    private HashMap<String, String> cityCodeHashMap;
    private Handler readXmlFinishHandler = new Handler() {
        public void handleMessage(Message msg) {
            completeTextViewSetData();
        }

    };


    @Override
    protected View createView() {
        return View.inflate(this, R.layout.activity_search_city_code, null);
    }

    @Override
    protected void initData() {
        //因为读取xml文件是耗时性工作，所以最好放到后台线程当中
        new Thread(new Runnable() {

            @Override
            public void run() {
                getCityCityCodeFromXml();
            }
        }).start();
    }

    @Override
    protected void findView() {
        setActionBarDatas(true, "搜索一下下", false, false, null, false, null);
        finishImageView = (ImageView) findViewById(R.id.sea_finish_img);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search_edit_actv);


        finishImageView.setOnClickListener(this);
    }

    private void completeTextViewSetData() {
        //异常处理
        if (cityCodeHashMap == null) {
            return;
        }
        //从cityCodeHashMap获取所有的城市的名称用于自动提示
        Set<String> cityNameSet = cityCodeHashMap.keySet();
        String[] cityNameStrings = new String[cityNameSet.size()];
        cityNameSet.toArray(cityNameStrings);
        //自动提示的数据源就完成，把数据源配置到autocompleteTextView
    /*
     * ArrayAdapter<显示内容的类型，自动提示时显示的文字，String>
     * 参数1：上下文（当前的Activity）
     * 参数2：一个布局（显示内容用的布局android.R.layout.simple_list_item_1安卓定义好的一个列表布局：只有一行显示一段文字）
     * 参数3：数据源数组
     * */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityNameStrings);
        //把ArrayAdapter绑定到autoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);
        //设置autoCompleteTextView从第几个字符开始提示
        autoCompleteTextView.setThreshold(1);
    }

    private void getCityCityCodeFromXml() {
        try {
            InputStream xmlStream = getAssets().open("city_code.xml");
            readXMLInputStream(xmlStream);
            Message message = new Message();
            message.what = 100;
            readXmlFinishHandler.sendMessage(message);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void readXMLInputStream(InputStream xmlStream) {
        //解析XML需要一个ＸＭＬ的解析类XmlPullParser
        //用工厂方法去获取XmlPullParser的对象
        //ArrayList<E>和hashmap都是容器型空间
        cityCodeHashMap = new HashMap<String, String>();
        try {
            XmlPullParserFactory factoy = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factoy.newPullParser();
            //把数据流和编码的方式设置到解析器上
            parser.setInput(xmlStream, "utf-8");
            //获取xml中的第一个标签
            int eventType = parser.getEventType();
            //非结束标签继续解析，遇到xml的结束标签则停止解析
            //XmlPullParser.END_TAG  xml的结束标签
            //XmlPullParser.START_TAG xml的开始标签
            //parser.getName()获取标签的名字
            //parser.nextText()获取标签后的内容
            //parser.next()移动指针移向下一个标签
            while (eventType != XmlPullParser.END_TAG) {
                if (eventType == XmlPullParser.START_TAG) {
                    String nameString = parser.getName();
                    if (nameString.equals("key")) {
                        String cityNameKeyString = parser.nextText();
                        parser.next();
                        parser.next();
                        String cityCodeValue = parser.nextText();
                        cityCodeHashMap.put(cityNameKeyString, cityCodeValue);
                    }

                }
                parser.next();
                eventType = parser.getEventType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sea_finish_img:
                String cityName = autoCompleteTextView.getText().toString().trim();
                if (TextUtils.isEmpty(cityName)) {
                    return;
                }
                if (cityCodeHashMap.get(cityName)==null)
                    return;
                String cityCodeString = cityCodeHashMap.get(cityName);
                int cityId = Integer.valueOf(cityCodeString);
                //向mainActivity返回结果
                setResult(cityId);
                //结束当前Activity
                finish();
                break;
        }
    }
}
