package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;

/**
 * <p>Title: SubjectArticleMapper</P>
 * <p>Description: 专题文章的映射表</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectArticleMapper {
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
    /**
     * 
     * <p>方法名称：getfristmedia</p>
     * <p>方法描述：获取首发媒体</p>
     * @param map
     * @return
     * @author Administrator
     * @since  2016年7月27日
     * <p> history 2016年7月27日 Administrator  创建   <p>
     */
    List<SubjectArticle> getfristmedia(SubjectParamBo sParamBo);
    /**
     * 
     * <p>方法名称：getPubmsgtop</p>
     * <p>方法描述：获取网站发布信息top10</p>
     * @param sParamBo
     * @return
     * @author Administrator
     * @since  2016年7月28日
     * <p> history 2016年7月28日 Administrator  创建   <p>
     */
    List<Map<String, Object>>getPubmsgtop(SubjectParamBo sParamBo);
    /**
     * 
     * <p>方法名称：selectArticleByActive</p>
     * <p>方法描述：根据条件查询专题下的文章</p>
     * @param sParamBo
     * @return
     * @author sql
     * @since  2016年8月5日
     * <p> history 2016年8月5日 sql 创建   <p>
     */
    List<SubjectArticle> selectArticleByActive(SubjectParamBo sParamBo);
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
    List<Map<String,String>> getMediaTimeShaft(SubjectParamBo sParamBo);
    List<Map<String,String>> getMediaTime(SubjectParamBo sParamBo);
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
    List<Map<String,String>> getInfoTimeShaft(SubjectParamBo sParamBo);
    List<Map<String,String>> getInfoTime(SubjectParamBo sParamBo);
    /**
     * 
     * <p>方法名称：getArticles</p>
     * <p>方法描述：信息时间轴</p>
     * @param sParamBo
     * @return
     * @author sql
     * @since  2016年9月22日
     * <p> history 2016年9月22日 sql  创建   <p>
     */
    List<SubJectArticleBo> getArticles(SubjectParamBo sParamBo);
    /**
     * 
     * <p>方法名称：selectOneInfoById</p>
     * <p>方法描述：根据id查询文章详细信息</p>
     * @param id
     * @return
     * @author Administrator
     * @since  2016年10月5日
     * <p> history 2016年10月5日 Administrator  创建   <p>
     */
    
    SubjectArticle selectOneInfoById(String id);
    
    SubjectArticle selectArticleInfo(String id);
   
    List<Map<String,Object>> selectArticleByUserid(String userid,String pubdate);
    
    Map<String, Object> selectSimhashcode(String id);
    
    List<Map<String, Object>> selectArticlebyIds(String ids[]);

	Map<String, Object> selectArticleDetail(String id);
	List<Map<String,Object>> selectSimilarArticleByUserid(SubJectArticleBo record);
	SubJectArticleBo getArticlesTotal(SubjectParamBo sParamBo);
	List<SubjectArticle> selectAllId();
}