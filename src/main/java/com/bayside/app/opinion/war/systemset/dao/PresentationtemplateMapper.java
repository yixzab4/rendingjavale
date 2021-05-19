package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemset.model.Presentationtemplate;

public interface PresentationtemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Presentationtemplate record);

    int insertSelective(Presentationtemplate record);

    Presentationtemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Presentationtemplate record);

    int updateByPrimaryKey(Presentationtemplate record);
    
    Presentationtemplate selectByUserid(Presentationtemplate record);
    
    int updateByUseridSelective(Presentationtemplate record);
    
}