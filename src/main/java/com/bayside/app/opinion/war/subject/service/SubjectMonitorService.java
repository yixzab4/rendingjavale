package com.bayside.app.opinion.war.subject.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.bo.OrdinarySiteBo;
import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.bo.SubjectClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectHotspotBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMediaBo;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.bo.SubjectTiebaBo;
import com.bayside.app.opinion.war.subject.bo.SubjectWeiboBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.subject.model.SubjectWeibo;

public interface SubjectMonitorService {
	/**
	 * 
	 * <p>方法名称：getSubjectClassify</p>
	 * <p>方法描述：查询分类列表</p>
	 * @return
	 * @author sql
	 * @since  2016年7月13日
	 * <p> history 2016年7月13日 sql  创建   <p>
	 */
	List<SubjectClassifyBo> getSubjectClassify(SubjectClassifyBo classifyBo);
	/**
	 * 
	 * <p>方法名称：saveSubject</p>
	 * <p>方法描述：保存</p>
	 * @param subjectBo
	 * @author sql
	 * @return 
	 * @since  2016年7月14日
	 * <p> history 2016年7月14日 sql  创建   <p>
	 */
	boolean saveSubject(SubjectBo subjectBo,String ownindustry);
	/**
	 * 
	 * <p>方法名称：getSubject</p>
	 * <p>方法描述：获取专题</p>
	 * @param subjectBo
	 * @return
	 * @author sql
	 * @since  2016年7月15日
	 * <p> history 2016年7月15日 sql  创建   <p>
	 */
	List<SubjectBo> getSubject(SubjectBo subjectBo);
	/**
	 * 获取去统计数据
	 * <p>方法名称：getSubjectStatistical</p>
	 * <p>方法描述：</p>
	 * @param statisticalBo
	 * @return
	 * @author sql
	 * @since  2016年7月15日
	 * <p> history 2016年7月15日 sql  创建   <p>
	 */
	SubjectStatisticalBo getSubjectStatistical(SubjectStatisticalBo statisticalBo);
	/**
	 * 获取专题追踪描述的数据
	 * <p>方法名称：selectByTimeInterval</p>
	 * <p>方法描述：</p>
	 * @param subjectid
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author sql
	 * @since  2016年7月25日
	 * <p> history 2016年7月25日 sql  创建   <p>
	 */
	List<SubjectStatisticalBo> getSubjectTrackingDesc(SubjectStatisticalBo statisticalBo);
	/**
	 * 
	 * <p>方法名称：getfristmedia</p>
	 * <p>方法描述：获取首发媒体数据</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年7月28日
	 * <p> history 2016年7月28日 sql  创建   <p>
	 */
	List<SubJectArticleBo> getfristmedia(SubjectParamBo sParamBo);
	/**
	 * 
	 * <p>方法名称：pubmsgtop</p>
	 * <p>方法描述：获取网站发布top10</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年7月28日
	 * <p> history 2016年7月28日 sql  创建   <p>
	 */
	List<Map<String, Object>> getPubmsgtop(SubjectParamBo sParamBo);
	/**
	 * 
	 * <p>方法名称：getMinHotWord</p>
	 * <p>方法描述：获取最小时间热点词</p>
	 * @param sHotspotBo
	 * @return
	 * @author sql
	 * @since  2016年7月28日
	 * <p> history 2016年7月28日 sql  创建   <p>
	 */
	List<Map<String, Object>> getHotTrendWord(SubjectHotspotBo sHotspotBo);
	/**
	 * 
	 * <p>方法名称：getHotTrendLine</p>
	 * <p>方法描述：发展趋势数据</p>
	 * @param sHotspotBo
	 * @return
	 * @author sql
	 * @since  2016年7月29日
	 * <p> history 2016年7月29日 sql  创建   <p>
	 */
	List<SubjectHotspotBo> getHotTrendLine(SubjectHotspotBo sHotspotBo);
	/**
	 * 
	 * <p>方法名称：getMediaInfluence</p>
	 * <p>方法描述：获取媒体影响力分布</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年8月1日
	 * <p> history 2016年8月1日 sql  创建   <p>
	 */
	List<OrdinarySiteBo> getMediaInfluence(SubjectParamBo sParamBo);
	/**
	 * 
	 * <p>方法名称：getWeiboTrend</p>
	 * <p>方法描述：获取微博发展趋势</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年8月2日
	 * <p> history 2016年8月2日 sql  创建   <p>
	 */
	List<SubjectWeiboBo> getWeiboTrend(SubjectWeiboBo subjectWeiboBo);
	/**
	 * 
	 * <p>方法名称：getSubWeiboStat</p>
	 * <p>方法描述：微博统计</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年8月2日
	 * <p> history 2016年8月2日 sql  创建   <p>
	 */
	SubjectWeiboBo getSubWeiboStat(SubjectWeiboBo subjectWeiboBo);
	/**
	 * 
	 * <p>方法名称：getWeiboTrend</p>
	 * <p>方法描述：获取贴吧发展趋势</p>
	 * @param SubjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年8月2日
	 * <p> history 2016年8月2日 sql  创建   <p>
	 */
	List<SubjectTiebaBo> getSubTiebaTrend(SubjectTiebaBo subjectTiebaBo);
	/**
	 * 
	 * <p>方法名称：getViewPointArticle</p>
	 * <p>方法描述：根据观点获取文章</p>
	 * @param subjectParamBo
	 * @return
	 * @author sql
	 * @since  2016年8月5日
	 * <p> history 2016年8月5日 sql  创建   <p>
	 */
	List<SubJectArticleBo> getViewPointArticle(SubjectParamBo subjectParamBo);
	/**
	 * 
	 * <p>方法名称：getMediaList</p>
	 * <p>方法描述：查询媒体 </p>
	 * @param subjectParamBo
	 * @return
	 * @author sql
	 * @since  2016年10月19日
	 * <p> history 2016年10月19日 sql 创建   <p>
	 */
	List<SubjectMArticleBo> getMediaList(SubjectParamBo subjectParamBo);
	//查询近10天媒体类型文章数量
	List<SubjectStatistical> selectMediaArticleNumber(String userid);
	// 近10天 媒体正面数量 -->
	SubjectStatistical selectMediaArticlePositive(String userid);
    //负面
	SubjectStatistical selectMediaArticleNegative(String userid);
	//zhongxin 
	SubjectStatistical selectMediaArticleneutral(String userid);
	//当天
	SubjectStatistical selectByTodayMediaArticleNumber(String userid,String subjectid);
	//一周内
	SubjectStatistical selectByWeekMediaArticleNumber(String userid,String subjectid);
	//自定义时间
    SubjectStatistical selectByDefinedArticleNumber(String userid,String subjectid,String startTime,String endTime);
    //当天正面数量
    SubjectStatistical selectByTodayMediaArticlePositive(String userid,String subjectid);
    //当天负面数量
    SubjectStatistical selectByTodayMediaArticleNegative(String userid,String subjectid);
    //当天中性数量
    SubjectStatistical selectByTodayMediaArticleneutral(String userid,String subjectid);
    //week正面数量
    SubjectStatistical selectByWeekMediaArticlePositive(String userid,String subjectid);
    //week负面数量
    SubjectStatistical selectByWeekMediaArticleNegative(String userid,String subjectid);
    //week中性数量
    SubjectStatistical selectByWeekMediaArticleneutral(String userid,String subjectid);
    //自定义时间 正面
    SubjectStatistical selectByDefinedMediaArticlePositive(String userid,String subjectid,String startTime,String endTime);
    //自定义时间 负面
    SubjectStatistical selectByDefinedMediaArticleNegative(String userid,String subjectid,String startTime,String endTime);
    //自定义时间 中面
    SubjectStatistical selectByDefinedMediaArticleneutral(String userid,String subjectid,String startTime,String endTime);
    
    //时间为空
    SubjectStatistical selectNoTimeMediaArticlePositive(String userid,String subjectid);
    SubjectStatistical selectNoTimeMediaArticleNegative(String userid,String subjectid);
    SubjectStatistical selectNoTimeMediaArticleneutral(String userid,String subjectid);
    SubjectStatistical selectNoTimeArticleNumber(String userid,String subjectid);
    /**
     * 
     * <p>方法名称：getSubjectById</p>
     * <p>方法描述：通过主键id获取专题</p>
     * @param id
     * @return
     * @author sql
     * @since  2016年9月20日
     * <p> history 2016年9月20日 sql  创建   <p>
     */
	SubjectBo getSubjectById(String id);
	/**
	 * 
	 * <p>方法名称：updateSubjectById</p>
	 * <p>方法描述：通过id修改专题</p>
	 * @param subjectBo
	 * @return
	 * @author Administrator
	 * @since  2016年9月20日
	 * <p> history 2016年9月20日 Administrator  创建   <p>
	 */
	boolean updateSubjectById(SubjectBo subjectBo,String ownindustry);
	/**
	 * 
	 * <p>方法名称：deleteSubject</p>
	 * <p>方法描述：删除专题</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2016年9月20日
	 * <p> history 2016年9月20日 Administrator  创建   <p>
	 */
	boolean deleteSubjectById(Subject subject,String userid);
	/**
     * 
     * <p>方法名称：getMediaTimeShaft</p>
     * <p>方法描述：媒体时间轴</p>
     * @param sParamBo
     * @return
     * @author sql
     * @since  2016年9月22日
     * <p> history 2016年9月22日 sql  创建   <p>
     */
	List<Map<String, String>> getMediaTimeShaft(SubjectParamBo sParamBo);
	 /**
     * 
     * <p>方法名称：getInfoTimeShaft</p>
     * <p>方法描述：信息时间轴</p>
     * @param sParamBo
     * @return
     * @author sql
     * @since  2016年9月22日
     * <p> history 2016年9月22日 sql  创建   <p>
     */
	List<Map<String, String>> getInfoTimeShaft(SubjectParamBo sParamBo);
	/**
	 * 
	 * <p>方法名称：getArticles</p>
	 * <p>方法描述：获取文章</p>
	 * @param suParamBo
	 * @return
	 * @author Administrator
	 * @since  2016年9月24日
	 * <p> history 2016年9月24日 Administrator  创建   <p>
	 */
	List<SubJectArticleBo> getArticles(SubjectParamBo suParamBo);
	/**
	 * 
	 * <p>方法名称：getBloggerTop</p>
	 * <p>方法描述：微博top10</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月25日
	 * <p> history 2016年10月25日 Administrator  创建   <p>
	 */
	List<SubjectWeiboBo> getBloggerTop(SubjectWeiboBo subjectWeiboBo);
	/**
	 * 
	 * <p>方法名称：getBloggerProvince</p>
	 * <p>方法描述：微博地域</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月25日
	 * <p> history 2016年10月25日 Administrator  创建   <p>
	 */
	List<SubjectWeiboBo> getBloggerProvince(SubjectWeiboBo subjectWeiboBo);
	/**
	 * 
	 * <p>方法名称：getWeiboEmotion</p>
	 * <p>方法描述：微博情感属性</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	List<SubjectWeiboBo> getWeiboEmotion(SubjectWeiboBo subjectWeiboBo);
	/**
	 * 
	 * <p>方法名称：getCommentRepeat</p>
	 * <p>方法描述：评论转发</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	SubjectWeiboBo getCommentRepeat(SubjectWeiboBo subjectWeiboBo);
	/**
	 * 
	 * <p>方法名称：getSubTiebaTop</p>
	 * <p>方法描述：贴吧排行</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	List<SubjectTiebaBo> getSubTiebaTop(SubjectTiebaBo subjectTiebaBo);
	/**
	 * 
	 * <p>方法名称：getSubTiebaProvince</p>
	 * <p>方法描述：贴吧区域</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	List<SubjectTiebaBo> getSubTiebaProvince(SubjectTiebaBo subjectTiebaBo);
	/**
	 * 
	 * <p>方法名称：getsubTiebaEmotion</p>
	 * <p>方法描述：贴吧区域</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	List<SubjectTiebaBo> getsubTiebaEmotion(SubjectTiebaBo subjectTiebaBo);
	/**
	 * 
	 * <p>方法名称：getsubTiebaActive</p>
	 * <p>方法描述：活跃贴吧数</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	SubjectTiebaBo getsubTiebaActive(SubjectTiebaBo subjectTiebaBo);
	/**
	 * 
	 * <p>方法名称：getsubArticleTop</p>
	 * <p>方法描述：获取文章top</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 Administrator  创建   <p>
	 */
	List<SubjectTiebaBo> getsubTiebaArticleTop(SubjectTiebaBo subjectTiebaBo);
	/**
	 * 
	 * <p>方法名称：getMediatrend</p>
	 * <p>方法描述：载体趋势分布</p>
	 * @param statisticalBo
	 * @return
	 * @author Administrator
	 * @since  2016年11月16日
	 * <p> history 2016年11月16日 Administrator  创建   <p>
	 */
	List<SubjectStatisticalBo> getMediatrend(SubjectStatisticalBo statisticalBo);
	/**
	 * 
	 * <p>方法名称：newopinion</p>
	 * <p>方法描述：观点分析</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2017年2月22日
	 * <p> history 2017年2月22日 Administrator  创建   <p>
	 */
	List<SubjectStatisticalBo> newopinion(SubjectStatisticalBo record);
	List<SubJectArticleBo> opinionArticleList(SubJectArticleBo record);
	SubJectArticleBo getArticlesTotal(SubjectParamBo sParamBo);
	List<Map<String,String>> getMediaTime(SubjectParamBo sParamBo);
	List<Map<String,String>> getInfoTime(SubjectParamBo sParamBo);
	SubJectArticleBo opinionTotal(SubJectArticleBo record);
	int cleanSubjectById(String id);
	SubjectWeibo getSubWeiboTotal(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboGender(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboActive(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboRenzheng(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboRepublic(SubjectWeibo subjectWeibo);

}
