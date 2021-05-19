package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.model.ManagerUser;

public interface ManagerUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(ManagerUser record);

    int insertSelective(ManagerUser record);

    ManagerUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ManagerUser record);

    int updateByPrimaryKey(ManagerUser record);
    List<ManagerUser> selectAllManager(ManagerUser record);
    List<ManagerUser> selectManager();
}