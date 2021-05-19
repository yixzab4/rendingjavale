package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;

/**
 * <p>Title: SubjectMArticleMapper</P>
 * <p>Description:专题与专题文章的中间表的映射类 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectMArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectMArticle record);

    int insertSelective(SubjectMArticle record);

    SubjectMArticle selectByPrimaryKey(String id);
    SubjectMArticle selectsmids(String id);

    int updateByPrimaryKeySelective(SubjectMArticle record);

    int updateByPrimaryKey(SubjectMArticle record);
    
    /**
     * <p>方法名称：getArticleById</p>
     * <p>方法描述：根据用户id或者专题id获取文章id列表</p>
     * @param id
     * @return
     * @author Administrator
     * @since  2016年7月18日
     * <p> history 2016年7月18日 Administrator  创建   <p>
     */
    List<String> getArticleById(String userid,String subjectid);
    
    /**
     * <p>方法名称：updateById</p>
     * <p>方法描述：根据id更改属性</p>
     * @param id
     * @return
     * @author Administrator
     * @since  2016年7月26日
     * <p> history 2016年7月26日 Administrator  创建   <p>
     */
    int updateById(SubjectMArticle record);

    /**
     * <p>方法名称：deleteByid</p>
     * <p>方法描述：根据用户选择的文章id删除专题下的文章</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年7月29日
     * <p> history 2016年7月29日 Administrator  创建   <p>
     */
    int deleteByid(SubjectMArticle record);
    int deletefumianById(@Param("id")String id);
    int updatefumian(SubjectMArticle record);
    SubjectMArticle selectAttention(@Param("articleid")String articleid);
    /**
     * 
     * <p>方法名称：deleteBySubjectId</p>
     * <p>方法描述：通过subjectid删除</p>
     * @param subjectid
     * @author Administrator
     * @since  2016年9月20日
     * <p> history 2016年9月20日 Administrator  创建   <p>
     */
	void deleteBySubjectId(String subjectid);
	/**
	 * 
	 * <p>方法名称：selectArticleById</p>
	 * <p>方法描述：通过articleid 查询信息</p>
	 * @param record
	 * @return
	 * @author Administrator
	 * @since  2016年9月29日
	 * <p> history 2016年9月29日 Administrator  创建   <p>
	 */
	SubjectMArticle selectArticleById(SubjectMArticle record);
	/**
	 * 
	 * <p>方法名称：selectMaxSource</p>
	 * <p>方法描述：查询来源最多的数据</p>
	 * @param userid
	 * @param formats
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author Administrator
	 * @since  2016年10月4日
	 * <p> history 2016年10月4日 Administrator  创建   <p>
	 */
	Map<String,Object> selectMaxFormatSource(@Param("userid")String userid,@Param("formats")String formats,@Param("startTime")String startTime,@Param("endTime")String endTime);
	/**
	 * 
	 * <p>方法名称：selectMaxSource</p>
	 * <p>方法描述：查询来源多的前8条数据</p>
	 * @param userid
	 * @param formats
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author Administrator
	 * @since  2016年10月4日
	 * <p> history 2016年10月4日 Administrator  创建   <p>
	 */
	List<Map<String,Object>> selectMaxSource(SubjectParamBo subjectParamBo);
	/**
	 * 
	 * <p>方法名称：selectAttentionOpinion</p>
	 * <p>方法描述：查询来热点舆情</p>
	 * @param userid
	 * @param formats
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author Administrator
	 * @since  2016年10月4日
	 * <p> history 2016年10月4日 Administrator  创建   <p>
	 */
	List<Map<String,Object>> selectAttentionOpinion(SubjectParamBo subjectParamBo);
	/**
	 * 
	 * <p>方法名称：selectMediaList</p>
	 * <p>方法描述：查询媒体列表</p>
	 * @param subjectParamBo
	 * @return
	 * @author sql
	 * @since  2016年10月19日
	 * <p> history 2016年10月19日 sql  创建   <p>
	 */
	List<SubjectMArticle>  selectMediaList(SubjectParamBo subjectParamBo);
	/**
	 * 
	 * <p>方法名称：updateEmotion</p>
	 * <p>方法描述：更新情感</p>
	 * @param record
	 * @return
	 * @author Administrator
	 * @since  2017年3月1日
	 * <p> history 2017年3月1日 Administrator  创建   <p>
	 */
	int updateEmotion(SubjectMArticle record);
	/**
	 * 
	 * <p>方法名称：selectReportDatainfo</p>
	 * <p>方法描述：查询报告正负面预警数量信息</p>
	 * @return
	 * @author Administrator
	 * @since  2017年3月14日
	 * <p> history 2017年3月14日 Administrator  创建   <p>
	 */
	Map<String,Object> selectReportDataInfo(SubjectParamBo subjectParamBo);
	int deletefumianByObject(SubjectMArticle record);
	int deleteIndexArticle(SubjectMArticle record);
	int deleteIndexSimilarArticle(SubjectMArticle record);
	int updateArticleNoquery(SubjectMArticle record);
}
