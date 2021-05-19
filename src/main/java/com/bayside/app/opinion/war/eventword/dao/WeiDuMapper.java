package com.bayside.app.opinion.war.eventword.dao;

import java.util.List;

import com.bayside.app.opinion.war.eventword.model.WeiDu;

public interface WeiDuMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeiDu record);

    int insertSelective(WeiDu record);

    WeiDu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WeiDu record);

    int updateByPrimaryKey(WeiDu record);
    
    List<WeiDu> selectByTradeId(String name);
    List<WeiDu> selectByTradeByName(String name);
    
}