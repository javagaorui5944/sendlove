package com.gaorui.util;

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
	 * @author jipeng
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
	
	//查看两个经度纬度的距离(单位km)
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   final  double EARTH_RADIUS = 6378.137;
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10000;
	   return s;
	}

}
