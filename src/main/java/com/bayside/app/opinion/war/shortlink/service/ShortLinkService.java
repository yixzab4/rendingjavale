package com.bayside.app.opinion.war.shortlink.service;

import java.util.List;
import java.util.Map;

import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.shortlink.model.ShortLink;
import com.github.pagehelper.PageInfo;

public interface ShortLinkService {
	/**
	 * 
	 * <p>方法名称：getArticleList</p>
	 * <p>方法描述：获取消息列表</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @param page 
	 * @since  2017年3月17日
	 * <p> history 2017年3月17日 Administrator  创建   <p>
	 */
	List<Map<String, Object>> getArticleList(String id, PageInfo page);
	/**
	 * 
	 * <p>方法名称：getArticleDetil</p>
	 * <p>方法描述：查询文章详情</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2017年3月18日
	 * <p> history 2017年3月18日 Administrator  创建   <p>
	 */
	Map<String,Object> getArticleDetil(String id);
	int insertShort(ShortLink record);

}
