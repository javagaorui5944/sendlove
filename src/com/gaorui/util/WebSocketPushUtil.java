package com.gaorui.util;

import java.util.HashMap;
import java.util.Iterator;

import java.util.Set;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint("/hello")
public class WebSocketPushUtil {

	static Session session1 = null;
	static HashMap<String, Session> Sessions = new HashMap<String, Session>();

	@OnMessage
	public void hello(String message, Session session, JSONObject jsonObject) {

		if (session != null) {
			session1 = session;

			for (Session openSession : session.getOpenSessions()) {
				System.out
						.println("openSession.getId():" + openSession.getId());

			}

		}

		else if (session == null) {
			session = session1;
			System.out.println("info" + jsonObject.toString());
			{

				Session openSession = null;
				try {

					// 把消息发送给所有连接的会话
					Set<String> keys = Sessions.keySet();
					Iterator<String> temp = keys.iterator();
					while (temp.hasNext()) {
						String i = temp.next();

						openSession = Sessions.get(i);

						System.out.println("size" + Sessions.size());
						System.out.println("openSession.getId():"
								+ openSession.getId());

						openSession.getBasicRemote().sendText(
								jsonObject.toString());
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}

	}

	@OnOpen
	public void myOnOpen(Session session) {

		Sessions.put(session.getId(), session);
		System.out.println("WebSocket opened: " + session.getId());

		for (Session openSession : session.getOpenSessions()) {

			System.out.println("myOnOpen——openSession.getId():"
					+ openSession.getId());

		}
	}

	@OnClose
	public void myOnClose(CloseReason reason, Session session) {
		Sessions.remove(session.getId());
		System.out.println("Closing a WebSocket due to " + session.getId());
	}

}