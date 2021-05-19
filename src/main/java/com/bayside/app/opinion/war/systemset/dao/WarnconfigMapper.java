package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemset.bo.WarnconfigBo;
import com.bayside.app.opinion.war.systemset.model.Warnconfig;

public interface WarnconfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(Warnconfig record);

    int insertSelective(Warnconfig record);

    Warnconfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Warnconfig record);

    int updateByPrimaryKey(Warnconfig record);
    List<Warnconfig> selectAllWarnconfig(WarnconfigBo record);
    List<Warnconfig> selectWarnByUserId(String userid);
}