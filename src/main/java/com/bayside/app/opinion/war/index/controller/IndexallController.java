package com.bayside.app.opinion.war.index.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.bayside.app.opinion.war.index.model.Hotnews;
import com.bayside.app.opinion.war.index.model.Monitorall;
import com.bayside.app.opinion.war.index.service.MonitorallService;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.opinion.war.subject.bo.SubjectHotspotBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.CommonUtil;
import com.bayside.util.SimpleDate;

/**
 * <p>
 * Title: IndexallController
 * </P>
 * <p>
 * Description: 首页信息
 * </p>
 * <p>
 * Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016
 * </p>
 * 
 * @author liuyy
 * @version 1.0
 * @since 2016年10月12日
 */
@RestController
@EnableAutoConfiguration
public class IndexallController {
	@Autowired
	private MonitorallService monitorallServiceImpl;
	@Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private SystemSetService systemSetServiceImpl;
	private static Logger Log = LoggerFactory.getLogger(IndexallController.class);

	/**
	 * <p>
	 * 方法名称：getNewmonitor
	 * </p>
	 * <p>
	 * 方法描述：//查询最新监控信息
	 * </p>
	 * 
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getNewmonitor", method = RequestMethod.GET)
	public Response getNewmonitor() {
		Monitorall ma = monitorallServiceImpl.selectNewInfoByTime();
		if (ma != null) {
			return new Response(ResponseStatus.Success, ma, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllMonitorNumber
	 * </p>
	 * <p>
	 * 方法描述：查询总的监控数量
	 * </p>
	 * 
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllMonitorNumber", method = RequestMethod.GET)
	public Response selectAllMonitorNumber() {
		Monitorall ma = monitorallServiceImpl.getAllMonitorNumber();
		if (ma != null) {
			return new Response(ResponseStatus.Success, ma, true);
		} else {
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：getyuqingNumberByEmotion
	 * </p>
	 * <p>
	 * 方法描述： 查询舆情监测数量
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getyuqingNumberByEmotion", method = RequestMethod.GET)
	public Response getyuqingNumberByEmotion(SubjectStatisticalBo record, HttpServletRequest request) {
		List<SubjectStatisticalBo> list = monitorallServiceImpl.selectIndexyuqing(record, request);

		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectlastsubjectarticle
	 * </p>
	 * <p>
	 * 方法描述： 查询首页前6条负面信息
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectlastsubjectarticle", method = RequestMethod.GET)
	public Response selectlastsubjectarticle(HttpServletRequest request) {
		String emotion = AppConstant.finalltype.FUEMOTION;
		String userid = (String) request.getSession().getAttribute("userid");
		 @SuppressWarnings("unchecked")
		List<String> formatslist = null;
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(trade!=1){
			formatslist = (List<String>) request.getSession().getAttribute("formatslist");
		}
		List<SubJectArticleBo> list = monitorallServiceImpl.selectlastsubjectarticle(userid, emotion,formatslist);
		List<SubJectArticleBo> li = new ArrayList<SubJectArticleBo>();
		for (int i = 0; i < list.size(); i++) {
			SubJectArticleBo ss = new SubJectArticleBo();
			BeanUtils.copyProperties(list.get(i), ss);
			Date date = list.get(i).getPubdate();
			//SimpleDateFormat df = new SimpleDateFormat("MM-dd");
			String d = "";
			String current="";
			try {
				d = SimpleDate.formatDateD(date);
				current = SimpleDate.formatDateD(new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage());
			}
			if (d.equals(current)) {
				ss.setTime(DateFormatUtil.timeString(list.get(i).getPubdate()));
			} else {
				ss.setTime(d);
			}
			ss.setTittle(list.get(i).getTittle());
			li.add(ss);
		}
		if (li.size() > 0) {
			return new Response(ResponseStatus.Success, li, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectMediaAcountByTime
	 * </p>
	 * <p>
	 * 方法描述： 查询首页媒体分布信息
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectMediaAcountByTime", method = RequestMethod.GET)
	public Response selectMediaAcountByTime(SubjectStatisticalBo record, HttpServletRequest request) {
		List<SubjectStatistical> list = subjectArticleServiceImpl.indextongjimedia(record, request);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectSubjectArticle
	 * </p>
	 * <p>
	 * 方法描述：//查询媒体分布信息 正面 负面 中性 //查询专题统计
	 * </p>
	 * 
	 * @param list
	 * @param userid
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSubjectArticle", method = RequestMethod.GET)
	public Response selectSubjectArticle(List list, String userid, HttpServletRequest request) {
		String userid1 = (String) request.getSession().getAttribute("userid");
		List<SubJectArticleBo> lis = monitorallServiceImpl.selectnewSixsubjectarticle(list, userid1);
		if (lis.size() > 0) {
			return new Response(ResponseStatus.Success, lis, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectzaitiNumber
	 * </p>
	 * <p>
	 * 方法描述： 载体趋势图
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectzaitiNumber", method = RequestMethod.GET)
	public Response selectzaitiNumber(SubjectStatisticalBo record, HttpServletRequest request) {
		List<SubjectStatisticalBo> sBos = monitorallServiceImpl.selectTodayzaitiByTime(record, request);
		if (sBos.size() > 0) {
			return new Response(ResponseStatus.Success, sBos, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectnewWarning
	 * </p>
	 * <p>
	 * 方法描述：查询预警推送
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectnewWarning", method = RequestMethod.GET)
	public Response selectnewWarning(HttpServletRequest request) {
		SubJectArticleBo record = new SubJectArticleBo();
		String userid = (String) request.getSession().getAttribute("userid");
		
	
		record.setUserid(userid);
		record.setWarning(true);
		List<String> formatslist = null;
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(trade!=1){
			formatslist = (List<String>) request.getSession().getAttribute("formatslist");

			record.setMedialist(formatslist);
		}
		// List<>
		List<SubJectArticleBo> list = monitorallServiceImpl.selectnewWarningarticle(record);
		List<SubJectArticleBo> li = new ArrayList<SubJectArticleBo>();
		for (int i = 0; i < list.size(); i++) {
			SubJectArticleBo ss = new SubJectArticleBo();
			BeanUtils.copyProperties(list.get(i), ss);
			Date date = list.get(i).getPubdate();
			//SimpleDateFormat df = new SimpleDateFormat("MM-dd");
			String d = "";
			String current="";
			try {
				d = SimpleDate.formatDateD(date);
			
				 current = SimpleDate.formatDateD(new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage());
			}
			
			if (d.equals(current)) {
				ss.setTime(DateFormatUtil.timeString(list.get(i).getPubdate()));
			} else {
				ss.setTime(d);
			}
			ss.setTittle(list.get(i).getTittle());
			ss.setEmotion(list.get(i).getEmotion());
			li.add(ss);
		}

		if (li.size() > 0) {
			return new Response(ResponseStatus.Success, li, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectTypeArticle
	 * </p>
	 * <p>
	 * 方法描述：查询 微信()//微博 //贴吧//APP 文章信息
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectTypeArticle", method = RequestMethod.GET)
	public Response selectTypeArticle(SubjectArticle record, HttpServletRequest request) {
		String type = record.getFormats();

		String userid = (String) request.getSession().getAttribute("userid");
		List<SubJectArticleBo> list = monitorallServiceImpl.selectnewTypearticle(type, userid);
		List<SubJectArticleBo> li = new ArrayList<SubJectArticleBo>();
		for (int i = 0; i < list.size(); i++) {
			SubJectArticleBo ss = new SubJectArticleBo();
			BeanUtils.copyProperties(list.get(i), ss);
			Date date = list.get(i).getUpdatetime();
		//	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String d = "";
			String current="";
			try {
				d = SimpleDate.formatDate(date);
			
				 current = SimpleDate.formatDate(new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage());
			}
			if (d.equals(current)) {
				ss.setTime(DateFormatUtil.timeString(list.get(i).getUpdatetime()));
			} else {
				ss.setTime(d);
			}
			ss.setTittle(list.get(i).getTittle());
			ss.setEmotion(list.get(i).getEmotion());
			li.add(ss);
		}
		if (li.size() > 0) {
			return new Response(ResponseStatus.Success, li, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selecthot
	 * </p>
	 * <p>
	 * 方法描述：//热词云图
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selecthot", method = RequestMethod.GET)
	public Response selecthot(SubjectHotspotBo record, HttpServletRequest request) {
		String time = record.getUpdatetime();
		String userid = (String) request.getSession().getAttribute("userid");
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		List<Map<String, Object>> lis = new ArrayList<Map<String, Object>>();
		try {
			if (!CommonUtil.isEmpty(time)) {
				if (time.equals(AppConstant.timetype.CURRENT)) {
				//	String current = df.format(new Date());
				
					String current = SimpleDate.formatDate(new Date());
					
					lis = monitorallServiceImpl.selectnewhot(userid, current);
				} else if (time.equals(AppConstant.timetype.WEEK)) {
					c.add(Calendar.DATE, -7);
					String current = SimpleDate.formatDate(c.getTime());
					lis = monitorallServiceImpl.selectnewhot(userid, current);
				
				} else if (time.equals(AppConstant.timetype.MONTH)) {
					c.add(Calendar.DATE, -30);
					String current = SimpleDate.formatDate(c.getTime());
					lis = monitorallServiceImpl.selectnewhot(userid, current);
					
				}
			} else {
				String current = SimpleDate.formatDate(new Date());
				lis = monitorallServiceImpl.selectnewhot(userid, current);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
             Log.error(e.getMessage());
		}
		return new Response(ResponseStatus.Success, lis, true);
	}


	/**
	 * <p>
	 * 方法名称：selectAllSubject
	 * </p>
	 * <p>
	 * 方法描述：//查询所有专题的 文章：
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllSubject", method = RequestMethod.GET)
	public Response selectAllSubject(HttpServletRequest request) {
		SubJectArticleBo record = new SubJectArticleBo();
		String userid = (String) request.getSession().getAttribute(AppConstant.finalltype.USERID);
	
		List<String> formatslist = null;
        Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(trade!=1){
			formatslist = (List<String>) request.getSession().getAttribute("formatslist");
			record.setMedialist(formatslist);
		}
		record.setUserid(userid);
		List<SubJectArticleBo> list = monitorallServiceImpl.selectAllSubjectarticle(record);
		List<SubJectArticleBo> li = new ArrayList<SubJectArticleBo>();
		for (int i = 0; i < list.size(); i++) {
			// SubjectMArticle s =
			// monitorallServiceImpl.selectAttention(list.get(i).getId());
			SubJectArticleBo ss = new SubJectArticleBo();
			BeanUtils.copyProperties(list.get(i), ss);
			// ss.setAttention(s.getAttention());
			Date date = list.get(i).getPubdate();
		//	SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		//	String d = df.format(date);
			
		
		//	String current = df.format(new Date());
			try {
				String d = SimpleDate.formatDateD(date);
				String current = SimpleDate.formatDateD(new Date());
				if (d.equals(current)) {
					ss.setTime(DateFormatUtil.timeString(list.get(i).getPubdate()));
				} else {
					ss.setTime(d);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.error(e.getMessage());
			}
		
			ss.setTittle(list.get(i).getTittle());
			ss.setEmotion(list.get(i).getEmotion());
			li.add(ss);
		}
		if (li.size() > 0) {
			return new Response(ResponseStatus.Success, li, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：deletefumianById
	 * </p>
	 * <p>
	 * 方法描述： //删除负面文章 //删除预警推送//删除专题文章// 删除微信
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deletefumianById", method = RequestMethod.GET)
	public Response deletefumianById(SubJectArticleBo record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		SubjectMArticle sm = new SubjectMArticle();
		sm.setUserid(userid);
		sm.setArticleid(record.getArticleid());
		//int i = monitorallServiceImpl.deletefumianById(record.getMid());
		// int n = monitorallServiceImpl.deletearticlebyId(record.getId());
		int i = monitorallServiceImpl.deletefumianByObject(sm);
		if (i > 0) {
			return new Response(ResponseStatus.Success, i, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	
	/**
	 * 
	 * <p>方法名称：deleteAllIndexArticle</p>
	 * <p>方法描述：首页删除所有相似文章</p>
	 * @param record
	 * @param request 
	 * @return
	 * 
	 * 
	 * @author liuyy
	 * @since  2017年5月15日
	 * <p> history 2017年5月15日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/deleteAllIndexArticle", method = RequestMethod.POST)
   public Response deleteAllIndexArticle(SubJectArticleBo record){
	   SubjectMArticle sm = new SubjectMArticle();
		if(null!=record){
			List<String> mids = new ArrayList<String>();
			String simids = record.getSimids();
			if(null!=simids && !"".equals(simids)){
				if(simids.contains(",")){
					String[] listmids = simids.split(",");
		     		for(int i=0;i<listmids.length;i++){
		     			mids.add(listmids[i]);
		     		}
				}else{
					mids.add(simids);
				}
			}
			mids.add(record.getMid());
			sm.setIds(mids);
			 int num = monitorallServiceImpl.deleteIndexSimilarArticle(sm);
			 if(num > 0){
				 return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
			 }else{
				 return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
			 }
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,true);
		}
   }
   /**
    * 
    * <p>方法名称：deleteOnlyArticle</p>
    * <p>方法描述：删除当前文章,修改相似文章noquery</p>
    * @param record
    * @param request
    * @return
    * @author liuyy
    * @since  2017年5月15日
    * <p> history 2017年5月15日 Administrator  创建   <p>
    */
	@RequestMapping(value = "/deleteOnlyArticle", method = RequestMethod.POST)
   public Response deleteOnlyArticle(SubJectArticleBo record, HttpServletRequest request){
	   SubjectMArticle sm = new SubjectMArticle();
	   if(null!=record){
		   sm.setId(record.getMid());
		   List<String> mids = new ArrayList<String>();
		   int num = monitorallServiceImpl.deleteIndexArticle(sm);
		   //List<String> mids = new ArrayList<String>();
			String simids = record.getSimids();
			if(null!=simids && !"".equals(simids)){
				if(simids.contains(",")){
					String[] listmids = simids.split(",");
		     		for(int i=0;i<listmids.length;i++){
		     			mids.add(listmids[i]);
		     		}
				}else{
					mids.add(simids);
				}
				//修改相似文章noquery
				  SubjectMArticle sa = new SubjectMArticle();
				  sa.setId(mids.get(0));
				  sa.setNoquery(0);
				int s = monitorallServiceImpl.updateArticleNoquery(sa);
			}else{
				mids.add(record.getMid());
			}
		
			
		   if(num > 0){
			   return new Response(ResponseStatus.Success,num,true);
		   }else{
			   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
		   }
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,true);
	   }
	  
	 
	  
   }
	
	/**
	 * 
	 * <p>方法名称：deleteAllIndexArticle</p>
	 * <p>方法描述：首页删除所有相似文章</p>
	 * @param record
	 * @param request 
	 * @return
	 * 
	 * 
	 * @author liuyy
	 * @since  2017年5月15日
	 * <p> history 2017年5月15日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/deleteOpinionAllIndexArticle", method = RequestMethod.POST)
   public Response deleteOpinionAllIndexArticle(@RequestBody SubJectArticleBo record){
	   SubjectMArticle sm = new SubjectMArticle();
		if(null!=record){
			List<String> mids = new ArrayList<String>();
			String simids = record.getSimids();
			if(null!=simids && !"".equals(simids)){
				if(simids.contains(",")){
					String[] listmids = simids.split(",");
		     		for(int i=0;i<listmids.length;i++){
		     			mids.add(listmids[i]);
		     		}
				}else{
					mids.add(simids);
				}
			}
			mids.add(record.getMid());
			sm.setIds(mids);
			 int num = monitorallServiceImpl.deleteIndexSimilarArticle(sm);
			 if(num > 0){
				 return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
			 }else{
				 return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
			 }
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,true);
		}
   }
   /**
    * 
    * <p>方法名称：deleteOnlyArticle</p>
    * <p>方法描述：删除当前文章,修改相似文章noquery</p>
    * @param record
    * @param request
    * @return
    * @author liuyy
    * @since  2017年5月15日
    * <p> history 2017年5月15日 Administrator  创建   <p>
    */
	@RequestMapping(value = "/deleteOpinionOnlyArticle", method = RequestMethod.POST)
   public Response deleteOpinionOnlyArticle(@RequestBody SubJectArticleBo record, HttpServletRequest request){
	   SubjectMArticle sm = new SubjectMArticle();
	   if(null!=record){
		   sm.setId(record.getMid());
		   List<String> mids = new ArrayList<String>();
		   int num = monitorallServiceImpl.deleteIndexArticle(sm);
		   //List<String> mids = new ArrayList<String>();
			String simids = record.getSimids();
			if(null!=simids && !"".equals(simids)){
				if(simids.contains(",")){
					String[] listmids = simids.split(",");
		     		for(int i=0;i<listmids.length;i++){
		     			mids.add(listmids[i]);
		     		}
				}else{
					mids.add(simids);
				}
				//修改相似文章noquery
				  SubjectMArticle sa = new SubjectMArticle();
				  sa.setId(mids.get(0));
				  sa.setNoquery(0);
				int s = monitorallServiceImpl.updateArticleNoquery(sa);
			}else{
				mids.add(record.getMid());
			}
		   if(num > 0){
			   return new Response(ResponseStatus.Success,num,true);
		   }else{
			   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
		   }
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,true);
	   }
	  
	 
	  
   }
   /**
    * 
    * <p>方法名称：deleteSubjectArticle</p>
    * <p>方法描述：删除同一专题下的相似文章</p>
    * 
    * @param record
    * @param request
    * @return
    * @author liuyy
    * @since  2017年5月15日
    * <p> history 2017年5月15日 Administrator  创建   <p>
    */
   public Response deleteSubjectArticle(SubJectArticleBo record, HttpServletRequest request){
	   SubjectMArticle sm = new SubjectMArticle();
			if(null!=record){
				List<String> mids = new ArrayList<String>();
				String simids = record.getSimids();
				if(null!=simids && !"".equals(simids)){
					if(simids.contains(",")){
						String[] listmids = simids.split(",");
			     		for(int i=0;i<listmids.length;i++){
			     			mids.add(listmids[i]);
			     		}
					}else{
						mids.add(simids);
					}
					//
				}
				mids.add(record.getMid());
				sm.setIds(mids);
				 int num = monitorallServiceImpl.deleteIndexSimilarArticle(sm);
				 if(num > 0){
					 return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
				 }else{
					 return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
				 }
			}
	   return null;
   }
	/**
	 * <p>
	 * 方法名称：updatefumian
	 * </p>
	 * <p>
	 * 方法描述：//关注负面文章 //关注预警推送//关注专题//关注微信
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updatefumian", method = RequestMethod.GET)
	public Response updatefumian(SubjectMArticle record, HttpServletRequest request) {
		System.out.println(record.getArticleid());
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		record.setUpdatetime(new Date());
		if (record.getAttention() != null) {
			if (record.getAttention().equals(true)) {
				record.setAttentiontime(new Date());
			}
		}
		int i = monitorallServiceImpl.updatefumian(record);
		if (i > 0) {
			return new Response(ResponseStatus.Success, i, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updatenews
	 * </p>
	 * <p>
	 * 方法描述：//关注新闻
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
	@RequestMapping(value = "/updatenews", method = RequestMethod.GET)
	public Response updatenews(Hotnews record) {
		int i = monitorallServiceImpl.updatenews(record);
		if (i > 0) {
			return new Response(ResponseStatus.Success, i, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：deletenews
	 * </p>
	 * <p>
	 * 方法描述： //删除新闻
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
	@RequestMapping(value = "/deletenews", method = RequestMethod.GET)
	public Response deletenews(Hotnews record) {
		int i = monitorallServiceImpl.deletenews(record.getId());
		if (i > 0) {
			return new Response(ResponseStatus.Success, i, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectattention
	 * </p>
	 * <p>
	 * 方法描述：查询文章状态 是否关注
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
	@RequestMapping(value = "/selectattention", method = RequestMethod.GET)
	public Response selectattention(SubjectMArticle record) {
		SubjectMArticle s = monitorallServiceImpl.selectAttention(record.getArticleid());
		if (s != null) {
			return new Response(ResponseStatus.Success, s, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}


	
	/**
	 * <p>方法名称：insertIndexModel</p>
	 * <p>方法描述：首页模板自定义添加 </p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2017年2月23日
	 * <p> history 2017年2月23日 Administrator  创建   <p>
	 */
  @RequestMapping(value = "/insertIndexModel", method = RequestMethod.GET)
  public Response insertIndexModel(SetIndexModalBo record,HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	 record.setId(UuidUtil.getUUID());
	  record.setUserid(userid);
	 // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  if(record.getTimescope()!=null){
		  if(record.getTimescope().equals(AppConstant.timetype.CURRENT)){
			  try {
				String time = SimpleDate.formatDate(new Date());
				  record.setStartTime(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage());
			}
			
		  }else if(record.getTimescope().equals(AppConstant.timetype.WEEK)){
			  Calendar c = Calendar.getInstance();
			  c.add(Calendar.DATE, -7);
				try {
					String str = SimpleDate.formatDate(c.getTime());
					record.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.error(e.getMessage());
				}
		  }
	  }
	 
	  int num = systemSetServiceImpl.insertIndexModel(record);
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	  }
	
  }
  /**
   * 
   * <p>方法名称：insertAllIndex</p>
   * <p>方法描述：生成首页</p>
   * @param list
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月25日
   * <p> history 2017年2月25日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/insertAllIndex", method = RequestMethod.GET)
  public Response insertAllIndex(String info,HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	  int num = monitorallServiceImpl.deleteByUserId(userid);
	  List<SetIndexModalBo> list = new ArrayList<SetIndexModalBo>(); 
	  list = JSONArray.parseArray(info, SetIndexModalBo.class);//这里的t是Class<T> 
	  System.out.println(info);
     
      Boolean flag = monitorallServiceImpl.insertAllModalBo(list, userid);
	  if(flag){
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.SAVESUCCESS,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	  }
	  
  }
  /**
   * 
   * <p>方法名称：selectIndexModel</p>
   * <p>方法描述：查询自定义模板</p>
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月23日
   * <p> history 2017年2月23日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/selectIndexModel", method = RequestMethod.GET)
  public Response selectIndexModel(HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	  List<SetIndexModal> list = monitorallServiceImpl.selectIndexModel(userid);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
   //查询专题
  /**
   * 
   * <p>方法名称：selectIndexSubject</p>
   * <p>方法描述：查询专题</p>
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月23日
   * <p> history 2017年2月23日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/selectIndexSubject", method = RequestMethod.GET)
  public Response selectIndexSubject(HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	  Subject record = new Subject();
	  record.setUserid(userid);
	  record.setIsdelete(false);
	  List<Subject> list = systemSetServiceImpl.selectSubjectInfo(record);
	  if(list!=null){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);  
	  }
  }
  /**
   * 
   * <p>方法名称：updateIndexModel</p>
   * <p>方法描述：修改自定义模块</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年2月25日
   * <p> history 2017年2月25日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/updateIndexModel", method = RequestMethod.GET)
  public Response updateIndexModel(SetIndexModalBo record){
	 int num = systemSetServiceImpl.updateSetIndexModal(record);
	 if(num > 0){
		 return new Response(ResponseStatus.Success,num,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	 }
  }
  /**
   * 
   * <p>方法名称：deleteIndexModal</p>
   * <p>方法描述：删除自定义模块</p>
   * @param id
   * @return
   * @author liuyy
   * @since  2017年2月25日
   * <p> history 2017年2月25日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/deleteIndexModal", method = RequestMethod.GET)
  public Response deleteIndexModal(String id){
	  int num = monitorallServiceImpl.deleteIndexModal(id);
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	  }
  }
  //查询文章
  /**
   * 
   * <p>方法名称：articleIndex</p>
   * <p>方法描述：首页自定义查询文章</p>
   * @param record
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月25日
   * <p> history 2017年2月25日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/articleIndex", method = RequestMethod.GET)
  public Response articleIndex(SetIndexModalBo record,HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	//  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  SubJectArticleBo sb = new SubJectArticleBo();
	  List<SubJectArticleBo> list = new ArrayList<SubJectArticleBo>();
	  List<String> subjectList = new ArrayList<String>();
	  List<String> mediaList = new ArrayList<String>();
	  List<String> emotionList = new ArrayList<String>();
	  String[] sList = record.getSubject().split(",");
	  String[] mList = record.getMediatype().split(",");
	  String[] eList = record.getEmotion().split(",");
	  for(int i=0;i<sList.length;i++){
		  subjectList.add(sList[i]);
		  sb.setSubjectlist(subjectList);
	  }
	  for(int i=0;i<mList.length;i++){
		  mediaList.add(mList[i]);
		  sb.setMedialist(mediaList);
	  }
	  for(int i=0;i<eList.length;i++){
		  emotionList.add(eList[i]);
		  sb.setEmotionlist(emotionList);
	  }
	  sb.setUserid(userid);
	  if(record.getTimescope()!=null){
		  if(record.getTimescope().equals(AppConstant.timetype.CURRENT)){
			  try {
				sb.setStartTime(SimpleDate.formatDate(new Date()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage());
			}
		  }else if(record.getTimescope().equals(AppConstant.timetype.WEEK)){
			  Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(c.getTime());
					sb.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.error(e.getMessage());
				}
				
		  }else if(record.getTimescope().equals(AppConstant.timetype.DEFINED)){
			  sb.setStartTime(record.getStartTime());
			  sb.setEndTime(record.getEndTime());
		  }
	  }
	  //
	  if(null!=record.getWarning()){
		  sb.setWarning(record.getWarning());
	  }
	  list = monitorallServiceImpl.definedSubjectList(sb);
	  
	  if(list!=null){
		  List<SubJectArticleBo> li = new ArrayList<SubJectArticleBo>();
			for (int i = 0; i < list.size(); i++) {
				SubJectArticleBo ss = new SubJectArticleBo();
				BeanUtils.copyProperties(list.get(i), ss);
				// ss.setAttention(s.getAttention());
				Date date = list.get(i).getPubdate();
				//SimpleDateFormat dfs = new SimpleDateFormat("MM-dd");
				try {
					String d = SimpleDate.formatDateD(date);
					String current = SimpleDate.formatDateD(new Date());
					if (d.equals(current)) {
						ss.setTime(DateFormatUtil.timeString(list.get(i).getPubdate()));
					} else {
						ss.setTime(d);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.error(e.getMessage());
				}
				ss.setTittle(list.get(i).getTittle());
				ss.setEmotion(list.get(i).getEmotion());
				li.add(ss);
			}
		  return new Response(ResponseStatus.Success,li,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);  
	  }
	  
  }
  //载体分布
  /**
   * 
   * <p>方法名称：selectZaitiFenbu</p>
   * <p>方法描述：首页自定义载体分布
   * </p>
   * @param record
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月25日
   * <p> history 2017年2月25日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/selectZaitiFenbu", method = RequestMethod.GET)
  public Response selectZaitiFenbu(SetIndexModalBo record,HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	///  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  SubjectStatisticalBo sb = new SubjectStatisticalBo();
	  List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();
	  List<String> subjectList = new ArrayList<String>();
	  List<String> mediaList = new ArrayList<String>();
	  List<String> emotionList = new ArrayList<String>();
	  String[] sList = record.getSubject().split(",");
	  String[] mList = record.getMediatype().split(",");
	  String[] eList = record.getEmotion().split(",");
	  for(int i=0;i<sList.length;i++){
		  subjectList.add(sList[i]);
		  sb.setSubjectlist(subjectList);
	  }
	  for(int i=0;i<mList.length;i++){
		  mediaList.add(mList[i]);
		  sb.setMedialist(mediaList);
	  }
	  for(int i=0;i<eList.length;i++){
		  emotionList.add(eList[i]);
		  sb.setEmotionlist(emotionList);
	  }
	  sb.setUserid(userid);
	  if(record.getTimescope()!=null){
		  if(record.getTimescope().equals(AppConstant.timetype.CURRENT)){
			  try {
				sb.setStartTime(SimpleDate.formatDate(new Date()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage());
			}
		  }else if(record.getTimescope().equals(AppConstant.timetype.WEEK)){
			  Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(c.getTime());
					sb.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.error(e.getMessage());
				}
				
		  }else if(record.getTimescope().equals(AppConstant.timetype.DEFINED)){
			  sb.setStartTime(record.getStartTime());
			  sb.setEndTime(record.getEndTime());
		  }
	  }
	  list = monitorallServiceImpl.definedmedia(sb);
	  if(list!=null){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);  
	  }
  }
  //载体趋势
  /**
   * 首页载体趋势
   * <p>方法名称：selectZaitiqushi</p>
   * <p>方法描述：</p>
   * @param record
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月25日
   * <p> history 2017年2月25日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/selectZaitiqushi", method = RequestMethod.GET)
  public Response selectZaitiqushi(SetIndexModalBo record,HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	 // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  SubjectStatisticalBo sb = new SubjectStatisticalBo();
	  List<SubjectStatisticalBo> list = new ArrayList<SubjectStatisticalBo>();
	  List<String> subjectList = new ArrayList<String>();
	  List<String> mediaList = new ArrayList<String>();
	  List<String> emotionList = new ArrayList<String>();
	  String[] sList = record.getSubject().split(",");
	  String[] mList = record.getMediatype().split(",");
	  String[] eList = record.getEmotion().split(",");
	  for(int i=0;i<sList.length;i++){
		  subjectList.add(sList[i]);
		  sb.setSubjectlist(subjectList);
	  }
	  for(int i=0;i<mList.length;i++){
		  mediaList.add(mList[i]);
		  sb.setMedialist(mediaList);
	  }
	  for(int i=0;i<eList.length;i++){
		  emotionList.add(eList[i]);
		  sb.setEmotionlist(emotionList);
	  }
	      sb.setUserid(userid);
		  sb.setStartTime(record.getStartTime());
		  sb.setEndTime(record.getEndTime());
	  list = monitorallServiceImpl.indexTodayzaitiByTime(sb, record.getTimescope());
	  if(list!=null){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);  
	  }
  }
  //首页自定义热词云图
  @RequestMapping(value = "/indexselectHot", method = RequestMethod.GET)
  public Response indexselectHot(SetIndexModalBo record,HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	  record.setUserid(userid);
	  List<Map<String, Object>> list = monitorallServiceImpl.indexselectnewhot(record);
	  if(list!=null){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  //初始化首页
  @RequestMapping(value = "/initindex", method = RequestMethod.GET)
  public Response initindex(HttpServletRequest request){
	  List<SetIndexModal> totallist = monitorallServiceImpl.initindexmodal(request);
	 
	  if(totallist!=null){
		  return new Response(ResponseStatus.Success,totallist,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
  }
  @RequestMapping(value = "/deleteAllModal", method = RequestMethod.POST)
  public Response deleteAllModal(HttpServletRequest request){
	  String userid = (String) request.getSession().getAttribute("userid");
	  int num = monitorallServiceImpl.deleteByUserId(userid);
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
}
