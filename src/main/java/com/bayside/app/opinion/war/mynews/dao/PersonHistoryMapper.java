package com.bayside.app.opinion.war.mynews.dao;

import java.util.List;

import com.bayside.app.opinion.war.mynews.model.PersonHistory;

public interface PersonHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonHistory record);

    int insertSelective(PersonHistory record);

    PersonHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonHistory record);

    int updateByPrimaryKey(PersonHistory record);
    //查询当月每天的数据量
    List<PersonHistory> selectMdInfo(String personid);
    //查询上个月每天的数据量
    List<PersonHistory> selectLastMdInfo(String personid);
} 