package com.bayside.app.opinion.war.systemmessage.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemmessage.model.YuqingDeal;

public interface YuqingDealMapper {
    int deleteByPrimaryKey(String id);

    int insert(YuqingDeal record);

    int insertSelective(YuqingDeal record);

     YuqingDeal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YuqingDeal record);

    int updateByPrimaryKey(YuqingDeal record);
    YuqingDeal selectDealByMid(YuqingDeal record);
    int insertbatchDeal(List<YuqingDeal> list);
}