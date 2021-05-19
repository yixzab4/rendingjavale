package com.bayside.util;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.sun.mail.util.MailSSLSocketFactory; 
@EnableAutoConfiguration
@PropertySource("classpath:server.properties")
public class NewSendMail {
	@Resource
	private Environment environment;
	
	 public void send(String toAddress,String code) throws Exception { 
		  // 配置信息 
		  Properties pro = new Properties(); 
		  pro.put("mail.smtp.host", environment.getProperty("host")); 
		  pro.put("mail.smtp.auth", "true"); 
		  // SSL加密 
		  MailSSLSocketFactory sf = null; 
		  sf = new MailSSLSocketFactory(); 
		  // 设置信任所有的主机 
		  sf.setTrustAllHosts(true); 
		  pro.put("mail.smtp.ssl.enable", "true"); 
		  pro.put("mail.smtp.ssl.socketFactory", sf); 
		  // 根据邮件的会话属性构造一个发送邮件的Session，这里需要注意的是用户名那里不能加后缀，否则便不是用户名了 
		  //还需要注意的是，这里的密码不是正常使用邮箱的登陆密码，而是客户端生成的另一个专门的授权码 
		  MailAuthenticator authenticator = new MailAuthenticator(environment.getProperty("account"), 
				  environment.getProperty("password")); 
		  Session session = Session.getInstance(pro, authenticator); 
		  // 根据Session 构建邮件信息 
		  Message message = new MimeMessage(session); 
		  // 创建邮件发送者地址 
		  Address from = new InternetAddress(environment.getProperty("username")); 
		  // 设置邮件消息的发送者 
		  message.setFrom(from); 
		 
		  //String toAddress = buffer.toString(); 
		  if (!toAddress.isEmpty()) { 
		   // 创建邮件的接收者地址 
		   Address[] to = InternetAddress.parse(toAddress); 
		   // 设置邮件接收人地址 
		   message.setRecipients(Message.RecipientType.TO, to); 
		   // 邮件主题 
		   // message.setSubject("java邮件测试"); 
		   message.setSubject("请查收验证码"); 
		   // 邮件容器 
		   MimeMultipart mimeMultiPart = new MimeMultipart(); 
		   // 设置HTML 
		   BodyPart bodyPart = new MimeBodyPart(); 
		   // 邮件内容 
		   // String htmlText = "java邮件测试111"; 
		   String htmlText = "验证码是："+code; 
		   bodyPart.setContent(htmlText, "text/html;charset=utf-8"); 
		   mimeMultiPart.addBodyPart(bodyPart); 
		   // 添加附件 
		/*   List<String> fileAddressList = new ArrayList<String>(); 
		   fileAddressList 
		     .add("C:\\Users\\tuzongxun123\\Desktop\\新建 Microsoft Office Word 文档.docx"); 
		   if (fileAddressList != null) { 
		    BodyPart attchPart = null; 
		    for (int i = 0; i < fileAddressList.size(); i++) { 
		     if (!fileAddressList.get(i).isEmpty()) { 
		      attchPart = new MimeBodyPart(); 
		      // 附件数据源 
		      DataSource source = new FileDataSource( 
		        fileAddressList.get(i)); 
		      // 将附件数据源添加到邮件体 
		      attchPart.setDataHandler(new DataHandler(source)); 
		      // 设置附件名称为原文件名 
		      attchPart.setFileName(MimeUtility.encodeText(source 
		        .getName())); 
		      mimeMultiPart.addBodyPart(attchPart); 
		     } 
		    } 
		   } */
		   message.setContent(mimeMultiPart); 
		   message.setSentDate(new Date()); 
		   // 保存邮件 
		   message.saveChanges(); 
		   // 发送邮件 
		   Transport.send(message); 
		  } 
		 } 
	 //向多人发邮件

	 public void qunsend(String code) throws Exception { 
		  // 配置信息 
		  Properties pro = new Properties(); 
		  pro.put("mail.smtp.host", environment.getProperty("host")); 
		  pro.put("mail.smtp.auth", "true"); 
		  // SSL加密 
		  MailSSLSocketFactory sf = null; 
		  sf = new MailSSLSocketFactory(); 
		  // 设置信任所有的主机 
		  sf.setTrustAllHosts(true); 
		  pro.put("mail.smtp.ssl.enable", "true"); 
		  pro.put("mail.smtp.ssl.socketFactory", sf); 
		  // 根据邮件的会话属性构造一个发送邮件的Session，这里需要注意的是用户名那里不能加后缀，否则便不是用户名了 
		  //还需要注意的是，这里的密码不是正常使用邮箱的登陆密码，而是客户端生成的另一个专门的授权码 
		  MailAuthenticator authenticator = new MailAuthenticator(environment.getProperty("account"), 
				 environment.getProperty("password")); 
		  Session session = Session.getInstance(pro, authenticator); 
		  // 根据Session 构建邮件信息 
		  Message message = new MimeMessage(session); 
		  // 创建邮件发送者地址 
		  Address from = new InternetAddress(environment.getProperty("username")); 
		  // 设置邮件消息的发送者 
		  message.setFrom(from); 
		 
		  //String toAddress = buffer.toString(); 
		  String to[]={"1747976593@qq.com","408275829@qq.com"};  
		  String toList = getMailList(to);  
		   // 创建邮件的接收者地址 
		   Address[] iaToList = InternetAddress.parse(toList); 
		   // 设置邮件接收人地址 
		   message.setRecipients(Message.RecipientType.TO, iaToList); 
		   // 邮件主题 
		   // message.setSubject("java邮件测试"); 
		   message.setSubject("请查收验证码"); 
		   message.setSentDate(new Date()); // 发送日期     
		   message.setSubject("javamail测试邮件"); // 主题     
		   message.setText("注意，这是测试程序发的，请不要回复！"); //内容     
		   // 邮件容器 
		   MimeMultipart mimeMultiPart = new MimeMultipart(); 
		   // 设置HTML 
		   BodyPart bodyPart = new MimeBodyPart(); 
		   // 邮件内容 
		   // String htmlText = "java邮件测试111"; 
		   String htmlText = "验证码是："+code; 
		   bodyPart.setContent(htmlText, "text/html;charset=utf-8"); 
		   mimeMultiPart.addBodyPart(bodyPart); 
		   // 添加附件 
		/*   List<String> fileAddressList = new ArrayList<String>(); 
		   fileAddressList 
		     .add("C:\\Users\\tuzongxun123\\Desktop\\新建 Microsoft Office Word 文档.docx"); 
		   if (fileAddressList != null) { 
		    BodyPart attchPart = null; 
		    for (int i = 0; i < fileAddressList.size(); i++) { 
		     if (!fileAddressList.get(i).isEmpty()) { 
		      attchPart = new MimeBodyPart(); 
		      // 附件数据源 
		      DataSource source = new FileDataSource( 
		        fileAddressList.get(i)); 
		      // 将附件数据源添加到邮件体 
		      attchPart.setDataHandler(new DataHandler(source)); 
		      // 设置附件名称为原文件名 
		      attchPart.setFileName(MimeUtility.encodeText(source 
		        .getName())); 
		      mimeMultiPart.addBodyPart(attchPart); 
		     } 
		    } 
		   } */
		   message.setContent(mimeMultiPart); 
		   message.setSentDate(new Date()); 
		   // 保存邮件 
		   message.saveChanges(); 
		   // 发送邮件 
		   Transport.send(message); 
		
		 } 
	 private String getMailList(String[] mailArray){  
         
	        StringBuffer toList = new StringBuffer();  
	    int length = mailArray.length;  
	        if(mailArray!=null && length <2){  
	             toList.append(mailArray[0]);  
	        }else{  
	             for(int i=0;i<length;i++){  
	                     toList.append(mailArray[i]);  
	                     if(i!=(length-1)){  
	                         toList.append(",");  
	                     }  
	  
	             }  
	         }  
	     return toList.toString();  
	  
	}  
	 //找回密码
	 public void find(String toAddress,String code) throws Exception { 
		  // 配置信息 
		  Properties pro = new Properties(); 
		  pro.put("mail.smtp.host", "smtp.163.com"); 
		  pro.put("mail.smtp.auth", "true"); 
		  // SSL加密 
		  MailSSLSocketFactory sf = null; 
		  sf = new MailSSLSocketFactory(); 
		  // 设置信任所有的主机 
		  sf.setTrustAllHosts(true); 
		  pro.put("mail.smtp.ssl.enable", "true"); 
		  pro.put("mail.smtp.ssl.socketFactory", sf); 
		  // 根据邮件的会话属性构造一个发送邮件的Session，这里需要注意的是用户名那里不能加后缀，否则便不是用户名了 
		  //还需要注意的是，这里的密码不是正常使用邮箱的登陆密码，而是客户端生成的另一个专门的授权码 
		  MailAuthenticator authenticator = new MailAuthenticator("15753158017", 
		    "lyy0122"); 
		  Session session = Session.getInstance(pro, authenticator); 
		  // 根据Session 构建邮件信息 
		  Message message = new MimeMessage(session); 
		  // 创建邮件发送者地址 
		  Address from = new InternetAddress("15753158017@163.com"); 
		  // 设置邮件消息的发送者 
		  message.setFrom(from); 
		 
		  //String toAddress = buffer.toString(); 
		  if (!toAddress.isEmpty()) { 
		   // 创建邮件的接收者地址 
		   Address[] to = InternetAddress.parse(toAddress); 
		   // 设置邮件接收人地址 
		   message.setRecipients(Message.RecipientType.TO, to); 
		   // 邮件主题 
		   // message.setSubject("java邮件测试"); 
		   message.setSubject("请查看找回的密码"); 
		   // 邮件容器 
		   MimeMultipart mimeMultiPart = new MimeMultipart(); 
		   // 设置HTML 
		   BodyPart bodyPart = new MimeBodyPart(); 
		   // 邮件内容 
		   // String htmlText = "java邮件测试111"; 
		   String htmlText = "您的密码是："+code; 
		   bodyPart.setContent(htmlText, "text/html;charset=utf-8"); 
		   mimeMultiPart.addBodyPart(bodyPart); 
		   // 添加附件 
		/*   List<String> fileAddressList = new ArrayList<String>(); 
		   fileAddressList 
		     .add("C:\\Users\\tuzongxun123\\Desktop\\新建 Microsoft Office Word 文档.docx"); 
		   if (fileAddressList != null) { 
		    BodyPart attchPart = null; 
		    for (int i = 0; i < fileAddressList.size(); i++) { 
		     if (!fileAddressList.get(i).isEmpty()) { 
		      attchPart = new MimeBodyPart(); 
		      // 附件数据源 
		      DataSource source = new FileDataSource( 
		        fileAddressList.get(i)); 
		      // 将附件数据源添加到邮件体 
		      attchPart.setDataHandler(new DataHandler(source)); 
		      // 设置附件名称为原文件名 
		      attchPart.setFileName(MimeUtility.encodeText(source 
		        .getName())); 
		      mimeMultiPart.addBodyPart(attchPart); 
		     } 
		    } 
		   } */
		   message.setContent(mimeMultiPart); 
		   message.setSentDate(new Date()); 
		   // 保存邮件 
		   message.saveChanges(); 
		   // 发送邮件 
		   Transport.send(message); 
		  } 
		 } 
		
} 
		  
		class MailAuthenticator extends Authenticator { 
		  
		 /** 
		  * 用户名 
		  */
		 private String username; 
		 /** 
		  * 密码 
		  */
		 private String password; 
		  
		 /** 
		  * 创建一个新的实例 MailAuthenticator. 
		  * 
		  * @param username 
		  * @param password 
		  */
		 public MailAuthenticator(String username, String password) { 
		  this.username = username; 
		  this.password = password; 
		 } 
		  
		 public String getPassword() { 
		  return password; 
		 } 
		  
		 @Override
		 protected PasswordAuthentication getPasswordAuthentication() { 
		  return new PasswordAuthentication(username, password); 
		 } 
		  
		 public String getUsername() { 
		  return username; 
		 } 
		  
		 public void setPassword(String password) { 
		  this.password = password; 
		 } 
		  
		 public void setUsername(String username) { 
		  this.username = username; 
		 } 
		  
		}



