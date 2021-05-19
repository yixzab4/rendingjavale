package com.bayside.app.opinion.war.myuser.service;

import java.util.List;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.bo.WordSetBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.Resources;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;


public interface UserService {
  int saveUser(User user);
  User selectUser(User user);
  List<User> selectByName(User user);
  List<User> selectByTel(User user);
  List<User> selectByEmail(User user);
  User querySingleUser(String userName);
 /* int countUser(String userName,String userPassword);*/
  
  List<Resources> getUserResources(String userid);
  List<Resources> findAll();
  List<User> selectAllagent(UserBo record);
  int insertagentuser(User record);
  int updateagentuser(User record);
  User selectagentById(String id);
  List<User> selectuserInfo(User record);
  int insertSubjectClassify(SubjectClassify record);
  int insertSelective(UserLogBo record);
  int insertWordSet(WordSet record);
  int updateWordSet(WordSet record);
  List<WordSet> selectAllWordSet(String userid);
  WordSet selectPowerByName(WordSet record);
  User selectByPrimaryKey(String id);
  List<WordSet> selectPowerByUserId(WordSet record);
  int insert(UserType record);
  List<UserType> selectUserType();
  List<StanderPower> selectStanderPower(StanderPower record);
  int insertPower(StanderPower record);
  UserTypeBo selectUserTypeBo(String typeid,String name);
  int updateUserType(UserType record);
  
  int updateStanderPower(StanderPower record);
  
  public Boolean updateUS(UserTypeBo record);
  
  int deleteUserType(String id);
  int deleteStanderPower(StanderPower record);
  UserType selectByTypeName(String typename);
  List<User> selectByUserType(String usertype);
  ManagerUser selectManagerUserById(String id);
  User checklogin(User record);
  int forgetPassword(User record);
  Boolean insertWordSetBo(UserBo user);
  User checkloginzongjian(User record);
  UserBo selectWordSetPowerByUserId(UserBo user);
  Boolean updateSonUserInfo(UserBo user);
  List<WordSet> selectAllWordSet();
  int bathaddWordSet(WordSet record);
  int updateWordSetById(WordSet record);
  
}
