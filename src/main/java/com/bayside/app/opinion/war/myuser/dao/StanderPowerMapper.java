package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.model.StanderPower;

public interface StanderPowerMapper {
    int deleteByPrimaryKey(StanderPower record);

    int insert(StanderPower record);

    int insertSelective(StanderPower record);

    StanderPower selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StanderPower record);

    int updateByPrimaryKey(StanderPower record);
    
    List<StanderPower> selectStanderPower(StanderPower record);
}