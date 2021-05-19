package com.bayside.app.opinion.war.opinionMonitor.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.junit.Test;
import org.mortbay.log.Log;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.index.service.MonitorallService;
import com.bayside.app.opinion.war.mynews.bo.PersonStatisticalBo;
import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.opinionMonitor.bo.DayCountBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.Common;
import com.bayside.app.opinion.war.opinionMonitor.model.DayCount;
import com.bayside.app.opinion.war.opinionMonitor.model.FocusCount;
import com.bayside.app.opinion.war.opinionMonitor.model.HotOpinion;
import com.bayside.app.opinion.war.opinionMonitor.model.HotWords;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.Zfwb;
import com.bayside.app.opinion.war.opinionMonitor.service.CommonService;
import com.bayside.app.opinion.war.opinionMonitor.service.DayCountService;
import com.bayside.app.opinion.war.opinionMonitor.service.FocusCountService;
import com.bayside.app.opinion.war.opinionMonitor.service.HotOpinionService;
import com.bayside.app.opinion.war.opinionMonitor.service.HotWordsService;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectMArticleService;
import com.bayside.app.opinion.war.subject.bo.SubjectClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
import com.bayside.app.opinion.war.systemmessage.service.SystemmessageService;
import com.bayside.app.opinion.war.systemset.model.Emailconfig;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.CustomXWPFDocument;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.crawler.metasearch.model.MetasearchModel;
import com.bayside.util.CommonUtil;
import com.bayside.util.PageModel;
import com.bayside.util.SendCode;
import com.bayside.util.SimpleDate;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.sun.mail.util.MailSSLSocketFactory;

import redis.clients.jedis.ShardedJedis;
import sun.misc.BASE64Decoder;

/**
 * <p>Title: DayCountController</P>
 * <p>Description: </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Administrator
 * @version 1.0
 * @since 2016年9月19日
 */
/**
 * <p>
 * Title: DayCountController
 * </P>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016
 * </p>
 * 
 * @author Administrator
 * @version 1.0
 * @since 2016年9月19日
 */
@RestController
@EnableAutoConfiguration
@PropertySource("classpath:server.properties")
public class DayCountController {
	private static final Logger Log = Logger.getLogger(DayCountController.class);
	@Autowired
	private DayCountService dayCountServiceImpl;
	@Autowired
	private HotOpinionService hotOpinionServiceImpl;
	@Autowired
	private HotWordsService hotWordsServiceImpl;
	@Autowired
	private FocusCountService focusCountServiceImpl;
	@Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	@Autowired
	private SubjectMArticleService subjectMArticleServiceImpl;
	@Autowired
	private CommonService commonServiceImpl;
	@Autowired
	private SystemSetService systemSetServiceImpl;
	@Autowired
	private MonitorallService monitorallServiceImpl;

	@Autowired
	private SystemmessageService systemmessageServiceImpl;
	@Resource
	private Environment environment;

	/**
	 * 
	 * <p>
	 * 方法名称：deleteBymid
	 * </p>
	 * <p>
	 * 方法描述：内容详情页删除文章
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月9日
	 *        <p>
	 *        history 2016年11月9日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteBymid", method = RequestMethod.GET)
	public Response deleteBymid(SubjectMArticle record) {
		if(record!=null){
			int num = subjectMArticleServiceImpl.deleteByPrimaryKey(record.getId());
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
		
	}

	/**
	 * <p>
	 * 方法名称：selectClassifyByUserId
	 * </p>
	 * <p>
	 * 方法描述：通过userid 查询专题分类
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
	@RequestMapping(value = "/selectClassifyByUserId", method = RequestMethod.GET)
	public Response selectClassifyByUserId(HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("userid");
		if(userid==null||"".equals(userid)){
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		User user = (User) request.getSession().getAttribute("user");
		String userparentid ="";
		if(user!=null){
			userparentid = user.getParentid();
		}
		if(userparentid==null||"".equals(userparentid)||userparentid.equals(userid)){
			userparentid = userid;
			userid=null;
		}else{
			userparentid =null;
		}
		List<SubjectClassify> list = systemSetServiceImpl.getCateogy(userid,userparentid);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);

		}
		
	}
	@RequestMapping(value = "/selectAllSubjectByClassify", method = RequestMethod.GET)
	public Response selectAllSubjectByClassify(String classifyid,HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("userid");
		List<Subject> list = systemSetServiceImpl.selectById(userid,classifyid);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);

		}
	}
	@RequestMapping(value = "/getMediaType", method = RequestMethod.GET)
	public Response getMediaType() throws Exception {
		List<Common> mediaType = commonServiceImpl.getbyType("mediaType");
		return new Response(ResponseStatus.Success, mediaType, true);
	}

	@RequestMapping(value = "/getWeidu", method = RequestMethod.GET)
	public Response getWeidu() throws Exception {
		List<Common> weidu = commonServiceImpl.getbyType("weidu");
		return new Response(ResponseStatus.Success, weidu, true);
	}

	/** -+
	 * <p>
	 * 方法名称：getDayCount
	 * </p>
	 * <p>
	 * 方法描述：获取今日舆情统计列表
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月4日
	 *        <p>
	 *        history 2016年7月4日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getDayCount", method = RequestMethod.GET)
	public Response getDayCount() throws Exception {
		List<DayCount> dayCount = dayCountServiceImpl.getDaySum("2016-07-01");
		List<DayCountBo> list = new ArrayList<DayCountBo>();
		if (dayCount != null && dayCount.size() > 0) {
			for (DayCount count : dayCount) {
				DayCountBo dayCountbo = new DayCountBo();
				BeanUtils.copyProperties(count, dayCountbo);
				list.add(dayCountbo);
			}
		}
		return new Response(ResponseStatus.Success, list, true);
	}

	/**
	 * <p>
	 * 方法名称：getEmotionCount
	 * </p>
	 * <p>
	 * 方法描述：获取漏斗图的数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getEmotionCount", method = RequestMethod.GET)
	public Response getEmotionCount() throws Exception {
		DayCount dayc = dayCountServiceImpl.getEmotionSum();
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getDayHot
	 * </p>
	 * <p>
	 * 方法描述：获取今日热点数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getDayHot", method = RequestMethod.GET)
	public Response getDayHot() throws Exception {
		List<HotOpinion> dayc = hotOpinionServiceImpl.getDayHot("2016-07-06");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getSunHot
	 * </p>
	 * <p>
	 * 方法描述：回去近七日热点数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getSunHot", method = RequestMethod.GET)
	public Response getSunHot() throws Exception {
		List<HotOpinion> dayc = hotOpinionServiceImpl.getSunHot("2016-07-06");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getMonthHot
	 * </p>
	 * <p>
	 * 方法描述：回去近30天热点数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getMonthHot", method = RequestMethod.GET)
	public Response getMonthHot() throws Exception {
		List<HotOpinion> dayc = hotOpinionServiceImpl.getMonthHot("2016-07-06");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getHotWords
	 * </p>
	 * <p>
	 * 方法描述：热点词云图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getHotWords", method = RequestMethod.GET)
	public Response getHotWords() throws Exception {
		List<HotWords> dayc = hotWordsServiceImpl.getAll();
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getFocusCount
	 * </p>
	 * <p>
	 * 方法描述：重点关注的统计
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getFocusCount", method = RequestMethod.GET)
	public Response getFocusCount() throws Exception {
		List<FocusCount> dayc = focusCountServiceImpl.getFocusCount("2016-07-04");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getPieData
	 * </p>
	 * <p>
	 * 方法描述：重点关注微信饼图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getPieWeiXin", method = RequestMethod.GET)
	public Response getPieData() throws Exception {
		FocusCount dayc = focusCountServiceImpl.getPieDate("2016-07-04", "微信");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getPieTieba
	 * </p>
	 * <p>
	 * 方法描述：重点关注贴吧饼图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getPieTieba", method = RequestMethod.GET)
	public Response getPieTieba() throws Exception {
		FocusCount dayc = focusCountServiceImpl.getPieDate("2016-07-04", "贴吧");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getPieWeibo
	 * </p>
	 * <p>
	 * 方法描述：重点关注微博饼图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getPieWeibo", method = RequestMethod.GET)
	public Response getPieWeibo() throws Exception {
		FocusCount dayc = focusCountServiceImpl.getPieDate("2016-07-04", "微博");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getPieNews
	 * </p>
	 * <p>
	 * 方法描述：重点关注新闻饼图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getPieNews", method = RequestMethod.GET)
	public Response getPieNews() throws Exception {
		FocusCount dayc = focusCountServiceImpl.getPieDate("2016-07-04", "新闻");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getPieArticle
	 * </p>
	 * <p>
	 * 方法描述：重点关注论坛饼图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getPieArticle", method = RequestMethod.GET)
	public Response getPieArticle() throws Exception {
		FocusCount dayc = focusCountServiceImpl.getPieDate("2016-07-04", "论坛");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getPieVideo
	 * </p>
	 * <p>
	 * 方法描述：重点关注视频饼图
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月11日
	 *        <p>
	 *        history 2016年7月11日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getPieVideo", method = RequestMethod.GET)
	public Response getPieVideo() throws Exception {
		FocusCount dayc = focusCountServiceImpl.getPieDate("2016-07-04", "视频");
		return new Response(ResponseStatus.Success, dayc, true);
	}

	/**
	 * <p>
	 * 方法名称：getOpinionStage
	 * </p>
	 * <p>
	 * 方法描述：根据登录用户获取所有舆情信息
	 * </p>
	 * 
	 * @param page
	 * @param stage
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月12日
	 *        <p>
	 *        history 2016年7月12日 Administrator 创建
	 *        <p>
	 */
	/*
	 * @RequestMapping(value = "/getOpinionStage", method = RequestMethod.GET)
	 * public Response getOpinionStage(PageInfo page,String userId) throws
	 * Exception { //分步获取数据 //userId="0100"; List<String> articles =
	 * subjectMArticleServiceImpl.getArticleIdByid(userId, null);
	 * PageHelper.startPage(page.getPageNum(), page.getPageSize());
	 * List<SubjectArticle> list =
	 * subjectArticleServiceImpl.getAllOPinion(articles);
	 * PageInfo<SubjectArticle> info = new PageInfo<SubjectArticle>(list);
	 * return new Response(ResponseStatus.Success, info, true); }
	 */
	@RequestMapping(value = "/getOpinionStage", method = RequestMethod.GET)
	public Response getOpinionStage(SubJectArticleBo page, HttpServletRequest request, PageInfo pagen) { // 联合查询获取数据
		String userid = (String) request.getSession().getAttribute("userid");
		page.setUserid(userid);
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(null == page.getIstrade()){
			if(trade!=1){
				List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
				page.setMedialist(formatslist);
			}	
		}else if(1 == page.getIstrade()){
			//如果开通 交易类型 查询 交易类型 
			if(trade == 1){
				if(null == page.getFormats() || "".equals(page.getFormats())){
					page.setFormats("trade");
					page.setDependency(Double.parseDouble("2"));
				}else if("trade".equals(page.getFormats())){
					
				}else{
					page.setDependency(Double.parseDouble("2"));
				}
				page.setTrade(trade);
			}else{
				//查询相关性小于2
				page.setDependency(Double.parseDouble("2"));
				List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
				page.setMedialist(formatslist);
			}
		}else{
			List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
			page.setMedialist(formatslist);
	
		}
		long temp = System.currentTimeMillis();
		page.setNoquery(0);
		page.setStart((pagen.getPageNum()-1)*pagen.getPageSize());
		page.setSize(pagen.getPageSize());
		if (page.getStartTime() != null) {
			if (page.getStartTime().equals(AppConstant.timetype.CURRENT)) {
				try {
					page.setStartTime(SimpleDate.formatDate(new Date()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			} else if (page.getStartTime().equals(AppConstant.timetype.SUN)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(c.getTime());
					page.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
				
			} else if (page.getStartTime().equals(AppConstant.timetype.MONTH)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -30);
				try {
					String  str = SimpleDate.formatDate(c.getTime());
					page.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			
			} else if (page.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
				page.setStartTime(page.getSttime()+" 00:00:00");
				page.setEdtime(page.getEndTime()+" 23:59:59");
			}
		}
	
		List<SubJectArticleBo> list = subjectArticleServiceImpl.selectAllByUserId(page);
		//SubJectArticleBo  sab = subjectArticleServiceImpl.selectAllByPage(page);
		 //加入缓存
	      String key=page.getIsrepeat()+trade+userid+""+page.getEmotion()+page.getStartTime()+page.getSubjectid()+page.getWeidu()+page.getFormats()+page.getEndTime()+page.getSttime()+page.getEdtime()+page.getDependency()+page.getIstrade()+page.getWarning()+page.getPubtime()+page.getUptime()+page.getSimnumorder();
	     // String key = userid+page.getStartTime()+page.getEdtime()+trade+page.getEmotion()+page.getFormats()+page.getWeidu()+page.getDependency()+page.getIstrade();
	      ObjectMapper mapper = new ObjectMapper();
			 ShardedJedis shardedJedis = null;
			if (shardedJedis == null) {
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
			}
			boolean flag = false;
			SubJectArticleBo  sab = null;
			try {
				String sttr = shardedJedis.hget(key, "getinitopinion");
				if(sttr!=null&&!"".equals(sttr)){
					sab = mapper.readValue(sttr, SubJectArticleBo.class);
					flag = true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			if(!flag){
				try {
					sab = subjectArticleServiceImpl.selectAllByPage(page);
					//放到redis
					shardedJedis.hset(key, "getinitopinion",mapper.writeValueAsString(sab));
					shardedJedis.expire(key, 60);
				} catch (Exception e) {
					// TODO: handle exception
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			}
        SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
		if(null!=sab){
			info.setTotal(sab.getTotal());
		}else{
			info.setTotal(0);
		}
		info.setPageNum(pagen.getPageNum());
		info.setPageSize(pagen.getPageSize());
		info.setNavigatePages(8);
		info.setDatas(list);
		
		if (null!=info) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
		
	}
	///
	/**
	 * 
	 * <p>方法名称：delOneArticle</p>
	 * <p>方法描述：删除当前文章</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2017年7月17日
	 * <p> history 2017年7月17日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/delOneArticle", method = RequestMethod.POST)
	 public Response delOneArticle(SubJectArticleBo stage, HttpServletRequest request, SubjectMArticle obj){
		   SubjectMArticle sm = new SubjectMArticle();
		   if(null!=stage){
			   sm.setId(stage.getMid());
			   List<String> mids = new ArrayList<String>();
			   int num = monitorallServiceImpl.deleteIndexArticle(sm);
			   //List<String> mids = new ArrayList<String>();
				String simids = stage.getSimids();
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
					mids.add(stage.getMid());
				}
		      // 修改redis 缓存数量
			   if(num > 0){
				   String userid = stage.getUserid();
				 //修改缓存数据
					Integer trade = (Integer)request.getSession().getAttribute("setTrade");
					if(null == stage.getIstrade()){
						if(trade!=1){
							List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
							stage.setMedialist(formatslist);
						}	
					}else if(1 == stage.getIstrade()){
						//如果开通 交易类型 查询 交易类型 
						if(trade == 1){
							if(null == stage.getFormats() || "".equals(stage.getFormats())){
								stage.setFormats("trade");
								stage.setDependency(Double.parseDouble("2"));
							}else if("trade".equals(stage.getFormats())){
								
							}else{
								stage.setDependency(Double.parseDouble("2"));
							}
							stage.setTrade(trade);
							
						}else{
							//查询相关性小于2
							stage.setDependency(Double.parseDouble("2"));
							List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
							stage.setMedialist(formatslist);
						}
					}else{
						//if(trade!=1){
							List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
							stage.setMedialist(formatslist);
						//}	
					}
				      ObjectMapper mapper = new ObjectMapper();
								 ShardedJedis shardedJedis = null;
								if (shardedJedis == null) {
									shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
											Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
								}
					if(obj.getFiltertag().equals(1)){
						if (stage.getStartTime() != null) {
							if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
								try {
									stage.setStartTime(SimpleDate.formatDate(new Date()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}
							} else if (stage.getStartTime().equals(AppConstant.timetype.SUN)) {
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, -7);
								
								try {
									String str = SimpleDate.formatDate(c.getTime());
									stage.setStartTime(str);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}
								
							} else if (stage.getStartTime().equals(AppConstant.timetype.MONTH)) {
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, -30);
								try {
									String  str = SimpleDate.formatDate(c.getTime());
									stage.setStartTime(str);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}
							
							} else if (stage.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
								stage.setStartTime(stage.getSttime()+" 00:00:00");
								stage.setEdtime(stage.getEdtime()+" 23:59:59");
							}
						}
					      String key=stage.getIsrepeat()+trade+userid+""+stage.getEmotion()+stage.getStartTime()+stage.getSubjectid()+stage.getWeidu()+stage.getFormats()+stage.getEndTime()+stage.getSttime()+stage.getEdtime()+stage.getDependency()+stage.getIstrade()+stage.getWarning()+stage.getPubtime()+stage.getUptime()+stage.getSimnumorder();
						
				
						  
					
							if(shardedJedis.exists(key)){
							       String time = shardedJedis.ttl(key).toString();
							       if(!time.equals(-1)){
							    	   //redis 存在 删除redis 重新添加redis  getinitopinion  getsearchopinion
							    	   //获取redis 数据
										SubJectArticleBo  sab = null;
										try {
											String sttr = shardedJedis.hget(key, "getinitopinion");
											if(sttr!=null&&!"".equals(sttr)){
												sab = mapper.readValue(sttr, SubJectArticleBo.class);
												sab.setTotal(sab.getTotal()*1-num*1);
												 if(shardedJedis.del(key).equals(1)){//删除redis
													//放到redis
														shardedJedis.hset(key, "getinitopinion",mapper.writeValueAsString(sab));
														shardedJedis.expire(key, Integer.parseInt(time));
										    	   }
												
											}
										} catch (Exception e) {
											// TODO: handle exception
											Log.info(e.getMessage());
											Log.error(e.getMessage(),e);
										}
							       }
							}
					}else{
					
						
						
						if (stage.getStartTime() != null) {
							if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
								try {
									stage.setStartTime(SimpleDate.formatDate(new Date()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}
							} else if (stage.getStartTime().equals(AppConstant.timetype.SUN)) {
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, -7);
								
								try {
									String str = SimpleDate.formatDate(c.getTime());
									stage.setStartTime(str);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}
								
							} else if (stage.getStartTime().equals(AppConstant.timetype.MONTH)) {
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, -30);
								try {
									String  str = SimpleDate.formatDate(c.getTime());
									stage.setStartTime(str);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}
							
							} else if (stage.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
								stage.setStartTime(stage.getSttime()+" 00:00:00");
								stage.setEdtime(stage.getEdtime()+" 23:59:59");
							}
						}
						
						
						if("".equals(stage.getFormats())){
							stage.setFormats(null);
						}
						if("".equals(stage.getWeidu())){
							stage.setWeidu(null);
						}
						   String key=stage.getIsrepeat()+trade+userid+""+stage.getEmotion()+stage.getStartTime()+stage.getSubjectid()+stage.getWeidu()+stage.getFormats()+stage.getEndTime()+stage.getSttime()+stage.getEdtime()+stage.getDependency()+stage.getIstrade()+stage.getPubtime()+stage.getUptime()+stage.getSimnumorder();
					   if(shardedJedis.exists(key)){
							       String time = shardedJedis.ttl(key).toString();
							       if(!time.equals(-1)){
							    	   //redis 存在 删除redis 重新添加redis
							    	   //获取redis 数据
							    	   
										SubJectArticleBo  sab = null;
										try {
											String sttr = shardedJedis.hget(key, "getsearchopinion");
											if(sttr!=null&&!"".equals(sttr)){
												sab = mapper.readValue(sttr, SubJectArticleBo.class);
												sab.setTotal(sab.getTotal()*1-num*1);
												 if(shardedJedis.del(key).equals(1)){//删除redis
													//放到redis
														shardedJedis.hset(key, "getsearchopinion",mapper.writeValueAsString(sab));
														shardedJedis.expire(key, Integer.parseInt(time));
										    	   }
												
											}
										} catch (Exception e) {
											// TODO: handle exception
											Log.info(e.getMessage());
											Log.error(e.getMessage(),e);
										}
							       }
							}
					}
				   return new Response(ResponseStatus.Success,num,true);
			   }else{
				   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
			   }
		   }else{
			   return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,true);
		   }
		  
	   }
	@RequestMapping(value = "/delsimlarArticle", method = RequestMethod.POST)
	  public Response delsimlarArticle(SubJectArticleBo stage, HttpServletRequest request, SubjectMArticle obj){
		   SubjectMArticle sm = new SubjectMArticle();
			if(null!=stage){
				List<String> mids = new ArrayList<String>();
				String simids = stage.getSimids();
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
				mids.add(stage.getMid());
				sm.setIds(mids);
				 int num = monitorallServiceImpl.deleteIndexSimilarArticle(sm);
				 if(num > 0){
					 String userid = stage.getUserid();
					 //修改缓存数据
						Integer trade = (Integer)request.getSession().getAttribute("setTrade");
						if(null == stage.getIstrade()){
							if(trade!=1){
								List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
								stage.setMedialist(formatslist);
							}	
						}else if(1 == stage.getIstrade()){
							//如果开通 交易类型 查询 交易类型 
							if(trade == 1){
								if(null == stage.getFormats() || "".equals(stage.getFormats())){
									stage.setFormats("trade");
									stage.setDependency(Double.parseDouble("2"));
								}else if("trade".equals(stage.getFormats())){
									
								}else{
									stage.setDependency(Double.parseDouble("2"));
								}
								stage.setTrade(trade);
							}else{
								//查询相关性小于2
								stage.setDependency(Double.parseDouble("2"));
								List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
								stage.setMedialist(formatslist);
							}
						}else{
							//if(trade!=1){
								List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
								stage.setMedialist(formatslist);
							//}	
						}
					      ObjectMapper mapper = new ObjectMapper();
									 ShardedJedis shardedJedis = null;
									if (shardedJedis == null) {
										shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
												Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
									}
						if(obj.getFiltertag().equals(1)){
							if (stage.getStartTime() != null) {
								if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
									try {
										stage.setStartTime(SimpleDate.formatDate(new Date()));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										
										Log.info(e.getMessage());
										Log.error(e.getMessage(),e);
									}
								} else if (stage.getStartTime().equals(AppConstant.timetype.SUN)) {
									Calendar c = Calendar.getInstance();
									c.add(Calendar.DATE, -7);
									
									try {
										String str = SimpleDate.formatDate(c.getTime());
										stage.setStartTime(str);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										Log.info(e.getMessage());
										Log.error(e.getMessage(),e);
									}
									
								} else if (stage.getStartTime().equals(AppConstant.timetype.MONTH)) {
									Calendar c = Calendar.getInstance();
									c.add(Calendar.DATE, -30);
									try {
										String  str = SimpleDate.formatDate(c.getTime());
										stage.setStartTime(str);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										Log.info(e.getMessage());
										Log.error(e.getMessage(),e);
									}
								
								} else if (stage.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
									stage.setStartTime(stage.getSttime()+" 00:00:00");
									stage.setEdtime(stage.getEdtime()+" 23:59:59");
								}
							}
						//	String key = userid+stage.getStartTime()+stage.getEdtime()+trade+stage.getEmotion()+stage.getFormats()+stage.getWeidu()+stage.getDependency()+stage.getIstrade();
							   String key=stage.getIsrepeat()+trade+userid+""+stage.getEmotion()+stage.getStartTime()+stage.getSubjectid()+stage.getWeidu()+stage.getFormats()+stage.getEndTime()+stage.getSttime()+stage.getEdtime()+stage.getDependency()+stage.getIstrade()+stage.getWarning()+stage.getPubtime()+stage.getUptime()+stage.getSimnumorder();
								
							if(shardedJedis.exists(key)){
								       String time = shardedJedis.ttl(key).toString();
								       if(!time.equals(-1)){
								    	   //redis 存在 删除redis 重新添加redis  getinitopinion  getsearchopinion
								    	   //获取redis 数据
											SubJectArticleBo  sab = null;
											try {
												String sttr = shardedJedis.hget(key, "getinitopinion");
												if(sttr!=null&&!"".equals(sttr)){
													sab = mapper.readValue(sttr, SubJectArticleBo.class);
													sab.setTotal(sab.getTotal()*1-num*1);
													 if(shardedJedis.del(key).equals(1)){//删除redis
														//放到redis
															shardedJedis.hset(key, "getinitopinion",mapper.writeValueAsString(sab));
															shardedJedis.expire(key, Integer.parseInt(time));
											    	   }
													
												}
											} catch (Exception e) {
												// TODO: handle exception
												Log.info(e.getMessage());
												Log.error(e.getMessage(),e);
											}
								       }
								}
						}else{
							if (stage.getStartTime() != null) {
								if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
									try {
										stage.setStartTime(SimpleDate.formatDate(new Date()));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										
										Log.info(e.getMessage());
										Log.error(e.getMessage(),e);
									}
								} else if (stage.getStartTime().equals(AppConstant.timetype.SUN)) {
									Calendar c = Calendar.getInstance();
									c.add(Calendar.DATE, -7);
									
									try {
										String str = SimpleDate.formatDate(c.getTime());
										stage.setStartTime(str);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										Log.info(e.getMessage());
										Log.error(e.getMessage(),e);
									}
									
								} else if (stage.getStartTime().equals(AppConstant.timetype.MONTH)) {
									Calendar c = Calendar.getInstance();
									c.add(Calendar.DATE, -30);
									try {
										String  str = SimpleDate.formatDate(c.getTime());
										stage.setStartTime(str);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										Log.info(e.getMessage());
										Log.error(e.getMessage(),e);
									}
								
								} else if (stage.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
									stage.setStartTime(stage.getSttime()+" 00:00:00");
									stage.setEdtime(stage.getEdtime()+" 23:59:59");
								}
							}
							if("".equals(stage.getFormats())){
								stage.setFormats(null);
							}
							if("".equals(stage.getWeidu())){
								stage.setWeidu(null);
							}
							   String key=stage.getIsrepeat()+trade+userid+""+stage.getEmotion()+stage.getStartTime()+stage.getSubjectid()+stage.getWeidu()+stage.getFormats()+stage.getEndTime()+stage.getSttime()+stage.getEdtime()+stage.getDependency()+stage.getIstrade()+stage.getPubtime()+stage.getUptime()+stage.getSimnumorder();
						   if(shardedJedis.exists(key)){
								       String time = shardedJedis.ttl(key).toString();
								       if(!time.equals(-1)){
								    	   //redis 存在 删除redis 重新添加redis
								    	   //获取redis 数据
								    	   
											SubJectArticleBo  sab = null;
											try {
												String sttr = shardedJedis.hget(key, "getsearchopinion");
												if(sttr!=null&&!"".equals(sttr)){
													sab = mapper.readValue(sttr, SubJectArticleBo.class);
													sab.setTotal(sab.getTotal()*1-num*1);
													 if(shardedJedis.del(key).equals(1)){//删除redis
														//放到redis
															shardedJedis.hset(key, "getsearchopinion",mapper.writeValueAsString(sab));
															shardedJedis.expire(key, Integer.parseInt(time));
											    	   }
													
												}
											} catch (Exception e) {
												// TODO: handle exception
												Log.info(e.getMessage());
												Log.error(e.getMessage(),e);
											}
								       }
								}
						}
					 return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
				 }else{
					 return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
				 }
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,true);
			}
	   }
	
	/**
	 * <p>
	 * 方法名称：getAllSubject
	 * </p>
	 * <p>
	 * 方法描述：根据登录用户获取专题
	 * </p>
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月20日
	 *        <p>
	 *        history 2016年7月20日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getAllSubject", method = RequestMethod.POST)
	public Response getAllSubject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		String classifyid1 = request.getParameter("classifyid");
		String userid = (String) request.getSession().getAttribute("userid");
		List<Subject> subject = subjectMArticleServiceImpl.getSubjectById(userid, classifyid1);
		return new Response(ResponseStatus.Success, subject, true);
	}

	/**
	 * <p>
	 * 方法名称：getSubjectOpinion
	 * </p>
	 * <p>
	 * 方法描述：根据专题id获取舆情
	 * </p>
	 * 
	 * @param page
	 * @param subjectId
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月12日
	 *        <p>
	 *        history 2016年7月12日 Administrator 创建
	 *        <p>
	 */
	/*
	 * @RequestMapping(value = "/getSubjectOpinion", method = RequestMethod.GET)
	 * public Response getSubjectOpinion(PageInfo page,String subjectId) throws
	 * Exception { // subjectId = "ba622d9a06904afe87ec483fb4b36023";
	 * List<String> articles = subjectMArticleServiceImpl.getArticleIdByid(null,
	 * subjectId); if(articles.size()<=0){ return new
	 * Response(ResponseStatus.Success, "", true); }
	 * PageHelper.startPage(page.getPageNum(), page.getPageSize());
	 * List<SubjectArticle> list =
	 * subjectArticleServiceImpl.getBysubject(articles);
	 * 
	 * 
	 * 	 * PageInfo<SubjectArticle> info = new PageInfo<SubjectArticle>(list);
	 * return new Response(ResponseStatus.Success, info, true); }
	 */
	@RequestMapping(value = "/getSubjectOpinion", method = RequestMethod.GET)
	public Response getSubjectOpinion(PageInfo page, String subjectId, SubJectArticleBo sb, HttpServletRequest request)
			throws Exception {
		// userId="0100";
		// subjectId = "ba622d9a06904afe87ec483fb4b36023";
		String userid = (String) request.getSession().getAttribute("userid");
		sb.setUserid(userid);
		if (userid != null && !"".equals(userid)) {
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<SubJectArticleBo> list = subjectArticleServiceImpl.selectAllByUserId(sb);
			//PageInfo<SubJectArticleBo> info = new PageInfo<SubJectArticleBo>(list);
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SELECTEERRO, true);
		}
	}
	@RequestMapping(value = "/selectSimilarArticle", method = RequestMethod.GET)
    public Response selectSimilarArticle(SubJectArticleBo stage){
    	List<String> mids = new ArrayList<String>();
    	if(null!=stage){
    		 String simids= stage.getSimids();
     		if(null!=simids && !"".equals(simids)&& simids.contains(",")){
         		String[] listmids = simids.split(",");
         		for(int i=0;i<listmids.length;i++){
         			mids.add(listmids[i]);
         		}
         	}else{
         		mids.add(stage.getSimids());
         	}
         	stage.setMids(mids);
         	List<SubJectArticleBo> list = subjectArticleServiceImpl.filterSimlarArticle(stage);
         	if(list.size()>0){
         		return new Response(ResponseStatus.Success, list, true);
         	}else{
         		return new Response(ResponseStatus.Success, AppConstant.responseInfo.SELECTEERRO, true);
         	}
    	}else{
    		return new Response(ResponseStatus.Success, AppConstant.responseInfo.SELECTEERRO, true);
    	}
    	 
    }
	/**
	 * <p>
	 * 方法名称：getFilterOpinion
	 * </p>
	 * <p>
	 * 方法描述：根据条件过滤取文章
	 * </p>
	 * 
	 * @param page
	 * @param stage
	 * @param userId
	 * @param subjectid
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月19日
	 *        <p>
	 *        history 2016年7月19日 Administrator 创建
	 *        <p>
	 */
	
	@RequestMapping(value = "/getFilterOpinion", method = RequestMethod.GET)
	public Response getFilterOpinion(PageInfo page, SubJectArticleBo stage, String userId, String subjectid,
			HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		stage.setUserid(userid);
		stage.setSubjectid(subjectid);

		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		//if("".equals(stage.getFormats()) || null ==stage.getFormats()){
		
			if(null == stage.getIstrade()){
				if(trade!=1){
					List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
					stage.setMedialist(formatslist);
				}	
			}else if(1 == stage.getIstrade()){
				//如果开通 交易类型 查询 交易类型 
				if(trade == 1){
					if(null == stage.getFormats() || "".equals(stage.getFormats())){
						stage.setFormats("trade");
						stage.setDependency(Double.parseDouble("2"));
					}else if("trade".equals(stage.getFormats())){
						
					}else{
						stage.setDependency(Double.parseDouble("2"));
					}
					stage.setTrade(trade);
				}else{
					//查询相关性小于2
					stage.setDependency(Double.parseDouble("2"));
					List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
					stage.setMedialist(formatslist);
				}
			}else{
//				if(trade!=1){
					List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
					stage.setMedialist(formatslist);
				//}	
			}
			
		//}
		stage.setNoquery(0);
		stage.setStart((page.getPageNum()-1)*page.getPageSize());
		stage.setSize(page.getPageSize());
		if (stage.getStartTime() != null) {
			if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
				try {
					stage.setStartTime(SimpleDate.formatDate(new Date()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			} else if (stage.getStartTime().equals(AppConstant.timetype.SUN)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(c.getTime());
					stage.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
				
			} else if (stage.getStartTime().equals(AppConstant.timetype.MONTH)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -30);
				try {
					String  str = SimpleDate.formatDate(c.getTime());
					stage.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			
			} else if (stage.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
				stage.setStartTime(stage.getSttime()+" 00:00:00");
				stage.setEdtime(stage.getEdtime()+" 23:59:59");
			}
		}
		
		List<SubJectArticleBo> list = subjectArticleServiceImpl.getByFilterCom(stage);
     // SubJectArticleBo  sab = subjectArticleServiceImpl.selectAllByPage(stage);
      String key=stage.getIsrepeat()+trade+userid+""+stage.getEmotion()+stage.getStartTime()+stage.getSubjectid()+stage.getWeidu()+stage.getFormats()+stage.getEndTime()+stage.getSttime()+stage.getEdtime()+stage.getDependency()+stage.getIstrade()+stage.getWarning()+stage.getPubtime()+stage.getUptime()+stage.getSimnumorder();
//       
	      ObjectMapper mapper = new ObjectMapper();
			 ShardedJedis shardedJedis = null;
			if (shardedJedis == null) {
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
			}
			boolean flag = false;
			SubJectArticleBo  sab = null;
			try {
				String sttr = shardedJedis.hget(key, "getsearchopinion");
				if(sttr!=null&&!"".equals(sttr)){
					sab = mapper.readValue(sttr, SubJectArticleBo.class);
					flag = true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			if(!flag){
				try {
					sab = subjectArticleServiceImpl.selectAllByPage(stage);
					//放到redis
					shardedJedis.hset(key, "getsearchopinion",mapper.writeValueAsString(sab));
				shardedJedis.expire(key, 60);
				} catch (Exception e) {
					// TODO: handle exception
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			}
        SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
		if(null!=sab){
			info.setTotal(sab.getTotal());
		}else{
			info.setTotal(0);
		}
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
	 * 方法名称：updateArticle
	 * </p>
	 * <p>
	 * 方法描述：用户更改文章属性
	 * </p>
	 * 
	 * @param page
	 * @param stage
	 * @param userId
	 * @param subjectid
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月27日
	 *        <p>
	 *        history 2016年7月27日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateArticle", method = RequestMethod.GET)
	public Response updateArticle(SubjectMArticle obj,HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		if(null!=obj){
			List<String> arids = obj.getIds();
			List<String> newIds = new ArrayList<String>();
			if (arids != null) {
				for (String id : arids) {
					id = id.replace("\"", "").replace("[", "").replace("]", "");
					newIds.add(id);
				}
				obj.setIds(newIds);
			}
			if (obj.getAttention() != null) {
				if (obj.getAttention().equals(true)) {
					obj.setAttentiontime(new Date());
				}
			}
			int res = 0;
			obj.setUserid(userid);
			res = subjectMArticleServiceImpl.updateById(obj);
			
			if (res > 0) {
				return new Response(ResponseStatus.Success, res, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}
	@RequestMapping(value = "/updateWarningArticle", method = RequestMethod.GET)
	public Response updateWarningArticle(SubjectMArticle obj,Systemmessage record,SubJectArticleBo sa,String type,HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		if(null!=obj){
			List<String> arids = obj.getIds();
			List<String> newIds = new ArrayList<String>();
			if (arids != null) {
				for (String id : arids) {
					id = id.replace("\"", "").replace("[", "").replace("]", "");
					newIds.add(id);
				}
				obj.setIds(newIds);
			}
			if (obj.getAttention() != null) {
				if (obj.getAttention().equals(true)) {
					obj.setAttentiontime(new Date());
				}
			}
			int res = 0;
			obj.setUserid(userid);
			res = subjectMArticleServiceImpl.updateWarning(obj, record, request);
			//
			String a = subjectArticleServiceImpl.sendinfo(sa, type, request);
			if (res > 0) {
				return new Response(ResponseStatus.Success, res, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}
	
	@RequestMapping(value = "/updateBrief", method = RequestMethod.GET)
	public Response updateBrief(SubjectMArticle obj, SubJectArticleBo sa, String type, HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		if(null!=obj){
			List<String> arids = obj.getIds();
			List<String> newIds = new ArrayList<String>();
			if (arids != null) {
				for (String id : arids) {
					id = id.replace("\"", "").replace("[", "").replace("]", "");
					newIds.add(id);
				}
				obj.setIds(newIds);
			}
			if (obj.getAttention() != null) {
				if (obj.getAttention().equals(true)) {
					obj.setAttentiontime(new Date());
				}
			}
			int res = 0;
			obj.setUserid(userid);
		    res = subjectMArticleServiceImpl.updateById(obj);
		    String a = subjectArticleServiceImpl.sendinfo(sa, type, request);
			if (res > 0) {
				return new Response(ResponseStatus.Success, res, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}
	@RequestMapping(value = "/paperupdateArticle", method = RequestMethod.GET)
	public Response paperupdateArticle(SubjectMArticle obj, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		if(null!=obj){
			List<String> arids = obj.getIds();
			List<String> newIds = new ArrayList<String>();
			if (arids != null) {
				for (String id : arids) {
					id = id.replace("\"", "").replace("[", "").replace("]", "");
					newIds.add(id);
				}
				obj.setIds(newIds);
			}

			if (obj.getAttention() != null) {
				if (obj.getAttention().equals(true)) {
					obj.setAttentiontime(new Date());
				}

			}
			int res = 0;
			if(obj.getTagAttention().equals(1)){
				obj.setUserid(userid);
				res = subjectMArticleServiceImpl.updateById(obj);
			}else{
				PersonmanagemarticleBo pb = new PersonmanagemarticleBo();
				pb.setUserid(userid);
				pb.setIds(arids);
				res = subjectMArticleServiceImpl.updatePersonAttention(pb);
			}
			//
			if (res > 0) {
				return new Response(ResponseStatus.Success, res, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}
	@RequestMapping(value = "/updateEmotion", method = RequestMethod.GET)
	public Response updateEmotion(SubjectMArticle obj) {
		int res = subjectMArticleServiceImpl.updateEmotion(obj);
		if (res > 0) {
			return new Response(ResponseStatus.Success, res, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	@RequestMapping(value = "/deleteByid", method = RequestMethod.GET)
	public Response deleteByid(SubjectMArticle obj,SubJectArticleBo stage, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		obj.setUserid(userid);
		List<String> arids = obj.getIds();
		List<String> newIds = new ArrayList<String>();
		for (String id : arids) {
			id = id.replace("\"", "").replace("[", "").replace("]", "");
			newIds.add(id);
		}
		obj.setIds(newIds);
		int res = subjectMArticleServiceImpl.deleteByid(obj);
		if(res>0){
		//修改缓存数据
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
	      ObjectMapper mapper = new ObjectMapper();
					 ShardedJedis shardedJedis = null;
					if (shardedJedis == null) {
						shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
								Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
					}
		if(obj.getFiltertag().equals(1)){
			String str = null;
			try {
				str = SimpleDate.formatDate(new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			 String key = userid+""+str+trade+stage.getEmotion();
			  
		
				if(shardedJedis.exists(key)){
				       String time = shardedJedis.ttl(key).toString();
				       if(!time.equals(-1)){
				    	   //redis 存在 删除redis 重新添加redis  getinitopinion  getsearchopinion
				    	   //获取redis 数据
							SubJectArticleBo  sab = null;
							try {
								String sttr = shardedJedis.hget(key, "getinitopinion");
								if(sttr!=null&&!"".equals(sttr)){
									sab = mapper.readValue(sttr, SubJectArticleBo.class);
									sab.setTotal(sab.getTotal()*1-newIds.size()*1);
									 if(shardedJedis.del(key).equals(1)){//删除redis
										//放到redis
											shardedJedis.hset(key, "getinitopinion",mapper.writeValueAsString(sab));
											shardedJedis.expire(key, Integer.parseInt(time));
							    	   }
									
								}
							} catch (Exception e) {
								// TODO: handle exception
								Log.info(e.getMessage());
								Log.error(e.getMessage(),e);
							}
				       }
				}
		}else{
		
			
			
			if (stage.getStartTime() != null) {
				if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
					try {
						stage.setStartTime(SimpleDate.formatDate(new Date()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						
						Log.info(e.getMessage());
						Log.error(e.getMessage(),e);
					}
				} else if (stage.getStartTime().equals(AppConstant.timetype.SUN)) {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -7);
					
					try {
						String str = SimpleDate.formatDate(c.getTime());
						stage.setStartTime(str);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Log.info(e.getMessage());
						Log.error(e.getMessage(),e);
					}
					
				} else if (stage.getStartTime().equals(AppConstant.timetype.MONTH)) {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -30);
					try {
						String  str = SimpleDate.formatDate(c.getTime());
						stage.setStartTime(str);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Log.info(e.getMessage());
						Log.error(e.getMessage(),e);
					}
				
				} else if (stage.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
					stage.setStartTime(stage.getSttime()+" 00:00:00");
					stage.setEdtime(stage.getEdtime()+" 23:59:59");
				}
			}else{
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(new Date());
					stage.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			}
			   String key=trade+userid+""+stage.getEmotion()+stage.getStartTime()+stage.getSubjectid()+stage.getWeidu()+stage.getFormats()+stage.getEndTime()+stage.getSttime()+stage.getEdtime()+stage.getWarning()+stage.getPubtime()+stage.getUptime()+stage.getSimnumorder();
		   if(shardedJedis.exists(key)){
				       String time = shardedJedis.ttl(key).toString();
				       if(!time.equals(-1)){
				    	   //redis 存在 删除redis 重新添加redis
				    	   //获取redis 数据
				    	   
							SubJectArticleBo  sab = null;
							try {
								String sttr = shardedJedis.hget(key, "getsearchopinion");
								if(sttr!=null&&!"".equals(sttr)){
									sab = mapper.readValue(sttr, SubJectArticleBo.class);
									sab.setTotal(sab.getTotal()*1-newIds.size()*1);
									 if(shardedJedis.del(key).equals(1)){//删除redis
										//放到redis
											shardedJedis.hset(key, "getsearchopinion",mapper.writeValueAsString(sab));
											shardedJedis.expire(key, Integer.parseInt(time));
							    	   }
									
								}
							} catch (Exception e) {
								// TODO: handle exception
								Log.info(e.getMessage());
								Log.error(e.getMessage(),e);
							}
				       }
				}
		}
		}
		return new Response(ResponseStatus.Success, res, true);
	}

	/**
	 * <p>
	 * 方法名称：getSearchOpinion
	 * </p>
	 * <p>
	 * 方法描述：根据条件搜索舆情文章
	 * </p>
	 * 
	 * @param page
	 * @param search
	 * @param userId
	 * @param subjectId
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年7月23日
	 *        <p>
	 *        history 2016年7月23日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getSearchOpinion", method = RequestMethod.GET)
	public Response getSearchOpinion(PageInfo page, String search, String searchTwo, String searchTall,
			String searchall, String subjectId, SubJectArticleBo record, HttpSession session,
			HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String userid = (String) request.getSession().getAttribute("userid");
		
		record.setUserid(userid);
		if (record.getStartTime() != null) {
			if (record.getStartTime().equals(AppConstant.timetype.CURRENT)) {
				record.setStartTime(df.format(new Date()));
			} else if (record.getStartTime().equals(AppConstant.timetype.SUN)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				String str = df.format(c.getTime());
				record.setStartTime(str);
			} else if (record.getStartTime().equals(AppConstant.timetype.MONTH)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -30);
				String str = df.format(c.getTime());
				record.setStartTime(str);
			} else if (record.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
			/*	record.setStartTime(record.getSttime());*/
				record.setStartTime(record.getSttime()+" 00:00:00");
				record.setEdtime(record.getEdtime()+" 23:59:59");
			}
		}/*else{
			try {
				String str = SimpleDate.formatDate(new Date());
				record.setStartTime(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}*/
		//if("".equals(record.getFormats()) || null ==record.getFormats()){
		    Integer trade = (Integer)request.getSession().getAttribute("setTrade");
//			if(trade!=1){
//				List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
//
//				record.setMedialist(formatslist);
//			}
		    if(null == record.getIstrade()){
				if(trade!=1){
					List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
					record.setMedialist(formatslist);
				}	
			}else if(1 == record.getIstrade()){
				//如果开通 交易类型 查询 交易类型 
				if(trade == 1){
					if(null == record.getFormats() || "".equals(record.getFormats())){
						record.setFormats("trade");
						record.setDependency(Double.parseDouble("2"));
					}else if("trade".equals(record.getFormats())){
						
					}else{
						record.setDependency(Double.parseDouble("2"));
					}
					record.setTrade(trade);
				}else{
					//查询相关性小于2
                    record.setDependency(Double.parseDouble("2"));
					List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
					record.setMedialist(formatslist);
				}
			}else{
			//	if(trade!=1){
					List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
					record.setMedialist(formatslist);
				//}	
			}
			//
	/*	}else{
			
		}*/
		record.setNoquery(0);
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		List<SubJectArticleBo>  aroBject = subjectArticleServiceImpl.selectAllBySearch(record);
		PageInfo<SubJectArticleBo> info = new PageInfo<SubJectArticleBo>(aroBject);
		return new Response(ResponseStatus.Success, info, true);
	
		
		
	}

	/**
	 * <p>
	 * 方法名称：downloadFile
	 * </p>
	 * <p>
	 * 方法描述：下载选中的舆情信息
	 * </p>
	 * 
	 * @param response
	 * @param selected
	 * @author Administrator
	 * @since 2016年7月23日
	 *        <p>
	 *        history 2016年7月23日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletResponse response, String[] selected) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "file.txt";
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		StringBuffer obj = new StringBuffer();
		List<SubjectArticle> list = subjectArticleServiceImpl.getAllOPinion(Arrays.asList(selected));
		for (SubjectArticle sub : list) {
			obj.append("title:	").append(sub.getTittle()).append("\r\nurl: ").append(sub.getUrl())
					.append("\r\nscore: ").append(sub.getScore()).append("\r\npubdate: ").append(sub.getUpdatetime())
					.append("\r\ndataSource: ").append(sub.getDataSource()).append("\r\nauthor: ")
					.append(sub.getAuthor()).append("\r\nemotion: ").append(sub.getEmotion())
					.append("\r\ncontentType: ").append(sub.getContentType()).append("\r\nformats: ")
					.append(sub.getFormats()).append("\r\nreadcount: ").append(sub.getReadcount())
					.append("\r\n\r\n\r\n").append("\r\ncontent: ").append(sub.getContent());
		}
		try {
			InputStream inputStream = new ByteArrayInputStream(obj.toString().getBytes());
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.error(e.getMessage(),e);
		
		} catch (IOException e) {
			e.printStackTrace();
			Log.error(e.getMessage(),e);
		}
	}

	/**
	 * <p>
	 * 方法名称：getSubjectArticleByDataSource
	 * </p>
	 * <p>
	 * 方法描述：通过文章来源查询专题文章
	 * </p>
	 * 
	 * @param sb
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/getSubjectArticleByDataSource", method = RequestMethod.GET)
	public Response getSubjectArticleByDataSource(SubJectArticleBo sb, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		sb.setUserid(userid);
		if (userid != null && !"".equals(userid)) {
			SubjectMArticleBo s = new SubjectMArticleBo();
			s.setUserid(userid);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -7);
			String str = df.format(c.getTime());
			s.setUpdatetime(str);
			
			//s.setMediaList(formatslist);
			Integer trade = (Integer)request.getSession().getAttribute("setTrade");
			if(trade!=1){
				List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");

				s.setMediaList(formatslist);
			}
             
			List<SubJectArticleBo> list = subjectArticleServiceImpl.selectAllByDataSource(s);
			if (list.size() > 0) {
				return new Response(ResponseStatus.Success, list, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：exportWord
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "exportWord", method = RequestMethod.POST)
	public Response exportWord(HttpServletRequest request, HttpServletResponse response) {
		String add = subjectArticleServiceImpl.daochutongji(request, response);
		return new Response(ResponseStatus.Success, add, true);
	}

	public String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			System.out.println("时间格式不正确");
			e.printStackTrace();
			Log.error("时间格式不正确"+e.getMessage(),e);
		}
		return currentdatetime;
	}

	/**
	 * 基本的写操作
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSimpleWrite() throws Exception {
		// 新建一个文档
		XWPFDocument doc = new XWPFDocument();
		// 创建一个段落
		XWPFParagraph para = doc.createParagraph();

		// 一个XWPFRun代表具有相同属性的一个区域。
		XWPFRun run = para.createRun();
		run.setBold(true); // 加粗
		run.setText("加粗的内容");
		run = para.createRun();
		run.setColor("FF0000");
		run.setText("红色的字。");
		OutputStream os = new FileOutputStream("D:\\simpleWrite.docx");
		// 把doc输出到输出流
		doc.write(os);
		this.close(os);
	}

	/**
	 * 关闭输出流
	 * 
	 * @param os
	 */
	private void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			//	e.printStackTrace();
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
	}

	@RequestMapping(value = "/tongjiemotion", method = RequestMethod.GET)
	public Response tongjiemotion(SubJectArticleBo record) {

		return null;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：checkSubjectByClassifyid
	 * </p>
	 * <p>
	 * 方法描述：查询分类下的所有专题
	 * </p>
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年11月1日
	 *        <p>
	 *        history 2016年11月1日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/checkSubjectByClassifyid", method = RequestMethod.GET)
	public Response checkSubjectByClassifyid(Subject record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		record.setIsdelete(false);
		List<Subject> list = subjectArticleServiceImpl.checkSubjectByClassifyid(record);
		if (list != null) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：inittongjiemotion
	 * </p>
	 * <p>
	 * 方法描述：统计分析 近7天的情感倾向走势图
	 * </p>
	 * 
	 * @param statisticalBo
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/inittongjiemotion", method = RequestMethod.GET)
	public Response inittongjiemotion(SubjectStatisticalBo statisticalBo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		statisticalBo.setUserid(userid);
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		
		try {
			String str = SimpleDate.formatDate(c.getTime());
			statisticalBo.setUpdatetime(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		
	
		 Integer trade = (Integer)request.getSession().getAttribute("setTrade");
			if(trade!=1){
				List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");

				statisticalBo.setMedialist(formatslist);
			}
		List<SubjectStatisticalBo> list = subjectArticleServiceImpl.inittongjiemotion(statisticalBo);
		System.out.println(list);
	//	if(list.size()>0){
			return new Response(ResponseStatus.Success, list, true);
		/*}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}*/
		
	}

	/**
	 * <p>
	 * 方法名称：tongjiemotioninfo
	 * </p>
	 * <p>
	 * 方法描述：// 根据条件查询负面 全部 信息
	 * </p>
	 * 
	 * @param stage
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/tongjiemotioninfo", method = RequestMethod.GET)
	public Response tongjiemotioninfo(SubJectArticleBo stage, HttpServletRequest request) {
		List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();
		List<String> medialist = stage.getMedialist();
		List<String> list1 = new ArrayList<String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		stage.setMedialist(list1);
		String userid = (String) request.getSession().getAttribute("userid");
		stage.setUserid(userid);
		if (medialist != null) {
			for (String media : medialist) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				list1.add(media);
			}
		}else{
			list1 = null;
		}
		if (stage.getStartTime() != null) {
			if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
				try {
					stage.setStartTime(SimpleDate.formatDate(new Date()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
				sBos = subjectArticleServiceImpl.daytiaojiantongjiemotion(list1, stage.getSubjectid(),
						stage.getStartTime(), stage.getEndTime(), userid, stage.getClassifyid(), stage.getTrade());
			} else if (stage.getStartTime().equals(AppConstant.timetype.WEEK)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(c.getTime());
					stage.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch blo
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
				
				//list = subjectArticleServiceImpl.tiaojiantongjiemotion(list1, stage.getSubjectid(),
						//stage.getStartTime(), stage.getEndTime(), userid, stage.getClassifyid());
				sBos = subjectArticleServiceImpl.zaitiemotion(list1, stage.getSubjectid(), stage.getStartTime(),
						stage.getEndTime(), userid, stage.getClassifyid(),"week",stage.getTrade());
			}
		}
		if (stage.getEndTime() != null && !"".equals(stage.getEndTime())) {
			if(stage.getEndTime().equals(stage.getStartTime())){
				if(stage.getEndTime().equals(df.format(new Date()))){
					sBos = subjectArticleServiceImpl.daytiaojiantongjiemotion(list1, stage.getSubjectid(),
							stage.getStartTime(), stage.getEndTime(), userid, stage.getClassifyid(), stage.getTrade());
				}else{
					sBos = subjectArticleServiceImpl.zaitiemotion(list1, stage.getSubjectid(), stage.getStartTime(),
							stage.getEndTime(), userid, stage.getClassifyid(),"month",stage.getTrade());
				}
				
			}else{
				sBos = subjectArticleServiceImpl.zaitiemotion(list1, stage.getSubjectid(), stage.getStartTime(),
						stage.getEndTime(), userid, stage.getClassifyid(),"month",stage.getTrade());
			}
			//自定义时间查询
			//list = subjectArticleServiceImpl.tiaojiantongjiemotion(list1, stage.getSubjectid(), stage.getStartTime(),
				//	stage.getEndTime(), userid, stage.getClassifyid());
			
		}

		
		if (sBos != null) {
			return new Response(ResponseStatus.Success, sBos, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：tongjimedia
	 * </p>
	 * <p>
	 * 方法描述：统计分析 信息来源分类 倾向性数据统计
	 * </p>
	 * 
	 * @param statisticalBo
	 * @param request
	 * @return
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/tongjimedia", method = RequestMethod.GET)
	public Response tongjimedia(SubjectStatisticalBo statisticalBo, HttpServletRequest request) {

		String userid = (String) request.getSession().getAttribute("userid");
		statisticalBo.setUserid(userid);
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		try {
			String str = SimpleDate.formatDate(c.getTime());
			statisticalBo.setUpdatetime(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
			
		}
		List<SubjectStatistical> list = subjectArticleServiceImpl.tongjimedia(statisticalBo);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：tongjisource
	 * </p>
	 * <p>
	 * 方法描述：统计分析 不通时间段的 信息来源站点文章数量
	 * </p>
	 * 
	 * @param stage
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/tongjisource", method = RequestMethod.GET)
	public Response tongjisource(SubJectArticleBo stage, HttpServletRequest request) {
		List<String> medialist = stage.getMedialist();
		List<String> emotionlist = stage.getEmotionlist();
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
	//	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (medialist != null) {
			for (String media : medialist) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				list1.add(media);
			}
			stage.setMedialist(list1);
		}
		if (emotionlist != null) {
			for (String me : emotionlist) {
				me = me.replace("\"", "").replace("[", "").replace("]", "");
				list2.add(me);
			}
			
			
			stage.setEmotionlist(list2);
		}
		if (stage.getStartTime() != null) {
			if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
				try {
					stage.setStartTime(SimpleDate.formatDate(new Date()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			} else if (stage.getStartTime().equals(AppConstant.timetype.WEEK)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				try {
					String str = SimpleDate.formatDate(c.getTime());
					stage.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
				
			}
		}
  if(null!=stage.getTrade()){
	  if(stage.getTrade() ==0){
    	  list1 =null;
      }
  }
      
		String userid = (String) request.getSession().getAttribute("userid");
		stage.setUserid(userid);
		List<SubJectArticleBo> listinfo = subjectArticleServiceImpl.tongjisource(list2, list1, userid,
				stage.getSubjectid(), stage.getStartTime(), stage.getEndTime(), stage.getClassifyid(),stage.getTrade());
		if (listinfo.size() > 0) {
			return new Response(ResponseStatus.Success, listinfo, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/timetongjisource", method = RequestMethod.GET)
	public Response timetongjisource(SubjectStatisticalBo stage, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		stage.setUserid(userid);
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (stage.getStartTime() != null) {
			if (stage.getStartTime().equals(AppConstant.timetype.CURRENT)) {
				try {
					stage.setStartTime(SimpleDate.formatDate(new Date()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			} else if (stage.getStartTime().equals(AppConstant.timetype.WEEK)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				try {
					String str = SimpleDate.formatDate(c.getTime());
					stage.setStartTime(str);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
					
				}
				
			}
		}
		List<SubjectStatistical> list = subjectArticleServiceImpl.timetongjimedia(stage);
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：myconcern
	 * </p>
	 * <p>
	 * 方法描述：查询我的关注
	 * </p>
	 * 
	 * @param page
	 * @param stage
	 * @retur
	 * @throws Exception
	 * @author Administrator
	 * @since 2016年9月27日
	 *        <p>
	 *        history 2016年9月27日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/myconcern", method = RequestMethod.GET)
	public Response myconcern(SolrPage page, SubJectArticleBo stage, HttpServletRequest request) {
		//PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String userid = (String) request.getSession().getAttribute("userid");
		stage.setUserid(userid);
		stage.setStart((page.getPageNum()-1)*page.getPageSize());
		stage.setSize(page.getPageSize());
		List<SubJectArticleBo> list = subjectArticleServiceImpl.myconcern(stage);
		SubJectArticleBo sb = subjectArticleServiceImpl.myconcernTotal(stage);
		SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
		if(null!=sb){
			info.setTotal(sb.getTotal());
		}else{
			info.setTotal(0);
		}
		info.setPageNum(page.getPageNum());
		info.setPageSize(page.getPageSize());
		info.setNavigatePages(8);
		info.setDatas(list);
		//PageInfo<SubJectArticleBo> info = new PageInfo<SubJectArticleBo>(list);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/myPersonconcern", method = RequestMethod.GET)
	public Response myPersonconcern(SolrPage page, SubJectArticleBo stage, HttpServletRequest request) {
		//PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String userid = (String) request.getSession().getAttribute("userid");
		stage.setUserid(userid);
		stage.setStart((page.getPageNum()-1)*page.getPageSize());
		stage.setSize(page.getPageSize());
		List<SubJectArticleBo> list = subjectArticleServiceImpl.myPersonconcern(stage);
		SubJectArticleBo sb = subjectArticleServiceImpl.myPaperTotal(stage);
		SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
		if(null!=info){
			info.setTotal(sb.getTotal());
		}else{
			info.setTotal(0);
		}
		info.setPageNum(page.getPageNum());
		info.setPageSize(page.getPageSize());
		info.setNavigatePages(8);
		info.setDatas(list);
		if(info != null){
			return new Response(ResponseStatus.Success, info, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	public void setTableWidth(XWPFTable table, String width) {
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		CTJc cTJc = tblPr.addNewJc();
		cTJc.setVal(STJc.Enum.forString("center"));
		tblWidth.setW(new BigInteger(width));
		tblWidth.setType(STTblWidth.DXA);
	}

	public static void appendExternalHyperlink(String url, String text, XWPFParagraph paragraph) {
		// Add the link as External relationship
		String id = paragraph.getDocument().getPackagePart()
				.addExternalRelationship(url, XWPFRelation.HYPERLINK.getRelation()).getId();
		// Append the link and bind it to the relationship
		CTHyperlink cLink = paragraph.getCTP().addNewHyperlink();
		cLink.setId(id);

		// Create the linked text
		CTText ctText = CTText.Factory.newInstance();
		ctText.setStringValue(text);
		CTR ctr = CTR.Factory.newInstance();
		CTRPr rpr = ctr.addNewRPr();

		// 设置超链接样式
		CTColor color = CTColor.Factory.newInstance();
		color.setVal("0000FF");
		rpr.setColor(color);
		rpr.addNewU().setVal(STUnderline.SINGLE);

		// 设置字体
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");

		// 设置字体大小
		CTHpsMeasure sz = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
		sz.setVal(new BigInteger("20"));

		ctr.setTArray(new CTText[] { ctText });
		// Insert the linked text into the link
		cLink.setRArray(new CTR[] { ctr });

		/*
		 * //设置段落居中 paragraph.setAlignment(ParagraphAlignment.CENTER);
		 * paragraph.setVerticalAlignment(TextAlignment.CENTER);
		 */
	}

	/**
	 * <p>
	 * 方法名称：exportMyConcern
	 * </p>
	 * <p>
	 * 方法描述：// 批量导出我的关注
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param record
	 * @return
	 * @throws IOException
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/exportMyConcern", method = RequestMethod.GET)
	public Response exportMyConcern(HttpServletRequest request, HttpServletResponse response, SubjectArticle record) {

		String s = subjectArticleServiceImpl.myconcern(request, response, record);
		return new Response(ResponseStatus.Success, s, true);

	}

	/**
	 * <p>
	 * 方法名称：qunsend
	 * </p>
	 * <p>
	 * 方法描述：上报邮箱 向多人发邮件 发送手机短信
	 * </p>
	 * 
	 * @param id
	 * @param warning
	 * @param request
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/qunsend", method = RequestMethod.GET)
	public Response qunsend(SubJectArticleBo sa, String type, HttpServletRequest request) {
		String a = subjectArticleServiceImpl.sendinfo(sa, type, request);
		return new Response(ResponseStatus.Success, a, true);

	}

	/*
	 * @RequestMapping(value="/selectArticleById",method=RequestMethod.GET)
	 * public Response selectArticleById(SubJectArticleBo
	 * record,HttpServletRequest request){ String userid =
	 * (String)request.getSession().getAttribute("userid"); SubJectArticleBo sm
	 * = subjectArticleServiceImpl.selectArticleInfoById(userid,
	 * record.getArticleid()); if(sm!=null){ return new
	 * Response(ResponseStatus.Success,sm,true); }else{ return new
	 * Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false)
	 * ; } }
	 */
	// selectArticleDetailById
	/**
	 * <p>
	 * 方法名称：selectArticleDetailById
	 * </p>
	 * <p>
	 * 方法描述：查询文章详情页
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
	@RequestMapping(value = "/selectArticleDetailById", method = RequestMethod.GET)
	public Response selectArticleDetailById(SubJectArticleBo record) {
		SubJectArticleBo sb = new SubJectArticleBo();
		if(AppConstant.finalltype.WEI.equals(record.getWei())){
			sb = subjectArticleServiceImpl.selectArticleSolrDetailById(record.getArticleid());
		}else{
			sb = subjectArticleServiceImpl.selectArticleDetailById(record.getMid(),record.getArticleid());
		}
		if (sb != null) {
			return new Response(ResponseStatus.Success, sb, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/selectZfwbUrl", method = RequestMethod.GET)
    public Response selectZfwbUrl(String articleid){
    	List<Zfwb> list = subjectArticleServiceImpl.selectByArticleid(articleid);
    	if(list.size()>0){
    		return new Response(ResponseStatus.Success,list,true);
    	}else{
    		return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
    	}
    }
	/**
	 * <p>
	 * 方法名称：getSimArticle
	 * </p>
	 * <p>
	 * 方法描述：查询文章详情页相似文章
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
	@RequestMapping(value = "/getSimArticle", method = RequestMethod.GET)
	public Response getSimArticle(SubJectArticleBo record, HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		List<SubJectArticleBo> sb = subjectArticleServiceImpl.getSimArticle(record);
		if (sb != null) {
			return new Response(ResponseStatus.Success, sb, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectArticleStatus
	 * </p>
	 * <p>
	 * 方法描述：// 查询文章状态信息
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
	@RequestMapping(value = "/selectArticleStatus", method = RequestMethod.GET)
	public Response selectArticleStatus(SubjectMArticle record, HttpServletRequest request) {
		//
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		SubjectMArticle sb = subjectMArticleServiceImpl.selectMAById(record);
		SubjectMArticleBo sma = new SubjectMArticleBo();
		if (sb != null) {
			BeanUtils.copyProperties(sb, sma);
		}
		if (sma != null) {
			return new Response(ResponseStatus.Success, sma, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	@RequestMapping(value = "/exportMyYuqing", method = RequestMethod.GET)
	public Response exportMyYuqing(HttpServletRequest request, HttpServletResponse response, SubjectArticle record) {
		String s = subjectArticleServiceImpl.daochuyuqing(request, response, record);
		return new Response(ResponseStatus.Success, s, true);

	}
	@RequestMapping(value = "/selectSMids", method = RequestMethod.GET)
	public Response selectSMids(String mid,PageInfo page){
	   SubjectMArticle sm = subjectArticleServiceImpl.selectSimids(mid);
	   SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
	   if (null!=sm) {
		   List<String> mids = new ArrayList<String>();
		   String simids= sm.getSimids();
		   SubJectArticleBo stage = new SubJectArticleBo();
		   if(null!=simids && !"".equals(simids)&& simids.contains(",")){
	     		String[] listmids = simids.split(",");
	     		//分页查询
	        	info.setTotal(listmids.length);
	    		int start = (page.getPageNum()-1)*page.getPageSize();
	    	   int end = (page.getPageNum()-1)*page.getPageSize()+10;
		    	if(end>listmids.length){
	    			for(int i=start;i<listmids.length;i++){
		     			mids.add(listmids[i]);
		     		}
		    	}else{
	    			for(int j=start;j<end;j++){
		     			mids.add(listmids[j]);
		     		}
		    	}	
		     	}else{
		     	  	info.setTotal(1);
		     		mids.add(sm.getSimids());
		     	}
	     	stage.setMids(mids);
	     	List<SubJectArticleBo> list = subjectArticleServiceImpl.filterSimlarArticle(stage);
	     	for(int i=0;i<list.size();i++){
	     		if(null!=list.get(i).getDataSource()){
	     			if(list.get(i).getDataSource().equals(AppConstant.mediaText.WEIXIN)){
		     			list.get(i).setDataSource(list.get(i).getAuthor());
		     		}else if(list.get(i).getDataSource().equals(AppConstant.mediaText.WEIBO)){
		     			list.get(i).setDataSource(list.get(i).getAuthor());
		     		}else if(list.get(i).getDataSource().equals(AppConstant.mediaText.TIEBA)){
		     			list.get(i).setDataSource(list.get(i).getAuthor());
		     		}
	     		}
	     		
	     	}
    		info.setPageNum(page.getPageNum());
    		info.setPageSize(page.getPageSize());
    		info.setNavigatePages(8);
	     	info.setDatas(list);
	     	if(info!=null){
	     		return new Response(ResponseStatus.Success,info, true);
	     	}else{
	     		return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
	     	}
			
		}else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/exportSimlar", method = RequestMethod.GET)
	public Response exportSimlar(String mid,HttpServletRequest request,HttpServletResponse response){
		 SubjectMArticle sm = subjectArticleServiceImpl.selectSimids(mid);
		 if(null!=sm){
			 List<String> mids = new ArrayList<String>();
			   String simids= sm.getSimids();
			   SubJectArticleBo stage = new SubJectArticleBo();
			   if(null!=simids && !"".equals(simids)&& simids.contains(",")){
		     		String[] listmids = simids.split(",");
		     		for(int i=0;i<listmids.length;i++){
		     			mids.add(listmids[i]);
		     		}
		       }else{
		    	  mids.add(sm.getSimids());
		      }
			 stage.setMids(mids);
			 String address =subjectArticleServiceImpl.outSimlarArticle(stage, request, response);
			 if(!"".equals(address)){
				 return new Response(ResponseStatus.Success,address,true);
			 }else{
				 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
			 }
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		 
	
	}
	@RequestMapping(value = "/selectaddressData", method = RequestMethod.GET)
	public Response selectaddressData(SubJectArticleBo record,String startTime,HttpServletRequest request,SolrPage page){
		String userid = (String)request.getSession().getAttribute("userid");
		record.setUserid(userid);
		System.out.println(startTime);
		SolrPage<SubJectArticleBo> list = subjectArticleServiceImpl.selectAddressArticle(record,request,page);
	   return new Response(ResponseStatus.Success,list,true);
		
	}
	@RequestMapping(value = "/exportMyAddressYuqing", method = RequestMethod.GET)
	public Response exportMyAddressYuqing(HttpServletRequest request, HttpServletResponse response, SubjectArticle record) {
		String s = subjectArticleServiceImpl.daochuAddressYuqing(request, response, record);
		return new Response(ResponseStatus.Success, s, true);

	}
}
