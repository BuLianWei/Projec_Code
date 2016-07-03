package com.example.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class AddBean implements Serializable{
	private Bitmap icon;
	private String iconString;
	private String content;
	private long date;
	private float money;
	private int type;//0表示收入，1表示支出
    
	public AddBean() {
	}
	public AddBean(Bitmap icon, String content, long date, float money,int type,String iconString) {
		this.icon=icon;
		this.iconString = iconString;
		this.content = content;
		this.date = date;
		this.money = money;
		this.type=type;
	}

	public String  getIconString() {
		return iconString;
	}

	public void setIconString(String iconString) {
		this.iconString = iconString;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
}
