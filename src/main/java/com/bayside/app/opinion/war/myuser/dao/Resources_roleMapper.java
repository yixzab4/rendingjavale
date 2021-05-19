package com.bayside.app.opinion.war.myuser.dao;

import com.bayside.app.opinion.war.myuser.model.Resources_roleKey;

public interface Resources_roleMapper {
    int deleteByPrimaryKey(Resources_roleKey key);

    int insert(Resources_roleKey record);

    int insertSelective(Resources_roleKey record);
}