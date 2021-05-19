package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.systemset.model.Wordset;

public interface WordsetMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Wordset record);

    int insertSelective(Wordset record);

    Wordset selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WordSet record);

    int updateByPrimaryKey(Wordset record);
    
    List<Wordset> selectAllWordset(String userid);

	int bathupdatePower(WordSet wordset);
	int updatePowerOrder(WordSet wordset);
	int batchInsert(WordSet ws);
	Wordset selectKeyWordByUserId(@Param("userid")String userid,@Param("name")String name);
 
}