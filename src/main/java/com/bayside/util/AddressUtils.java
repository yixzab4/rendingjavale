package com.bayside.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.json.JSONObject;

import com.bayside.app.opinion.war.power.controller.PowerController;
import com.bayside.app.util.HttpRequest; 

public class AddressUtils {
	private static Logger logger = Logger.getLogger(AddressUtils.class);  
	/**
	  * 
	  * @param content
	  *            请求的参数 格式为：name=xxx&pwd=xxx
	  * @param encoding
	  *            服务器端请求编码。如GBK,UTF-8等
	  * @return
	  * @throws UnsupportedEncodingException
	  */
	 public String getAddresses(String content, String encodingString)
	   throws UnsupportedEncodingException {
	  // 这里调用pconline的接口
	  String urlStr = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
	  // 从http://whois.pconline.com.cn取得IP所在的省市区信息
	 // String returnStr = this.getResult(urlStr, content, encodingString);
	 String returnStr =  HttpRequest.sendGet(urlStr, content);
	  if (returnStr != null) {
	   // 处理返回的省市区信息
	   System.out.println(returnStr);
	   String[] temp = returnStr.split("\t");
	   if(temp.length<3){
	    return "0";//无效IP，局域网测试
	   }
	   String region = "";
	   try {
		   region = temp[3]+temp[4]+temp[5];
		} catch (Exception e) {
			logger.info("数组越界："+e.getMessage());
		}
	   
	   region = decodeUnicode(region);// 省份
	   /**
	    * String country = ""; String area = ""; String region = ""; String
	    * city = ""; String county = ""; String isp = ""; for(int i=0;i<temp.length;i++){
	    * switch(i){ case 1:country =
	    * (temp[i].split(":"))[2].replaceAll("\"", ""); country =
	    * decodeUnicode(country);//国家 break; case 3:area =
	    * (temp[i].split(":"))[1].replaceAll("\"", ""); area =
	    * decodeUnicode(area);//地区 break; case 5:region =
	    * (temp[i].split(":"))[1].replaceAll("\"", ""); region =
	    * decodeUnicode(region);//省份 break; case 7:city =
	    * (temp[i].split(":"))[1].replaceAll("\"", ""); city =
	    * decodeUnicode(city);//市区 break; case 9:county =
	    * (temp[i].split(":"))[1].replaceAll("\"", ""); county =
	    * decodeUnicode(county);//地区 break; case 11:isp =
	    * (temp[i].split(":"))[1].replaceAll("\"", ""); isp =
	    * decodeUnicode(isp);//ISP公司 break; } }
	    */
	   // System.out.println(country+"="+area+"="+region+"="+city+"="+county+"="+isp);
	   return region;
	  }
	  return null;
	 }
	 /**
	  * @param urlStr
	  *            请求的地址
	  * @param content
	  *            请求的参数 格式为：name=xxx&pwd=xxx
	  * @param encoding
	  *            服务器端请求编码。如GBK,UTF-8等
	  * @return
	  */
	/* private String getResult(String urlStr, String content, String encoding) {
	  URL url = null;
	  HttpURLConnection connection = null;
	  try {
	   url = new URL(urlStr);
	   connection = (HttpURLConnection) url.openConnection();// 新建连接实例
	   connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
	   connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
	   connection.setDoOutput(true);// 是否打开输出流 true|false
	   connection.setDoInput(true);// 是否打开输入流true|false
	   connection.setRequestMethod("GET");// 提交方法POST|GET
	   connection.setUseCaches(false);// 是否缓存true|false
	   connection.connect();// 打开连接端口
	   DataOutputStream out = new DataOutputStream(connection
	     .getOutputStream());// 打开输出流往对端服务器写数据
	   out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
	   out.flush();// 刷新
	   out.close();// 关闭输出流
	   BufferedReader reader = new BufferedReader(new InputStreamReader(
	     connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
	   // ,以BufferedReader流来读取
	   StringBuffer buffer = new StringBuffer();
	   String line = "";
	   while ((line = reader.readLine()) != null) {
	    buffer.append(line);
	   }
	   reader.close();
	   return buffer.toString();
	  } catch (IOException e) {
	   e.printStackTrace();
	  } finally {
	   if (connection != null) {
	    connection.disconnect();// 关闭连接
	   }
	  }
	  return null;
	 }*/
	 /**
	  * unicode 转换成 中文
	  * 
	  * @author fanhui 2007-3-15
	  * @param theString
	  * @return
	  */
	 public static String decodeUnicode(String theString) {
	  char aChar;
	  int len = theString.length();
	  StringBuffer outBuffer = new StringBuffer(len);
	  for (int x = 0; x < len;) {
	   aChar = theString.charAt(x++);
	   if (aChar == '\\') {
	    aChar = theString.charAt(x++);
	    if (aChar == 'u') {
	     int value = 0;
	     for (int i = 0; i < 4; i++) {
	      aChar = theString.charAt(x++);
	      switch (aChar) {
	      case '0':
	      case '1':
	      case '2':
	      case '3':
	      case '4':
	      case '5':
	      case '6':
	      case '7':
	      case '8':
	      case '9':
	       value = (value << 4) + aChar - '0';
	       break;
	      case 'a':
	      case 'b':
	      case 'c':
	      case 'd':
	      case 'e':
	      case 'f':
	       value = (value << 4) + 10 + aChar - 'a';
	       break;
	      case 'A':
	      case 'B':
	      case 'C':
	      case 'D':
	      case 'E':
	      case 'F':
	       value = (value << 4) + 10 + aChar - 'A';
	       break;
	      default:
	       throw new IllegalArgumentException(
	         "Malformed      encoding.");
	      }
	     }
	     outBuffer.append((char) value);
	    } else {
	     if (aChar == 't') {
	      aChar = '\t';
	     } else if (aChar == 'r') {
	      aChar = '\r';
	     } else if (aChar == 'n') {
	      aChar = '\n';
	     } else if (aChar == 'f') {
	      aChar = '\f';
	     }
	     outBuffer.append(aChar);
	    }
	   } else {
	    outBuffer.append(aChar);
	   }
	  }
	  return outBuffer.toString();
	 }
	 // 测试
	 public static void main(String[] args) {
	  AddressUtils addressUtils = new AddressUtils();
	  // 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信
	  String ip = "192.168.253.1";
	  String address = "";
	  try {
	   address = addressUtils.getAddresses("ip="+ip, "utf-8");
	  } catch (UnsupportedEncodingException e) {
	   // TODO Auto-generated catch block
		  logger.info(e.getMessage());
	  }
	  System.out.println(address);
	  // 输出结果为：广东省,广州市,越秀区
	 }
	 
	 /**
		 * 
		 * <p>
		 * 方法名称：getSystemRoot
		 * </p>
		 * <p>
		 * 方法描述：获取系统命令路径 ：SystemRoot=C:\WINDOWS
		 * </p>
		 * 
		 * @return
		 * @author liuyy
		 * @since 2016年10月27日
		 *        <p>
		 *        history 2016年10月27日 Administrator 创建
		 *        <p>
		 */
		public static String getSystemRoot() {
			String cmd = null;
			String os = null;
			String result = null;
			String envName = "windir";
			os = System.getProperty("os.name").toLowerCase();
			if (os.startsWith("windows")) {
				cmd = "cmd /c SET";
			} else {
				cmd = "env";
			}
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				InputStreamReader isr = new InputStreamReader(p.getInputStream());
				BufferedReader commandResult = new BufferedReader(isr);
				String line = null;
				while ((line = commandResult.readLine()) != null) {
					line = line.toLowerCase();// 重要(同一操作系统不同电脑有些返回的是小写,有些是大写.因此需要统一转换成小写)
					if (line.indexOf(envName) > -1) {
						result = line.substring(line.indexOf(envName) + envName.length() + 1);
						return result;
					}
				}
			} catch (Exception e) {
				System.out.println("获取系统命令路径 error: " + cmd + ":" + e);
			  logger.info(e.getMessage());
			}
			return null;
		}

		/**
		 * 
		 * <p>
		 * 方法名称：getOSName
		 * </p>
		 * <p>
		 * 方法描述： 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等
		 * </p>
		 * 
		 * @return
		 * @author liuyy
		 * @since 2016年10月27日
		 *        <p>
		 *        history 2016年10月27日 Administrator 创建
		 *        <p>
		 */
		public static String getOSName() {
			return System.getProperty("os.name").toLowerCase();
		}

		public static String getWindowXPMACAddress(String execStr) {
			String mac = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				// windows下的命令，显示信息中包含有mac地址信息
				process = Runtime.getRuntime().exec(execStr);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					if (line.indexOf("本地连接") != -1) // 排除有虚拟网卡的情况
						continue;

					// 寻找标示字符串[physical address]
					index = line.toLowerCase().indexOf("physical address");
					if (index != -1) {
						index = line.indexOf(":");
						if (index != -1) {
							// 取出mac地址并去除2边空格
							mac = line.substring(index + 1).trim();
						}
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					 logger.info(e1.getMessage());
				}
				bufferedReader = null;
				process = null;
			}
			return mac;
		}

		public static String getWindow7MACAddress() {
			// 获取本地IP对象
			InetAddress ia = null;
			try {
				ia = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				logger.info(e.getMessage());
			}
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = null;
			try {
				if(null!=ia){
					mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
				}
				
			} catch (SocketException e) {
				logger.info(e.getMessage());
			}
			// 下面代码是把mac地址拼装成String
			StringBuffer sb = new StringBuffer();
			if(null!=mac){
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						sb.append("-");
					}
					// mac[i] & 0xFF 是为了把byte转化为正整数
					String s = Integer.toHexString(mac[i] & 0xFF);
					sb.append(s.length() == 1 ? 0 + s : s);
				}
			}
			// 把字符串所有小写字母改为大写成为正规的mac地址并返回
			return sb.toString().toUpperCase();
		}

		public static String getLinuxMACAddress() {
			String mac = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				// linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
				process = Runtime.getRuntime().exec("ifconfig eth0");
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					index = line.toLowerCase().indexOf("硬件地址");
					if (index != -1) {
						// 取出mac地址并去除2边空格
						mac = line.substring(index + 4).trim();
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					logger.info(e1.getMessage());
				}
				bufferedReader = null;
				process = null;
			}
			return mac;
		}

		public static String getUnixMACAddress() {
			String mac = null;
			BufferedReader bufferedReader = null;
			Process process = null;
			try {
				// Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
				process = Runtime.getRuntime().exec("ifconfig eth0");
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				int index = -1;
				while ((line = bufferedReader.readLine()) != null) {
					// 寻找标示字符串[hwaddr]
					index = line.toLowerCase().indexOf("hwaddr");
					if (index != -1) {
						// 取出mac地址并去除2边空格
						mac = line.substring(index + "hwaddr".length() + 1).trim();
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e1) {
					logger.info(e1.getMessage());
				}
				bufferedReader = null;
				process = null;
			}

			return mac;
		}

		public static String getMacAddress() {
			String os = getOSName();
			String execStr = getSystemRoot() + "/system32/ipconfig /all";
			String mac = null;
			if (os.startsWith("windows")) {
				if (os.equals("windows xp")) {// xp
					mac = getWindowXPMACAddress(execStr);
				} else if (os.equals("windows 2003")) {// 2003
					mac = getWindowXPMACAddress(execStr);
				} else {// win7
					mac = getWindow7MACAddress();
				}
			} else if (os.startsWith("linux")) {
				mac = getLinuxMACAddress();
			} else {
				mac = getUnixMACAddress();
			}
			return mac;
		}
		/** 
	     * 获取来访者的浏览器版本 
	     * @param request 
	     * @return 
	     */  
	    public static String getRequestBrowserInfo(HttpServletRequest request){  
	        String browserVersion = null;  
	        String header = request.getHeader("user-agent");  
	        if(header == null || header.equals("")){  
	           return "";  
	         }  
	        if(header.indexOf("MSIE")>0){  
	            browserVersion = "IE";  
	        }else if(header.indexOf("Firefox")>0){  
	            browserVersion = "Firefox";  
	        }else if(header.indexOf("Chrome")>0){  
	            browserVersion = "Chrome";  
	        }else if(header.indexOf("Safari")>0){  
	            browserVersion = "Safari";  
	        }else if(header.indexOf("Camino")>0){  
	            browserVersion = "Camino";  
	        }else if(header.indexOf("Konqueror")>0){  
	            browserVersion = "Konqueror";  
	        }  
	        return browserVersion;  
	    }  
	    /** 
	     * 获取访问者IP 
	     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。 
	     *  
	     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 
	     * 如果还不存在则调用Request .getRemoteAddr()。 
	     * @param request 
	     * @return 
	     */  
	    public static String getIpAddr(HttpServletRequest request) {  
	        String ip = request.getHeader("X-Real-IP");  
	        if (ip!= null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {  
	            return ip;  
	        }  
	        ip = request.getHeader("X-Forwarded-For");  
	        if (ip!= null && !"".equals(ip)  && !"unknown".equalsIgnoreCase(ip)) {  
	            // 多次反向代理后会有多个IP值，第一个为真实IP。  
	            int index = ip.indexOf(',');  
	            if (index != -1) {  
	                return ip.substring(0, index);  
	            } else {  
	                return ip;  
	            }  
	        } else {  
	            return request.getRemoteAddr();  
	        }  
	    }  
	    /** 
	     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	     * 
	     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
	     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
	     * 
	     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
	     * 192.168.1.100 
	     * 
	     * 用户真实IP为： 192.168.1.110 
	     * 
	     * @param request 
	     * @return 
	     */
	  
	    public final static String getIpAddress(HttpServletRequest request) throws IOException {  
	        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
	  
	        String ip = request.getHeader("X-Forwarded-For");  
	        if (logger.isInfoEnabled()) {  
	            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);  
	        }  
	  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("Proxy-Client-IP");  
	                if (logger.isInfoEnabled()) {  
	                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("WL-Proxy-Client-IP");  
	                if (logger.isInfoEnabled()) {  
	                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("HTTP_CLIENT_IP");  
	                if (logger.isInfoEnabled()) {  
	                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	                if (logger.isInfoEnabled()) {  
	                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);  
	                }  
	            }  
	            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getRemoteAddr();  
	                if (logger.isInfoEnabled()) {  
	                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);  
	                }  
	            }  
	        } else if (ip.length() > 15) {  
	            String[] ips = ip.split(",");  
	            for (int index = 0; index < ips.length; index++) {  
	                String strIp = (String) ips[index];  
	                if (!("unknown".equalsIgnoreCase(strIp))) {  
	                    ip = strIp;  
	                    break;  
	                }  
	            }  
	        }  
	        return ip;  
	    }  
	    private static String judgeBrowser(String userAgent) {  
	        if (userAgent.contains("Chrome")) {  
	            /** 
	             * *********** 
	             * Chrome 系列 
	             * *********** 
	             * Chrome 24.0.1295.0   -   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.15 (KHTML, like Gecko) Chrome/24.0.1295.0 Safari/537.15 
	             * Chrome 24.0.1292.0   -   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.14 (KHTML, like Gecko) Chrome/24.0.1292.0 Safari/537.14 
	             * Chrome 24.0.1290.1   -   Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.13 (KHTML, like Gecko) Chrome/24.0.1290.1 Safari/537.13 
	             * 判断依据:http://www.useragentstring.com/pages/Chrome/ 
	             */  
	            String temp = userAgent.substring(userAgent.indexOf("Chrome/") + 7);//拿到User Agent String "Chrome/" 之后的字符串,结果形如"24.0.1295.0 Safari/537.15"或"24.0.1295.0"  
	            String chromeVersion = null;  
	            if (temp.indexOf(" ") < 0) {//temp形如"24.0.1295.0"  
	                chromeVersion = temp;  
	            } else {//temp形如"24.0.1295.0 Safari/537.15"  
	                chromeVersion = temp.substring(0, temp.indexOf(" "));  
	            }  
	            return chromeVersion;  
	        } else if (userAgent.contains("Firefox")) {  
	            /** 
	             * ******* 
	             * FF 系列 
	             * ******* 
	             * Firefox 16.0.1   -   Mozilla/5.0 (Windows NT 6.2; Win64; x64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1 
	             * Firefox 15.0a2   -   Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20120716 Firefox/15.0a2 
	             * Firefox 15.0.2   -   Mozilla/5.0 (Windows NT 6.2; WOW64; rv:15.0) Gecko/20120910144328 Firefox/15.0.2 
	             * 判断依据:http://www.useragentstring.com/pages/Firefox/ 
	             */  
	            String temp = userAgent.substring(userAgent.indexOf("Firefox/") + 8);//拿到User Agent String "Firefox/" 之后的字符串,结果形如"16.0.1 Gecko/20121011"或"16.0.1"  
	            String ffVersion = null;  
	            if (temp.indexOf(" ") < 0) {//temp形如"16.0.1"  
	                ffVersion = temp;  
	            } else {//temp形如"16.0.1 Gecko/20121011"  
	                ffVersion = temp.substring(0, temp.indexOf(" "));  
	            }  
	            return ffVersion;  
	        } else if (userAgent.contains("MSIE")) {  
	            /** 
	             * ******* 
	             * IE 系列 
	             * ******* 
	             * MSIE 10.0    -   Internet Explorer 10 
	             * MSIE 9.0 -   Internet Explorer 9 
	             * MSIE 8.0 -   Internet Explorer 8 or IE8 Compatibility View/Browser Mode 
	             * MSIE 7.0 -   Windows Internet Explorer 7 or IE7 Compatibility View/Browser Mode 
	             * MSIE 6.0 -   Microsoft Internet Explorer 6 
	             * 判断依据:http://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx 
	             */  
	            if (userAgent.contains("MSIE 10.0")) {//Internet Explorer 10  
	                return "10";  
	            } else if (userAgent.contains("MSIE 9.0")) {//Internet Explorer 9  
	                return "9";  
	            } else if (userAgent.contains("MSIE 8.0")) {//Internet Explorer 8  
	                return "8";  
	            } else if (userAgent.contains("MSIE 7.0")) {//Internet Explorer 7  
	                return "7";  
	            } else if (userAgent.contains("MSIE 6.0")) {//Internet Explorer 6  
	                return "6";  
	            }  
	        } else {//暂时支持以上三个主流.其它浏览器,待续...  
	            return null;  
	        }  
	        return null;  
	    }  
	    public static String getIp() throws IOException {
	    	
	    	 InputStream ins = null;  
	         try {  
	             URL url = new URL("http://iframe.ip138.com/ic.asp");  
	             URLConnection con = url.openConnection();  
	             ins = con.getInputStream();  
	             InputStreamReader isReader = new InputStreamReader(ins, "GB2312");  
	             BufferedReader bReader = new BufferedReader(isReader);  
	             StringBuffer webContent = new StringBuffer();  
	             String str = null;  
	             while ((str = bReader.readLine()) != null) {  
	                 webContent.append(str);  
	             }  
	             int start = webContent.indexOf("[") + 1;  
	             int end = webContent.indexOf("]");
	             System.out.println(webContent.substring(start, end));
	            return webContent.substring(start, end);  
	         } finally {  
	             if (ins != null) {  
	                 ins.close();  
	             }  
	         }  
	    	
	    	}
}
