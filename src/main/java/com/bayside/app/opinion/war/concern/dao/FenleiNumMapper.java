package com.bayside.app.opinion.war.concern.dao;

import com.bayside.app.opinion.war.concern.model.Article;
import java.util.List;
import java.util.Map;
public interface FenleiNumMapper {
   
	/**
	 * 获取专题新闻数量
	 * @param map
	 * @return
	 */
    int GetNewsNum(Map<String, Object> map);
    /**
     * 获取专题微信数量
     * @param map
     * @return
     */
    int GetWeixinNum(Map<String, Object> map);
    /**
     * 获取专题博客数量
     * @param map
     * @return
     */
    int GetBlogNum(Map<String, Object> map);
    /**
     * 获取专题微博数量
     * @param map
     * @return
     */
    int GetWeiboNum(Map<String, Object> map);
    /**
     * 获取专题贴吧数量
     * @param map
     * @return
     */
    int GetTiebaNum(Map<String, Object> map);
    /**
     * 获取专题视频数量
     * @param map
     * @return
     */
    int GetVideoNum(Map<String, Object> map);
    /**
     * 获取专题网页数量
     * @param map
     * @return
     */
    int GetWebNum(Map<String, Object> map);
    /**
     * 获取专题知乎数量
     * @param map
     * @return
     */
    int GetZhihuNum(Map<String, Object> map);
    
	
}