package com.bayside.app.opinion.war.index.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.index.bo.Hotnewsbo;
import com.bayside.app.opinion.war.index.model.Hotnews;

public interface HotnewsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Hotnews record);

    int insertSelective(Hotnews record);

    Hotnews selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Hotnews record);

    int updateByPrimaryKey(Hotnews record);
    
    List<Hotnews> selectNewsByType(Hotnewsbo type);
}