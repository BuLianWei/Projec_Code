package com.example.util;


public class GetProportionData {
	public static double getData(double realData, int max) {
		double data;
		
		if (realData / max <= 1) {
			data = (realData / 1.0);
		} else if (realData / (max * 10) <= 1) {
			data = (realData / 10.0);
		} else if (realData / (max * 100) <= 1) {
			data = (realData / 100.0);
		} else if (realData / (max * 1000) <= 1) {
			data = (realData / 1000.0);
		} else if (realData / (max * 10000) <= 1) {
			data = (realData / 10000.0);
		} else if (realData / (max * 100000) <= 1) {
			data = (realData / 100000.0);
		} else {
			data = (realData / 1000000.0);
		}
		return data;
	}
}
