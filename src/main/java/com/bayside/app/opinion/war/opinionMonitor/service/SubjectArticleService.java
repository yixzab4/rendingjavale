package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.Zfwb;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.util.SolrPage;
import com.github.pagehelper.PageInfo;

/**
 * <p>Title: SubjectArticleService</P>
 * <p>Description:文章service </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectArticleService {
	List<SubJectArticleBo> subjectArtileInsertMess(SubJectArticleBo record);
	List<SubJectArticleBo> myPersonconcern(SubJectArticleBo record);
	List<SubJectArticleBo> newselectAllByUserId(SubJectArticleBo record);
	/**
	 * <p>方法名称：getAllOPinion</p>
	 * <p>方法描述：获取当前用户所有的舆情信息</p>
	 * @param userid
	 * @return
	 * @author Administrator
	 * @since  2016年7月12日
	 * <p> history 2016年7月12日 Administrator  创建   <p>
	 */
	List<SubjectArticle> getAllOPinion(List<String> ids);
	
	/**
	 * <p>方法名称：getByFilter</p>
	 * <p>方法描述：通过过滤条件查询</p>
	 * @param stage
	 * @return
	 * @author Administrator
	 * @since  2016年7月12日
	 * <p> history 2016年7月12日 Administrator  创建   <p>
	 */
	List<SubjectArticle> getByFilter(SubjectArticle stage);
	
	List<SubJectArticleBo> getByFilterCom(SubJectArticleBo stage);
	
	/**
	 * <p>方法名称：getByTime</p>
	 * <p>方法描述：通过时间段查询</p>
	 * @param time
	 * @param time2
	 * @return
	 * @author Administrator
	 * @since  2016年7月12日
	 * <p> history 2016年7月12日 Administrator  创建   <p>
	 */
	List<SubjectArticle> getByTime(String time,String time2,List<String> ids);
	
	
	/**
	 * <p>方法名称：getBysubject</p>
	 * <p>方法描述：根据专题获取专题文章</p>
	 * @param subjectId
	 * @return
	 * @author Administrator
	 * @since  2016年7月18日
	 * <p> history 2016年7月18日 Administrator  创建   <p>
	 */
	List<SubjectArticle> getBysubject(List<String> ids);
	List<SubJectArticleBo> getByArticleId(List<String> list,String subjectid,String uptime,String pubtime);
	
	List<SubJectArticleBo> selectAllByUserId(SubJectArticleBo record);
	//查询近10天 的 全部专题文章数量
	List<SubJectArticleBo> selectAllByDay(String userid,String subjectid);
	//近10天 每隔媒体类型的所有专题文章数量
	List<SubJectArticleBo> selectByDayByMedia(String userid,List<String> medianame,String subjectid);
	//selectAllByDataSource
	List<SubJectArticleBo> selectAllByDataSource(SubjectMArticleBo record);
	//一周内的倾向性文章数量
	List<SubJectArticleBo> selectAllByWeek(String userid,String subjectid,String emotion,List<String> list);
	List<SubJectArticleBo> selectAllByToday(String userid,String subjectid,String emotion,List<String> list);
	List<SubJectArticleBo> selectAllByDefined(String userid,String subjectid,String emotion,List<String> list,String startTime,String endTime);
	   // selectByDataSourceByToday  selectByWeekBySource
	List<SubJectArticleBo> selectByDataSourceByToday(String userid,String subjectid,List<String> list,List<String> list1);
	List<SubJectArticleBo> selectByWeekBySource(String userid,String subjectid,List<String> list,List<String> list1);
	List<SubJectArticleBo> selectByDefinedByDataSource(String userid,String subjectid,List<String> list,List<String> list1,String startTime,String endTime);
  
	 List<SubJectArticleBo> selectInfoByWeek(String userid,String subjectid,String emotion);
	    //获取当天倾向性信息
	 List<SubJectArticleBo> selectInfoByToday(String userid,String subjectid,String emotion);
	 List<SubJectArticleBo> selectDefinedTime(String userid,String subjectid,String emotion,String startTime,String endTime);
	 
	 List<SubJectArticleBo> getByDataSourceByToday(String userid,String subjectid,List<String> list1);
     List<SubJectArticleBo> getByWeekBySource(String userid,String subjectid,List<String> list1);
     List<SubJectArticleBo> getByDefinedByDataSource(String userid,String subjectid,String startTime,String endTime,List<String> list1);
    
     List<SubJectArticleBo> getAllByWeek(String userid,String subjectid,List<String> list);
     //获取当天倾向性信息
    List<SubJectArticleBo> getAllByToday(String userid,String subjectid,List<String> list);
    List<SubJectArticleBo> getAllByDefinedTime(String userid,String subjectid,List<String> list,String startTime,String endTime);
    //获取倾向性信息
    List<SubJectArticleBo> getqingxiang(String userid,String subjectid,String emotion);
    List<SubJectArticleBo> selectqingxiang(String userid,String subjectid,String emotion,List<String> list);
    
    List<SubJectArticleBo> getDateSourceNoTime(String userid,String subjectid,List<String> list,List<String> list1);
    List<SubJectArticleBo> getEmotionAll(String userid,String subjectid,List<String> list);
    /**
     * 
     * <p>方法名称：solrfilterCom</p>
     * <p>方法描述：solr 查询 通过条件筛选数据</p>
     * @param stage
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
    List<SubJectArticleBo> solrfilterCom(SubJectArticleBo stage);
    /**
     * 
     * <p>方法名称：tongjiemotion</p>
     * <p>方法描述：统计分析 统计正负面信息</p>
     * @param stage
     * @return
     * @author Administrator
     * @since  2016年9月21日
     * <p> history 2016年9月21日 Administrator  创建   <p>
     */
    List<SubjectArticle> tongjiemotion(List<String> medialist,String subjectid,String startTime,String endTime,String userid,String classifyid);
	 List<SubjectArticle> tongjifumian(List<String> medialist,String subjectid,String startTime,String endTime,String userid,String emotion,String classifyid);
    List<SubjectStatisticalBo> inittongjiemotion(SubjectStatisticalBo record);
    SubjectStatistical inittongjifumian(SubjectStatisticalBo record);
    List<SubjectStatistical> tongjimedia(SubjectStatisticalBo record);
    List<SubJectArticleBo> tongjisource(List<String> list2,List<String> list1,String userid,String subjectid,String startTime,String endTime,String classifyid,Integer trade);
    List<SubjectStatistical> timetongjimedia(SubjectStatisticalBo record);
    /**
     * 
     * <p>方法名称：myconcern</p>
     * <p>方法描述：查询我的关注</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月27日
     * <p> history 2016年9月27日 Administrator  创建   <p>
     */
    List<SubJectArticleBo> myconcern(SubJectArticleBo record);
    /**
     * 
     * <p>方法名称：selectArticleInfoById</p>
     * <p>方法描述：文章详情页</p>
     * @param userid
     * @param articleid
     * @return
     * @author Administrator
     * @since  2016年9月29日
     * <p> history 2016年9月29日 Administrator  创建   <p>
     */
    SubJectArticleBo selectArticleInfoById(String userid,String articleid);
    //
    
    SubJectArticleBo selectArticleDetailById(String articleid,String id);
    SubjectMArticle selectArticleMById(String id);
    
   // List<SubJectArticleBo> selectAllByUserId(String userid,String subjectid);
    SubjectArticle selectOneInfoById(String id);
    
    SubjectArticle selectArticleInfo(String id);
    List<SubjectArticle> subjectArtileAllInfo(SubJectArticleBo stage);
    List<Subject> checkSubjectByClassifyid(Subject record);
    List<SubjectStatistical> indextongjimedia(SubjectStatisticalBo record,HttpServletRequest request);
    List<SubJectArticleBo> myPaperListInfo(PersonmanagemarticleBo record);
    List<SubjectStatistical> tiaojiantongjiemotion(List<String>medialist,String subjectid,String startTime,String endTime,String userid,String classifyid,Integer trade);
    List<SubjectStatisticalBo> zaitiemotion(List<String>medialist,String subjectid,String startTime,String endTime,String userid,String classifyid,String time,Integer trade);
    List<SubjectStatisticalBo> daytiaojiantongjiemotion(List<String>medialist,String subjectid,String startTime,String endTime,String userid,String classifyid,Integer trade);

    List<String> selectarticles( String search, String searchTwo, String searchTall,
			String searchall, String subjectId, SubJectArticleBo record, HttpSession session,
			HttpServletRequest request);
    /**
     * 
     * <p>方法名称：daochutongji</p>
     * <p>方法描述：导出我的统计</p>
     * @param request
     * @param response
     * @return
     * @author liuyy
     * @since  2016年11月30日
     * <p> history 2016年11月30日 Administrator  创建   <p>
     */
    String daochutongji(HttpServletRequest request, HttpServletResponse response);
    /**
     * 
     * <p>方法名称：myconcern</p>
     * <p>方法描述：我的报纸</p>
     * @param request
     * @param response
     * @param record
     * @return
     * @author liuyy
     * @since  2016年11月30日
     * <p> history 2016年11月30日 Administrator  创建   <p>
     */
    String myconcern(HttpServletRequest request, HttpServletResponse response, SubjectArticle record);
    
    String daochuyuqing(HttpServletRequest request, HttpServletResponse response, SubjectArticle record);
   
     String sendinfo(SubJectArticleBo sa, String type,HttpServletRequest request);

	/**
	 * 
	 * <p>方法名称：getSimArticle</p>
	 * <p>方法描述：获取相似文章</p>
	 * @param record
	 * @return
	 * @author Administrator
	 * @since  2016年11月29日
	 * <p> history 2016年11月29日 Administrator  创建   <p>
	 */
    List<SubJectArticleBo> getSimArticle(SubJectArticleBo record);
    List<SubJectArticleBo> selectAllBySearch(SubJectArticleBo record);
    /**
     * 通过ID从solr中获取数据
     * @param articleid
     * @return
     */
	SubJectArticleBo selectArticleSolrDetailById(String articleid);
	List<SubJectArticleBo> filterSimlarArticle(SubJectArticleBo record);
	SubjectMArticle selectSimids(String id);
	SubJectArticleBo selectAllByPage(SubJectArticleBo record);
	List<SubJectArticleBo> selectAllBYSS(SubJectArticleBo record);
	SubJectArticleBo selectTotalPaperList(PersonmanagemarticleBo record);
	SubJectArticleBo myconcernTotal(SubJectArticleBo record);
	SubJectArticleBo myPaperTotal(SubJectArticleBo record);
	List<SubjectArticle> selectAllId();
	SolrPage<SubJectArticleBo> selectAddressArticle(SubJectArticleBo record,HttpServletRequest request,SolrPage page);
	String daochuAddressYuqing(HttpServletRequest request, HttpServletResponse response, SubjectArticle record);
	List<Zfwb> selectByArticleid(String oId);
	String outSimlarArticle(SubJectArticleBo record,HttpServletRequest request,HttpServletResponse response);
}
