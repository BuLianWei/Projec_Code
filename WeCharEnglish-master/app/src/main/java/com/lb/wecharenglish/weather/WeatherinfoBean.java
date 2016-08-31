package com.lb.wecharenglish.weather;

import java.util.ArrayList;
import org.json.JSONObject;

public class WeatherinfoBean {
     private int cityId;
     private String cityName;
     private String weekString;
     private String dateY;
     private String weather;
     private String temperature;
     private String wind;
     private String windLevel;
     private String pm;
     private String pmLevel;
	private ArrayList<FutureWeatherBean>futureWeatherList;
	//对象的属性 定义完成 写构造方法
	public WeatherinfoBean(JSONObject weatherJsonObject) {
		super();
		try {
			cityId = weatherJsonObject.getInt("cityid");
			cityName = weatherJsonObject.getString("city");
			weekString = weatherJsonObject.getString("week");
			dateY= weatherJsonObject.getString("date_y");
			weather = weatherJsonObject.getString("img_title_single");
			temperature=weatherJsonObject.getString("temp");
			wind = weatherJsonObject.getString("wd");
			windLevel = weatherJsonObject.getString("ws");
			pm = weatherJsonObject.getString("pm");
			pmLevel = weatherJsonObject.getString("pm-level");
            //ArrayList使用时候一定要初始化对象
			futureWeatherList=new ArrayList<FutureWeatherBean>();
			for(int i=1;i<7;i++)
			{
				String futureWeather = weatherJsonObject.getString("weather"+i);
				String futureTemperature = weatherJsonObject.getString("temp"+i);
				String futureWind = weatherJsonObject.getString("wind"+i);
			    String futureWeekString = FutureWeatherBean.futureWeekString(weekString, i);
				FutureWeatherBean futureWeatherBean=new FutureWeatherBean(futureTemperature, futureWeather, futureWind, futureWeekString);
			    futureWeatherList.add(futureWeatherBean);		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getWeekString() {
		return weekString;
	}
	public void setWeekString(String weekString) {
		this.weekString = weekString;
	}
	public String getDateY() {
		return dateY;
	}
	public void setDateY(String dateY) {
		this.dateY = dateY;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getWindLevel() {
		return windLevel;
	}
	public void setWindLevel(String windLevel) {
		this.windLevel = windLevel;
	}
	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public String getPmLevel() {
		return pmLevel;
	}
	public void setPmLevel(String pmLevel) {
		this.pmLevel = pmLevel;
	}
	public ArrayList<FutureWeatherBean> getFutureWeatherList() {
		return futureWeatherList;
	}
	public void setFutureWeatherList(ArrayList<FutureWeatherBean> futureWeatherList) {
		this.futureWeatherList = futureWeatherList;
	}
	
}
