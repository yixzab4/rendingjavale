package com.bayside.app.opinion.war.index.controller;

import org.json.JSONException;
import org.json.JSONObject;

public class ConsJson {

	public ConsJson() {
		// TODO Auto-generated constructor stub
	}
	 public String Object2Json(Object obj) throws JSONException{  
	        String json = JSONObject.valueToString(obj);//将java对象转换为json对象  
	        String str = json.toString();//将json对象转换为字符串  
	          
	        return str;  
	    }  

}
