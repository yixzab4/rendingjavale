package com.bayside.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gexin.fastjson.JSONObject;

public class WeixinUser {
	private static Logger logger = Logger.getLogger(WeixinUser.class);  
 public List<String> allWeiXinUser(String accessToken){
	 String url="https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+"&next_openid=";
	 List<String> namelist = new ArrayList<String>();
	 try {
			URL getUrl=new URL(url);
			HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded");
			http.connect();
			InputStream is = http.getInputStream(); 
			int size = is.available(); 
			byte[] b = new byte[size];
			is.read(b);

			String message = new String(b, "UTF-8");

			JSONObject json = JSONObject.parseObject(message);
			JSONObject jsonb  = (JSONObject) json.get("data");
			//用户openid
		    List<String> list = (List<String>) jsonb.get("openid");
		    System.out.println(list);
		    for(int i=0;i<list.size();i++){
		    	  //获取用户基本信息
				String us = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+list.get(i)+"&lang=zh_CN";

				URL getUrls=new URL(us);
				HttpURLConnection https=(HttpURLConnection)getUrls.openConnection();
				https.setRequestMethod("GET"); 
				https.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
				https.connect();
				InputStream iss= https.getInputStream(); 
				int sizes = iss.available(); 
				byte[] bs = new byte[sizes];
				iss.read(bs);
				String messages = new String(bs, "UTF-8");

				JSONObject jsons = JSONObject.parseObject(messages);
				String name = (String) jsons.get("nickname");
				String openid =(String) jsons.get("openid"); 
				System.out.println(name);
				namelist.add(openid);
		    }
		  
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
	 return namelist;
	 
 }
 
 public List<Map<String,String>> weiXinUser(String accessToken){
	 String url="https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+"&next_openid=";
	 List<Map<String,String>> namelist = new ArrayList<Map<String,String>>();
	 try {
			URL getUrl=new URL(url);
			HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded");
			http.connect();
			InputStream is = http.getInputStream(); 
			int size = is.available(); 
			byte[] b = new byte[size];
			is.read(b);

			String message = new String(b, "UTF-8");

			JSONObject json = JSONObject.parseObject(message);
			JSONObject jsonb  = (JSONObject) json.get("data");
			//用户openid
		    List<String> list = (List<String>) jsonb.get("openid");
		    System.out.println(list);
		    for(int i=0;i<list.size();i++){
		    	Map<String,String> map = new HashMap<String,String>();
		    	  //获取用户基本信息
				String us = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+list.get(i)+"&lang=zh_CN";

				URL getUrls=new URL(us);
				HttpURLConnection https=(HttpURLConnection)getUrls.openConnection();
				https.setRequestMethod("GET"); 
				https.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
				https.connect();
				InputStream iss= https.getInputStream(); 
				int sizes = iss.available(); 
				byte[] bs = new byte[sizes];
				iss.read(bs);
				String messages = new String(bs, "UTF-8");

				JSONObject jsons = JSONObject.parseObject(messages);
				String name = (String) jsons.get("nickname");
				String openid =(String) jsons.get("openid"); 
				System.out.println(name);
				map.put(name, openid);
				namelist.add(map);
		    }
		  
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
	 return namelist;
	 
 }

}
