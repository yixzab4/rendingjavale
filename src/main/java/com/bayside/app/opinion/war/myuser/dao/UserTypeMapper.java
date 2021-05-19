package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.model.UserType;

public interface UserTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserType record);

    int insertSelective(UserType record);

    UserType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserType record);

    int updateByPrimaryKey(UserType record);
    
    List<UserType> selectUserType();
    
    UserType selectByTypeName(String typename);
}