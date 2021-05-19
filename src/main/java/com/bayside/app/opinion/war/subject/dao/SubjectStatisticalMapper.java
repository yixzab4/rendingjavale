package com.bayside.app.opinion.war.subject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;

public interface SubjectStatisticalMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectStatistical record);

    int insertSelective(SubjectStatistical record);

    SubjectStatistical selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectStatistical record);

    int updateByPrimaryKey(SubjectStatistical record);
    /**
     * 
     * <p>方法名称：selectNewest</p>
     * <p>方法描述：查询最新的一条数据</p>
     * @param subjectid
     * @return
     * @author sql
     * @since  2016年9月20日
     * <p> history 2016年9月20日 sql  创建   <p>
     */
    SubjectStatistical selectNewest(String subjectid);
    /**
     * 
     * <p>方法名称：selectSumAcount</p>
     * <p>方法描述：根据时间间隔查询总数</p>
     * @param subjectid
     * @param startTime
     * @param endTime
     * @param emotion
     * @return
     * @author Administrator
     * @since  2016年7月26日
     * <p> history 2016年7月26日 Administrator  创建   <p>
     */
    SubjectStatistical getSubjectStatistical(SubjectStatisticalBo record);
    /**
     * <p>方法名称：selectByTimeInterval</p>
     * <p>方法描述：查询时间区间的数据</p>
     * @param statisticalBo
     * @return
     * @author sql
     * @since  2016年7月25日
     * <p> history 2016年7月25日 sql  创建   <p>
     */
    List<SubjectStatistical> getSubjectTrackingDesc(SubjectStatisticalBo statisticalBo);
    /**
     * 
     * <p>方法名称：selectMaxSubjectInfo</p>
     * <p>方法描述：查询数据量最大的专题</p>
     * @param statisticalBo
     * @return
     * @author Administrator
     * @since  2016年10月4日
     * <p> history 2016年10月4日 Administrator  创建   <p>
     */
    SubjectStatistical  selectMaxSubjectInfo(SubjectStatisticalBo statisticalBo);
    /**
     * 
     * <p>方法名称：selectAcount</p>
     * <p>方法描述：查询某一段时间内的数据总量</p>
     * @param statisticalBo
     * @return
     * @author sql
     * @since  2016年10月4日
     * <p> history 2016年10月4日 sql  创建   <p>
     */
    List<SubjectStatistical> selectAcount(SubjectStatisticalBo statisticalBo);
    /**
     * 
     * <p>方法名称：selectDayreportTrend</p>
     * <p>方法描述：日报中的舆情走势图</p>
     * @param statisticalBo
     * @return
     * @author Administrator
     * @since  2016年10月5日
     * <p> history 2016年10月5日 Administrator  创建   <p>
     */
    List<SubjectStatistical> selectDayReportTrend(SubjectStatisticalBo statisticalBo);
    /**
     * 
     * <p>方法名称：selectReportTrend</p>
     * <p>方法描述：周报或者月报的趋势图</p>
     * @param statisticalBo
     * @return
     * @author Administrator
     * @since  2016年10月5日
     * <p> history 2016年10月5日 Administrator  创建   <p>
     */
    List<SubjectStatistical> selectReportTrend(SubjectStatisticalBo statisticalBo);
    /**
     * 
     * <p>方法名称：selectOpinionMediaType</p>
     * <p>方法描述：查询某一时间段的载体趋势分布图</p>
     * @param userid
     * @return
     * @author sql
     * @since  2016年10月5日
     * <p> history 2016年10月5日 sql  创建   <p>
     */
    SubjectStatistical selectOpinionMediaType(SubjectStatisticalBo statisticalBo);
    /**
     * 
     * <p>方法名称：selectEmotionAnaly</p>
     * <p>方法描述：查询情感分析</p>
     * @param statisticalBo
     * @return
     * @author sql
     * @since  2016年10月6日
     * <p> history 2016年10月6日 sql  创建   <p>
     */
    Map<String,Object> selectEmotionAnaly(SubjectStatisticalBo statisticalBo);
    
    //selectMediaArticleNumber selectMediaArticlePositive selectMediaArticleNegative selectMediaArticleneutral
    List<SubjectStatistical> selectMediaArticleNumber(@Param("userid")String userid);
    SubjectStatistical selectMediaArticlePositive(@Param("userid")String userid);
    SubjectStatistical selectMediaArticleNegative(@Param("userid")String userid);
    SubjectStatistical selectMediaArticleneutral(@Param("userid")String userid);
     //当天总数量
    SubjectStatistical selectBydayArticleNumber(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //一周内数量
    SubjectStatistical selectByWeekArticleNumber(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //自定义时间
    SubjectStatistical selectByDefinedArticleNumber(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime);
    //当天正面数量
    SubjectStatistical selectByTodayMediaArticlePositive(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //当天负面数量
    SubjectStatistical selectByTodayMediaArticleNegative(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //当天中性数量
    SubjectStatistical selectByTodayMediaArticleneutral(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //week正面数量
    SubjectStatistical selectByWeekMediaArticlePositive(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //week负面数量
    SubjectStatistical selectByWeekMediaArticleNegative(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //week中性数量
    SubjectStatistical selectByWeekMediaArticleneutral(@Param("userid")String userid,@Param("subjectid")String subjectid);
    //自定义时间 正面
    SubjectStatistical selectByDefinedMediaArticlePositive(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime);
    //自定义时间 负面
    SubjectStatistical selectByDefinedMediaArticleNegative(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime);
    //自定义时间 中面
    SubjectStatistical selectByDefinedMediaArticleneutral(@Param("userid")String userid,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime);
    //时间为空
    SubjectStatistical selectNoTimeMediaArticlePositive(@Param("userid")String userid,@Param("subjectid")String subjectid);
    SubjectStatistical selectNoTimeMediaArticleNegative(@Param("userid")String userid,@Param("subjectid")String subjectid);
    SubjectStatistical selectNoTimeMediaArticleneutral(@Param("userid")String userid,@Param("subjectid")String subjectid);
    SubjectStatistical selectNoTimeArticleNumber(@Param("userid")String userid,@Param("subjectid")String subjectid);
    
    //首页查询信息
   // SubjectStatistical selectByTimeAcount(@Param("time")String time,@Param("emotion")String emotion);
    List<SubjectStatisticalBo> selectByTimeAcount(SubjectStatisticalBo record);
    //首页查询媒体信息selectMediaAcountByTime
    SubjectStatistical selectMediaAcountByTime(@Param("time")String time,@Param("userid")String userid);
    List<SubjectStatistical> selectzaitiByTime(@Param("updatetime")String time,@Param("userid")String userid,@Param("emotion")String emotion);
    SubjectStatistical  selectSumAcountByTime(@Param("time")String time,@Param("userid")String userid,@Param("emotion")String emotion);

    List<SubjectStatistical> inittongjiemotion(SubjectStatisticalBo record);
    SubjectStatistical inittongjifumian(SubjectStatisticalBo record); 
    List<SubjectStatistical> tongjimedia(SubjectStatisticalBo record);
    List<SubjectStatistical> indextongjimedia(SubjectStatisticalBo record);
    List<SubjectStatistical> timetongjimedia(SubjectStatisticalBo record);

    /**
     * 
     * <p>方法名称：deleteBySubjectId</p>
     * <p>方法描述：根据专题id删除数据</p>
     * @param id
     * @author sql
     * @since  2016年9月20日
     * <p> history 2016年9月20日 sql  创建   <p>
     */
	int deleteBySubjectId(String subjectid);
	List<SubjectStatisticalBo> selectTodayzaitiByTime(SubjectStatisticalBo record);
	//newselectSumAcountByTime

	List<SubjectStatistical> getMediatrend(SubjectStatisticalBo statisticalBo);//
	List<SubjectStatistical> tiaojiantongjiemotion(@Param("medialist")List<String>medialist,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("userid")String userid,@Param("classifyid")String classifyid,@Param("trade")Integer trade);
	List<SubjectStatisticalBo> daytiaojiantongjiemotion(@Param("medialist")List<String>medialist,@Param("subjectid")String subjectid,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("userid")String userid,@Param("classifyid")String classifyid,@Param("trade")Integer trade);
	List<SubjectStatisticalBo> newopinion(SubjectStatisticalBo record);
	List<SubjectStatistical> definedmedia(SubjectStatisticalBo statisticalBo);
	List<SubjectStatisticalBo> indexTodayzaitiByTime(SubjectStatisticalBo statisticalBo);
	List<SubjectStatisticalBo> indexselectzaitiByTime(SubjectStatisticalBo statisticalBo);

}