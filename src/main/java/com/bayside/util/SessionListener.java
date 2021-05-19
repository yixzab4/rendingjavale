package com.bayside.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	private static ConcurrentHashMap<String, String> hUserName = new ConcurrentHashMap<String, String>();// 保存sessionID和username的映射

	/** 以下是实现HttpSessionListener中的方法 **/

	public void sessionCreated(HttpSessionEvent se) {

	}

	public void sessionDestroyed(HttpSessionEvent se) {

		hUserName.remove(se.getSession().getId());

	}

	/*
	 * 
	 * isAlreadyEnter-用于判断用户是否已经登录以及相应的处理方法
	 * 
	 * @param sUserName String-登录的用户名称
	 * 
	 * @return boolean-该用户是否已经登录过的标志
	 * 
	 */

	public static boolean isAlreadyEnter(HttpSession session, String sUserName) {

		boolean flag = false;
		if(hUserName.containsKey(sUserName)){
			flag = true;
			hUserName.remove(sUserName);
			hUserName.put(sUserName, session.getId());
		}
		else {// 如果该用户没登录过，直接添加现在的sessionID和username

			flag = false;

			hUserName.put(sUserName, session.getId());

			System.out.println("hUserName = " + hUserName);

		}
		return flag;

	}

	/*
	 * 
	 * isOnline-用于判断用户是否在线
	 * 
	 * @param session HttpSession-登录的用户名称
	 * 
	 * @return boolean-该用户是否在线的标志
	 * 
	 */

	public static boolean isOnline(HttpSession session) {

		boolean flag = true;

		if (hUserName.containsValue(session.getId())) {

			flag = true;

		}

		else {

			flag = false;

		}

		return flag;

	}

}
