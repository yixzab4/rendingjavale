package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.FocusCount;

/**
 * <p>Title: FocusCountService</P>
 * <p>Description:舆情监控台下的重点关注表的service </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface FocusCountService {
    
	/**
	 * <p>方法名称：getFocusCount</p>
	 * <p>方法描述：获取当前重点关注的统计数据</p>
	 * @return
	 * @author Administrator
	 * @since  2016年7月7日
	 * <p> history 2016年7月7日 Administrator  创建   <p>
	 */
	List<FocusCount> getFocusCount(String time);
	
	/**
	 * <p>方法名称：getPieDate</p>
	 * <p>方法描述：获取圆饼图显示的数据</p>
	 * @param time
	 * @return
	 * @author Administrator
	 * @since  2016年7月7日
	 * <p> history 2016年7月7日 Administrator  创建   <p>
	 */
	FocusCount getPieDate(String time,String type);
}
