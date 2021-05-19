package com.bayside.app.opinion.fources.dao;

import java.util.List;

import com.bayside.app.opinion.fources.bo.Sweibobo;
import com.bayside.app.opinion.fources.model.Sweibo;

public interface SweiboMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sweibo record);

    int insertSelective(Sweibo record);

    Sweibo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sweibo record);

    int updateByPrimaryKey(Sweibo record);
    List<Sweibobo> selectSearchWeibo(Sweibobo record);
    Sweibobo selectSearchWeiboTotal(Sweibobo record);
}