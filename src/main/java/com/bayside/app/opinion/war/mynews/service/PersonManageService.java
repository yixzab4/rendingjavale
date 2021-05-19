package com.bayside.app.opinion.war.mynews.service;

import java.util.List;

import com.bayside.app.opinion.war.mynews.bo.PersonManageBo;
import com.bayside.app.opinion.war.mynews.model.OwnNews;
import com.bayside.app.opinion.war.mynews.model.PersonHistory;
import com.bayside.app.opinion.war.mynews.model.PersonManage;

public interface PersonManageService {
	//保存人物信息
   int savePersonManage(PersonManage personManage);
   //查询人物信息
   List<PersonManage> getAllPersonManage(String userid);
   PersonManage selectById(String id);
   //修改信息
   PersonManage updatePersonManage(String id);
   int updatePerson(PersonManage personManage);
   int updateByPrimaryKey(PersonManage personManage);
   // 查询各方面的信息
   List<OwnNews> selectByPersonId(String personid);
   //查询 信息量
   PersonHistory selectHistory(String personid);
   List<PersonHistory> selectAllHistory(String personid);
   //查询最新10条信息
   List<OwnNews> getNewInfo(OwnNews record);
   //查询当月每天信息量
   List<PersonHistory> selectMdInfo(String personid);
  //查询上个月每天信息量
   List<PersonHistory> selectLastMdInfo(String personid);
   List<PersonManage> selectpageAll(PersonManageBo record);
   Boolean deletePersonManage(String id);
   List<PersonManage> selectpageParent(PersonManageBo record);
   
  
   
   
}
