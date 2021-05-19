package com.bayside.app.opinion.war.knowledge.dao;

import java.util.List;

import com.bayside.app.opinion.war.knowledge.model.Relevant;

public interface RelevantMapper {
    int deleteByPrimaryKey(String id);

    int insert(Relevant record);

    int insertSelective(Relevant record);

    Relevant selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Relevant record);

    int updateByPrimaryKey(Relevant record);
    
    List<Relevant> selectBySelective(Relevant record);
    List<Relevant> selectByRevelatByUserid(String userid);
}