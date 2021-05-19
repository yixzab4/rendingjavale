package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
/**
 * <p>Title: SubjectMArticleService</P>
 * <p>Description: 专题与专题文章的中间表是service</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectMArticleService {

	/**
	 * <p>方法名称：getArticleIdByid</p>
	 * <p>方法描述：根据用户id,专题id获取所属专题的文章id</p>
	 * @param userId
	 * @param subjectId
	 * @return
	 * @author Administrator
	 * @since  2016年7月21日
	 * <p> history 2016年7月21日 Administrator  创建   <p>
	 */
	List<String> getArticleIdByid(String userId,String subjectId);
	
	/**
	 * <p>方法名称：getSubjectById</p>
	 * <p>方法描述：根据用户id获取专题id</p>
	 * @param userId
	 * @return
	 * @author Administrator
	 * @since  2016年7月21日
	 * <p> history 2016年7月21日 Administrator  创建   <p>
	 */
	List<Subject> getSubjectById(String userId,String classifyid);
	
	/**
	 * <p>方法名称：getArticleIdForSearch</p>
	 * <p>方法描述：solr查询数据与专题数据取交集</p>
	 * @param sql
	 * @param userId
	 * @param subjectId
	 * @return
	 * @author Administrator
	 * @since  2016年7月21日
	 * <p> history 2016年7月21日 Administrator  创建   <p>
	 */
	List<String> getArticleIdForSearch(String sql,String sqlTwo, String searchTall,String searchall,String userId,String subjectId,List<String> mysqlId,SubJectArticleBo record);
	
	/**
	 * <p>方法名称：updateById</p>
	 * <p>方法描述：根据文章id更改属性</p>
	 * @param obj
	 * @return
	 * @author Administrator
	 * @since  2016年7月26日
	 * <p> history 2016年7月26日 Administrator  创建   <p>
	 */
	int updateById(SubjectMArticle obj);
	
	int deleteByid(SubjectMArticle obj);  
	/**
	 * 
	 * <p>方法名称：selectArticleById</p>
	 * <p>方法描述：通过articleid 查询文章信息</p>
	 * @param record
	 * @return
	 * @author Administrator
	 * @since  2016年9月29日
	 * <p> history 2016年9月29日 Administrator  创建   <p>
	 */
	SubjectMArticle selectArticleById(SubjectMArticle record);
	SubjectMArticle selectMAById(SubjectMArticle record);
	int	deleteByPrimaryKey(String id);
	int updateEmotion(SubjectMArticle record);
	 int updatePersonAttention(PersonmanagemarticleBo record);
	 int updateWarning(SubjectMArticle obj,Systemmessage record,HttpServletRequest request);
	
}
