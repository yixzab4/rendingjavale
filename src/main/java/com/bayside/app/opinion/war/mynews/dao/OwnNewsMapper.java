package com.bayside.app.opinion.war.mynews.dao;

import java.util.List;

import com.bayside.app.opinion.war.mynews.model.OwnNews;

public interface OwnNewsMapper {
    int deleteByPrimaryKey(String id);

    int insert(OwnNews record);

    int insertSelective(OwnNews record);

    OwnNews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OwnNews record);

    int updateByPrimaryKeyWithBLOBs(OwnNews record);

    int updateByPrimaryKey(OwnNews record);
    //查询最新10条信息
    List<OwnNews> getNewInfo(OwnNews record);
}