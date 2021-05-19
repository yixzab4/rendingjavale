package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.myuser.model.Resources;

public interface ResourcesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Resources record);

    int insertSelective(Resources record);

    Resources selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Resources record);

    int updateByPrimaryKey(Resources record);
    //!-- 根据用户Id获取该用户的权限--
    List<Resources> getUserResources(@Param("userid")String userid);
    List<Resources> findAll();
	/*//<!-- 根据角色Id获取该角色的权限-->
	List<Resources> getRoleResources(@Param("roleId")String roleId);
	//<!-- 根据用户名获取该用户的权限-->
    List<Resources> getResourcesByUserName(@Param("username")String username);*/
}