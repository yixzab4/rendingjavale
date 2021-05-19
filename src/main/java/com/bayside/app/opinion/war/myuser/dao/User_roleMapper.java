package com.bayside.app.opinion.war.myuser.dao;

import com.bayside.app.opinion.war.myuser.model.User_roleKey;

public interface User_roleMapper {
    int deleteByPrimaryKey(User_roleKey key);

    int insert(User_roleKey record);

    int insertSelective(User_roleKey record);
}