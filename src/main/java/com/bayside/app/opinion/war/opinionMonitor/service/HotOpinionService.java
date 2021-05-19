package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.HotOpinion;

/**
 * <p>Title: HotOpinionService</P>
 * <p>Description: 热点舆情service层</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface HotOpinionService {
	
	/**
	 * <p>方法名称：getDayOpinion</p>
	 * <p>方法描述：获取今日热点</p>
	 * @param time
	 * @return
	 * @author Administrator
	 * @since  2016年7月6日
	 * <p> history 2016年7月6日 Administrator  创建   <p>
	 */
	List<HotOpinion> getDayHot(String time);
	
	/**
	 * <p>方法名称：getSunOpinion</p>
	 * <p>方法描述：获取近7天热点</p>
	 * @param time
	 * @return
	 * @author Administrator
	 * @since  2016年7月6日
	 * <p> history 2016年7月6日 Administrator  创建   <p>
	 */
	List<HotOpinion> getSunHot(String time);
	
	/**
	 * <p>方法名称：getMonthOpinion</p>
	 * <p>方法描述：获取近30天热点</p>
	 * @param time
	 * @return
	 * @author Administrator
	 * @since  2016年7月6日
	 * <p> history 2016年7月6日 Administrator  创建   <p>
	 */
	List<HotOpinion> getMonthHot(String time);
}
