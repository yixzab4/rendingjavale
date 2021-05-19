package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.FocusCount;

/**
 * <p>Title: FocusCountMapper</P>
 * <p>Description:舆情监控台下的重点关注表的映射类 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface FocusCountMapper {
    int deleteByPrimaryKey(String id);

    int insert(FocusCount record);

    int insertSelective(FocusCount record);

    FocusCount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FocusCount record);

    int updateByPrimaryKey(FocusCount record);
    /**
     * <p>方法名称：getByTime</p>
     * <p>方法描述：获取当前重点关注的统计数据</p>
     * @param time
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    List<FocusCount> getByTime(String time);
    
    /**
     * <p>方法名称：getPieByTime</p>
     * <p>方法描述：获取圆饼图显示的数据</p>
     * @param time
     * @param type
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    FocusCount getPieByTime(String time,String type);
}