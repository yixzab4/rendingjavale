package com.bayside.app.opinion.war.report.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.report.bo.OpinionReportBo;
import com.bayside.app.opinion.war.report.model.OpinionReport;
import com.bayside.app.opinion.war.report.service.OpinionReportService;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * <p>Title: OpinionReportController</P>
 * <p>Description:舆情报告的控制层 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Administrator
 * @version 1.0
 * @since 2016年10月4日
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/opinionReportController")
public class OpinionReportController {
	@Autowired
	private OpinionReportService opinionReportServiceImpl;
	/**
	 * 
	 * <p>方法名称：getOpinionReport</p>
	 * <p>方法描述：获取舆情报告</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author Administrator
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getOpinionReport", method = RequestMethod.GET)
	public Response getOpinionReport(OpinionReportBo oReportBo,PageInfo page,HttpServletRequest request){
		String userid = (String) request.getSession().getAttribute("userid");
		oReportBo.setUserid(userid);
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<OpinionReport> list = opinionReportServiceImpl.getOpinionReport(oReportBo);
		PageInfo<OpinionReport> pageInfo = new PageInfo<OpinionReport>(list);
		return new Response(ResponseStatus.Success, pageInfo, true);
	}
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述：获取用户的详情信息</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public Response getUserInfo(String id,HttpServletRequest request){
		//String userid = (String) request.getSession().getAttribute("userid");
		UserBo userBo = opinionReportServiceImpl.getUserInfo(id);
		return new Response(ResponseStatus.Success, userBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getReportInfo</p>
	 * <p>方法描述：获取报告详情</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getReportInfo", method = RequestMethod.GET)
	public Response getReportInfo(String id){
		OpinionReportBo opBo = opinionReportServiceImpl.getReportInfo(id);
		return new Response(ResponseStatus.Success, opBo, true);
	}
	
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述：获取最大数据的专题</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getMaxSubjectInfo", method = RequestMethod.GET)
	public Response getMaxSubjectInfo(SubjectStatisticalBo statisticalBo,HttpServletRequest request){
		/*String userid = (String) request.getSession().getAttribute("userid");
		statisticalBo.setUserid(userid);*/
		SubjectStatisticalBo subBo = opinionReportServiceImpl.getMaxSubjectInfo(statisticalBo);
		return new Response(ResponseStatus.Success, subBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述：获取舆情趋势</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getOpinionTrend", method = RequestMethod.GET)
	public Response getOpinionTrend(SubjectStatisticalBo statisticalBo,String reportType,HttpServletRequest request){
		/*String userid = (String) request.getSession().getAttribute("userid");
		statisticalBo.setUserid(userid);*/
		List<SubjectStatisticalBo> subBo = opinionReportServiceImpl.getOpinionTrend(statisticalBo,reportType);
		return new Response(ResponseStatus.Success, subBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述：获取最大数据的专题</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getOpinionMediaType", method = RequestMethod.GET)
	public Response getOpinionMediaType(SubjectStatisticalBo statisticalBo,HttpServletRequest request){
	/*	String userid = (String) request.getSession().getAttribute("userid");
		statisticalBo.setUserid(userid);*/
		SubjectStatisticalBo subBo = opinionReportServiceImpl.getOpinionMediaType(statisticalBo);
		return new Response(ResponseStatus.Success, subBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述：获取活跃媒体</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getActiveMedia", method = RequestMethod.GET)
	public Response getActiveMedia(SubjectParamBo subjectParamBo,HttpServletRequest request){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);*/
		List<Map<String,Object>> subBo = opinionReportServiceImpl.getActiveMedia(subjectParamBo);
		return new Response(ResponseStatus.Success, subBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述：获取情感</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/selectEmotionAnaly", method = RequestMethod.GET)
	public Response selectEmotionAnaly(SubjectStatisticalBo subjectStatisticalBo,HttpServletRequest request){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectStatisticalBo.setUserid(userid);*/
		Map<String, Object> subBo = opinionReportServiceImpl.selectEmotionAnaly(subjectStatisticalBo);
		return new Response(ResponseStatus.Success, subBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getAttentionOpinion</p>
	 * <p>方法描述：重点舆情</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getAttentionOpinion", method = RequestMethod.GET)
	public Response getAttentionOpinion(SubjectParamBo subjectParamBo,HttpServletRequest request){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);*/
		List<Map<String, Object>> subBo = opinionReportServiceImpl.getAttentionOpinion(subjectParamBo);
		return new Response(ResponseStatus.Success, subBo, true);
	}
	/**
	 * 
	 * <p>方法名称：saveImg</p>
	 * <p>方法描述：重点舆情</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/saveImg", method = RequestMethod.POST)
	public Response saveImg(String opinionTrend,String opinionMedia,HttpServletRequest request){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);*/
		 opinionReportServiceImpl.saveImg(opinionTrend,opinionMedia);
		return new Response(ResponseStatus.Success, null, true);
	}
	/**
	 * 
	 * <p>方法名称：saveImg</p>
	 * <p>方法描述：重点舆情</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/privew", method = RequestMethod.GET)
	public Response privew(String id,HttpServletRequest request,HttpServletResponse response){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);*/
		String html = opinionReportServiceImpl.privew(id,request,response);
		if(html!=null&&!"".equals(html)){
			return new Response(ResponseStatus.Success, html, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
		
	}
	/**
	 * 
	 * <p>方法名称：saveImg</p>
	 * <p>方法描述：下载word</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/downloadWorld", method = RequestMethod.GET)
	public void downloadWorld(String id,HttpServletRequest request,HttpServletResponse response){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);*/
		opinionReportServiceImpl.downloadWorld(id,request,response);
		//return new Response(ResponseStatus.Success, null, true);
	}
	/**
	 * 
	 * <p>方法名称：saveImg</p>
	 * <p>方法描述：下载html</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/downloadHtml", method = RequestMethod.GET)
	public void downloadHtml(String id,HttpServletRequest request,HttpServletResponse response){
		/*String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);*/
		opinionReportServiceImpl.downloadHtml(id,request,response);
		//return new Response(ResponseStatus.Success, null, true);
	}
	/**
	 * 
	 * <p>方法名称：saveImg</p>
	 * <p>方法描述：重点舆情</p>
	 * @param oReportBo
	 * @param page
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 sql  创建   <p>
	 */
	@RequestMapping(value = "/getReportDataInfo", method = RequestMethod.GET)
	public Response getReportDataInfo(SubjectParamBo subjectParamBo,HttpServletRequest request,String id){
		String userid = (String) request.getSession().getAttribute("userid");
		subjectParamBo.setUserid(userid);
		Map<String,Object> list = opinionReportServiceImpl.getReportDataInfo(subjectParamBo,id);
		return new Response(ResponseStatus.Success, list, true);
	}
	
}
