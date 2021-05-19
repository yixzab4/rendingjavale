package com.bayside.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
public class IpUtil {

	public static String getIpAddr(final HttpServletRequest request)
			throws Exception {
		if (request == null) {
			throw (new Exception(
					"getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}
		return ipString;
	}
	 /**  
     * 通过IP地址获取MAC地址  
     * @param ip String,127.0.0.1格式  
     * @return mac String  
     * @throws Exception  
     */    
    public static String getMACAddress(String ip) throws Exception {    
        String line = "";    
        String macAddress = "";    
        final String MAC_ADDRESS_PREFIX = "MAC Address = ";    
        final String LOOPBACK_ADDRESS = "127.0.0.1";    
        //如果为127.0.0.1,则获取本地MAC地址。    
        if (LOOPBACK_ADDRESS.equals(ip)) {    
            InetAddress inetAddress = InetAddress.getLocalHost();    
            //貌似此方法需要JDK1.6。    
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();    
            //下面代码是把mac地址拼装成String    
            StringBuilder sb = new StringBuilder();    
            for (int i = 0; i < mac.length; i++) {    
                if (i != 0) {    
                    sb.append("-");    
                }    
                //mac[i] & 0xFF 是为了把byte转化为正整数    
                String s = Integer.toHexString(mac[i] & 0xFF);    
                sb.append(s.length() == 1 ? 0 + s : s);    
            }    
            //把字符串所有小写字母改为大写成为正规的mac地址并返回    
            macAddress = sb.toString().trim().toUpperCase();    
            return macAddress;    
        }    
        //获取非本地IP的MAC地址    
        try {    
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);    
            InputStreamReader isr = new InputStreamReader(p.getInputStream(),"GBK");    
            BufferedReader br = new BufferedReader(isr);    
            while ((line = br.readLine()) != null) {    
                if (line != null) {    
                    int index = line.indexOf(MAC_ADDRESS_PREFIX);    
                    if (index != -1) {    
                        macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();    
                    }    
                }    
            }    
            br.close();    
        } catch (IOException e) {    
            e.printStackTrace(System.out);    
        }    
        return macAddress;    
    }    
    /**
     * 根据IP地址获取mac地址
     * @param ipAddress 127.0.0.1
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
    public static String getLocalMac(String ipAddress) throws SocketException,
        UnknownHostException {
      // TODO Auto-generated method stub
      String str = "";
      String macAddress = "";
      final String LOOPBACK_ADDRESS = "127.0.0.1";
      // 如果为127.0.0.1,则获取本地MAC地址。
      if (LOOPBACK_ADDRESS.equals(ipAddress)) {
        InetAddress inetAddress = InetAddress.getLocalHost();
        // 貌似此方法需要JDK1.6。
        byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
            .getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
          if (i != 0) {
            sb.append("-");
          }
          // mac[i] & 0xFF 是为了把byte转化为正整数
          String s = Integer.toHexString(mac[i] & 0xFF);
          sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        macAddress = sb.toString().trim().toUpperCase();
        return macAddress;
      } else {
        // 获取非本地IP的MAC地址
        try {
          System.out.println(ipAddress);
          Process p = Runtime.getRuntime()
              .exec("nbtstat -A " + ipAddress);
          System.out.println("===process=="+p);
          InputStreamReader ir = new InputStreamReader(p.getInputStream());
            
          BufferedReader br = new BufferedReader(ir);
          
          while ((str = br.readLine()) != null) {
            if(str.indexOf("MAC")>1){
              macAddress = str.substring(str.indexOf("MAC")+9, str.length());
              macAddress = macAddress.trim();
              System.out.println("macAddress:" + macAddress);
              break;
            }
          }
          p.destroy();
          br.close();
          ir.close();
        } catch (IOException ex) {
        }
        return macAddress;
      }
    }
    public static void main(String[] args) {
		try {
			System.out.println(IpUtil.getMACAddress("59.110.15.12"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
}
