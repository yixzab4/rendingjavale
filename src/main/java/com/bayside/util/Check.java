package com.bayside.util;


import java.util.Date;
/**
 * 计算checkSum的案例
 * @author netease
 *
 */
public class Check {
	public static void main(String[] args) {
		String appKey = "";
        String appSecret = "";
        String nonce =  "a1cae678af59c92bbf86e04d9949aad7";
//        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String curTime = "1460432381";
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考计算CheckSum的java代码
        System.out.println(checkSum);
	}
}
