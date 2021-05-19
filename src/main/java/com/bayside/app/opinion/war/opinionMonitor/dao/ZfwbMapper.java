package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.Zfwb;

public interface ZfwbMapper {
    int deleteByPrimaryKey(String id);

    int insert(Zfwb record);

    int insertSelective(Zfwb record);

    Zfwb selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Zfwb record);

    int updateByPrimaryKeyWithBLOBs(Zfwb record);

    int updateByPrimaryKey(Zfwb record);
    List<Zfwb> selectByArticleid(String oId);
}