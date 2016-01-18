package com.gaorui.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

public class CommonUtil {

	/**
	 * @author gr
	 * @param code
	 * @param msg
	 * @param data
	 * @return 构造返回JSON
	 */
	public static JSONObject constructResponseJSON(int code, String msg,
			String data) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("data", data);
		return jo;
	}

	/**
	 * @author gr
	 * @return
	 * 
	 *         构造返回json
	 * 
	 */
	public static JSONObject constructResponse(int code, String msg, Object data) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("msg", msg);
		jo.put("data", data);
		return jo;
	}

	/**
	 * UUID
	 * 
	 * @return生成的GUID为一串32位字符组成的128位数据
	 */
	public String GUID() {
		String a = null;

		// 产生 5 个 GUID
		for (int i = 0; i < 5; i++) {
			// 创建 GUID 对象
			UUID uuid = UUID.randomUUID();
			// 得到对象产生的ID
			a = uuid.toString();
			// 转换为大写
			a = a.toUpperCase();
			// 替换 -
			a = a.replaceAll("-", "_");
			// System.out.println(a);
		}
		System.out.println(a + "a");
		return a;
	}

	// 查看两个经度纬度的距离(单位km)
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {

		final double EARTH_RADIUS = 6378.137;

		double radLat1 = rad(lat1);

		double radLat2 = rad(lat2);

		double a = radLat1 - radLat2;

		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));

		s = s * EARTH_RADIUS;

		s = Math.round(s * 10000) / 10000;

		return s;
	}

	// 根据用户传入的范围距离(m)判断B点经纬度是否在用户定点A的范围内,返回true代表在范围内。
	public static boolean JudgeRange(double lat1, double lng1, double lat2,
			double lng2, int radius) {
		boolean res = false;

		final double EARTH_RADIUS = 6378.137;

		double radLat1 = rad(lat1);

		double radLat2 = rad(lat2);

		double a = radLat1 - radLat2;

		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));

		s = s * EARTH_RADIUS;

		s = Math.round(s * 10000) / 1000;// 返回长度单位为m

		if (radius > s) {
			res = true;
		}

		return res;

	}

	// 比较时间的大小
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
			//	System.out.println("dt1在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
			//	System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
}
