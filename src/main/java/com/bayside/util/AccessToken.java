package com.bayside.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.gexin.fastjson.JSONObject;




public class AccessToken {
	private static Logger logger = Logger.getLogger(AccessToken.class);  
	public String getAccessToken(){
		String appID="wxbbf9259beddfa2f7";
		String appScret="5e4e6ac14e04a71dcf50dba988e56f5b";
		// 访问微信服务器
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
		+ appScret;
		String accessToken="";
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
			accessToken = (String) json.get("access_token");
			System.out.println(accessToken);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
		return accessToken;
	}
	
	
}
