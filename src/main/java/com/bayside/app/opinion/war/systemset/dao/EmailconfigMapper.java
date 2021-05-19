package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.systemset.model.Emailconfig;

public interface EmailconfigMapper {
    int deleteByPrimaryKey(Emailconfig record);
    int insert(Emailconfig record);
    int insertSelective(Emailconfig record);
    Emailconfig selectByPrimaryKey(String id);
    int updateByPrimaryKeySelective(Emailconfig record);
    int updateByPrimaryKey(Emailconfig record);
    List<Emailconfig> selectAllEmailconfig(Emailconfig record);
    Emailconfig selectEmailByEmail(Emailconfig record);
    List<Emailconfig> selectAllConfig(Emailconfig record);
    List<Emailconfig> selectEmailByUser(Emailconfig record);
    List<Emailconfig> selectEmailByOpenid(Emailconfig record);
}