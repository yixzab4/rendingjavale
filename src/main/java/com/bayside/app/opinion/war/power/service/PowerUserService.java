package com.bayside.app.opinion.war.power.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.model.Resources;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;

public interface PowerUserService {
	  User selectUserById(String id);
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
	  UserBo checkagentById(String id,User user);
	  List<User> selectuserInfo(User record);
	  int insertSubjectClassify(SubjectClassify record);
	  int insertSelective(User user,HttpServletRequest request);
	  int insertWordSet(WordSet record);
	  int updateWordSet(WordSet record);
	  List<WordSet> selectAllWordSet(String userid);
	  WordSet selectPowerByName(WordSet record);
	  User selectByPrimaryKey(String id);
	  List<WordSet> selectPowerByUserId(WordSet record);
	  int insert(UserType ut,UserTypeBo record);
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
	  int updatePassword(User user);
	  int updateUserStatus(User user);
	  int updateUserShen(User user);
	  int updateUserAttr(User user);
	  List<UserTypeBo> selectUserTypeBo();
	  User updateUserA(UserBo record);
	  
	  User userShen(UserBo record);
	  List<User> selectid();
	  int specialUser(User user);
	  List<User> selectByEndTime(User user);
	  UserBo selectPowerByUserId(UserBo record);
	 UserBo checkloginUserBo(User user,HttpSession session);
	 
	  User selectUserByIDForControl(User user);
	
}
