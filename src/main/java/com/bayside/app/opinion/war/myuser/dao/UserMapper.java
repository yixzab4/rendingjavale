package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.model.User;

public interface UserMapper {
	 int deleteByPrimaryKey(String id);
	 int deleteByClassifyKey(String id);
	 
	    int insert(User record);

	    int insertSelective(User record);

	    User selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(User record);

	    int updateByPrimaryKey(User record);
	    
	    User selectAll(User user);
	    
	    List<User> selectByName(User user);
	    
	    List<User> selectByTel(User user);
	    
	    List<User> selectByEmail(User user);
	    
	    List<User> selectByParentId(String parentid);
	    int countUser(User user);
		
	    User querySingleUser(@Param("name")String name);
	    
	    List<User> selectAllagent(User record);
	    /**
	     * 
	     * <p>方法名称：selectParentUser</p>
	     * <p>方法描述：查询所有的父级账号</p>
	     * @return
	     * @author sql
	     * @since  2016年10月2日
	     * <p> history 2016年10月2日 sql  创建   <p>
	     */
	    List<User> selectParentUser();

	    List<User> selectuserInfo(User record);
	    List<User> selectByUserType(String usertype);
	    int updatePassword(User user);
	    int updateUserStatus(User user);
	    int updateUserShen(User user);
	    int updateUserAttr(User user);
	    int updateUserInfo(User user);
	    int registerUserInfo(User user);
	    List<User> selectid();
	    List<User> selectByEndTime(User user);
	    UserBo selectPowerByUserId(UserBo user);
	    User checklogin(User record);
	    User checkloginzongjian(User record);
	    int forgetPassword(User record);
	    List<User> selectUserList(String id);
	    int insertSelectiveBo(UserBo user);
	    UserBo selectWordSetPowerByUserId(UserBo user);
	    int updateSonUserInfo(UserBo user);
	    UserBo selectRelyPower(UserBo user);
	    int updateip(User record);
	    
	    User selectUserByIDForControl(User user);


}