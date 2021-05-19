package com.bayside.app.opinion.war.eventword.dao;

import java.util.List;

import com.bayside.app.opinion.war.eventword.model.NegativeWord;

public interface NegativeWordMapper {
    int deleteByPrimaryKey(String id);

    int insert(NegativeWord record);

    int insertSelective(NegativeWord record);

    NegativeWord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NegativeWord record);

    int updateByPrimaryKey(NegativeWord record);
    List<NegativeWord> selectByWeiDuId(String weiduid);
}