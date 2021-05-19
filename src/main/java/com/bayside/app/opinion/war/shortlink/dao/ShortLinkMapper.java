package com.bayside.app.opinion.war.shortlink.dao;

import com.bayside.app.opinion.war.shortlink.model.ShortLink;

public interface ShortLinkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ShortLink record);

    int insertSelective(ShortLink record);

    ShortLink selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShortLink record);

    int updateByPrimaryKey(ShortLink record);
}