package com.bayside.app.opinion.war.power.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.ws.rs.core.Application;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.GeneratorSqlmap;
import com.bayside.app.opinion.war.mymessage.bo.MessageCenterBo;
import com.bayside.app.opinion.war.mymessage.model.Messagecenter;
import com.bayside.app.opinion.war.mymessage.service.MyMessageService;
import com.bayside.app.opinion.war.myuser.bo.StanderPowerBo;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.myuser.service.impl.UserServiceImpl;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.power.service.impl.PowerServiceImpl;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DBUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.AddressUtils;
import com.bayside.util.CheckSumBuilder;
import com.bayside.util.IpUtil;
import com.bayside.util.NewSendMail;
import com.bayside.util.SendCode;
import com.bayside.util.SessionListener;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.PreparedStatement;

import redis.clients.jedis.ShardedJedis;

@RestController
@EnableAutoConfiguration
@PropertySource("classpath:server.properties")
public class PowerController {
	@Resource
	private Environment environment;
	@Autowired
	private PowerUserService powerServiceImpl;
	@Autowired
	private MyMessageService myMessageServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	public static Random r = new Random();
	private static Logger logger = Logger.getLogger(PowerController.class);
    /** 
 * 用户和Session绑定关系 
 */  
public static final Map<String, HttpSession> USER_SESSION=new HashMap<String, HttpSession>();  
  
/** 
 * seeionId和用户的绑定关系 
 */  
public static final Map<String, String> SESSIONID_USER=new HashMap<String, String>();
   /* @RequestMapping(value = "/bathupdatePower", method = RequestMethod.GET)
    public Response bathupdatePower(String id){
    	List<WordSet> list = userServiceImpl.selectAllWordSet();
    	for(int i=0;i<list.size();i++){
    		list.get(i).setId(UuidUtil.getUUID());
    		list.get(i).setName("重点关注个数");
    		list.get(i).setCansetword(1000);
    		list.get(i).setStatus(0);
    	}
    	WordSet ws = new WordSet();
    	ws.setListwordset(list);
    	int num = userServiceImpl.bathaddWordSet(ws);
	    if(num > 0){
	    	return new Response(ResponseStatus.Success,num,true);
	    }else{
	    	return null;
	    }
     }*/
	/**
	 * <p>
	 * 方法名称：saveUserInfo
	 * </p>
	 * <p>
	 * 方法描述：保存提交申请的用户
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/saveUserInfo", method = RequestMethod.GET)
	public Response saveUserInfo(User user, HttpServletRequest request, HttpSession session) {
     
        	user.setId(UuidUtil.getUUID());
    		System.out.println(user.getName() + "name11");
    		// HttpSession session = ServletActionContext.getRequest().getSession();
    		String code = request.getParameter("code");
    		List<User> s = powerServiceImpl.selectByTel(user);
            if(s.size()>0){
            	return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
            }else{
            	if (null!=code && !"".equals(code)&& code.equals(user.getCode())) {
        			// user.set
        			user.setRegistertime(new Date());
        			int num = powerServiceImpl.saveUser(user);
        			if (num > 0) {
        			
        				// HttpSession session = request.getSession();
        				// session.setAttribute("username", "yugi");

        				return new Response(ResponseStatus.Success, num, true);
        			} else {
        				
        				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
        			}
        		} else {
        			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
        		}	
            }
    		
       
		

	}

	/**
	 * <p>
	 * 方法名称：sendTelCheck
	 * </p>
	 * <p>
	 * 方法描述：//发送手机验证码
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/sendTelCheck", method = RequestMethod.GET)
	public Response sendTelCheck(User user, HttpSession session) {
		Boolean flag = true;
		long nums = Math.abs(r.nextLong() % 10000000000L);
	
		Random random = new Random();

		String s = "";

		for (int i = 0; i < 6; i++) {

			s += random.nextInt(10);

		}
		session.setAttribute("code", s);
		System.out.println(s);
		String name = "用户";
		String tel = user.getMobilephone();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("Account", "lyy"));
		formparams.add(new BasicNameValuePair("Pwd", "3355315CD86A2BC5B0A6F2114DC4"));
		formparams.add(new BasicNameValuePair("Content", name + "||" + s));
		formparams.add(new BasicNameValuePair("Mobile", tel));
		formparams.add(new BasicNameValuePair("TemplateId", "30065"));

		formparams.add(new BasicNameValuePair("SignId", "30273"));
		try {
			if (flag) {
				SendCode.Post(formparams);
				flag = false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage(),e);
		}
		return new Response(ResponseStatus.Success, s, true);

	}
	
	public void sessionDestroyed(HttpSessionEvent se) {  
	    String sessionId=se.getSession().getId();  
	    //当前session销毁时删除当前session绑定的用户信息  
	    //同时删除当前session绑定用户的HttpSession  
	    USER_SESSION.remove(SESSIONID_USER.remove(sessionId));  
	}  
	@RequestMapping(value = "/selectUserById", method = RequestMethod.GET)
	public Response selectUserById(HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		User user = powerServiceImpl.selectUserById(userid);
		if(null !=user){
			return new Response(ResponseStatus.Success, user.getAddress(), true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
    /** 
    * 用户登录 
    */  
	@RequestMapping(value = "/selectUserInfo", method = RequestMethod.POST)
    public Response selectUserInfo(HttpServletRequest request,HttpSession session,  
        HttpServletResponse response,@RequestBody User user,Application application){  
	
		//session.setAttribute("user"+i,userId);
	    String userName=user.getName();  
	    String password=user.getPassword(); 
	    String ip = request.getRemoteHost();
	    System.out.println("ipipipiipip"+ip);
	   session.setMaxInactiveInterval(30 * 60);
        //登录成功   
    	User newUser = powerServiceImpl.selectUser(user);
    	UserBo ub = new UserBo();
    	
        if(null!=newUser){ 
        	if(null==newUser.getParentid() || "".equals(newUser.getParentid())){
   			 ub.setIsparent(1);
   		}else{
   			ub.setIsparent(0);
   		}
   		BeanUtils.copyProperties(newUser, ub);
   		UserBo us = powerServiceImpl.selectPowerByUserId(ub);
        	//从redis 里获取缓存用户  查看是否已经超过最大个数 缓存用户 ip 与session ip 做对比  将新登录个数保存到缓存
        	 String key = newUser.getId();
        	  ObjectMapper mapper = new ObjectMapper();
 			 ShardedJedis shardedJedis = null;
 			if (shardedJedis == null) {
 				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
 						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
 			}
 			boolean flag = false;
 		     List<UserBo> listbo = new ArrayList<UserBo>();
 			try {
				String sttr = shardedJedis.hget(key, "getloginuser");
				if(sttr!=null&&!"".equals(sttr)){
					JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
							UserBo.class);
					listbo =  mapper.readValue(sttr,javaType);
					flag = true;
				
				}
			} catch (Exception e) {
				// TODO: handle exception
				
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.LOGINERRO,false);
			}
 			List<UserBo> newlist = new ArrayList<UserBo>(); 
        	if(flag){
        		//从缓存获取数据 与 session 做对比
        		
        		for(int i=0;i<listbo.size();i++){
        		      //判断   redis里是否存在 session
        			String name = "spring:session:sessions:"+listbo.get(i).getId();
        			String idkey ="sessionAttr:userid";
        		
        			
        			String sttr = shardedJedis.hget(idkey, name);
        			
        			if(sttr!=null&&!"".equals(sttr)){
        				newlist.add(listbo.get(i));
        			}
        		}
        		int loginnum = 1;
        		if(us!=null){
        			if(null!=us.getShiyongAcount()){
        				loginnum = us.getShiyongAcount();
        			}
        		}
        		if(newlist.size()>=loginnum){
        			return new Response(ResponseStatus.Error,AppConstant.responseInfo.LOGINEXCEPTION, false);
        		}else{
        			//将登录用户放入缓存
           		
           		
           		UserBo ipuser = new UserBo();
           		ipuser.setId(session.getId());
           		newlist.add(ipuser);
           		try {
           			shardedJedis.hset(key, "getloginuser",mapper.writeValueAsString(newlist));
           			shardedJedis.expire(key, 30 * 60);
				} catch (Exception e) {
					// TODO: handle exception

					logger.info(e.getMessage());
					logger.error(e.getMessage(),e);
					return new Response(ResponseStatus.Error,AppConstant.responseInfo.LOGINERRO,false);
				}finally {
					shardedJedis.disconnect();
					shardedJedis.close();
				} 
        			
        		}
        	}else{
        	  //将登录用户放入缓存
        		//将登录用户放入缓存
    		
        		UserBo ipuser = new UserBo();
        		ipuser.setId(session.getId());
        		newlist.add(ipuser);
        		try {
           			shardedJedis.hset(key, "getloginuser",mapper.writeValueAsString(newlist));
           			shardedJedis.expire(key, 30 * 60);
				} catch (Exception e) {
					// TODO: handle exception
					logger.info(e.getMessage());
					logger.error(e.getMessage(),e);
				}finally {
					shardedJedis.disconnect();
					shardedJedis.close();
				} 
        		
        	}
        	session.setAttribute("stander", "userstander");
           System.out.println(session.getId());
           if(userName.equals("ww")){
        	   System.out.println(userName);
           }else{
        	 // SessionListener.isAlreadyEnter(session, newUser.getId());
           }
           
         
 			
 			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
 			if (null!=newUser.getExpirydate()) {
 				String time = sd.format(newUser.getExpirydate());
 				ub.setExpirydate(time);
 			}
 			if (us != null) {
 				ub.setSubsetword(us.getSubsetword());
 				ub.setSubjecttimes(us.getSubjecttimes());
 				ub.setPersonsetword(us.getPersonsetword());
 				ub.setPersontimes(us.getPersontimes());
 				ub.setKeywordNum(us.getKeywordNum());
 				ub.setKeywordsetword(us.getKeywordsetword());
 				ub.setMonitortimes(us.getMonitortimes());
 				ub.setMonsetword(us.getMonsetword());
 				ub.setWarnsetword(us.getWarnsetword());
 				ub.setWarntimes(us.getWarntimes());
 				ub.setSreportsetword(us.getSreportsetword());
 				ub.setSubjectReport(us.getSubjectReport());
 				ub.setCloudsetword(us.getCloudsetword());
 				ub.setCloudtimes(us.getCloudtimes());
 				ub.setDayReport(us.getDayReport());
 				ub.setWeekReport(us.getWeekReport());
 				ub.setMonthReport(us.getMonthReport());
 				ub.setSmodalNum(us.getSmodalNum());
 				ub.setModalNum(us.getModalNum());
 				ub.setMicroopen(us.getMicroopen());
 				ub.setUsertimes(us.getUsertimes());
 				ub.setSetuserNum(us.getSetuserNum());
 				ub.setSetTrade(us.getSetTrade());
 				ub.setTotalid(us.getTotalid());
 				ub.setInnerAcount(us.getInnerAcount());
 			     if(null!=us.getIsupdate()){
 			    	 ub.setIsupdate(us.getIsupdate());
 			     }else{
 			    	 ub.setIsupdate(null);
 			     }
 			}
 			List<String> formatslist = new ArrayList<String>();
 			formatslist.add(AppConstant.mediaType.NEWS);
 			formatslist.add(AppConstant.mediaType.LUNTAN);
 			formatslist.add(AppConstant.mediaType.BLOG);
 			formatslist.add(AppConstant.mediaType.TIEBA);
 			formatslist.add(AppConstant.mediaType.WEIBO);
 			formatslist.add(AppConstant.mediaType.PRINT_MEDIA);
 			formatslist.add(AppConstant.mediaType.WEIXIN);
 			formatslist.add(AppConstant.mediaType.VIDEO);
 			formatslist.add(AppConstant.mediaType.APP);
 			formatslist.add(AppConstant.mediaType.COMMENT);
 			formatslist.add(AppConstant.mediaType.OTHER);
 			formatslist.add(AppConstant.mediaType.ABROAD);
 			formatslist.add(AppConstant.mediaType.TV);
 			formatslist.add(AppConstant.mediaType.BT);
 			if(null!=ub.getSetTrade()){
 				if(1==ub.getSetTrade()){
 					formatslist.add(AppConstant.mediaType.TRADE);
 					session.setAttribute("setTrade", ub.getSetTrade());
 				}else{
 					session.setAttribute("setTrade", 0);
 				}
 			}else{
 				session.setAttribute("setTrade", 0);
 			}
 			if(null!=ub.getInnerAcount()){
 				if(1==ub.getInnerAcount()){
 				
 					session.setAttribute("ismedia", ub.getInnerAcount());
 				}else{
 					session.setAttribute("ismedia", 0);
 				}
 			}else{
 				session.setAttribute("ismedia", 0);
 			}
 			/*if(null!= newUser.getManagerid() && !"".equals(newUser.getManagerid())){
 				ManagerUser mu = userServiceImpl.selectManagerUserById(newUser.getManagerid());
 				if(null!=mu){
 					ub.setStartTime(mu.getLogo());
 					ub.setEndTime(mu.getImg());
 					ub.setManagername(mu.getContent());
 				}else{
 					ub.setStartTime("");
 					ub.setEndTime("");
 					ub.setManagername("");

 				}
 			}*/
 			session.setAttribute("formatslist",formatslist);
 			session.setAttribute("userid", newUser.getId());
 			session.setAttribute("user", newUser);
 			session.setAttribute("name", newUser.getName());
 			session.setAttribute("orgname", newUser.getOrgname());
 			session.setAttribute("usertype", newUser.getUsertype());
 			session.setAttribute("managerid", newUser.getManagerid());
 			session.setAttribute("orgname", newUser.getOrgname());
 	        session.setAttribute("ub", ub);
 	       
 	     //  session.setAttribute("stander", "userlogin");
 			
 			int num = powerServiceImpl.insertSelective(newUser, request);
 			//  查询此用户的、logo 页面底部信息
 			
 			return new Response(ResponseStatus.Success, ub, true);
            }else{
            	return new Response(ResponseStatus.Error, AppConstant.responseInfo.LOGINEERRO, false);
            }
  
    }  
	@RequestMapping(value = "/selectManagerUser", method = RequestMethod.GET)
	public Response selectManagerUser(HttpServletRequest request) {
		String id = (String) request.getSession().getAttribute("managerid");
		if (id != null && !"".equals(id)) {
			ManagerUser mu = userServiceImpl.selectManagerUserById(id);
			if (mu != null) {
				return new Response(ResponseStatus.Success, mu, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}
	/**
	 * <p>
	 * Title:selectUserInfo
	 * </P>
	 * <p>
	 * Description: 验证登录是否成功
	 * </p>
	 * <p>
	 * Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016
	 * </p>
	 * 
	 * @author sql
	 * @version 1.0
	 * @since 2016年10月12日
	 */
	/*@RequestMapping(value = "/selectUserInfo", method = RequestMethod.POST)
	public Response selectUserInfo(@RequestBody User user, HttpSession session, HttpServletRequest request) {
		session.setMaxInactiveInterval(30 * 60);
		  //当前sessionId  
		User newUser = powerServiceImpl.selectUser(user);
		UserBo ub = new UserBo();
		
		if (newUser == null) {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		} else {
			///////唯一登录
			if(null==newUser.getParentid() || "".equals(newUser.getParentid())){
				 ub.setIsparent(1);
			}else{
				ub.setIsparent(0);
			}
			BeanUtils.copyProperties(newUser, ub);
			UserBo us = powerServiceImpl.selectPowerByUserId(ub);

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			if (newUser.getExpirydate() != null && !"".equals(newUser.getExpirydate())) {
				String time = sd.format(newUser.getExpirydate());
				ub.setExpirydate(time);
			}
			if (us != null) {
				ub.setSubsetword(us.getSubsetword());
				ub.setSubjecttimes(us.getSubjecttimes());
				ub.setPersonsetword(us.getPersonsetword());
				ub.setPersontimes(us.getPersontimes());
				ub.setKeywordNum(us.getKeywordNum());
				ub.setKeywordsetword(us.getKeywordsetword());
				ub.setMonitortimes(us.getMonitortimes());
				ub.setMonsetword(us.getMonsetword());
				ub.setWarnsetword(us.getWarnsetword());
				ub.setWarntimes(us.getWarntimes());
				ub.setSreportsetword(us.getSreportsetword());
				ub.setSubjectReport(us.getSubjectReport());
				ub.setCloudsetword(us.getCloudsetword());
				ub.setCloudtimes(us.getCloudtimes());
				ub.setDayReport(us.getDayReport());
				ub.setWeekReport(us.getWeekReport());
				ub.setMonthReport(us.getMonthReport());
				ub.setSmodalNum(us.getSmodalNum());
				ub.setModalNum(us.getModalNum());
				ub.setMicroopen(us.getMicroopen());
				ub.setUsertimes(us.getUsertimes());
				ub.setSetuserNum(us.getSetuserNum());
				ub.setSetTrade(us.getSetTrade());
			}
			List<String> formatslist = new ArrayList<String>();
			formatslist.add(AppConstant.mediaType.NEWS);
			formatslist.add(AppConstant.mediaType.LUNTAN);
			formatslist.add(AppConstant.mediaType.BLOG);
			formatslist.add(AppConstant.mediaType.TIEBA);
			formatslist.add(AppConstant.mediaType.WEIBO);
			formatslist.add(AppConstant.mediaType.PRINT_MEDIA);
			formatslist.add(AppConstant.mediaType.WEIXIN);
			formatslist.add(AppConstant.mediaType.VIDEO);
			formatslist.add(AppConstant.mediaType.APP);
			formatslist.add(AppConstant.mediaType.COMMENT);
			formatslist.add(AppConstant.mediaType.OTHER);
			formatslist.add(AppConstant.mediaType.ABROAD);
			formatslist.add(AppConstant.mediaType.TV);
			formatslist.add(AppConstant.mediaType.BT);
			if(null!=ub.getSetTrade()){
				if(1==ub.getSetTrade()){
					formatslist.add(AppConstant.mediaType.TRADE);
					session.setAttribute("setTrade", ub.getSetTrade());
				}else{
					session.setAttribute("setTrade", 0);
				}
			}else{
				session.setAttribute("setTrade", 0);
			}
			session.setAttribute("formatslist",formatslist);
			session.setAttribute("userid", newUser.getId());
			session.setAttribute("user", newUser);
			session.setAttribute("name", newUser.getName());
			session.setAttribute("orgname", newUser.getOrgname());
			session.setAttribute("usertype", newUser.getUsertype());
			session.setAttribute("managerid", newUser.getManagerid());
			session.setAttribute("orgname", newUser.getOrgname());
	        session.setAttribute("ub", ub);
			
			int num = powerServiceImpl.insertSelective(newUser, request);

			return new Response(ResponseStatus.Success, ub, true);
		}

	}*/
	@RequestMapping(value = "/selectPcPower", method = RequestMethod.GET)
    public Response selectPcPower(HttpServletRequest request){
    		UserBo u = (UserBo)request.getSession().getAttribute("ub");
    		if(u!=null){
    			return new Response(ResponseStatus.Success, u, true);
    		}else{
    			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
    		}
    }
	@RequestMapping(value = "/selectPowerByUserId", method = RequestMethod.GET)
	public Response selectPowerByUserId(HttpServletRequest request) {
		UserBo ub = new UserBo();
		String userid = (String) request.getSession().getAttribute("userid");
		ub.setId(userid);
		UserBo us = powerServiceImpl.selectPowerByUserId(ub);
		if (us != null) {
			return new Response(ResponseStatus.Success, us, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectByName
	 * </p>
	 * <p>
	 * 方法描述： 通过用户名查询登录用户是否存在
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByName", method = RequestMethod.POST)
	public Response selectByName(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		String name = request.getParameter("name");
		user.setName(name);
		List<User> newUser = new ArrayList<User>();
		if (user.getName() != null || !"".equals(user.getName())) {
			newUser = powerServiceImpl.selectByName(user);
		}

		if (newUser.size() > 0) {
			return new Response(ResponseStatus.Success, newUser, true);
		} else {
			return new Response(ResponseStatus.Error, "", false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectByTel
	 * </p>
	 * <p>
	 * 方法描述：通过手机号查询用户是否存在
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByTel", method = RequestMethod.POST)
	public Response selectByTel(HttpServletRequest request, HttpServletResponse response) {
		List<User> newuser = new ArrayList<User>();
		User user = new User();
		String tel = request.getParameter("tel");
		user.setMobilephone(tel);
		if (user.getMobilephone() != null || !"".equals(user.getMobilephone())) {
			newuser = powerServiceImpl.selectByTel(user);
		}

		return new Response(ResponseStatus.Success, newuser, true);

	}

	@RequestMapping(value = "/selectPassword", method = RequestMethod.POST)
	public Response selectPassword(HttpServletRequest request, HttpServletResponse response) {
		String id = (String) request.getSession().getAttribute("userid");
		User user = powerServiceImpl.selectagentById(id);

		if (user != null) {
			return new Response(ResponseStatus.Success, user, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectByEmail
	 * </p>
	 * <p>
	 * 方法描述：通过邮箱号查询登录用户
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByEmail", method = RequestMethod.POST)
	public Response selectByEmail(HttpServletRequest request, HttpServletResponse response) {
		List<User> newUser = new ArrayList<User>();
		User user = new User();
		String email = request.getParameter("email");
		user.setEmail(email);
		if (user.getEmail() != null || !"".equals(user.getEmail())) {
			newUser = powerServiceImpl.selectByEmail(user);
		}

		if (newUser.size() > 0) {
			return new Response(ResponseStatus.Success, newUser, true);
		} else {
			return new Response(ResponseStatus.Error, "", false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllUserInfo
	 * </p>
	 * <p>
	 * 方法描述：查询所有登录用户信息
	 * </p>
	 * 
	 * @param user
	 * @param page
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllUserInfo", method = RequestMethod.GET)
	public Response selectAllUserInfo(User user, PageInfo page) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<User> list = powerServiceImpl.selectuserInfo(user);
		PageInfo<User> info = new PageInfo<User>(list);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：updateUserInfo
	 * </p>
	 * <p>
	 * 方法描述： 修改用户
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.GET)
	public Response updateUserInfo(UserBo record) {
		User user = powerServiceImpl.updateUserA(record);
		if (user!=null) {
			if(!"".equals(user.getId()) && user.getId() != null){
				int num = powerServiceImpl.updateUserAttr(user);
				if (num > 0) {
					return new Response(ResponseStatus.Success, num, true);
				} else {
					return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
				}
			}else{
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
			
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateUserShen
	 * </p>
	 * <p>
	 * 方法描述：审核成功
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月28日
	 *        <p>
	 *        history 2016年11月28日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserShen", method = RequestMethod.GET)
	public Response updateUserShen(UserBo record) {
		User user = powerServiceImpl.userShen(record);
		if (user!=null) {
			if(user.getId() != null && !"".equals(user.getId())){
				int num = powerServiceImpl.updateUserShen(user);
				if (num > 0) {
					return new Response(ResponseStatus.Success, num, true);
				} else {
					return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
				}
			}else{
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
			
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateUserStatus
	 * </p>
	 * <p>
	 * 方法描述：修改用户状态 禁止 审核失败
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月28日
	 *        <p>
	 *        history 2016年11月28日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserStatus", method = RequestMethod.GET)
	public Response updateUserStatus(UserBo record) {
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (record.getExpirydate() != null && !"".equals(record.getExpirydate())) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage(),e);
			}
		}
		if (user.getId() != null && !"".equals(user.getId())) {
			int num = powerServiceImpl.updateUserStatus(user);
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectPowerByName
	 * </p>
	 * <p>
	 * 方法描述：根据用户id 权限名称 查询相应权限
	 * </p>
	 * 
	 * @param request
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectPowerByName", method = RequestMethod.GET)
	public Response selectPowerByName(HttpServletRequest request, WordSet record) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		WordSet ws = new WordSet();
		ws = powerServiceImpl.selectPowerByName(record);
		if (ws != null) {
			return new Response(ResponseStatus.Success, ws, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：checkUserInfo
	 * </p>
	 * <p>
	 * 方法描述： 查看用户属性
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/checkUserInfo", method = RequestMethod.GET)
	public Response checkUserInfo(User user) {
		UserBo ub = powerServiceImpl.checkagentById(user.getId(), user);
		if (ub != null) {
			return new Response(ResponseStatus.Success, ub, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：repassword
	 * </p>
	 * <p>
	 * 方法描述：随机生成密码
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/repassword", method = RequestMethod.GET)
	public Response repassword(User user) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		user.setPassword(result);
		if (user.getId() != null && !"".equals(user.getId())) {
			int num = powerServiceImpl.updatePassword(user);
			if (num > 0) {
				return new Response(ResponseStatus.Success, user, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：checkLoginUser
	 * </p>
	 * <p>
	 * 方法描述：查询登录用户信息 在header 显示登录人姓名
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年12月1日
	 *        <p>
	 *        history 2016年12月1日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/checkLoginUser", method = RequestMethod.GET)
	public Response checkLoginUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			return new Response(ResponseStatus.Success, user, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectUserPower
	 * </p>
	 * <p>
	 * 方法描述：查询用户权限
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectUserPower", method = RequestMethod.GET)
	public Response selectUserPower(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		User user = powerServiceImpl.selectByPrimaryKey(userid);
		if (user != null) {
			return new Response(ResponseStatus.Success, user, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/checklogin", method = RequestMethod.GET)
   public Response checklogin(User record,HttpSession session, HttpServletRequest request){
		String userName=record.getName();  
	    String password=record.getPassword(); 
	   session.setMaxInactiveInterval(30 * 60);
	   User newUser = userServiceImpl.checklogin(record);
		UserBo ub = new UserBo();
		if (newUser == null) {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		} else {
			/*SessionListener.isAlreadyEnter(session, newUser.getId());*/
			session.setAttribute("stander", "managerstander");
			if(null==newUser.getParentid() && "".equals(newUser.getParentid())){
				 ub.setIsparent(1);
			}else{
				ub.setIsparent(0);
			}
			BeanUtils.copyProperties(newUser, ub);
			UserBo us = powerServiceImpl.selectPowerByUserId(ub);

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			if (null!=newUser.getExpirydate()) {
				String time = sd.format(newUser.getExpirydate());
				ub.setExpirydate(time);
			}
			if (us != null) {
				ub.setSubsetword(us.getSubsetword());
				ub.setSubjecttimes(us.getSubjecttimes());
				ub.setPersonsetword(us.getPersonsetword());
				ub.setPersontimes(us.getPersontimes());
				ub.setKeywordNum(us.getKeywordNum());
				ub.setKeywordsetword(us.getKeywordsetword());
				ub.setMonitortimes(us.getMonitortimes());
				ub.setMonsetword(us.getMonsetword());
				ub.setWarnsetword(us.getWarnsetword());
				ub.setWarntimes(us.getWarntimes());
				ub.setSreportsetword(us.getSreportsetword());
				ub.setSubjectReport(us.getSubjectReport());
				ub.setCloudsetword(us.getCloudsetword());
				ub.setCloudtimes(us.getCloudtimes());
				ub.setDayReport(us.getDayReport());
				ub.setWeekReport(us.getWeekReport());
				ub.setMonthReport(us.getMonthReport());
				ub.setSmodalNum(us.getSmodalNum());
				ub.setModalNum(us.getModalNum());
				ub.setMicroopen(us.getMicroopen());
				ub.setUsertimes(us.getUsertimes());
				ub.setSetuserNum(us.getSetuserNum());
				ub.setSetTrade(us.getSetTrade());
				ub.setTotalid(us.getTotalid());
				ub.setInnerAcount(us.getInnerAcount());
				if(null !=us.getIsupdate()){
					ub.setIsupdate(us.getIsparent());
				}else{
					ub.setIsupdate(null);
				}
			}
			List<String> formatslist = new ArrayList<String>();
			formatslist.add(AppConstant.mediaType.NEWS);
			formatslist.add(AppConstant.mediaType.LUNTAN);
			formatslist.add(AppConstant.mediaType.BLOG);
			formatslist.add(AppConstant.mediaType.TIEBA);
			formatslist.add(AppConstant.mediaType.WEIBO);
			formatslist.add(AppConstant.mediaType.PRINT_MEDIA);
			formatslist.add(AppConstant.mediaType.WEIXIN);
			formatslist.add(AppConstant.mediaType.VIDEO);
			formatslist.add(AppConstant.mediaType.APP);
			formatslist.add(AppConstant.mediaType.COMMENT);
			formatslist.add(AppConstant.mediaType.OTHER);
			formatslist.add(AppConstant.mediaType.ABROAD);
			formatslist.add(AppConstant.mediaType.TV);
			formatslist.add(AppConstant.mediaType.BT);
			if(null!=ub.getSetTrade()){
 				if(1==ub.getSetTrade()){
 					formatslist.add(AppConstant.mediaType.TRADE);
 					session.setAttribute("setTrade", ub.getSetTrade());
 				}else{
 					session.setAttribute("setTrade", 0);
 				}
 			}else{
 				session.setAttribute("setTrade", 0);
 			}
			if(null!= newUser.getManagerid() && !"".equals(newUser.getManagerid())){
 				ManagerUser mu = userServiceImpl.selectManagerUserById(newUser.getManagerid());
 				if(null!=mu){
 					ub.setStartTime(mu.getLogo());
 					ub.setEndTime(mu.getImg());
 					ub.setManagername(mu.getContent());
 				}else{
 					ub.setStartTime("");
 					ub.setEndTime("");
 					ub.setManagername("");

 				}
 			}
 			session.setAttribute("formatslist",formatslist);
			session.setAttribute("userid", newUser.getId());
			session.setAttribute("user", newUser);
			session.setAttribute("name", newUser.getName());
			session.setAttribute("usertype", newUser.getUsertype());
			session.setAttribute("managerid", newUser.getManagerid());
			session.setAttribute("orgname", newUser.getOrgname());
	//  查询此用户的、logo 页面底部信息
 			
 		
			//int num = powerServiceImpl.insertSelective(newUser, request);

			return new Response(ResponseStatus.Success, ub, true);
		}
   }
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public Response forgetPassword(@RequestBody User record){
		int num = userServiceImpl.forgetPassword(record);
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectnewUserInfo", method = RequestMethod.GET)
	public Response selectnewUserInfo(User user, HttpSession session, HttpServletRequest request) {
		session.setMaxInactiveInterval(30 * 60);
		  User newUser = userServiceImpl.checkloginzongjian(user);
			UserBo ub = new UserBo();
			if (newUser == null){
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}else{
				/*SessionListener.isAlreadyEnter(session, newUser.getId());*/
				
				if(null==newUser.getParentid() && "".equals(newUser.getParentid())){
					 ub.setIsparent(1);
				}else{
					ub.setIsparent(0);
				}
				BeanUtils.copyProperties(newUser, ub);
				UserBo us = powerServiceImpl.selectPowerByUserId(ub);

				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (null!=newUser.getExpirydate()) {
					String time = sd.format(newUser.getExpirydate());
					ub.setExpirydate(time);
				}
				if (us != null) {
					ub.setSubsetword(us.getSubsetword());
					ub.setSubjecttimes(us.getSubjecttimes());
					ub.setPersonsetword(us.getPersonsetword());
					ub.setPersontimes(us.getPersontimes());
					ub.setKeywordNum(us.getKeywordNum());
					ub.setKeywordsetword(us.getKeywordsetword());
					ub.setMonitortimes(us.getMonitortimes());
					ub.setMonsetword(us.getMonsetword());
					ub.setWarnsetword(us.getWarnsetword());
					ub.setWarntimes(us.getWarntimes());
					ub.setSreportsetword(us.getSreportsetword());
					ub.setSubjectReport(us.getSubjectReport());
					ub.setCloudsetword(us.getCloudsetword());
					ub.setCloudtimes(us.getCloudtimes());
					ub.setDayReport(us.getDayReport());
					ub.setWeekReport(us.getWeekReport());
					ub.setMonthReport(us.getMonthReport());
					ub.setSmodalNum(us.getSmodalNum());
					ub.setModalNum(us.getModalNum());
					ub.setMicroopen(us.getMicroopen());
					ub.setUsertimes(us.getUsertimes());
					ub.setSetuserNum(us.getSetuserNum());
					ub.setSetTrade(us.getSetTrade());
					ub.setTotalid(us.getTotalid());
					ub.setInnerAcount(us.getInnerAcount());
					 if(null!=us.getIsupdate()){
	 			    	 ub.setIsupdate(us.getIsupdate());
	 			     }else{
	 			    	 ub.setIsupdate(null);
	 			     }
				}
				List<String> formatslist = new ArrayList<String>();
				formatslist.add(AppConstant.mediaType.NEWS);
				formatslist.add(AppConstant.mediaType.LUNTAN);
				formatslist.add(AppConstant.mediaType.BLOG);
				formatslist.add(AppConstant.mediaType.TIEBA);
				formatslist.add(AppConstant.mediaType.WEIBO);
				formatslist.add(AppConstant.mediaType.PRINT_MEDIA);
				formatslist.add(AppConstant.mediaType.WEIXIN);
				formatslist.add(AppConstant.mediaType.VIDEO);
				formatslist.add(AppConstant.mediaType.APP);
				formatslist.add(AppConstant.mediaType.COMMENT);
				formatslist.add(AppConstant.mediaType.OTHER);
				formatslist.add(AppConstant.mediaType.ABROAD);
				formatslist.add(AppConstant.mediaType.TV);
				formatslist.add(AppConstant.mediaType.BT);
				if(null!=ub.getSetTrade()){
					if(1==ub.getSetTrade()){
						formatslist.add(AppConstant.mediaType.TRADE);
						session.setAttribute("setTrade", ub.getSetTrade());
					}else{
						session.setAttribute("setTrade", 0);
					}
				}else{
					session.setAttribute("setTrade", 0);
				}
				if(null!= newUser.getManagerid() && !"".equals(newUser.getManagerid())){
	 				ManagerUser mu = userServiceImpl.selectManagerUserById(newUser.getManagerid());
	 				if(null!=mu){
	 					ub.setStartTime(mu.getLogo());
	 					ub.setEndTime(mu.getImg());
	 					ub.setManagername(mu.getContent());
	 				}else{
	 					ub.setStartTime("");
	 					ub.setEndTime("");
	 					ub.setManagername("");

	 				}
	 			}
				session.setAttribute("formatslist",formatslist);
				session.setAttribute("userid", newUser.getId());
				session.setAttribute("user", newUser);
				session.setAttribute("name", newUser.getName());
				session.setAttribute("orgname", newUser.getOrgname());
				session.setAttribute("usertype", newUser.getUsertype());
				session.setAttribute("ub", ub);
				//int num = powerServiceImpl.insertSelective(newUser, request);
				session.setAttribute("managerid", newUser.getManagerid());
				session.setAttribute("stander", "managerstander");
				return new Response(ResponseStatus.Success, ub, true);
		}

	}
	@RequestMapping(value = "/selectSonUserInfo", method = RequestMethod.GET)
	public Response selectSonUserInfo(UserBo record){
		UserBo ub = userServiceImpl.selectWordSetPowerByUserId(record);
		if(null !=ub){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			ub.setExpirtime(sd.format(ub.getExpir()));
		}
		if(null!=ub){
		   return new Response(ResponseStatus.Success,ub,true);	
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/updateSonInfo", method = RequestMethod.POST)
	public Response updateSonInfo(@RequestBody UserBo record){
	Boolean tag = userServiceImpl.updateSonUserInfo(record);
		//
		if(tag){
			return new Response(ResponseStatus.Success,tag,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
		}
	}
	
	@RequestMapping(value = "/selectLoginUser")
	public Response selectLoginUser(HttpServletRequest request){
		User rtnUser = (User) request.getSession().getAttribute("user");
		return new Response(ResponseStatus.Success,rtnUser,true);
	}
	
	@RequestMapping(value = "/selectUserByIDForControl")
	public String selectUserByIDForControl(HttpServletResponse response, HttpServletRequest request, String id){
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		User user = new User();
		user.setId(id);
		User userfh = powerServiceImpl.selectUserByIDForControl(user);
		if(null==userfh){
			try {
				response.sendError(401);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			/*try {
				response.sendError(402);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return "true,"+userfh.getName()+","+userfh.getMobilephone()+",";
		}
		return "false,,,";
	}

}
