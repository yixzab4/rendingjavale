package com.bayside.app.opinion.war.opinionMonitor.dao;

import com.bayside.app.opinion.war.opinionMonitor.model.DayCount;

/**
 * <p>Title: DayCountMapper</P>
 * <p>Description:今日舆情统计表的映射类 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface DayCountMapper {
    int deleteByPrimaryKey(String id);

    int insert(DayCount record);

    int insertSelective(DayCount record);

    DayCount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DayCount record);

    int updateByPrimaryKey(DayCount record);
    
    /**
     * <p>方法名称：getsumByTime</p>
     * <p>方法描述：根据时间统计舆情状态数据</p>
     * @param time
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    DayCount getsumByTime(String time);
    /**
     * <p>方法名称：getEmotionSum</p>
     * <p>方法描述：获取漏斗图数据</p>
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    DayCount getEmotionSum();
}