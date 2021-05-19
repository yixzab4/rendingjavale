package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.bo.WordSetBo;
import com.bayside.app.opinion.war.myuser.model.WordSet;

public interface WordSetMapper {
    int deleteByPrimaryKey(String id);

    int insert(WordSet record);

    int insertSelective(WordSet record);

    List<WordSet> selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(WordSet record);

    int updateByPrimaryKey(WordSet record);
    
    WordSet selectPowerByName(WordSet record);
    
    int deleteByUserid(String userid);
    
    List<WordSet> selectPowerByUserId(WordSet record);
    int insertWordSet(WordSetBo record);
    int updatePowerOrder(WordSet record);
    int batchInsert(WordSet record);
    List<WordSet> selectAllWordSet();
    int bathupdatePower(WordSetBo record);
    List<WordSet> selectWordSetByUserId(String userid);
}