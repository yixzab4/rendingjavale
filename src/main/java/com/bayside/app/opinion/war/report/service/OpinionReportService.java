package com.bayside.app.opinion.war.report.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.report.bo.OpinionReportBo;
import com.bayside.app.opinion.war.report.model.OpinionReport;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;

public interface OpinionReportService {
	/**
	 * 
	 * <p>方法名称：getOpinionReport</p>
	 * <p>方法描述：分页查询</p>
	 * @param oReportBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月3日
	 * <p> history 2016年10月3日 Administrator  创建   <p>
	 */
	List<OpinionReport> getOpinionReport(OpinionReportBo oReportBo);
	/**
	 * 
	 * <p>方法名称：getUserInfo</p>
	 * <p>方法描述： 获取用户的详情信息</p>
	 * @param userid
	 * @return
	 * @author Administrator
	 * @since  2016年10月4日
	 * <p> history 2016年10月4日 Administrator  创建   <p>
	 */
	UserBo getUserInfo(String userid);
	/**
	 * 
	 * <p>方法名称：getReportInfo</p>
	 * <p>方法描述：获取报告详情信息</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2016年10月4日
	 * <p> history 2016年10月4日 Administrator  创建   <p>
	 */
	OpinionReportBo getReportInfo(String id);
	/**
	 * 
	 * <p>方法名称：getMaxSubjectInfo</p>
	 * <p>方法描述：查询数据量最大的专题</p>
	 * @param statisticalBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月4日
	 * <p> history 2016年10月4日 Administrator  创建   <p>
	 */
	SubjectStatisticalBo getMaxSubjectInfo(SubjectStatisticalBo statisticalBo);
	/**
	 * 
	 * <p>方法名称：getOpinionTrend</p>
	 * <p>方法描述：舆情走势图</p>
	 * @param statisticalBo
	 * @param reportType
	 * @return
	 * @author Administrator
	 * @since  2016年10月5日
	 * <p> history 2016年10月5日 Administrator  创建   <p>
	 */
	List<SubjectStatisticalBo> getOpinionTrend(SubjectStatisticalBo statisticalBo, String reportType);
	/**
	 * 
	 * <p>方法名称：getOpinionMediaType</p>
	 * <p>方法描述：获取载体分布图</p>
	 * @param statisticalBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月5日
	 * <p> history 2016年10月5日 Administrator  创建   <p>
	 */
	SubjectStatisticalBo getOpinionMediaType(SubjectStatisticalBo statisticalBo);
	/**
	 * 
	 * <p>方法名称：getActiveMedia</p>
	 * <p>方法描述：活跃媒体</p>
	 * @param subjectParamBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月5日
	 * <p> history 2016年10月5日 Administrator  创建   <p>
	 */
	List<Map<String, Object>> getActiveMedia(SubjectParamBo subjectParamBo);
	/**
	 * 
	 * <p>方法名称：selectEmotionAnaly</p>
	 * <p>方法描述：查询情感分析</p>
	 * @param subjectParamBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月6日
	 * <p> history 2016年10月6日 Administrator  创建   <p>
	 */
	Map<String, Object> selectEmotionAnaly(SubjectStatisticalBo subjectStatisticalBo);
	/**
	 * 
	 * <p>方法名称：getAttentionOpinion</p>
	 * <p>方法描述：查询热点舆情</p>
	 * @param subjectParamBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月6日
	 * <p> history 2016年10月6日 Administrator  创建   <p>
	 */
	List<Map<String, Object>> getAttentionOpinion(SubjectParamBo subjectParamBo);
	/**
	 * 
	 * <p>方法名称：saveImg</p>
	 * <p>方法描述：保存图片</p>
	 * @param opinionTrend
	 * @author sql
	 * @param opinionMedia 
	 * @since  2016年10月6日
	 * <p> history 2016年10月6日 sql  创建   <p>
	 */
	void saveImg(String opinionTrend, String opinionMedia);
	/**
	 * 
	 * <p>方法名称：privew</p>
	 * <p>方法描述：预览</p>
	 * @param id
	 * @param request
	 * @param response
	 * @author Administrator
	 * @return 
	 * @since  2016年10月13日
	 * <p> history 2016年10月13日 Administrator  创建   <p>
	 */
	String privew(String id, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 
	 * <p>方法名称：downloadWorld</p>
	 * <p>方法描述：下载</p>
	 * @param id
	 * @param request
	 * @param response
	 * @author Administrator
	 * @since  2016年10月13日
	 * <p> history 2016年10月13日 Administrator  创建   <p>
	 */
	void downloadWorld(String id, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 
	 * <p>方法名称：getReportDataInfo</p>
	 * <p>方法描述：查询报告悬浮时的数据详细信息</p>
	 * @param subjectParamBo
	 * @return
	 * @author Administrator
	 * @param id 
	 * @since  2017年3月14日
	 * <p> history 2017年3月14日 Administrator  创建   <p>
	 */
	Map<String, Object> getReportDataInfo(SubjectParamBo subjectParamBo, String id);
	/**
	 * 
	 * <p>方法名称：downloadHtml</p>
	 * <p>方法描述：下载html</p>
	 * @param id
	 * @param request
	 * @param response
	 * @author Administrator
	 * @since  2017年3月14日
	 * <p> history 2017年3月14日 Administrator  创建   <p>
	 */
	void downloadHtml(String id, HttpServletRequest request, HttpServletResponse response);

}
