package com.bayside.app.opinion.war.eventword.dao;

import java.util.List;

import com.bayside.app.opinion.war.eventword.model.Trade;

public interface TradeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);
    
    List<Trade> selectAllTrade();
   
    Trade selectByTradeName(String name);
}