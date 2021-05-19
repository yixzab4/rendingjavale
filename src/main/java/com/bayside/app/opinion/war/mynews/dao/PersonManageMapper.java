package com.bayside.app.opinion.war.mynews.dao;

import java.util.List;

import com.bayside.app.opinion.war.mynews.bo.PersonManageBo;
import com.bayside.app.opinion.war.mynews.model.PersonManage;

public interface PersonManageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonManage record);

    int insertSelective(PersonManage record);

    PersonManage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonManage record);

    int updateByPrimaryKey(PersonManage record);
    PersonManage updateById(String id);
    int updateByPersonId(PersonManage record);
    List<PersonManage> selectAll(String userid);
    List<PersonManage> selectpageAll(PersonManageBo record);
    List<PersonManage> selectpageParent(PersonManageBo record);
    
}