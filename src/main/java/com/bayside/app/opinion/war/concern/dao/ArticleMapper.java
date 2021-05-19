package com.bayside.app.opinion.war.concern.dao;

import com.bayside.app.opinion.war.concern.model.Article;
import java.util.List;
import java.util.Map;
public interface ArticleMapper {
   

    /**
     * 获取用户关注的文章
     * @return
     */
	List<Article> selectByUsersid(Map<String, Object> map);
	 /**
     * 获取用户关注的文章
     * @return
     */
	List<Article> selectByUsersidAll(String id);
	/**
	 * 关注文章按时间排序
	 * @param id
	 * @return
	 */
	List<Article> selectByFabushijian(String id);
	/**
	 * 按点击数排序
	 * @param id
	 * @return
	 */
	List<Article> selectByDianjishu(String id);
	/**
	 * 按评论数排序
	 * @param id
	 * @return
	 */
	List<Article> selectByPinglunshu(String id);
	/**
	 * 获取特定文章内容
	 * @param id
	 * @return
	 */
	List<Article> selectByid(String id);
	/**
	 * 获取全部文章信息
	 * @return
	 */
	List<Article> selectByArticle();
	/**
	 * 文章模糊查询
	 * @param map
	 * @return
	 */
	List<Article> selectByMohuArticle(Map<String, Object> map);
	/**
	 * 根据专题id获取文章简报
	 * @param map
	 * @return
	 */
	List<Article> selectBySubjectRibao(Map<String, Object> map);
	/**
	 * 根据专题ID获取微博简报
	 * @param map
	 * @return
	 */
	List<Article> selectBySubjectWeibo(Map<String, Object> map);
	/**
	 * 推荐阅读
	 * @return
	 */
	List<Article> selectBySubjectTuijian(Map<String, Object> map);
	
}