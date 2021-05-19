package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemset.model.Setpresentationtemplate;

public interface SetpresentationtemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Setpresentationtemplate record);

    int insertSelective(Setpresentationtemplate record);

    Setpresentationtemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Setpresentationtemplate record);

    int updateByPrimaryKey(Setpresentationtemplate record);
    
    List<Setpresentationtemplate> selectAllSetpresentationtemplate(String type);
}