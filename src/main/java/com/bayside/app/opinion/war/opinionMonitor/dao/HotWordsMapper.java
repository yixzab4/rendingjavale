package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.HotWords;

/**
 * <p>Title: HotWordsMapper</P>
 * <p>Description:今日热词云图的映射表 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface HotWordsMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotWords record);

    int insertSelective(HotWords record);

    HotWords selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotWords record);

    int updateByPrimaryKey(HotWords record);
    
    /**
     * <p>方法名称：getAll</p>
     * <p>方法描述：获取所有的热词</p>
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    List<HotWords> getAll();
}