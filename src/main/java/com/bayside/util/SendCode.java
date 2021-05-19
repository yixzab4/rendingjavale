package com.bayside.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * 发送验证码
 * @author netease
 *
 */
public class SendCode {
	/*public static void main(String[] args) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = "https://api.netease.im/sms/sendcode.action";
		HttpPost httpPost = new HttpPost(url);

		String appKey = "ed6acefc3228edca4a240db6ee1ff38d";
		String appSecret = "cef2fe33a464";
		String nonce = "12345";// nonce随机数
		String curTime = String.valueOf((new Date()).getTime() / 1000L);// time
		String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);// 参考计算CheckSum的java代码

		// 设置请求的header
		httpPost.addHeader("AppKey", appKey);
		httpPost.addHeader("Nonce", nonce);
		httpPost.addHeader("CurTime", curTime);
		httpPost.addHeader("CheckSum", checkSum);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		// 设置请求的的参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 参数 jsonArray形式
		nvps.add(new BasicNameValuePair("mobile", "15753158017"));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

		// 执行请求
		HttpResponse response = httpClient.execute(httpPost);

		// 打印执行结果
		System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));

	}*/
	static String requestUrl="http://api.febook.cn/SmsService/Template";
	static String reUrl="http://api.febook.cn/SmsService/Send";
	/*public void main(String[] args) {
		System.out.println("Hello World!");
		try {
			  List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			   formparams.add(new BasicNameValuePair("Account","lyy"));
			   formparams.add(new BasicNameValuePair("Pwd","3355315CD86A2BC5B0A6F2114DC4"));
			   formparams.add(new BasicNameValuePair("Content","您的验证码是1234,密码是1111"));
			   formparams.add(new BasicNameValuePair("Mobile","15753158017"));
			   formparams.add(new BasicNameValuePair("SignId","30273"));
			   Post(formparams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

			public static void Post( List<NameValuePair> formparams) throws Exception {
				CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
		        
			 	httpClient.start();
			 
			   HttpPost requestPost=new HttpPost(requestUrl);
			   Boolean fa = true;
			 
		       requestPost.setEntity(new UrlEncodedFormEntity(formparams,"utf-8"));

			   httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {
				
				public void failed(Exception arg0) {
					
					 System.out.println("Exception: " + arg0.getMessage());
					 
				}
				
				public void completed(HttpResponse arg0) {
					  System.out.println("Response: " + arg0.getStatusLine());
					
				try {
					
					InputStream stram= arg0.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
					System.out.println(	reader.readLine());
					
				} catch (UnsupportedOperationException e) {
				
					System.out.println(e.getMessage());
				} catch (IOException e) {
					
					System.out.println(e.getMessage());
				}
		
					
				}
				
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
			}).get();
			 
			
			 
			 }
			public static void contPost( List<NameValuePair> formparams) throws Exception {
				CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
		        
			 	httpClient.start();
			 
			   HttpPost requestPost=new HttpPost(reUrl);
			 
		       requestPost.setEntity(new UrlEncodedFormEntity(formparams,"utf-8"));

			   httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {
				
				public void failed(Exception arg0) {
					
					 System.out.println("Exception: " + arg0.getMessage());
					 
				}
				
				public void completed(HttpResponse arg0) {
					  System.out.println("Response: " + arg0.getStatusLine());
					
				try {
					
					InputStream stram= arg0.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
					System.out.println(	reader.readLine());
					
				} catch (UnsupportedOperationException e) {
				
					System.out.println(e.getMessage());
				} catch (IOException e) {
					
					System.out.println(e.getMessage());
				}
		
					
				}
				
				public void cancelled() {
					// TODO Auto-generated method stub
					
				}
			}).get();
			 
			
			 
			 }
}

