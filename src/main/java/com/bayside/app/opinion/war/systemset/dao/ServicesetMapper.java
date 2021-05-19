package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemset.model.Serviceset;

public interface ServicesetMapper {
    int deleteByPrimaryKey(String id);

    int insert(Serviceset record);

    int insertSelective(Serviceset record);

    Serviceset selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Serviceset record);

    int updateByPrimaryKey(Serviceset record);
    
    List<Serviceset> selectAllServiceset(String userid);
}