package com.lb.wecharenglish.weather;

import java.util.ArrayList;

public class FutureWeatherBean {
	private String temperature;
	private String weather;
	private String wind;
	private String weekString;

	public FutureWeatherBean(String temperature, String weather, String wind,
			String weekString) {
		super();
		this.temperature = temperature;
		this.weather = weather;
		this.wind = wind;
		this.weekString = weekString;
	}

	public static String futureWeekString(String currentWeekString,
			int itemIndex) {
		ArrayList<String> weekStringList = new ArrayList<String>();
		weekStringList.add("星期一");
		weekStringList.add("星期二");
		weekStringList.add("星期三");
		weekStringList.add("星期四");
		weekStringList.add("星期五");
		weekStringList.add("星期六");
		weekStringList.add("星期日");
		// 获取当天星期下标
		int currentWeekIndex = weekStringList.indexOf(currentWeekString);
		// 计算未来某天下标
		int futureWeekIndex = currentWeekIndex + itemIndex;
		// 进行越界处理
		if (futureWeekIndex > 6) {
			futureWeekIndex = futureWeekIndex - 7;
		}
		return weekStringList.get(futureWeekIndex);

	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getWeekString() {
		return weekString;
	}

	public void setWeekString(String weekString) {
		this.weekString = weekString;
	}

}
