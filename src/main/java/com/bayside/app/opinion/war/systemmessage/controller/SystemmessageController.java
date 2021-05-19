package com.bayside.app.opinion.war.systemmessage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSizeExpr.Unit;
import com.bayside.app.opinion.war.infopush.model.MessagePush;
import com.bayside.app.opinion.war.infopush.service.InforpushService;
import com.bayside.app.opinion.war.infopush.service.SystemSettingsService;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.opinion.war.systemmessage.bo.SystemMessageBo;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
import com.bayside.app.opinion.war.systemmessage.model.YuqingDeal;
import com.bayside.app.opinion.war.systemmessage.service.SystemmessageService;
import com.bayside.app.opinion.war.systemset.model.Emailconfig;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.AccessToken;
import com.bayside.util.WeixinUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * Title: SystemmessageController
 * </P>
 * <p>
 * Description: 系统消息
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
public class SystemmessageController {
	@Autowired
	private SystemmessageService systemmessageServiceImpl;
	@Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	@Autowired
	private InforpushService inforpushImpl;
	@Autowired
	private SystemSetService systemSetServiceImpl;
	@Autowired
	private SystemSettingsService systemSettingsImpl;
	private static String appId = "FyoVWB1PIg9flN6u8ImYv7";
	private static String appKey = "qrOzZJ41qZ8tMhLGxmwZH9";
	private static String masterSecret = "SI6uuxqghV8Y6g9zTlMjF3";
	static String CID = "6e40ea2e32e38382bbe33a4f71209651";// 对应的手机 userid对应的是用户
	// 别名推送方式
	static String Alias = "TOM";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	/**
	 * <p>
	 * 方法名称：selectAllMessage
	 * </p>
	 * <p>
	 * 方法描述：查询用户所有的消息
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
	@RequestMapping(value = "selectAllMessage", method = RequestMethod.GET)
	public Response selectAllMessage(HttpServletRequest request, SolrPage page,Systemmessage record) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		record.setStart((page.getPageNum()-1)*page.getPageSize());
		record.setSize(page.getPageSize());
		List<Systemmessage> list = systemmessageServiceImpl.selectByPrimaryKey(record);
		SolrPage<Systemmessage> info = new SolrPage<Systemmessage>();
		Systemmessage ss = systemmessageServiceImpl.selectMessageTotal(record);
		if(null!=ss){
			info.setTotal(ss.getTotal());
		}else{
			info.setTotal(0);
		}

		
		info.setPageNum(page.getPageNum());//当前页数
		info.setPageSize(page.getPageSize());//总页数
		info.setNavigatePages(8);//页面默认显示的页数
		info.setDatas(list);///封装数据
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "selectMessageNumber", method = RequestMethod.GET)
	public Response selectMessageNumber(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		// String tag = request.getParameter("tag");
		Systemmessage record = new Systemmessage();
		record.setUserid(userid);
		record.setTag(0);
		Systemmessage list = systemmessageServiceImpl.selectMessageNum(record);
		if(list!=null){
			return new Response(ResponseStatus.Success, list.getId(), true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	
	}

	@RequestMapping(value = "updateSystemMessage", method = RequestMethod.GET)
	public Response updateSystemMessage(HttpServletRequest request, SystemMessageBo smb) {
		String userid = (String) request.getSession().getAttribute("userid");
		smb.setUserid(userid);
		smb.setTag(1);
		List<String> arids = smb.getIds();
		List<String> newIds = new ArrayList<String>();
		if (arids != null) {
			for (String id : arids) {
				id = id.replace("\"", "").replace("[", "").replace("]", "");
				newIds.add(id);
			}
		}

		int num = systemmessageServiceImpl.updateSystemMessage(smb);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}
	@RequestMapping(value = "updateSystemMessageById", method = RequestMethod.GET)
    public Response updateSystemMessageById(Systemmessage record){
    	int num = systemmessageServiceImpl.updateSystemMessageById(record);
    	if(num > 0){
    		return new Response(ResponseStatus.Success,num,true);
    	}else{
    		return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
    	}
    }
	@RequestMapping(value = "updateYuqingMessageById", method = RequestMethod.GET)
    public Response updateYuqingMessageById(Systemmessage record){
    	int num = systemmessageServiceImpl.updateYuqingMessage(record);
    	if(num > 0){
    		return new Response(ResponseStatus.Success,num,true);
    	}else{
    		return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
    	}
    }
	@RequestMapping(value = "updateDealInfo", method = RequestMethod.GET)
	public Response updateDealInfo(String dealid,String sysid){
		YuqingDeal yd = new YuqingDeal();
		yd.setId(dealid);
		yd.setTag(3);
		int nu = systemmessageServiceImpl.updateYuqingDeal(yd);
		if(nu > 0){
			return new Response(ResponseStatus.Success,nu,true);
	    }else{
	    	return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
	    }/*else{
			flag = false;
		}*/
		/*if(flag){
	    	return new Response(ResponseStatus.Success,flag,true);
	    }else{
	    	return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
	    }*/
		
	}
	@RequestMapping(value = "updateDeal", method = RequestMethod.GET)
	public Response updateDeal(YuqingDeal record){
		int num  = systemmessageServiceImpl.updateYuqingDeal(record);
		Systemmessage re = new Systemmessage();
		re.setId(record.getSystemid());
		re.setTag(record.getTag());
		int nu= systemmessageServiceImpl.updateSystemMessageById(re);
		if(num > 0){
	    	return new Response(ResponseStatus.Success,num,true);
	    }else{
	    	return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
	    }
	}
	/**
	 * <p>
	 * 方法名称：insertArticleInfo
	 * </p>
	 * <p>
	 * 方法描述：添加系统消息
	 * </p>
	 * 
	 * @param request
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "insertArticleInfo", method = RequestMethod.GET)
	public Response insertArticleInfo(HttpServletRequest request, Systemmessage record) {
		String userid = (String) request.getSession().getAttribute("userid");
		String orgname = (String) request.getSession().getAttribute("orgname");
		Systemmessage sm = new Systemmessage();
		BeanUtils.copyProperties(record, sm);
		sm.setId(UuidUtil.getUUID());
		sm.setTitle(record.getContent());
		if (record.getUrl() != null && !"".equals(record.getUrl())) {
			sm.setUrl(record.getUrl());
		}
		if (record.getContent() != null && !"".equals(record.getContent())) {
			sm.setContent(record.getContent());
		}
		sm.setUserid(record.getUserid());
		sm.setTime(new Date());
		sm.setTag(0);
		sm.setOrgname(orgname);
		int num = systemmessageServiceImpl.insertSystemMessage(sm);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}

	@RequestMapping(value = "insertArticle", method = RequestMethod.GET)
	public Response insertArticle(HttpServletRequest request, Systemmessage record) {
		String userid = (String) request.getSession().getAttribute("userid");
		String orgname = (String) request.getSession().getAttribute("orgname");
		Systemmessage sm = new Systemmessage();
		BeanUtils.copyProperties(record, sm);
		sm.setId(UuidUtil.getUUID());
		if (record.getTitle() != null && !"".equals(record.getTitle())) {
			sm.setTitle(record.getTitle());
		}
		if (record.getUrl() != null && !"".equals(record.getUrl())) {
			sm.setUrl(record.getUrl());
		}
		if (record.getContent() != null && !"".equals(record.getContent())) {
			sm.setContent(record.getContent());
		}
		sm.setUserid(userid);
		sm.setTime(new Date());
		sm.setTag(0);
		sm.setArticleid(record.getArticleid());
		sm.setOrgname(orgname);
		sm.setParentid(record.getParentid());
		int num = systemmessageServiceImpl.insertSystemMessage(sm);
		/*if(1 == record.getEmailtag()){
			YuqingDeal deal = new YuqingDeal();
			deal.setId(UuidUtil.getUUID());
			deal.setDealcontent(dealcontent);
			//内容保存在 deal 表
			systemmessageServiceImpl.insertYuqingDeal(record)
		}*/
		// 消息推送到 手机
		MessagePush messagePush = new MessagePush();
		messagePush.setUserid(userid);
		messagePush.setArticleid(record.getArticleid());
		messagePush.setTitle(record.getTitle());
		messagePush.setMid(record.getMid());
		messagePush.setContent(record.getContent());
		systemSettingsImpl.getOpenPush(messagePush);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}
	@RequestMapping(value = "insertEmailArticle", method = RequestMethod.POST)
	public Response insertEmailArticle(@RequestBody Systemmessage record,HttpServletRequest request){
		/*String userids = record.getUseridList();
		String[]  useridlist= null;
		String[] orgnamelist = null;
	    String orgnames = record.getOrgnameList();
	    List<YuqingDeal> listYD = new ArrayList<YuqingDeal>();
	    List<Systemmessage> listSyS = new ArrayList<Systemmessage>();
		if(!"".equals(userids) && null !=userids){
			if(userids.indexOf(",") !=-1){
				useridlist = userids.split(",");
				orgnamelist = orgnames.split(",");
				//批量插入舆情处理
				for(int i=0;i<useridlist.length;i++){
					YuqingDeal yd = new YuqingDeal();
					yd.setId(UuidUtil.getUUID());
					yd.setMid(record.getMid());
					yd.setUserid(useridlist[i]);
					yd.setType(record.getType());
					yd.setTag(record.getTag());
					yd.setReason(record.getReason());
					yd.setTag(record.getTag());
					listYD.add(yd);
				}
				systemmessageServiceImpl.insertbatchDeal(listYD);
				//批量插入系统消息
				for(int i=0;i<useridlist.length;i++){
					Systemmessage yd = new Systemmessage();
					yd.setId(UuidUtil.getUUID());
					yd.setMid(record.getMid());
					yd.setUserid(useridlist[i]);
					yd.setType(record.getType());
					yd.setTag(record.getTag());
				    yd.setArticleid(record.getArticleid());
				    yd.setUrl(record.getUrl());
				    yd.setTitle(record.getTitle());
				    yd.setTime(new Date());
				    yd.setOrgname(orgnamelist[i]);
				    listSyS.add(yd);
					//listYD.add(yd);
				}
				//
				systemmessageServiceImpl.batchinsertInfo(listSyS);
				
				
			}else{
				YuqingDeal yd = new YuqingDeal();
				yd.setId(UuidUtil.getUUID());
				yd.setMid(record.getMid());
				yd.setUserid(userids);
				yd.setType(record.getType());
				yd.setTag(record.getTag());
				yd.setReason(record.getReason());
				yd.setTag(record.getTag());
				//
				systemmessageServiceImpl.insertYuqingDeal(yd);
				
				Systemmessage yds = new Systemmessage();
				yds.setId(UuidUtil.getUUID());
				yds.setMid(record.getMid());
				yds.setUserid(userids);
				yds.setType(record.getType());
				yds.setTag(record.getTag());
			    yds.setArticleid(record.getArticleid());
			    yds.setUrl(record.getUrl());
			    yds.setTitle(record.getTitle());
			    yds.setTime(new Date());
			    yds.setOrgname(orgnames);
			    
			    systemmessageServiceImpl.insertSystemMessage(yds);
				
			}
		
		}*/
		String userid = (String) request.getSession().getAttribute("userid");
		String orgname = (String) request.getSession().getAttribute("orgname");
		Systemmessage sm = new Systemmessage();
		BeanUtils.copyProperties(record, sm);
		sm.setId(UuidUtil.getUUID());
		if (record.getTitle() != null && !"".equals(record.getTitle())) {
			sm.setTitle(record.getTitle());
		}
		if (record.getUrl() != null && !"".equals(record.getUrl())) {
			sm.setUrl(record.getUrl());
		}
		if (record.getContent() != null && !"".equals(record.getContent())) {
			sm.setContent(record.getContent());
		}
		sm.setUserid(userid);
		sm.setTime(new Date());
		sm.setTag(0);
		sm.setArticleid(record.getArticleid());
		sm.setOrgname(orgname);
		sm.setParentid(record.getParentid());
		int num = systemmessageServiceImpl.insertSystemMessage(sm);
		YuqingDeal yd = new YuqingDeal();
		yd.setId(UuidUtil.getUUID());
		yd.setMid(record.getMid());
		yd.setUserid(userid);
		yd.setType(record.getType());
		yd.setTag(record.getTag());
		yd.setReason(record.getReason());
		yd.setTag(record.getTag());
		systemmessageServiceImpl.insertYuqingDeal(yd);
		/*if(1 == record.getEmailtag()){
			YuqingDeal deal = new YuqingDeal();
			deal.setId(UuidUtil.getUUID());
			deal.setDealcontent(dealcontent);
			//内容保存在 deal 表
			systemmessageServiceImpl.insertYuqingDeal(record)
		}*/
		// 消息推送到 手机
		MessagePush messagePush = new MessagePush();
		messagePush.setUserid(userid);
		messagePush.setArticleid(record.getArticleid());
		messagePush.setTitle(record.getTitle());
		messagePush.setMid(record.getMid());
		messagePush.setContent(record.getContent());
		systemSettingsImpl.getOpenPush(messagePush);
		
		return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
		
	}
	@RequestMapping(value = "selectDealByMId", method = RequestMethod.GET)
	public Response selectDealByMId(YuqingDeal record,HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(record.getUserid());
		YuqingDeal yd = systemmessageServiceImpl.selectDealByMid(record);
		if(yd!=null){
			return new Response(ResponseStatus.Success, yd, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
    
	@RequestMapping(value = "addArticleInfo", method = RequestMethod.GET)
	public void addArticleInfo(HttpServletRequest request, SubJectArticleBo sms) {
		String userid = (String) request.getSession().getAttribute("userid");
		List<String> ids = sms.getIds();
		// List<SubjectArticle> sali =
		// subjectArticleServiceImpl.subjectArtileAllInfo(stage)
		sms.setUserid(userid);
		List<SubJectArticleBo> list = subjectArticleServiceImpl.subjectArtileInsertMess(sms);

		for (int i = 0; i < list.size(); i++) {
			Systemmessage sm = new Systemmessage();
			sm.setId(UuidUtil.getUUID());
			sm.setTitle(list.get(i).getTittle());
			sm.setUserid(userid);
			sm.setTime(new Date());
			sm.setTag(0);
			sm.setUrl(list.get(i).getUrl());
			sm.setArticleid(list.get(i).getId());
			sm.setMid(list.get(i).getMid());
			sm.setType(sms.getType());
			int num = systemmessageServiceImpl.insertSystemMessage(sm);

			// 消息推送到 手机
			MessagePush messagePush = new MessagePush();
			messagePush.setUserid(userid);
			messagePush.setArticleid(list.get(i).getId());
			messagePush.setTitle(list.get(i).getTittle());
			messagePush.setMid(list.get(i).getMid());
			messagePush.setContent(list.get(i).getContent().substring(0, 20));
			systemSettingsImpl.getOpenPush(messagePush);
		}

	}
	@RequestMapping(value = "selectUserList", method = RequestMethod.GET)
   public Response selectUserList(HttpServletRequest request){
	   String userid = (String) request.getSession().getAttribute("userid");
	   List<User> list = systemmessageServiceImpl.selectUserList(userid);
	   if(list.size()>0){
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
   }
	@RequestMapping(value = "selectEmailByUser", method = RequestMethod.GET)
	public Response selectEmailByUser(HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		Emailconfig ec = new Emailconfig();
		ec.setUserid(userid);
		ec.setType(1);
		List<Emailconfig> list = systemSetServiceImpl.selectEmailByUser(ec);
		 if(list.size()>0){
				return new Response(ResponseStatus.Success, list, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
	}

}
