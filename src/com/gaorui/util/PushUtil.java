package com.gaorui.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

import com.alibaba.fastjson.JSONObject;

public class PushUtil implements ServletContextListener {
	JSONObject jsonObject = null;
	public PushUtil(JSONObject jsonObject){
		
		 this.jsonObject = jsonObject;
		 
	}
	public PushUtil(){
		
	}
	private static final String CHANNEL = "hello";
	CometContext cc = null;
	public void contextInitialized(ServletContextEvent arg0) {
		cc = CometContext.getInstance();
		
		if(arg0 != null){
			cc.registChannel(CHANNEL);// 注册应用的channel
			System.out.println("Push");
			
		
		}
		
		CometEngine engine = CometContext.getInstance().getEngine();
		engine.sendToAll(CHANNEL,
				jsonObject+"");

	}



	public void contextDestroyed(ServletContextEvent arg0) {

	}
}