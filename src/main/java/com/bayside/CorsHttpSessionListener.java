package com.bayside;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * 使用实现HttpSessionListener接口
 *
 * @author   sql
 * @create    20160217
 */
public class CorsHttpSessionListener implements HttpSessionListener{

	@Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("ServletContex初始化");
    }

}
