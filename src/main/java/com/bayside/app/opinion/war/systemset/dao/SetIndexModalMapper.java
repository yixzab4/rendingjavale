package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;

public interface SetIndexModalMapper {
    int deleteByPrimaryKey(String id);

    int insert(SetIndexModal record);

    int insertSelective(SetIndexModalBo record);

    SetIndexModal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SetIndexModalBo record);

    int updateByPrimaryKey(SetIndexModal record);
     
    List<SetIndexModal> selectByUserid(String userid);
    List<SetIndexModal> selectIndexModel(String userid);
    SetIndexModal selectModalById(String id);
    int deleteByUserId(String userid);
}