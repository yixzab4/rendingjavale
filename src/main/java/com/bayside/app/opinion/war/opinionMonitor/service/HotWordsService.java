package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.HotWords;

/**
 * <p>Title: HotWordsService</P>
 * <p>Description: 今日热词云图service层</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface HotWordsService {

	/**
	 * <p>方法名称：getAll</p>
	 * <p>方法描述：获取所有热词</p>
	 * @return
	 * @author Administrator
	 * @since  2016年7月6日
	 * <p> history 2016年7月6日 Administrator  创建   <p>
	 */
	List<HotWords> getAll();
}
