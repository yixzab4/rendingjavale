package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemset.model.CustomIndexModal;

public interface CustomIndexModalMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomIndexModal record);

    int insertSelective(CustomIndexModal record);

    CustomIndexModal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomIndexModal record);

    int updateByPrimaryKey(CustomIndexModal record);
    
    List<CustomIndexModal> selectByUserid(String userid);
    
    List<CustomIndexModal> selectByPosition(CustomIndexModal record);
}