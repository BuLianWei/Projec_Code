package com.example.model;

public class MainListDetail {
	String titel;
	String content;
	String prize;
	String degree;
    String distence;
	public MainListDetail(String titel, String content, String prize,
			String degree, String distence) {
		super();
		this.titel = titel;
		this.content = content;
		this.prize = prize;
		this.degree = degree;
		this.distence=distence;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDistence() {
		return distence;
	}

	public void setDistence(String distence) {
		this.distence = distence;
	}
}
