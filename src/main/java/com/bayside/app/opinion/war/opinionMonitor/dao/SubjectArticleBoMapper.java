package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;

/**
 * <p>Title: SubjectArticleMapper</P>
 * <p>Description: 专题文章的映射表</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectArticleBoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectArticle record);

    int insertSelective(SubjectArticle record);

    SubjectArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectArticle record);

    int updateByPrimaryKey(SubjectArticle record);
    
    /**
     * <p>方法名称：filter</p>
     * <p>方法描述：根据对象条件过滤文章</p>
     * @param stage
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    List<SubjectArticle> filter(SubjectArticle stage);
    
    List<SubjectArticle> subjectArtileAllInfo(SubJectArticleBo stage); 
    List<SubJectArticleBo> filterCom(SubJectArticleBo stage);
    List<SubJectArticleBo> repeatfilterCom(SubJectArticleBo stage);
    
    /**
     * <p>方法名称：selectBydate</p>
     * <p>方法描述：time<=date<=time2，根据时间段及文章id获取文章对象列表
     * @param time
     * @param time2
     * @return 
     * @author Administrator
     * @since  2016年7月12日
     * <p> history 2016年7月12日 Administrator  创建   <p>
     */
    List<SubjectArticle> selectBydate(String time,String time2,List<String> id);  
    
    /**
     * <p>方法名称：selectAllById</p>
     * <p>方法描述：根据文章id获取专题文章</p>
     * @param id
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    List<SubjectArticle> selectAllById(List<String> id);
    List<SubJectArticleBo> selectAllByArticleId(@Param("list")List<String> list,@Param("subjectid")String subjectid,@Param("uptime")String uptime,@Param("pubtime")String pubtime);
    
    /**
     * <p>方法名称：selectAllByUserId</p>
     * <p>方法描述：通过用户id,专题id获取专题文章</p>
     * @param userid
     * @param subjectid
     * @return
     * @author Administrator
     * @since  2016年7月27日
     * <p> history 2016年7月27日 Administrator  创建   <p>s
     */
    List<SubJectArticleBo> selectAllByUserId(SubJectArticleBo record);
    List<SubJectArticleBo> repeatselectAllByUserId(SubJectArticleBo record);
    List<SubJectArticleBo> newselectAllByUserId(SubJectArticleBo record);
    
    //查询前10天所有专题的文章
    List<SubJectArticleBo> selectAllByDay(@Param("userid")String userid,@Param("subjectid")String subjectid);
     //近10天根据媒体类型查询的全部文章
    List<SubJectArticleBo> selectByDayByMedia(@Param("userid")String userid,@Param("list")List<String> list,@Param("subjectid")String subjectid);
     //查询数据来源近10天的文章数
    
    List<SubJectArticleBo> selectAllByDataSource(SubjectMArticleBo record);
     //自定义时间段获取倾向性信息
    List<SubJectArticleBo> selectAllByWeek(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion,@Param("list")List<String> list);
     //获取当天倾向性信息
    List<SubJectArticleBo> selectAllByToday(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion,@Param("list")List<String> list);
    List<SubJectArticleBo> selectAllByDefinedTime(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion,@Param("list")List<String> list,@Param("startTime")String startTime,@Param("endTime")String endTime);
   // selectByDataSourceByToday  selectByWeekBySource
    List<SubJectArticleBo> selectByDataSourceByToday(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list,@Param("list1")List<String> list1);
    List<SubJectArticleBo> selectByWeekBySource(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list,@Param("list1")List<String> list1);
    //selectByDefinedBySource
    List<SubJectArticleBo> selectByDefinedBySource(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list,@Param("list1")List<String> list1,@Param("startTime")String startTime,@Param("endTime")String endTime);

    //selectInfoByWeek selectInfoByToday selectDefinedTime
    List<SubJectArticleBo> selectInfoByWeek(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion);
    //获取当天倾向性信息
   List<SubJectArticleBo> selectInfoByToday(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion);
   List<SubJectArticleBo> selectDefinedTime(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion,@Param("startTime")String startTime,@Param("endTime")String endTime);
   
   List<SubJectArticleBo> getByDataSourceByToday(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list1")List<String> list1);
   List<SubJectArticleBo> getByWeekBySource(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list1")List<String> list1);
   //selectByDefinedBySource
   List<SubJectArticleBo> getByDefinedBySource(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("list1")List<String> list1);
    
   
   List<SubJectArticleBo> getAllByWeek(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list);
   //获取当天倾向性信息
  List<SubJectArticleBo> getAllByToday(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list);
  List<SubJectArticleBo> getAllByDefinedTime(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list,@Param("startTime")String startTime,@Param("endTime")String endTime);
  //获取倾向性信息
 List<SubJectArticleBo> getqingxiang(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion);
 List<SubJectArticleBo> selectqingxiang(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("emotion")String emotion,@Param("list")List<String> list);
 List<SubJectArticleBo> getDateSourceNoTime(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list,@Param("list1")List<String> list1);
//getEmotionAll
 List<SubJectArticleBo> getEmotionAll(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("list")List<String> list);
 List<SubJectArticleBo> selectlastsubjectarticle(@Param("userid")String userid,@Param("emotion")String emotion,@Param("list")List list);
 List<SubJectArticleBo>  selectnewSixsubjectarticle(@Param("list")List list,@Param("userid")String userid);
 List<SubJectArticleBo>  selectnewWarningarticle(SubJectArticleBo stage);
 List<SubJectArticleBo>  selectnewTypearticle(@Param("formats")String formats,@Param("userid")String userid);
 List<SubJectArticleBo> selectAllSubjectarticle(SubJectArticleBo stage);
 //今日舆情
 List<SubjectArticle> selectmypaperByTime(@Param("time")String time,@Param("personid")String personid,@Param("formats")String formats,@Param("emotion")String emotion,@Param("userid")String userid);
 int deletearticlebyId(@Param("id")String id);
 /**
  * 
  * <p>方法名称：solrfilterCom</p>
  * <p>方法描述：solr查询筛选条件</p>
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
  * <p>方法描述:统计分析 统计全部信息</p>
  * @return
  * @author Administrator
  * @since  2016年9月21日
  * <p> history 2016年9月21日 Administrator  创建   <p>
  */
 List<SubjectArticle> tongjiemotion(@Param("medialist")List<String>medialist,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("userid")String userid,@Param("classifyid")String classifyid);
 List<SubjectArticle> tongjifumian(@Param("medialist")List<String>medialist,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("userid")String userid,@Param("emotion")String emotion,@Param("classifyid")String classifyid);
 List<SubJectArticleBo> tongjisource(@Param("emotionlist")List<String> emotionlist,@Param("formatslist")List<String> formatslist,@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("classifyid")String classifyid,@Param("trade")Integer trade);
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
  * <p>方法描述：查询文章详情页</p>
  * @param userid
  * @param articleid
  * @return
  * @author Administrator
  * @since  2016年9月29日
  * <p> history 2016年9月29日 Administrator  创建   <p>
  */
 SubJectArticleBo selectArticleInfoById(@Param("userid")String userid,@Param("articleid")String articleid);
 List<SubJectArticleBo> myPaperListInfo(PersonmanagemarticleBo record);
 List<SubJectArticleBo> myPersonconcern(SubJectArticleBo record);
 List<SubJectArticleBo> subjectArtileInsertMess(SubJectArticleBo record);
 List<SubJectArticleBo> selectAllBySearch(SubJectArticleBo record);
 List<SubJectArticleBo> repeatselectAllBySearch(SubJectArticleBo record);
 List<SubJectArticleBo> opinionArticleList(SubJectArticleBo record);
 List<SubJectArticleBo> definedSubjectList(SubJectArticleBo record);
 List<SubJectArticleBo> filterSimlarArticle(SubJectArticleBo record);
 SubJectArticleBo selectAllByPage(SubJectArticleBo record);
 SubJectArticleBo repeatselectAllByPage(SubJectArticleBo record);
 List<SubJectArticleBo> selectAllBYSS(SubJectArticleBo record);
 SubJectArticleBo selectPageDetail(String id);
 SubJectArticleBo selectTotalPaperList(PersonmanagemarticleBo record);
 SubJectArticleBo myconcernTotal(SubJectArticleBo record);
 SubJectArticleBo myPaperTotal(SubJectArticleBo record);
 SubJectArticleBo opinionTotal(SubJectArticleBo record);
 List<SubJectArticleBo> outSimlarArticle(SubJectArticleBo record);
 
}

