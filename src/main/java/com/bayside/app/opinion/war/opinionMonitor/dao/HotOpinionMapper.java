package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.HotOpinion;

/**
 * <p>Title: HotOpinionMapper</P>
 * <p>Description:热点舆情的映射表 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface HotOpinionMapper {
    int deleteByPrimaryKey(String id);

    int insert(HotOpinion record);

    int insertSelective(HotOpinion record);

    HotOpinion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HotOpinion record);

    int updateByPrimaryKeyWithBLOBs(HotOpinion record);

    int updateByPrimaryKey(HotOpinion record);
    
    List<HotOpinion> getDayHot(String time);
}