package com.bayside.app.opinion.war.index.dao;

import com.bayside.app.opinion.war.index.model.Monitorall;

public interface MonitorallMapper {
    int deleteByPrimaryKey(String id);

    int insert(Monitorall record);

    int insertSelective(Monitorall record);

    Monitorall selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Monitorall record);

    int updateByPrimaryKey(Monitorall record);
    
    Monitorall getNumberLatestTime();
    
    Monitorall getAllMonitorNumber();
}