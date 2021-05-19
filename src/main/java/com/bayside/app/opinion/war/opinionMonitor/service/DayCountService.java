package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.DayCount;

/**
 * <p>Title: DayCountService</P>
 * <p>Description: 今日舆情数据统计类</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月1日
 */
public interface DayCountService {
	
    /**
     * <p>方法名称：getDaySum</p>
     * <p>方法描述：获取今日统计数据</p>
     * @return
     * @author Administrator
     * @since  2016年7月1日
     * <p> history 2016年7月1日 Administrator  创建   <p>
     */
	List<DayCount> getDaySum(String date);
	
	/**
	 * <p>方法名称：getEmotionSum</p>
	 * <p>方法描述：获取漏斗图数据</p>
	 * @return
	 * @author Administrator
	 * @since  2016年7月6日
	 * <p> history 2016年7月6日 Administrator  创建   <p>
	 */
	DayCount getEmotionSum();
}
