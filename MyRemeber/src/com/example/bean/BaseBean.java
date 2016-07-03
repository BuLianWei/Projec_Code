package com.example.bean;

public  class BaseBean {
	protected int date;//
	protected String type;//
	protected int income;
	protected int expenditure;

	public BaseBean(int date, String type, int income, int expenditure) {
		
		this.date = date;
		this.type = type;
		this.income = income;
		this.expenditure = expenditure;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(int expenditure) {
		this.expenditure = expenditure;
	}

}
