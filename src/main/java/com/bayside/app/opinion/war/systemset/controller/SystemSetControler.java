package com.bayside.app.opinion.war.systemset.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.bayside.app.opinion.fources.bo.Stiebabo;
import com.bayside.app.opinion.fources.bo.Swebsitebo;
import com.bayside.app.opinion.fources.bo.Sweibobo;
import com.bayside.app.opinion.fources.bo.Sweixinbo;
import com.bayside.app.opinion.war.knowledge.bo.RelevantBo;
import com.bayside.app.opinion.war.knowledge.model.Relevant;
import com.bayside.app.opinion.war.knowledge.service.KnowledgeService;
import com.bayside.app.opinion.war.mynews.bo.PersonManageBo;
import com.bayside.app.opinion.war.mynews.model.PersonManage;
import com.bayside.app.opinion.war.mynews.service.PersonManageService;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.WordSetBo;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.bo.SubjectClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMClassifyBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.opinion.war.subject.model.SubjectMClassify;
import com.bayside.app.opinion.war.subject.service.impl.SubjectMonitorServiceImpl;
import com.bayside.app.opinion.war.systemset.bo.DatasourceBo;
import com.bayside.app.opinion.war.systemset.bo.EmailConfigBo;
import com.bayside.app.opinion.war.systemset.bo.PresentationtemplateBo;
import com.bayside.app.opinion.war.systemset.bo.ServicesetBo;
import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.bo.WarnconfigBo;
import com.bayside.app.opinion.war.systemset.bo.WordsetBo;
import com.bayside.app.opinion.war.systemset.model.CustomIndexModal;
import com.bayside.app.opinion.war.systemset.model.Datasource;
import com.bayside.app.opinion.war.systemset.model.Emailconfig;
import com.bayside.app.opinion.war.systemset.model.NewSubject;
import com.bayside.app.opinion.war.systemset.model.Presentationtemplate;
import com.bayside.app.opinion.war.systemset.model.Serviceset;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;
import com.bayside.app.opinion.war.systemset.model.Setpresentationtemplate;
import com.bayside.app.opinion.war.systemset.model.Warnconfig;
import com.bayside.app.opinion.war.systemset.model.Wordset;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.AccessToken;
import com.bayside.util.HdfsUpLoadUtil;
import com.bayside.util.WeixinUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.ShardedJedis;

/**
 * <p>
 * Title: SystemSetControler
 * </P>
 * <p>
 * Description:系统设置
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
public class SystemSetControler {
	@Resource
	private Environment environment;
	@Autowired
	private SystemSetService systemSetServiceImpl;
	@Autowired
	private PersonManageService personManageServiceImpl;
	@Autowired
	private KnowledgeService knowledgeServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private PowerUserService powerServiceImpl;
	private static Logger Log = Logger.getLogger(SystemSetControler.class);
	/**
	 * <p>
	 * 方法名称：saveSubjectInfo
	 * </p>
	 * <p>
	 * 方法描述：添加专题
	 * </p>
	 * 
	 * @param subject
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/saveSubjectInfo", method = RequestMethod.GET)
	public Response saveSubjectInfo(Subject subject, HttpServletRequest request) {
		String id = UuidUtil.getUUID();
		String userid = (String) request.getSession().getAttribute("userid");
		subject.setId(id);
		subject.setUserid(userid);
		int num = systemSetServiceImpl.saveSubjectInfo(subject);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	/**
	 * <p>
	 * 方法名称：updateSubjectById
	 * </p>
	 * <p>
	 * 方法描述：修改专题
	 * </p>
	 * 
	 * @param smc
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/sysupdateSubjectById", method = RequestMethod.GET)
	public Response sysupdateSubjectById(Subject smc) throws Exception {

		int num = systemSetServiceImpl.updateByPrimaryKeySelective(smc);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectSubjectInfo
	 * </p>
	 * <p>
	 * 方法描述：查询专题信息
	 * </p>
	 * 
	 * @param subject
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSubjectInfo", method = RequestMethod.GET)
	public Response selectSubjectInfo(SubjectBo subject, HttpServletRequest request) {
		List<Subject> list = new ArrayList<Subject>();
		Subject s = new Subject();
		String userid = (String) request.getSession().getAttribute("userid");
		s.setUserid(userid);
		list = systemSetServiceImpl.selectSubjectInfo(s);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectAllClassify
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllClassify", method = RequestMethod.GET)
	public Response selectAllClassify() {
		List<SubjectClassify> list = systemSetServiceImpl.selectAllClassify();
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectOneSubject
	 * </p>
	 * <p>
	 * 方法描述：查询单个专题
	 * </p>
	 * 
	 * @param subject
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectOneSubject", method = RequestMethod.GET)
	public Response selectOneSubject(Subject subject) {
		Subject su = systemSetServiceImpl.selectOneSubject(subject);
		if (su != null) {
			return new Response(ResponseStatus.Success, su, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllSMC
	 * </p>
	 * <p>
	 * 方法描述： //查询全部专题分类
	 * </p>
	 * 
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllSMC", method = RequestMethod.GET)
	public Response selectAllSMC() {
		System.out.println("selectAllSMC");
		List<SubjectMClassify> list = systemSetServiceImpl.selectAllSMC();
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：unSelectSMC
	 * </p>
	 * <p>
	 * 方法描述： //查询没有分类的专题
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/unSelectSMC", method = RequestMethod.GET)
	public Response unSelectSMC(Subject record, HttpServletRequest request) {
		// 已经分类的专题
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		List<Subject> list = systemSetServiceImpl.selectSubjectNoClassifyid(record);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/selectOneSMC", method = RequestMethod.GET)
	public Response selectOneSMC(NewSubject subject) {
		SubjectMClassify smc = systemSetServiceImpl.selectBySubjectId(subject.getId());
		if (smc != null) {
			return new Response(ResponseStatus.Success, smc, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	@RequestMapping(value = "/updateSMC", method = RequestMethod.GET)
	public Response updateSMC(SubjectMClassify smc) {
		int num = systemSetServiceImpl.updateSMC(smc);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/deleteSMC", method = RequestMethod.GET)
	public Response deleteSMC(Subject subject) {
		int num = systemSetServiceImpl.deleteSMC(subject.getId());
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	@RequestMapping(value = "/saveSMC", method = RequestMethod.GET)
	public Response saveSMC(SubjectMClassify smc) {
		int num = systemSetServiceImpl.saveSubjectMClassify(smc);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/addSMC", method = RequestMethod.GET)
	public Response addSMC(Subject smc, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		smc.setUserid(userid);
		int num = systemSetServiceImpl.updateByPrimaryKeySelective(smc);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectSubjectById
	 * </p>
	 * <p>
	 * 方法描述：通过id查询专题
	 * </p>
	 * 
	 * @param subject
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSubjectById", method = RequestMethod.GET)
	public Response selectSubjectById(NewSubject subject) {
		Subject su = systemSetServiceImpl.selectSubjectById(subject.getId());
		NewSubject ss = new NewSubject();
		BeanUtils.copyProperties(su, ss);
		if (su != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			// SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd
			// HH:mm:ss");
			if (su.getStarttime() != null) {
				String starttime = formatter.format(su.getStarttime());
				ss.setStarttime(starttime);
			} else {
				ss.setStarttime(null);
			}
			if (su.getEndtime() != null) {
				String endtime = formatter.format(su.getEndtime());
				ss.setEndtime(endtime);
			} else {
				ss.setEndtime(null);
			}
			/*
			 * if(su.getType()!=null&&su.getType()==1){ String combineword =
			 * su.getCombinedWord(); ObjectMapper mapper = new ObjectMapper();
			 * try { List<Map<String, String>> list =
			 * mapper.readValue(combineword, ArrayList.class); String comword =
			 * ""; for (Map<String, String> map : list) { String subject_word =
			 * map.get("subject_word"); String region_word =
			 * map.get("region_word"); String event_word =
			 * map.get("event_word");
			 * if(subject_word!=null&&!"".equals(subject_word)){
			 * comword+=subject_word;
			 * if(region_word!=null&&!"".equals(region_word)){
			 * comword+="+"+region_word; }
			 * if(event_word!=null&&!"".equals(event_word)){
			 * comword+="+"+event_word; } comword+=" "; } }
			 * ss.setComWord(comword); } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } }
			 */
			ss.setAmbiguityWord(su.getAmbiguityWord());
			ss.setEventWord(su.getEventWord());
			ss.setRegionWord(su.getRegionWord());
			ss.setSubjectname(su.getSubjectname());
			ss.setSubjectWord(su.getSubjectWord());
			ss.setTinterval(su.getTinterval());
			ss.setUserid(su.getUserid());
			ss.setWarning(su.getWarning());
			ss.setSubjectname(su.getSubjectname());
			ss.setSubjectWord(su.getSubjectWord());
			ss.setComWord(su.getCombinedWord());
			ss.setId(su.getId());
			ss.setClassifyid(su.getClassifyid());
			ss.setWarnStart(su.getWarnStart());
			ss.setWarnEnd(su.getWarnEnd());
			ss.setType(su.getType());
			System.out.println(ss.getClassifyid());
			return new Response(ResponseStatus.Success, ss, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：updateSubject
	 * </p>
	 * <p>
	 * 方法描述：修改专题
	 * </p>
	 * 
	 * @param subjectBo
	 * @param subjectMClassifyBo
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
	public Response updateSubject(SubjectBo subjectBo, SubjectMClassifyBo subjectMClassifyBo) {

		boolean flag = systemSetServiceImpl.updateSubject(subjectBo, subjectMClassifyBo);
		if (flag) {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}

	/*
	 * @RequestMapping(value = "/deleteSubject",method = RequestMethod.GET)
	 * public Response deleteSubject(NewSubject subject) throws Exception{ int
	 * num = systemSetServiceImpl.deleteSubjectInfo(subject); if(num > 0){
	 * return new Response(ResponseStatus.Success,num,true); }else{ return null;
	 * } }
	 */
	/**
	 * <p>
	 * 方法名称：deleteSubject
	 * </p>
	 * <p>
	 * 方法描述：删除专题
	 * </p>
	 * 
	 * @param subject
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteSubject", method = RequestMethod.GET)
	public Response deleteSubject(NewSubject subject) {
		boolean flag = systemSetServiceImpl.deleteSubjectInfo(subject);
		if (flag) {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllsubjectClassify
	 * </p>
	 * <p>
	 * 方法描述：查询所有专题分类
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllsubjectClassify", method = RequestMethod.GET)
	public Response selectAllsubjectClassify() {
		List<SubjectClassify> list = systemSetServiceImpl.selectAllClassify();
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updatesubjectClassify
	 * </p>
	 * <p>
	 * 方法描述：修改分类专题
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updatesubjectClassify", method = RequestMethod.GET)
	public Response updatesubjectClassify(SubjectClassifyBo record) {
		SubjectClassify scf = new SubjectClassify();
		BeanUtils.copyProperties(record, scf);
		int num = systemSetServiceImpl.updateSubjectClassify(scf);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/deletesubjectClassify", method = RequestMethod.GET)
	public Response deletesubjectClassify(SubjectClassify record) {
		int num = systemSetServiceImpl.deleteByPrimaryKey(record.getId());
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：addSubjectClassify
	 * </p>
	 * <p>
	 * 方法描述：添加专题分类
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/addSubjectClassify", method = RequestMethod.GET)
	public Response addSubjectClassify(SubjectClassifyBo record, HttpServletRequest request) {
		// 不同用户下的序号 获取userid
		// 根据用户id 查询 该用户已经添加的分类条数
		String userid = (String) request.getSession().getAttribute("userid");
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)){
			userparentid = userid;
		}
		record.setUserparentid(userparentid);
		SubjectClassify scf = new SubjectClassify();
		List<SubjectClassify> list = systemSetServiceImpl.selectSubjectClassifyByUserId(userid);
		if (list.size() > 0) {
			int order = list.size() + 1;

			BeanUtils.copyProperties(record, scf);
			scf.setOrderIndex(order);
			scf.setId(UuidUtil.getUUID());
			scf.setCreateTime(new Date());
			scf.setUserid(userid);
			int num = systemSetServiceImpl.addSubjectClassify(scf);
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
		} else {
			int order = 1;
			BeanUtils.copyProperties(record, scf);
			scf.setOrderIndex(order);
			scf.setId(UuidUtil.getUUID());
			scf.setCreateTime(new Date());
			scf.setUserid(userid);
			int num = systemSetServiceImpl.addSubjectClassify(scf);
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
		}
	}

	// 按序号查询
	/**
	 * <p>
	 * 方法名称：slelectSubjectClassifyOrder
	 * </p>
	 * <p>
	 * 方法描述：按序号查询分类
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/slelectSubjectClassifyOrder", method = RequestMethod.GET)
	public Response slelectSubjectClassifyOrder(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		List<SubjectClassify> list = systemSetServiceImpl.selectSubjectClassifyByOrder(userid);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：updateSubjectClassifyOrder
	 * </p>
	 * <p>
	 * 方法描述： //修改分类序号 ，上移
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateSubjectClassifyOrder", method = RequestMethod.GET)
	public Response updateSubjectClassifyOrder(SubjectClassify record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		System.out.println(userid);
		record.setUserid(userid);
		if (record.getOrderIndex() > 1) {
			int index = record.getOrderIndex() - 1;
			record.setOrderIndex(index);
			SubjectClassify sc = systemSetServiceImpl.selectOneInfoByOrder(record);
			sc.setOrderIndex(record.getOrderIndex() + 1);
			int n = systemSetServiceImpl.updateSubjectClassifyOrder(sc);
			SubjectClassify scf = new SubjectClassify();
			BeanUtils.copyProperties(record, scf);
			scf.setOrderIndex(index);
			Boolean flag = true;
			try {
				int num = systemSetServiceImpl.updateSubjectClassifyOrder(scf);
				if (num > 0 && n > 0) {
					flag = true;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			if (flag) {
				return new Response(ResponseStatus.Success, "上移成功", true);
			} else {
				return new Response(ResponseStatus.Error, "上移失败", false);
			}

		} else {
			return new Response(ResponseStatus.Error, "无需上移", false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectSubjectClassify
	 * </p>
	 * <p>
	 * 方法描述： //根据ID 查询分类
	 * </p>
	 * 
	 * @param scf
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSubjectClassify", method = RequestMethod.GET)
	public Response selectSubjectClassify(SubjectClassify scf) {
		SubjectClassify sc = systemSetServiceImpl.selectSubjectClassify(scf.getId());
		if (sc != null) {
			return new Response(ResponseStatus.Success, sc, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectSubjectByClassifyid
	 * </p>
	 * <p>
	 * 方法描述：//专题管理 查询该分类下的专题
	 * </p>
	 * 
	 * @param sc
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSubjectByClassifyid", method = RequestMethod.GET)
	public Response selectSubjectByClassifyid(Subject sc) {
		sc.setIsdelete(false);
		List<Subject> list = systemSetServiceImpl.selectSubjectByClassifyid(sc);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：addWarnconfig
	 * </p>
	 * <p>
	 * 方法描述：添加预警设置
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/addWarnconfig", method = RequestMethod.POST)
	public Response addWarnconfig(WarnconfigBo record, HttpServletRequest request) {
		record.setId(UuidUtil.getUUID());
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)){
			userparentid = userid;
		}
		record.setUserparentid(userparentid);
		// 校验权限
		boolean fag = false;
		boolean num = false;
		WordSet wordset = new WordSet();
		wordset.setUserid(userid);
		// wordset.setName("预警信息设置个数");
		wordset.setName(AppConstant.standPower.YUJINGNAME);
		WordSet ws = userServiceImpl.selectPowerByName(wordset);
		if(null!=ws){
			if (ws.getCansetword() > 0) {
				fag = true;
				num = systemSetServiceImpl.addWarnConfig(record);
				
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
		
		if (num) {
			//修改权限
			ws.setSetword(ws.getSetword()+1);
			userServiceImpl.updateWordSet(ws);
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllWarnconfig
	 * </p>
	 * <p>
	 * 方法描述：查询用户所有预警信息
	 * </p>
	 * 
	 * @param page
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllWarnconfig", method = RequestMethod.GET)
	public Response selectAllWarnconfig(PageInfo page, WarnconfigBo record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		if(userid==null||"".equals(userid)){
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)||userparentid.equals(userid)){
			userparentid = user.getId();	
			record.setUserparentid(userparentid);
		}else{
			record.setUserid(userid);
		}
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Warnconfig> list = systemSetServiceImpl.selectAllWarnconfig(record);
		PageInfo<Warnconfig> info = new PageInfo<Warnconfig>(list);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectOneWarnconfig
	 * </p>
	 * <p>
	 * 方法描述：查询单个预警
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectOneWarnconfig", method = RequestMethod.GET)
	public Response selectOneWarnconfig(Warnconfig record) {
		Warnconfig wc = systemSetServiceImpl.selectOneWarnconfig(record.getId());
		if (wc != null) {
			return new Response(ResponseStatus.Success, wc, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updateWarnconfig
	 * </p>
	 * <p>
	 * 方法描述：修改预警
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateWarnconfig", method = RequestMethod.POST)
	public Response updateWarnconfig(Warnconfig record) {
		int num = systemSetServiceImpl.updateWarnConfig(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, num, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：deleteWarnconfig
	 * </p>
	 * <p>
	 * 方法描述：删除预警
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteWarnconfig", method = RequestMethod.GET)
	public Response deleteWarnconfig(Warnconfig record) {

		int num = systemSetServiceImpl.deleteWarncondfig(record.getId());

		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}

	}

	// 邮箱设置
	/**
	 * <p>
	 * 方法名称：addEmailconfig
	 * </p>
	 * <p>
	 * 方法描述：添加邮箱
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/addEmailconfig", method = RequestMethod.POST)
	public Response addEmailconfig(HttpServletRequest request) {
		Emailconfig record = new Emailconfig();
		String email = request.getParameter("email");
		String type = request.getParameter("type");
		String openid = request.getParameter("openid");
		Integer ty = Integer.parseInt(type);
		record.setId(UuidUtil.getUUID());
		record.setEmail(email);
		record.setType(ty);
		record.setOpenid(openid);
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		Emailconfig rec = new Emailconfig();
		rec.setEmail(email);
		rec.setType(ty);
		rec.setUserid(userid);
		Emailconfig wc = systemSetServiceImpl.selectEmailByEmail(rec);
		int num = 0;
		if(null!=wc){
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.REPEATSAVE, false);
		}else{
			if("4".equals(type)){
				if(null == openid || "".equals(openid)){
					return new Response(ResponseStatus.Error, AppConstant.responseInfo.ATTENTIONEERRO, false);
				}else{
					//根据openid 查询数据库里是否已经存在
					List<Emailconfig> listemail = systemSetServiceImpl.selectEmailByOpenid(record);
					if(listemail.size()>0){
						return new Response(ResponseStatus.Error, AppConstant.responseInfo.ATTENTIONEERRO, false);
					}else{
						num = systemSetServiceImpl.addEmailconfig(record);
					}
					 
				}
			}else{
				
				num = systemSetServiceImpl.addEmailconfig(record);
			}
	
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, num, false);
			}
		}
		
	}

	/**
	 * <p>
	 * 方法名称：selectAllEmailconfig
	 * </p>
	 * <p>
	 * 方法描述：查询用户所有邮箱设置信息
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllEmailconfig", method = RequestMethod.GET)
	public Response selectAllEmailconfig(EmailConfigBo record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		Emailconfig rec = new Emailconfig();
		rec.setUserid(userid);
		rec.setType(record.getType());
		List<Emailconfig> list = systemSetServiceImpl.selectAllEmailconfig(rec);

		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/selectAllconfig", method = RequestMethod.GET)
	public Response selectAllconfig(EmailConfigBo record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		Emailconfig rec = new Emailconfig();
		rec.setUserid(userid);
		// rec.setType(record.getType());
		List<Emailconfig> list = systemSetServiceImpl.selectAllConfig(rec);

		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectOneEmailconfig
	 * </p>
	 * <p>
	 * 方法描述：查询单个邮箱设置信息
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectOneEmailconfig", method = RequestMethod.GET)
	public Response selectOneEmailconfig(Emailconfig record) {
		Emailconfig wc = systemSetServiceImpl.selectOneEmailconfig(record.getId());
		if (wc != null) {
			return new Response(ResponseStatus.Success, wc, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/selectEmailByText", method = RequestMethod.GET)
	public Response selectEmailByText(HttpServletRequest request) {
		String email = request.getParameter("email");
		String type = request.getParameter("type");
		String userid = (String) request.getSession().getAttribute("userid");
		Integer ty = Integer.parseInt(type);
		Emailconfig rec = new Emailconfig();
		rec.setEmail(email);
		rec.setType(ty);
		rec.setUserid(userid);
		Emailconfig wc = systemSetServiceImpl.selectEmailByEmail(rec);
		if(null!=wc){
			return new Response(ResponseStatus.Success, wc, true);
		}else{
			return null;
		}
		

	}

	/**
	 * <p>
	 * 方法名称：updateEmailconfig
	 * </p>
	 * <p>
	 * 方法描述：修改邮箱设置
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateEmailconfig", method = RequestMethod.GET)
	public Response updateEmailconfig(Emailconfig record) {
		int num = systemSetServiceImpl.updateEmailconfig(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, num, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：deleteEmailconfig
	 * </p>
	 * <p>
	 * 方法描述：删除邮箱设置
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteEmailconfig", method = RequestMethod.GET)
	public Response deleteEmailconfig(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		String email = request.getParameter("email");
		String type = request.getParameter("type");
		Integer ty = Integer.parseInt(type);
		Emailconfig rec = new Emailconfig();
		rec.setEmail(email);
		rec.setType(ty);
		rec.setUserid(userid);
		int num = systemSetServiceImpl.delectEmailconfig(rec);

		return new Response(ResponseStatus.Success, num, true);

	}

	/**
	 * <p>
	 * 方法名称：selectLoginUser
	 * </p>
	 * <p>
	 * 方法描述： //查询登录用户
	 * </p>
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectLoginUser", method = RequestMethod.GET)
	public Response selectLoginUser(User user, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		User loginuser = systemSetServiceImpl.selectLoginUser(userid);
		if (loginuser != null) {
			return new Response(ResponseStatus.Success, loginuser, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updateLoginUser
	 * </p>
	 * <p>
	 * 方法描述： //修改登录用户信息
	 * </p>
	 * 
	 * @param user
	 *            ownindustry
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateLoginUser", method = RequestMethod.GET)
	public Response updateLoginUser(User user, HttpServletRequest request, HttpSession session) {
		User newuser = (User) request.getSession().getAttribute("user");
		newuser.setCompanyshortname(user.getCompanyshortname());
		newuser.setOrgname(user.getOrgname());
		newuser.setMobilephone(user.getMobilephone());
		newuser.setEmail(user.getEmail());
		newuser.setMobilephone(user.getMobilephone());
		newuser.setOwnindustry(user.getOwnindustry());
		newuser.setProvince(user.getProvince());
		newuser.setCity(user.getCity());
		newuser.setFileaddress(user.getFileaddress());
		session.setAttribute("user", newuser);
		int num = systemSetServiceImpl.updateLoginUser(user);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	@RequestMapping(value = "/registerLoginUser", method = RequestMethod.GET)
	public Response registerLoginUser(User user, HttpServletRequest request, HttpSession session) {
		User newuser = (User) request.getSession().getAttribute("user");
		newuser.setCompanyshortname(user.getCompanyshortname());
		newuser.setCompanyfullname(user.getCompanyfullname());
		newuser.setMobilephone(user.getMobilephone());
		newuser.setEmail(user.getEmail());
		newuser.setMobilephone(user.getMobilephone());
		newuser.setOwnindustry(user.getOwnindustry());
		newuser.setProvince(user.getProvince());
		newuser.setCity(user.getCity());
		newuser.setFileaddress(user.getFileaddress());
		// newuser.setUsertype("标准用户");
		newuser.setStatus(1);
		session.setAttribute("user", newuser);
		user.setStatus(1);
		int num = systemSetServiceImpl.updateLoginUser(user);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	@RequestMapping(value = "/wanshanLoginUser", method = RequestMethod.GET)
	public Response wanshanLoginUser(User user, HttpServletRequest request, HttpSession session) {
		User newuser = (User) request.getSession().getAttribute("user");
		newuser.setCompanyshortname(user.getCompanyshortname());
		newuser.setCompanyfullname(user.getOrgname());
		newuser.setMobilephone(user.getMobilephone());
		newuser.setEmail(user.getEmail());
		newuser.setMobilephone(user.getMobilephone());
		newuser.setOwnindustry(user.getOwnindustry());
		newuser.setProvince(user.getProvince());
		newuser.setCity(user.getCity());
		newuser.setFileaddress(user.getFileaddress());
		// newuser.setUsertype("标准用户");
		newuser.setStatus(1);
		session.setAttribute("user", newuser);
		user.setStatus(1);
		int num = systemSetServiceImpl.registerUserInfo(user);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	@RequestMapping(value = "/updateLoginUserPassword", method = RequestMethod.GET)
	public Response updateLoginUserPassword(User user, HttpServletRequest request, HttpSession session) {
		User newuser = (User) request.getSession().getAttribute("user");
		newuser.setPassword(user.getPassword());
		session.setAttribute("user", newuser);
		int num = systemSetServiceImpl.updatepassword(user);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：addsonUser
	 * </p>
	 * <p>
	 * 方法描述： //添加子账号
	 * </p>
	 * 
	 * @param userBo
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/addsonUser", method = RequestMethod.POST)
	public Response addsonUser(UserBo userBo, HttpServletRequest request) {
		userBo.setId(UuidUtil.getUUID());
		User user = new User();
		BeanUtils.copyProperties(userBo, user);
		String userid = (String) request.getSession().getAttribute("userid");
		user.setParentid(userid);
		userBo.setParentid(userid);
		SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		userBo.setRegistertime(formats.format(new Date()));
		int num = 0;
		// 校验权限
		boolean fag = false;
		WordSet wordset = new WordSet();
		wordset.setUserid(userid);
		wordset.setName(AppConstant.standPower.SONNAME);
		WordSet ws1 = userServiceImpl.selectPowerByName(wordset);
		
		if (ws1.getCansetword() - ws1.getSetword() > 0) {
			fag = true;
			num = systemSetServiceImpl.addsonUser(userBo);
		}

       // Boolean f = userServiceImpl.insertWordSetBo(userBo);
		if (num > 0 ) {
			//修改父权限  父子账号个数加1
			WordSet wset = new WordSet();
			wset.setId(ws1.getId());
			wset.setSetword(ws1.getSetword()*1+1);
			userServiceImpl.updateWordSetById(wset);
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}
	@RequestMapping(value = "/addSonuserWordset", method = RequestMethod.GET)
	public Response addSonuserWordset(UserBo user){
		int num = systemSetServiceImpl.bathinsertSonUSerPower(user);
		if(num > 0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/isaddSonuserPower", method = RequestMethod.GET)
	public Response isaddSonuserPower(String id){
		List<WordSet> list = userServiceImpl.selectAllWordSet(id);
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/updateSonuserWordset", method = RequestMethod.GET)
	public Response updateSonuserWordset(UserBo user,HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		user.setParentid(userid);
		List<WordSet> list = userServiceImpl.selectAllWordSet(user.getId());
		int num =0;
		if(list.size()>0){
			num = systemSetServiceImpl.bathupdateSonUSerPower(user);
		}else{
			num = systemSetServiceImpl.bathinsertSonUSerPower(user);
		}
		
		if(num > 0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectSonUserAttr", method = RequestMethod.GET)
   public Response selectSonUserAttr(UserBo user){
	  UserBo userbo = systemSetServiceImpl.selectSonUserAtttr(user);
	  if(null!=userbo){
		  return new Response(ResponseStatus.Success,userbo,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
	}
	@RequestMapping(value = "/updateParentWordSet", method = RequestMethod.GET)
	public Response updateParentWordSet(UserBo userBo, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		// ws.setCansetword(cansetword);
		Boolean f = systemSetServiceImpl.updateParentPower(userBo, userid);
		return new Response(ResponseStatus.Success, AppConstant.responseInfo.UPDATESUCCESS, true);

	}
	@RequestMapping(value = "/addParentWordSet", method = RequestMethod.GET)
	public Response addParentWordSet(UserBo userBo, HttpServletRequest request) {
		String id = userBo.getId();
		String userid = (String) request.getSession().getAttribute("userid");
		User user = new User();
		BeanUtils.copyProperties(userBo, user);
		List<WordSet> list = userServiceImpl.selectAllWordSet(userid);
	    userBo = powerServiceImpl.selectPowerByUserId(userBo);
		for (int i = 0; i < list.size(); i++) {
			WordSet ws = new WordSet();
			ws.setUserid(userid);
			ws.setName(list.get(i).getName());
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				if(null!=user.getSubjecttimes()){
					ws.setSetword(list.get(i).getSetword() - user.getSubjecttimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				// ws.setCansetword(list.get(i).getCansetword() +
				// user.getWarntimes());
				if(null!=user.getWarntimes()){
					ws.setSetword(list.get(i).getSetword() - user.getWarntimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
				// ws.setCansetword(list.get(i).getCansetword() +
				// user.getMonitortimes());
				if(null!=user.getMonitortimes()){
					ws.setSetword(list.get(i).getSetword() - user.getMonitortimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.SONNAME)) {
				// ws.setCansetword(list.get(i).getCansetword() + 1);
				ws.setSetword(list.get(i).getSetword() - 1);
			}
			if (list.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
				// ws.setCansetword(list.get(i).getCansetword() +
				// user.getPersontimes());
				if(null!=user.getPersontimes()){
					ws.setSetword(list.get(i).getSetword() - user.getPersontimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			//附加权限
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				if(null != userBo.getSubjectReport()){
					ws.setSetword(list.get(i).getSetword() - userBo.getSubjectReport());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.MODALNAME)) {
				if(null!=userBo.getModalNum()){
					ws.setSetword(list.get(i).getSetword() - userBo.getModalNum());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.WORDNAME)) {
				if(null!=userBo.getKeywordNum()){
					ws.setSetword(list.get(i).getSetword() - userBo.getKeywordNum());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
				//ws.setSetword(list.get(i).getSetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)) {
				if(null!=userBo.getCloudtimes()){
					ws.setSetword(list.get(i).getSetword() - userBo.getCloudtimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
		
			}
			int num = systemSetServiceImpl.updateWordSet(ws);
		}
		//int num = systemSetServiceImpl.deleteByUserid(id);
		// ws.setCansetword(cansetword);
		return new Response(ResponseStatus.Success, AppConstant.responseInfo.UPDATESUCCESS, true);

	}

	@RequestMapping(value = "/updatePowerNum", method = RequestMethod.GET)
	public Response updatePowerNum(WordSet record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		int num = systemSetServiceImpl.updateWordSet(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}
	@RequestMapping(value = "/updateSubjectPowerNum", method = RequestMethod.GET)
	public Response updateSubjectPowerNum(WordSet record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		int num = systemSetServiceImpl.updateWordSet(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllsonUser
	 * </p>
	 * <p>
	 * 方法描述： //查询所有子账号信息
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllsonUser", method = RequestMethod.GET)
	public Response selectAllsonUser(UserBo record, HttpServletRequest request, PageInfo page) {
		String userid1 = (String) request.getSession().getAttribute("userid");
		String userid = userid1;
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<User> list = systemSetServiceImpl.selectsonUserByParentId(userid);
		PageInfo<User> info = new PageInfo<User>(list);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/selectUserSon", method = RequestMethod.GET)
	public Response selectUserSon(HttpServletRequest request) {
		String userid1 = (String) request.getSession().getAttribute("userid");
		String userid = userid1;

		List<User> list = systemSetServiceImpl.selectsonUserByParentId(userid);

		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/deleteWordSet", method = RequestMethod.GET)
	public Response deleteWordSet(UserBo record) {
		int num = systemSetServiceImpl.deleteByUserid(record.getId());
		if (num > 0) {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：deletesonUser
	 * </p>
	 * <p>
	 * 方法描述： //删除子账号信息
	 * </p>
	 * 
	 * @param userBo
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deletesonUser", method = RequestMethod.GET)
	public Response deletesonUser(UserBo userBo,HttpServletRequest request) {
	    String parentid = (String)request.getSession().getAttribute("userid");
		int num = systemSetServiceImpl.deletesonUser(userBo,parentid);
		
		if (num > 0) {
			//修改父权限
			systemSetServiceImpl.deleteByClassifyKey(userBo.getId());
			//修改父类权限
			
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectBySubjectName
	 * </p>
	 * <p>
	 * 方法描述： //根据专题名称查询专题
	 * </p>
	 * 
	 * @param subjectBo
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectBySubjectName", method = RequestMethod.GET)
	public Response selectBySubjectName(SubjectBo subjectBo, HttpServletRequest request) {
		Subject sub = new Subject();
		BeanUtils.copyProperties(subjectBo, sub);
		String userid = (String) request.getSession().getAttribute("userid");
		sub.setUserid(userid);
		sub.setIsdelete(false);
		Subject subject = systemSetServiceImpl.selectBySubjectName(sub);
		if (subject != null) {
			return new Response(ResponseStatus.Success, subject, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectNumBySubjectName
	 * </p>
	 * <p>
	 * 方法描述：查询是否有重复专题
	 * </p>
	 * 
	 * @param subjectBo
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年12月27日
	 *        <p>
	 *        history 2016年12月27日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectNumBySubjectName", method = RequestMethod.GET)
	public Response selectNumBySubjectName(SubjectBo subjectBo, HttpServletRequest request) {
		Subject sub = new Subject();
		BeanUtils.copyProperties(subjectBo, sub);
		String userid = (String) request.getSession().getAttribute("userid");
		sub.setUserid(userid);
		sub.setIsdelete(false);
		List<Subject> list = systemSetServiceImpl.selectNumBySubjectName(sub);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 关闭文件流
	 * 
	 * @param fos
	 * @param fis
	 */
	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
                Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * <p>
	 * 方法名称：saveSetTemplate
	 * </p>
	 * <p>
	 * 方法描述：//添加简报模板
	 * </p>
	 * 
	 * @param setTemplate
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/saveSetTemplate", method = RequestMethod.GET)
	public Response saveSetTemplate(Setpresentationtemplate setTemplate) {
		setTemplate.setId(UuidUtil.getUUID());
		int num = systemSetServiceImpl.saveSetTemplate(setTemplate);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, "保存失败", false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllSetTemplate
	 * </p>
	 * <p>
	 * 方法描述： //查询所有简报模板
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllSetTemplate", method = RequestMethod.GET)
	public Response selectAllSetTemplate(String type) {
		List list = systemSetServiceImpl.selectSetpresentationtemplate(type);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：saveTemplate
	 * </p>
	 * <p>
	 * 方法描述： //添加用户选择的简报模板
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.GET)
	public Response saveTemplate(PresentationtemplateBo record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		int num = systemSetServiceImpl.updateTemplate(record);
		// 查看该用户是否存在
		//List<Presentationtemplate> pt = systemSetServiceImpl.selectTemplateByUserid(userid);
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		
	}

	/**
	 * <p>
	 * 方法名称：selectedTemplate
	 * </p>
	 * <p>
	 * 方法描述： //查询用户选择的模板
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectedTemplate", method = RequestMethod.GET)
	public Response selectedTemplate(HttpServletRequest request,PresentationtemplateBo presentationtemplateBo) {
		String userid = (String) request.getSession().getAttribute("userid");
		presentationtemplateBo.setUserid(userid);
		Presentationtemplate pt = systemSetServiceImpl.selectTemplateByUserid(presentationtemplateBo);
		if (pt != null) {
			return new Response(ResponseStatus.Success, pt, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectSetIndexModal
	 * </p>
	 * <p>
	 * 方法描述： //查询系统设置的首页模板
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSetIndexModal", method = RequestMethod.GET)
	public Response selectSetIndexModal(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		List<SetIndexModal> list = systemSetServiceImpl.selectIndexModal(userid);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：addCustomIndexModal
	 * </p>
	 * <p>
	 * 方法描述：//添加自定义首页模板
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/addCustomIndexModal", method = RequestMethod.GET)
	public Response addCustomIndexModal(CustomIndexModal record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setId(UuidUtil.getUUID());
		record.setUserid(userid);
		record.setPosition("0");
		int num = systemSetServiceImpl.addCustomIndexModal(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectSetModalById
	 * </p>
	 * <p>
	 * 方法描述： //查询根据id查询首页系统设置模板
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectSetModalById", method = RequestMethod.GET)
	public Response selectSetModalById(SetIndexModal record) {
		SetIndexModal sm = systemSetServiceImpl.selectSetModalById(record.getId());
		if (sm != null) {
			return new Response(ResponseStatus.Success, sm, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);

		}
	}

	/**
	 * <p>
	 * 方法名称：selectCustomModalById
	 * </p>
	 * <p>
	 * 方法描述：//查询自定义首页模板
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectCustomModalById", method = RequestMethod.GET)
	public Response selectCustomModalById(CustomIndexModal record) {
		CustomIndexModal cm = systemSetServiceImpl.selectCustomIndexModal(record.getId());
		if (cm != null) {
			return new Response(ResponseStatus.Success, cm, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);

		}
	}

	/**
	 * <p>
	 * 方法名称：updateSetIndexModal
	 * </p>
	 * <p>
	 * 方法描述：修改系统设置模板
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateSetIndexModal", method = RequestMethod.GET)
	public Response updateSetIndexModal(SetIndexModalBo record) {
		int num = systemSetServiceImpl.updateSetIndexModal(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllCustomIndexModal
	 * </p>
	 * <p>
	 * 方法描述： //根据userid查询自定义模板
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllCustomIndexModal", method = RequestMethod.GET)
	public Response selectAllCustomIndexModal(HttpServletRequest request) {

		String userid = (String) request.getSession().getAttribute("userid");
		List<CustomIndexModal> list = systemSetServiceImpl.selectByUserId(userid);
		if (list != null) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	//
	/**
	 * <p>
	 * 方法名称：selectCustomIndexModal
	 * </p>
	 * <p>
	 * 方法描述：查询客户自定义模板
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectCustomIndexModal", method = RequestMethod.GET)
	public Response selectCustomIndexModal(CustomIndexModal record) {
		CustomIndexModal cm = systemSetServiceImpl.selectCustomIndexModal(record.getId());
		if (cm != null) {
			return new Response(ResponseStatus.Success, cm, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：deleteCustomIndexModal
	 * </p>
	 * <p>
	 * 方法描述：删除客户自定义模板
	 * </p>
	 * 
	 * @param cm
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteCustomIndexModal", method = RequestMethod.GET)
	public Response deleteCustomIndexModal(CustomIndexModal cm) {
		int num = systemSetServiceImpl.deleteCustomIndexModal(cm.getId());
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updateCustomIndexModal
	 * </p>
	 * <p>
	 * 方法描述：修改客户自定义模板
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateCustomIndexModal", method = RequestMethod.GET)
	public Response updateCustomIndexModal(CustomIndexModal record) {
		int num = systemSetServiceImpl.updateCustomIndexModal(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	@RequestMapping("updateCustomIndex")
	@ResponseBody // 此注解不能省略 否则ajax无法接受返回值
	public Map<String, Object> updateCustomIndex(String id, String position) {
		CustomIndexModal cm = new CustomIndexModal();
		cm.setId(id);
		cm.setPosition(position);
		int num = systemSetServiceImpl.updateCustomIndexModal(cm);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (num > 0) {
			resultMap.put("result", "SUCCESS");
		} else {
			resultMap.put("result", "修改失败");
		}
		return resultMap;

	}

	/**
	 * <p>
	 * 方法名称：selectByPosition
	 * </p>
	 * <p>
	 * 方法描述：根据位置查询模板
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByPosition", method = RequestMethod.GET)
	public Response selectByPosition(CustomIndexModal record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		List<CustomIndexModal> list = systemSetServiceImpl.selectByPosition(record);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllDataSource
	 * </p>
	 * <p>
	 * 方法描述：查询用户所有的数据源
	 * </p>
	 * 
	 * @param datas
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllDataSource", method = RequestMethod.GET)
	public Response selectAllDataSource(DatasourceBo datas, PageInfo page,HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		datas.setUserid(userid);
		datas.setStart((page.getPageNum()-1)*page.getPageSize());
		datas.setSize(page.getPageSize());
		List<Datasource> list = systemSetServiceImpl.selectAllDataSource(datas);
		Datasource  sd = systemSetServiceImpl.selectDateSouceTotal(datas);
		SolrPage info = new SolrPage<Datasource>();
		info.setTotal(sd.getTag());
		info.setPageNum(page.getPageNum());
		info.setPageSize(page.getPageSize());
		info.setNavigatePages(8);
		info.setDatas(list);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	
	

	/**
	 * <p>
	 * 方法名称：insertDataSource
	 * </p>
	 * <p>
	 * 方法描述：添加数据源
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
	@RequestMapping(value = "/insertDataSource", method = RequestMethod.POST)
	public Response insertDataSource(Datasource record, HttpServletRequest request) {
		record.setId(UuidUtil.getUUID());
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		if(null!=record.getSearchid()){
			//检查是否已经 添加过
	      List<DataSource> list = systemSetServiceImpl.selectBySearchid(record.getSearchid());
			if(list.size()>0){
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.ATTENTION, false);
			}
		}
		int ds = systemSetServiceImpl.insertDataSource(record);
		if (ds > 0) {
			return new Response(ResponseStatus.Success, ds, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}
	//
	

	/**
	 * 
	 * <p>
	 * 方法名称：updateDataSource
	 * </p>
	 * <p>
	 * 方法描述：修改重点关注
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月14日
	 *        <p>
	 *        history 2016年11月14日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateDataSource", method = RequestMethod.GET)
	public Response updateDataSource(Datasource record) {
		int num = systemSetServiceImpl.updateDataSource(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：deleteDataSource
	 * </p>
	 * <p>
	 * 方法描述：删除重点关注
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月14日
	 *        <p>
	 *        history 2016年11月14日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteDataSource", method = RequestMethod.GET)
	public Response deleteDataSource(Datasource record) {
		int num = systemSetServiceImpl.deleteDataSource(record.getId());
		if (num > 0) {
			WordSet ww = new WordSet();
			ww.setUserid(record.getUserid());
			ww.setName(AppConstant.standPower.ATTENTIONNUM);
			WordSet ws = powerServiceImpl.selectPowerByName(ww);
			if(ws!=null){
				ws.setSetword(ws.getSetword()-1);
				powerServiceImpl.updateWordSet(ws);
			}
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllWordset
	 * </p>
	 * <p>
	 * 方法描述：词条设置
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
	@RequestMapping(value = "/selectAllWordset", method = RequestMethod.GET)
	public Response selectAllWordset(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		List<Wordset> list = systemSetServiceImpl.selectAllWordset(userid);
		List<WordsetBo> newlist = new ArrayList<WordsetBo>();
		for (int i = 0; i < list.size(); i++) {
			WordsetBo wb = new WordsetBo();
			wb.setId(list.get(i).getId());
			wb.setCansetword(list.get(i).getCansetword());
			wb.setName(list.get(i).getName());
			wb.setSetword(list.get(i).getSetword());
			wb.setCansetword(list.get(i).getCansetword());
			wb.setUserid(list.get(i).getUserid());
			if (list.get(i).getSetword().equals(list.get(i).getCansetword())) {
				wb.setOperator(AppConstant.standPower.BUY);

			} else {
				wb.setOperator(AppConstant.standPower.SET);

			}
			newlist.add(wb);
		}
		if (newlist.size() > 0) {
			return new Response(ResponseStatus.Success, newlist, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectpageSubjectInfo
	 * </p>
	 * <p>
	 * 方法描述：分页查询专题设置
	 * </p>
	 * 
	 * @param subject
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectpageSubjectInfo", method = RequestMethod.GET)
	public Response selectpageSubjectInfo(PageInfo page, SubjectBo subject, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		if(userid==null){
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)||userparentid.equals(userid)){
			userparentid = user.getId();	
			subject.setUserparentid(userparentid);
		}else{
			subject.setUserid(userid);
		}
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Subject> list = new ArrayList<Subject>();
		Subject su = new Subject();
		BeanUtils.copyProperties(subject, su);
		su.setIsdelete(false);
		list = systemSetServiceImpl.selectSubjectInfo(su);
		PageInfo<Subject> info = new PageInfo<Subject>(list);

		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectpageSubjectInfo
	 * </p>
	 * <p>
	 * 方法描述：分页查询专题设置
	 * </p>
	 * 
	 * @param subject
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectdeletepageSubjectInfo", method = RequestMethod.GET)
	public Response selectdeletepageSubjectInfo(PageInfo page, SubjectBo subject, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		subject.setUserid(userid);
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Subject> list = new ArrayList<Subject>();
		Subject su = new Subject();
		BeanUtils.copyProperties(subject, su);
		list = systemSetServiceImpl.selectdeleteSubject(userid);
		PageInfo<Subject> info = new PageInfo<Subject>(list);

		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectAllSubjectInfo
	 * </p>
	 * <p>
	 * 方法描述：查询所有专题
	 * </p>
	 * 
	 * @param subject
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllSubjectInfo", method = RequestMethod.GET)
	public Response selectAllSubjectInfo(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");

		Subject su = new Subject();
		su.setUserid(userid);
		su.setIsdelete(false);
		List<Subject> list = new ArrayList<Subject>();

		list = systemSetServiceImpl.selectSubjectInfo(su);

		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	
	/**
	 * 
	 * <p>
	 * 方法名称：updatedeleteSubjectById
	 * </p>
	 * <p>
	 * 方法描述：恢复专题
	 * </p>
	 * 
	 * @param smc
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年11月29日
	 *        <p>
	 *        history 2016年11月29日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updatedeleteSubjectById", method = RequestMethod.GET)
	public Response updatedeleteSubjectById(Subject smc) throws Exception {
		smc.setIsdelete(false);
		int num = systemSetServiceImpl.updateDeleteSubject(smc);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：ajaxUpload
	 * </p>
	 * <p>
	 * 方法描述：用户信息图片上传
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @author liuyy
	 * @since 2016年10月19日
	 *        <p>
	 *        history 2016年10月19日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "ajaxUpload", method = RequestMethod.POST)
	public String ajaxUpload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//JSONArray re = new JSONArray();
		String fileName = "";
		String newFileName = "";
		String imgurl = "";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile mulfile = multipartRequest.getFile(key);
			fileName = mulfile.getOriginalFilename();
			// 获取图片的扩展名
			String extensionName = mulfile.getOriginalFilename()
					.substring(mulfile.getOriginalFilename().lastIndexOf(".") + 1);
			// 新的图片文件名 = 获取时间戳+"."图片扩展名
			newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;

			imgurl = systemSetServiceImpl.uploadImg(newFileName, mulfile);
		}
		return imgurl;
	}

	/**
	 * <p>
	 * 方法名称：templateUpload
	 * </p>
	 * <p>
	 * 方法描述：简报图片上传
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @author liuyy
	 * @since 2016年10月19日
	 *        <p>
	 *        history 2016年10月19日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "templateUpload", method = RequestMethod.POST)
	public String templateUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//JSONArray re = new JSONArray();
		String fileName = "";
		String newFileName = "";
		String imgurl = "";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile mulfile = multipartRequest.getFile(key);
			fileName = mulfile.getOriginalFilename();
			// 获取图片的扩展名
			String extensionName = mulfile.getOriginalFilename()
					.substring(mulfile.getOriginalFilename().lastIndexOf(".") + 1);
			// 新的图片文件名 = 获取时间戳+"."图片扩展名
			newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;

			imgurl = systemSetServiceImpl.uploadImg(newFileName, mulfile);
		}
		return imgurl;
	}

	// 文件名称处理
	private String handlerFileName(String fileName) {
		// 处理名称start
		fileName = (new Date()).getTime() + "_" + fileName;
		// 时间戳+文件名，防止覆盖重名文件
		String pre = StringUtils.substringBeforeLast(fileName, ".");
		String end = StringUtils.substringAfterLast(fileName, ".");
		// fileName = Digests.encodeByMd5(pre)+"."+end;//用MD5编码文件名，解析附件名称
		// 处理名称end
		return fileName;
	}

	// 预览，获取图片流
	@RequestMapping(value = "profile/readImage", produces = "text/plain;charset=UTF-8")
	public void readImage(HttpServletRequest request, HttpServletResponse response) {
		String imagePath = request.getSession().getServletContext().getRealPath("/")
				+ request.getParameter("imagePath");
		try {
			File file = new File(imagePath);
			if (file.exists()) {
				DataOutputStream temps = new DataOutputStream(response.getOutputStream());
				DataInputStream in = new DataInputStream(new FileInputStream(imagePath));
				byte[] b = new byte[2048];
				while ((in.read(b)) != -1) {
					temps.write(b);
					temps.flush();
				}
				in.close();
				temps.close();
			}
		} catch (Exception e) {
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
	}

	@RequestMapping(value = "selectAllSubjectByClassifyid", method = RequestMethod.GET)
	public Response selectAllSubjectByClassifyid(Subject record) {
		record.setIsdelete(false);
		List<Subject> list = systemSetServiceImpl.selectSubjectByClassifyid(record);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/getImg", method = RequestMethod.GET)
	public void getImg(String imgurl, HttpServletRequest request, HttpServletResponse response) {
		try {
			OutputStream os = response.getOutputStream();
			if (imgurl != null && !"".equals(imgurl)) {
				Configuration conf = new Configuration();
				conf.set("fs.defaultFS", "hdfs://60.205.106.32:9000");
				HdfsUpLoadUtil.readFile(imgurl, os, conf);
				os.close();
			}
		} catch (IOException e) { 
			Log.error(e.getMessage(),e);
		}

	}
	/**
	 * 
	 * <p>方法名称：selectParentUserPower</p>
	 * <p>方法描述：查询父级权限</p>
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2017年1月13日
	 * <p> history 2017年1月13日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectParentUserPower", method = RequestMethod.GET)
    public Response selectParentUserPower(HttpServletRequest request){
    	String userid = (String) request.getSession().getAttribute("userid");
    	UserBo userBo =  new UserBo();
    	userBo.setId(userid);
    	userBo = powerServiceImpl.selectPowerByUserId(userBo);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	if(null!=userBo){
    		userBo.setExpirydate(format.format(userBo.getExpir()));
    	}
    	if(userBo!=null){
    	   return new Response(ResponseStatus.Success,userBo,true);
    	}else{
    		return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
    	}
    }
	//
	@RequestMapping(value = "/selectLoginUserInfo", method = RequestMethod.GET)
	public Response selectLoginUserInfo(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		User loginuser = systemSetServiceImpl.selectLoginUser(userid);
		Integer monitor = 0;
		Integer subject = 0;
		Integer person = 0;
		Integer warn = 0;
		Integer keyword = 0;

		WordSet wse = new WordSet();
		wse.setUserid(userid);
		List<WordSet> list = userServiceImpl.selectPowerByUserId(wse);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				warn = list.get(i).getCansetword();

			}
			if (list.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
				person = list.get(i).getCansetword();

			}
			if (list.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
				monitor = list.get(i).getCansetword();

			}
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				subject = list.get(i).getCansetword();

			}
			//if (list.get(i).getName().equals(AppConstant.standPower.)) {
				subject = list.get(i).getCansetword();

			//}
				

		}
     if(null!=loginuser){
    	 loginuser.setMonitortimes(monitor);
 		loginuser.setSubjecttimes(subject);
 		loginuser.setPersontimes(person);
 		loginuser.setWarntimes(warn); 
       }
		UserBo ub = new UserBo();
		if (loginuser != null) {
			BeanUtils.copyProperties(loginuser, ub);
			if (loginuser.getExpirydate() != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String da = format.format(loginuser.getExpirydate());
				ub.setExpirydate(da);
			}
		}
		if (ub != null) {
			return new Response(ResponseStatus.Success, ub, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	@RequestMapping(value = "/selectSourceById", method = RequestMethod.GET)
	public Response selectSourceById(String id, String type) {
		Datasource ds = systemSetServiceImpl.selectSourceById(id, type);
		if (ds != null) {
			return new Response(ResponseStatus.Success, ds, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/selectAllPower", method = RequestMethod.GET)
	public Response selectAllPower(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		List<WordSet> list = userServiceImpl.selectAllWordSet(userid);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：outlogin
	 * </p>
	 * <p>
	 * 方法描述：退出登录
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
	@RequestMapping(value = "/outlogin", method = RequestMethod.GET)
	public Response outlogin(HttpServletRequest request) {
		String userid = (String)request.getSession().getAttribute("userid");
		String key = userid;
		 
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		//修改缓存
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
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		   }
			if(flag){
				for(int i=0;i<listbo.size();i++){
					if(session.getId().equals(listbo.get(i).getId())){
						listbo.remove(i);
					}
				}
				//修改redis
				String time = shardedJedis.ttl(key).toString();
				 if(shardedJedis.del(key).equals(1)){//删除redis
						//放到redis
					 try {
						 shardedJedis.hset(key, "getloginuser",mapper.writeValueAsString(listbo));
		           			shardedJedis.expire(key, Integer.parseInt(time));
					} catch (Exception e) {
						// TODO: handle exception
					}
						
			  }
			}
		session.removeAttribute("user");
		session.removeAttribute("userid");
		session.removeAttribute("usertype");
		session.removeAttribute("name");
		session.removeAttribute("formatslist");
		session.removeAttribute("setTrade");
		session.removeAttribute("managerid");
		session.removeAttribute("orgname");
	    session.removeAttribute("ub");
		return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);

	}

	/**
	 * <p>
	 * 方法名称：insertagentuser
	 * </p>
	 * <p>
	 * 方法描述：保存代理用户信息
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/insertagentuser", method = RequestMethod.POST)
	public Response insertagentuser(UserBo record, HttpServletRequest request) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		User pm = new User();
		String userid = (String) request.getSession().getAttribute("userid");

		record.setParentid(userid);
		record.setId(UuidUtil.getUUID());
		record.setUsertype("agent");
		BeanUtils.copyProperties(record, pm);
		if (record.getEndtime() != null) {

			try {
				Date endtime = formatter.parse(record.getEndtime());
				pm.setEndtime(endtime);
			} catch (ParseException e) {

				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				Log.error(e.getMessage(),e);
			}

		} else {
			pm.setEndtime(null);
		}
		if (record.getBirthday() != null) {

			try {
				Date endtime1 = formatter.parse(record.getBirthday());
				pm.setBirthday(endtime1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block

				System.out.println(e.getMessage());
				Log.error(e.getMessage(),e);
			}

		} else {
			pm.setBirthday(null);
		}
		System.out.println(record.getBirthday() + "fff" + record.getEndtime());

		System.out.println(pm.getBirthday());
		int num = 0;
		// 校验权限
		boolean fag = false;
		WordSet wordset = new WordSet();
		wordset.setUserid(userid);
		wordset.setName(AppConstant.standPower.AGENTNAME);
		WordSet ws = userServiceImpl.selectPowerByName(wordset);
		if (ws.getCansetword() > 0) {
			fag = true;
			num = userServiceImpl.insertagentuser(pm);
		}

		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, "", false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updateagentuser
	 * </p>
	 * <p>
	 * 方法描述：修改代理用户
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
	@RequestMapping(value = "/updateagentuser", method = RequestMethod.GET)
	public Response updateagentuser(User record) {
		int num = userServiceImpl.updateagentuser(record);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllagent
	 * </p>
	 * <p>
	 * 方法描述：查询代理用户
	 * </p>
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllagent", method = RequestMethod.GET)
	public Response selectAllagent(UserBo user, HttpServletRequest request, PageInfo page) {
		String userid = (String) request.getSession().getAttribute("userid");
		String usertype = (String) request.getSession().getAttribute("usertype");
		// user.setUsertype(usertype);
		user.setUsertype("agent");
		user.setParentid(userid);

		PageHelper.startPage(page.getPageNum(), page.getPageSize());

		List<User> list = userServiceImpl.selectAllagent(user);
		PageInfo<User> info = new PageInfo<User>(list);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/selectagentById", method = RequestMethod.GET)
	public Response selectagentById(User user) {
		User us = new User();
		if(null!=user){
			us = userServiceImpl.selectagentById(user.getId());
		}
		if (user != null) {
			return new Response(ResponseStatus.Success, us, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/selectReByUserId", method = RequestMethod.GET)
	public Response selectReByUserId(UserBo record){
		List<Relevant> list = systemSetServiceImpl.selectReByUserId(record);
		List<Subject> list1 = systemSetServiceImpl.selectSubjectByUserId(record);
		List<Warnconfig> list2 = systemSetServiceImpl.selectWarnByUserId(record);
		List<PersonManage> list3 = systemSetServiceImpl.selectPersonByUserId(record);
		if(list.size()>0 || list1.size()>0 || list2.size()>0 || list3.size()>0){
			return new Response(ResponseStatus.Success,AppConstant.responseInfo.SAVESUCCESS,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "selectWeiXinUser", method = RequestMethod.GET)
	public Response selectWeiXinUser(String name){
		 AccessToken at = new AccessToken();
		String accesstoken = at.getAccessToken();
		WeixinUser weixin = new WeixinUser();
		List<String> list = weixin.allWeiXinUser(accesstoken);
		List<Map<String,String>> listmap = weixin.weiXinUser(accesstoken);
		Boolean tag = false;
		String openid="";
		for(int i=0;i<listmap.size();i++){
			if(null!=listmap.get(i).get(name)){
				openid = listmap.get(i).get(name);
				tag = true;
			}
		}
		if(tag){
			return new Response(ResponseStatus.Success,openid,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	public Response exportData(){
		return null;
	}
	/**
	 * 
	 * <p>方法名称：selectSearchWeixin</p>
	 * <p>方法描述：微信分页搜索</p>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuyy
	 * @since  2017年6月20日
	 * <p> history 2017年6月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "selectSearchWeixin", method = RequestMethod.GET)
	public Response selectSearchWeixin(Integer pageNum,Integer pageSize,String name,HttpServletRequest request){
		Sweixinbo weixin = new Sweixinbo();
		weixin.setStart((pageNum-1)*pageSize);
		weixin.setSize(pageSize);
		weixin.setNick(name);
		String userid = (String) request.getSession().getAttribute("userid");
		List<Sweixinbo> list = systemSetServiceImpl.selectSearchWeixin(weixin);
		SolrPage<Sweixinbo> info = new SolrPage<Sweixinbo>();
		Long total=(long) 0;
		Datasource ds = new Datasource();
		List<String> sids = new ArrayList<String>();
		if(list.size()>0){
			total = list.get(0).getTotal();
			for(int i=0;i<list.size();i++){
				sids.add(list.get(i).getId());
			}
			ds.setSearchids(sids);
			ds.setUserid(userid);
			List<Datasource> listdata = systemSetServiceImpl.selectDataSourceBySearchid(ds);
			for(int i=0;i<list.size();i++){
				for(int j=0;j<listdata.size();j++){
					if(list.get(i).getId().equals(listdata.get(j).getSearchid())){
						list.get(i).setSearchid(listdata.get(j).getSearchid());
					}
				}
			}
		}
		if(total>200){
			info.setTotal(200);
		}else{
		    info.setTotal(total);
		}
		info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		info.setNavigatePages(8);
		info.setDatas(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	/**
	 * 
	 * <p>方法名称：selectSearchWeibo</p>
	 * <p>方法描述：微博搜索</p>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuyy
	 * @since  2017年6月20日
	 * <p> history 2017年6月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "selectSearchWeibo", method = RequestMethod.GET)
	public Response selectSearchWeibo(Integer pageNum,Integer pageSize,String name,HttpServletRequest request){
		Sweibobo weibo = new Sweibobo();
		weibo.setStart((pageNum-1)*pageSize);
		weibo.setSize(pageSize);
		weibo.setName(name);
		String userid = (String) request.getSession().getAttribute("userid");
		List<Sweibobo> list = systemSetServiceImpl.selectSearchWeibo(weibo);
		SolrPage<Sweibobo> info = new SolrPage<Sweibobo>();
		Long total=(long) 0;
		Datasource ds = new Datasource();
		List<String> sids = new ArrayList<String>();
		if(list.size()>0){
			total = list.get(0).getTotal();
			System.out.println(total);
			for(int i=0;i<list.size();i++){
				sids.add(list.get(i).getId());
			}
			ds.setSearchids(sids);
			ds.setUserid(userid);
			List<Datasource> listdata = systemSetServiceImpl.selectDataSourceBySearchid(ds);
			for(int i=0;i<list.size();i++){
				for(int j=0;j<listdata.size();j++){
					if(list.get(i).getId().equals(listdata.get(j).getSearchid())){
						list.get(i).setSearchid(listdata.get(j).getSearchid());
					}
				}
			}
			
		} 
		if(total>200){
			 info.setTotal(200);
		}else{
		    info.setTotal(total);
		}
		info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		info.setNavigatePages(8);
		info.setDatas(list);
		
		/*Sweibobo sb = systemSetServiceImpl.selectSearchWeiboTotal(weibo);
		SolrPage<Sweibobo> info = new SolrPage<Sweibobo>();*/
	
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	
	/**
	 * 
	 * <p>方法名称：selectSearchWeibo</p>
	 * <p>方法描述：贴吧搜索</p>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuyy
	 * @since  2017年6月20日
	 * <p> history 2017年6月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "selectSearchTieba", method = RequestMethod.GET)
	public Response selectSearchTieba(Integer pageNum,Integer pageSize,String name,HttpServletRequest request){
		Stiebabo tieba = new Stiebabo();
		tieba.setStart((pageNum-1)*pageSize);
		tieba.setSize(pageSize);
		tieba.setName(name);
		String userid = (String) request.getSession().getAttribute("userid");
	//	PageHelper.startPage(pageNum, pageSize);
		List<Stiebabo> list = systemSetServiceImpl.selectSearchTieba(tieba);
		SolrPage<Stiebabo> info = new SolrPage<Stiebabo>();
		Long total=(long) 0;
		Datasource ds = new Datasource();
		List<String> sids = new ArrayList<String>();
		if(list.size()>0){
			total = list.get(0).getTotal();
			for(int i=0;i<list.size();i++){
				sids.add(list.get(i).getId());
			}
			ds.setSearchids(sids);
			ds.setUserid(userid);
			List<Datasource> listdata = systemSetServiceImpl.selectDataSourceBySearchid(ds);
			for(int i=0;i<list.size();i++){
				for(int j=0;j<listdata.size();j++){
					if(list.get(i).getId().equals(listdata.get(j).getSearchid())){
						list.get(i).setSearchid(listdata.get(j).getSearchid());
					}
				}
			}
		} 
		if(total>200){
			  info.setTotal(200);
			
		}else{
		    info.setTotal(total);
		}
		info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		info.setNavigatePages(8);
		info.setDatas(list);
		/*PageInfo<Stiebabo> info = new PageInfo<Stiebabo>(list);*/
		/*Stiebabo sb = systemSetServiceImpl.selectSearchTiebaTotal(tieba);
		
		SolrPage<Stiebabo> info = new SolrPage<Stiebabo>();
		if(null!=info){
			info.setTotal(sb.getTotal());
		}else{
			info.setTotal(0);
		}
		info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		info.setNavigatePages(15);
		info.setDatas(list);*/
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectSearchWeibo</p>
	 * <p>方法描述：网站搜索</p>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author liuyy
	 * @since  2017年6月20日
	 * <p> history 2017年6月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "selectSearchsite", method = RequestMethod.GET)
	public Response selectSearchsite(Integer pageNum,Integer pageSize,String name){
		
		Swebsitebo site = new Swebsitebo();
		site.setStart((pageNum-1)*pageSize);
		site.setSize(pageSize);
		site.setName(name);
		List<Swebsitebo> list = systemSetServiceImpl.selectSearchWebsite(site);
		//PageInfo<Swebsitebo> info = new PageInfo<Swebsitebo>(list);
		List<Swebsitebo> sb = systemSetServiceImpl.selectSearchWebsiteTotal(site);
		SolrPage<Swebsitebo> info = new SolrPage<Swebsitebo>();
		
			info.setTotal(sb.size());
	   info.setPageNum(pageNum);
		info.setPageSize(pageSize);
		info.setNavigatePages(8);
		info.setDatas(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "addDataSource", method = RequestMethod.POST)
	public Response addDataSource(Datasource record,HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		record.setId(UuidUtil.getUUID());
		record.setAttentiontime(new Date());
		WordSet ww = new WordSet();
		ww.setUserid(userid);
		ww.setName(AppConstant.standPower.ATTENTIONNUM);
		WordSet ws = powerServiceImpl.selectPowerByName(ww);
		if(null!=ws){
			if(null == ws.getCansetword()){
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.ATTENTIONMAXNUM,false);
			}else{
				if(null == ws.getSetword()){
					ws.setSetword(0);
				}
				if(ws.getSetword() >=ws.getCansetword()){
					return new Response(ResponseStatus.Error,AppConstant.responseInfo.ATTENTIONMAXNUM,false);
				}
			}
			
			ws.setSetword(ws.getSetword()+1);
			powerServiceImpl.updateWordSet(ws);
		}
		
			int num = systemSetServiceImpl.addDatasource(record);
			if(num > 0){
				//修改权限
				return new Response(ResponseStatus.Success,num,true);
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
			}
	}
	@RequestMapping(value = "batchaddDatasource", method = RequestMethod.POST)
	public Response batchaddDatasource(HttpServletRequest request,String info){
		String userid = (String) request.getSession().getAttribute("userid");
		  List<Datasource> list = new ArrayList<Datasource>(); 
		  list = JSONArray.parseArray(info, Datasource.class);//这里的t是Class<T> 
		WordSet ww = new WordSet();
		ww.setUserid(userid);
		ww.setName(AppConstant.standPower.ATTENTIONNUM);
		WordSet ws = powerServiceImpl.selectPowerByName(ww);
		if(null!=ws){
			if(ws.getSetword() >=ws.getCansetword()){
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.ATTENTIONMAXNUM,false);
			}
			  if((ws.getSetword()+list.size())>=ws.getCansetword()){
				  return new Response(ResponseStatus.Error,AppConstant.responseInfo.ATTENTIONCHAONUM,false);
			  }
			ws.setSetword(ws.getSetword()+list.size());
			powerServiceImpl.updateWordSet(ws);
		}
		
		  for(int i=0;i<list.size();i++){
			  list.get(i).setId(UuidUtil.getUUID());
			  list.get(i).setAttentiontime(new Date());
			  list.get(i).setUserid(userid);
		  }
		  Datasource sd = new Datasource();
		  sd.setList(list);
		  int num = systemSetServiceImpl.batchInsertSource(sd);
		  
		  if(num > 0){
				
			  return new Response(ResponseStatus.Success,num,true);
		  }else{
			  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false); 
		  }
	}
	/**
	 * 
	 * <p>方法名称：selectKeywordNum</p>
	 * <p>方法描述：专题监测剩余关键词个数</p>
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2017年8月2日
	 * <p> history 2017年8月2日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "selectKeywordNum", method = RequestMethod.GET)
	public Response selectKeywordNum(HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		String name = AppConstant.standPower.WORDNAME;
		Wordset ws = systemSetServiceImpl.selectKeyWordByUserId(userid, name);
	    int num = 0;
		if(ws!=null){
			if(null!=ws.getCansetword()){
				if(null!=ws.getSetword()){
					num = ws.getCansetword()*1-ws.getSetword();
				}else{
					num = ws.getCansetword();
				}
			}else{
				num = 0;
			}
		}
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,num,false);
	  }
	}
	@RequestMapping(value = "selectReplyPower", method = RequestMethod.GET)
	public Response selectReplyPower(HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		UserBo use = new UserBo();
		use.setId(userid);
		UserBo userbo = systemSetServiceImpl.selectRelyPower(use);
		if(userbo!=null){
			return new Response(ResponseStatus.Success,userbo,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
}