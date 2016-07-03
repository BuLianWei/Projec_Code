package com.example.bean;

public class DayBean extends BaseBean {
private int time;
	public DayBean(int date,int time, String type, int income, int expenditure) {
		super(date, type, income, expenditure);
		// TODO Auto-generated constructor stub
		this.time=time;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

	
}
