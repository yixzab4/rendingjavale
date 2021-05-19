package com.bayside.app.opinion.war.myuser.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.bo.WordSetBo;
import com.bayside.app.opinion.war.myuser.dao.ManagerUserMapper;
import com.bayside.app.opinion.war.myuser.dao.ResourcesMapper;
import com.bayside.app.opinion.war.myuser.dao.StanderPowerMapper;
import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.dao.UserTypeMapper;
import com.bayside.app.opinion.war.myuser.dao.WordSetMapper;
import com.bayside.app.opinion.war.myuser.dao.userLogMapper;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.Resources;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.subject.dao.SubjectClassifyMapper;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.opinion.war.systemset.bo.WordsetBo;
import com.bayside.app.opinion.war.systemset.model.Wordset;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.UuidUtil;
@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapper userMapper;
	@Autowired
	private userLogMapper userLogMapper;
	@Autowired
	private ResourcesMapper resourcesMapper;
	@Autowired
	private SubjectClassifyMapper subjectClassifyMapper;
	@Autowired
	private WordSetMapper wordSetMapper;
	@Autowired
	private UserTypeMapper userTypeMapper;
	@Autowired
	private StanderPowerMapper standerPowerMapper;
	@Autowired
	private ManagerUserMapper managerUserMapper;
    //@Override
	public int saveUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(user);
	}

	@Override
	public User selectUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectAll(user);
	}

	@Override
	public List<User> selectByName(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByName(user);
	}

	@Override
	public List<User> selectByTel(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByTel(user);
	}
	@Override
	public User querySingleUser(String userName) {
		// TODO Auto-generated method stub
		return userMapper.querySingleUser(userName);
	}
	@Override
	public List<User> selectByEmail(User user) {
		// TODO Auto-generated method stub
		
		return userMapper.selectByEmail(user);
	}

	/*@Override
	public int countUser(String userName, String userPassword) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setName(userName);
		user.setPassword(userPassword);
		return userMapper.countUser(user);
	}

	
*/
	@Override
	public List<Resources> getUserResources(String userid) {
		// TODO Auto-generated method stub
		return resourcesMapper.getUserResources(userid);
	}

	@Override
	public List<Resources> findAll() {
		// TODO Auto-generated method stub
		return resourcesMapper.findAll();
	}
	@Override
	public List<User> selectAllagent(UserBo record) {
		// TODO Auto-generated method stub
		User us = new User();
		BeanUtils.copyProperties(record, us);
		return userMapper.selectAllagent(us);
	}

	@Override
	public int insertagentuser(User record) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(record);
	}

	@Override
	public int updateagentuser(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public User selectagentById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> selectuserInfo(User record) {
		// TODO Auto-generated method stub
		return userMapper.selectuserInfo(record);
	}

	@Override
	public int insertSubjectClassify(SubjectClassify record) {
		// TODO Auto-generated method stub
		return subjectClassifyMapper.insertSelective(record);
	}

	@Override
	public int insertSelective(UserLogBo record) {
		// TODO Auto-generated method stub
		return userLogMapper.insertSelective(record);
	}

	@Override
	public int insertWordSet(WordSet record) {
		// TODO Auto-generated method stub
		
		
		
		return wordSetMapper.insertSelective(record);
	}

	@Override
	public int updateWordSet(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<WordSet> selectAllWordSet(String userid) {
		// TODO Auto-generated method stub
		return wordSetMapper.selectByPrimaryKey(userid);
	}

	@Override
	public WordSet selectPowerByName(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.selectPowerByName(record);
	}

	@Override
	public User selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<WordSet> selectPowerByUserId(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.selectPowerByUserId(record);
	}

	@Override
	public int insert(UserType record) {
		return userTypeMapper.insertSelective(record);
	}

	@Override
	public List<UserType> selectUserType() {
		// TODO Auto-generated method stub
		return userTypeMapper.selectUserType();
	}

	@Override
	public List<StanderPower> selectStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.selectStanderPower(record);
	}

	@Override
	public int insertPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.insertSelective(record);
	}

	@Override
	public UserTypeBo selectUserTypeBo(String typeid,String name) {
		// TODO Auto-generated method stub
		StanderPower sp = new StanderPower();
		sp.setTypeid(typeid);
		List<StanderPower> listsp = standerPowerMapper.selectStanderPower(sp);
		UserTypeBo ub = new UserTypeBo();
		ub.setId(typeid);
		ub.setTypename(name);
		 for(int j=0;j<listsp.size();j++){
			  if(listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)){
				  ub.setAgentnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)){
				  ub.setCloudnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)){
				  ub.setJiancenum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)){
				  ub.setPersonnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)){
				  ub.setSonnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)){
				  ub.setSubjectnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)){
				  ub.setYujingnum(listsp.get(j).getCansetword());
			  }
		  }
		 
	    
		return ub;
	}
	@Override
	public int updateUserType(UserType record) {
		// TODO Auto-generated method stub
		return userTypeMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.updateByPrimaryKeySelective(record);
	}
	public Boolean updateUS(UserTypeBo record){	
		UserType ut = new UserType();
		BeanUtils.copyProperties(record, ut);
		int num = userTypeMapper.updateByPrimaryKeySelective(ut);
		StanderPower sp = new StanderPower();
		sp.setTypeid(record.getId());
		//sp.setTypename(record.getTypename());
		List<StanderPower> listsp = standerPowerMapper.selectStanderPower(sp);
		 for(int j=0;j<listsp.size();j++){
			   StanderPower spw = new StanderPower();
				spw.setTypeid(record.getId());
				spw.setTypename(record.getTypename());
			  if(listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)){
				   spw.setCansetword(record.getAgentnum());
				   spw.setName(AppConstant.standPower.AGENTNAME);
				  standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)){
				    spw.setCansetword(record.getCloudnum());
				    spw.setName(AppConstant.standPower.CLOUDNAME);
				 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)){
				    spw.setCansetword(record.getJiancenum());
				    spw.setName(AppConstant.standPower.JIANCENAME);
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)){
				    spw.setCansetword(record.getPersonnum());
				    spw.setName(AppConstant.standPower.PERSONNAME);
					standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)){
				    spw.setCansetword(record.getSonnum());
				    spw.setName(AppConstant.standPower.SONNAME); 
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)){
				    spw.setCansetword(record.getSubjectnum());
				    spw.setName(AppConstant.standPower.SUBJECTNAME);
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)){
				    spw.setCansetword(record.getYujingnum());
				    spw.setName(AppConstant.standPower.YUJINGNAME);
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
		  }
		if(num >0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public int deleteUserType(String id) {
		// TODO Auto-generated method stub
		return userTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.deleteByPrimaryKey(record);
	}

	@Override
	public UserType selectByTypeName(String typename) {
		// TODO Auto-generated method stub
		return userTypeMapper.selectByTypeName(typename);
	}

	@Override
	public List<User> selectByUserType(String usertype) {
		// TODO Auto-generated method stub
		return userMapper.selectByUserType(usertype);
	}

	@Override
	public ManagerUser selectManagerUserById(String id) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public User checklogin(User record) {
		// TODO Auto-generated method stub
		return userMapper.checklogin(record);
	}

	@Override
	public int forgetPassword(User record) {
		// TODO Auto-generated method stub
		return userMapper.forgetPassword(record);
	}

	@Override
	public Boolean insertWordSetBo(UserBo userBo) {
		   Boolean flag=false;
				// 赋予权限
				List<String> listpower = new ArrayList<String>();
				/*
				 * listpower.add(0, "子账号个数"); listpower.add(1, "代理商个数");
				 */
				listpower.add(AppConstant.standPower.YUJINGNAME);
				listpower.add(AppConstant.standPower.CLOUDNAME);
				listpower.add(AppConstant.standPower.PERSONNAME);
				listpower.add(AppConstant.standPower.JIANCENAME);
				listpower.add(AppConstant.standPower.SUBJECTNAME);
				listpower.add(AppConstant.standPower.MICROOPENNAME);
				
				 //附加权限
				listpower.add(AppConstant.standPower.SUBJECTREPORTNAME);
				listpower.add(AppConstant.standPower.EXPIRDATE);
				listpower.add(AppConstant.standPower.DAYREPORTNAME);
				listpower.add(AppConstant.standPower.WEEKREPORTNAME);
				listpower.add(AppConstant.standPower.MONTHREPORTNAME);
				listpower.add(AppConstant.standPower.MODALNAME);
				listpower.add(AppConstant.standPower.WORDNAME);
				listpower.add(AppConstant.standPower.SONNAME);
				listpower.add(AppConstant.standPower.SETREPORT);
				listpower.add(AppConstant.standPower.SETTRADE);
				listpower.add(AppConstant.standPower.ISUPDATE);
				listpower.add(AppConstant.standPower.ATTENTIONNUM);
				WordSet wst = new WordSet();
			    List<WordSetBo> list = new ArrayList<WordSetBo>();
				
				for (int i = 0; i < listpower.size(); i++) {
					WordSetBo ws = new WordSetBo();
					if (listpower.get(i).equals(AppConstant.standPower.YUJINGNAME)) {
						ws.setCansetword(userBo.getWarntimes());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.CLOUDNAME)) {
						ws.setCansetword(userBo.getCloudtimes());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.PERSONNAME)) {
						ws.setCansetword(userBo.getPersontimes());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.JIANCENAME)) {
						ws.setCansetword(userBo.getMonitortimes());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.SUBJECTNAME)) {
						ws.setCansetword(userBo.getSubjecttimes());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
						ws.setCansetword(userBo.getSubjectReport());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.EXPIRDATE)) {
						ws.setEndtime(userBo.getExpirydate());
					}
					if (listpower.get(i).equals(AppConstant.standPower.DAYREPORTNAME)) {
						ws.setStatus(userBo.getDayReport());
					}
					if (listpower.get(i).equals(AppConstant.standPower.WEEKREPORTNAME)) {
						ws.setStatus(userBo.getWeekReport());
					}
					if (listpower.get(i).equals(AppConstant.standPower.MONTHREPORTNAME)) {
						ws.setStatus(userBo.getMonthReport());
					}
					if (listpower.get(i).equals(AppConstant.standPower.MODALNAME)) {
						ws.setCansetword(userBo.getModalNum());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.WORDNAME)) {
						ws.setCansetword(userBo.getKeywordNum());
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.SONNAME)) {
						ws.setCansetword(0);
						ws.setSetword(0);
					}
					if (listpower.get(i).equals(AppConstant.standPower.SETREPORT)) {
						ws.setStatus(userBo.getSetReport());
					}
					if(listpower.get(i).equals(AppConstant.standPower.MICROOPENNAME)){
						ws.setStatus(userBo.getMicroopen());
					}
					if(listpower.get(i).equals(AppConstant.standPower.ATTENTIONNUM)){
						ws.setCansetword(userBo.getAuthority());
						ws.setSetword(0);
					}
					ws.setId(UuidUtil.getUUID());
				//	ws.setStatus(1);
					ws.setUserid(userBo.getId());
					ws.setName(listpower.get(i));
					list.add(ws);
					//int nu = wordSetMapper.insertWordSet(ws);
					/*if(nu>0){
						flag = true;
					}*/
				}
				wst.setList(list);
		        int nu = wordSetMapper.batchInsert(wst);
		        if(nu > 0){
		        	flag = true;
		        }
		        return flag;
	}

	@Override
	public User checkloginzongjian(User record) {
		// TODO Auto-generated method stub
		return userMapper.checkloginzongjian(record);
	}

	@Override
	public UserBo selectWordSetPowerByUserId(UserBo user) {
		// TODO Auto-generated method stub
		return userMapper.selectWordSetPowerByUserId(user);
	}

	@Override
	public Boolean updateSonUserInfo(UserBo record) {
		// TODO Auto-generated method stub
		 int f = userMapper.updateSonUserInfo(record);
		//修改权限
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		WordSet ws = new WordSet();
		List<WordSet> listws = wordSetMapper.selectByPrimaryKey(record.getId());
		Boolean reporttag = false;
		Boolean tradetag = false;
		Boolean isupdatetag = false;
		Boolean focustag = false;
		  for(int i=0;i<listws.size();i++){
				if(AppConstant.standPower.SUBJECTNAME.equals(listws.get(i).getName())){
					try {
						Date date = sdf.parse(record.getExpirydate());
						listws.get(i).setEndtime(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
						
					}
					if (null ==record.getSubjecttimes()) {
						listws.get(i).setCansetword(0);
					}else {
						listws.get(i).setCansetword(record.getSubjecttimes());
					}
				}else if(AppConstant.standPower.SONNAME.equals(listws.get(i).getName())){
					if (null == record.getChildtimes()) {
						listws.get(i).setCansetword(0);
					} else {
						listws.get(i).setCansetword(record.getChildtimes());
					}
				}else if(AppConstant.standPower.YUJINGNAME.equals(listws.get(i).getName())){
					if (record.getWarntimes() == null) {
						listws.get(i).setCansetword(0);
					} else {
						listws.get(i).setCansetword(record.getWarntimes());
					}
				}else if(AppConstant.standPower.CLOUDNAME.equals(listws.get(i).getName())){
					if (record.getCloudtimes() == null) {
						listws.get(i).setCansetword(0);
					} else {
						listws.get(i).setCansetword(record.getWarntimes());
					}
				}else if(AppConstant.standPower.JIANCENAME.equals(listws.get(i).getName())){
					if (null ==record.getMonitortimes()) {
						listws.get(i).setCansetword(0);
					} else {
						listws.get(i).setCansetword(record.getMonitortimes());
					}
				}else if(AppConstant.standPower.PERSONNAME.equals(listws.get(i).getName())){
					if (null ==record.getPersontimes()) {
						listws.get(i).setCansetword(0);
					} else {
						listws.get(i).setCansetword(record.getWarntimes());
					}
				}else if(AppConstant.standPower.MICROOPENNAME.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.MICROOPENNAME);
					listws.get(i).setStatus(record.getMicroopen());
				}
				else if(AppConstant.standPower.SUBJECTREPORTNAME.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.SUBJECTREPORTNAME);
					listws.get(i).setCansetword(record.getSubjectReport());
				}
				else if(AppConstant.standPower.WORDNAME.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.WORDNAME);
					listws.get(i).setCansetword(record.getKeywordNum());
				}
				else if(AppConstant.standPower.DAYREPORTNAME.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.DAYREPORTNAME);
					listws.get(i).setStatus(record.getDayReport());
				}
				else if(AppConstant.standPower.WEEKREPORTNAME.equals(listws.get(i).getName())){
				//	listws.get(i).setName(AppConstant.standPower.WEEKREPORTNAME);
					listws.get(i).setStatus(record.getWeekReport());
				}
				else if(AppConstant.standPower.MONTHREPORTNAME.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.MONTHREPORTNAME);
					listws.get(i).setStatus(record.getMonthReport());
				}
				else if(AppConstant.standPower.MODALNAME.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.MODALNAME);
					//listws.get(i).setStatus(record.getModalNum());
					listws.get(i).setCansetword(record.getModalNum());
					
				}
				else if(AppConstant.standPower.SETREPORT.equals(listws.get(i).getName())){
					reporttag = true;
					listws.get(i).setName(AppConstant.standPower.SETREPORT);
					listws.get(i).setStatus(record.getSetReport());
				}
				else if(AppConstant.standPower.SETTRADE.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.SETTRADE);
					tradetag = true;
					listws.get(i).setStatus(record.getSetTrade());
				}
				else if(AppConstant.standPower.EXPIRDATE.equals(listws.get(i).getName())){
					//listws.get(i).setName(AppConstant.standPower.EXPIRDATE);
					try {
						Date date = sdf.parse(record.getExpirydate());
						listws.get(i).setEndtime(date);
					} catch (ParseException e) {
						
					}
				}else if(AppConstant.standPower.ISUPDATE.equals(listws.get(i).getName())){
					isupdatetag = true;
					listws.get(i).setIsupdate(record.getIsupdate());
				}else if(AppConstant.standPower.ATTENTIONNUM.equals(listws.get(i).getName())){
					focustag = true;
					listws.get(i).setCansetword(record.getAuthority());
				}
			}
		  ws.setListwordset(listws);
		  int num = wordSetMapper.updatePowerOrder(ws);
		  if(!tradetag){
			  WordSet wss = new WordSet();
				 wss.setName(AppConstant.standPower.SETTRADE);
				 try {
						Date date = sdf.parse(record.getExpirydate());
						wss.setEndtime(date);
					} catch (ParseException e) {
						//Log.error(e.getMessage(),e);
					}
				 wss.setStatus(record.getSetTrade());
				 wss.setId(UuidUtil.getUUID());
				 wss.setUserid(record.getId());
				wordSetMapper.insertSelective(wss);
		  }
		  if(!reporttag){
			  WordSet wss = new WordSet();
				 wss.setName(AppConstant.standPower.SETTRADE);
				 try {
						Date date = sdf.parse(record.getExpirydate());
						wss.setEndtime(date);
					} catch (ParseException e) {
						//Log.error(e.getMessage(),e);
					}
				 wss.setStatus(record.getSetReport());
				 wss.setId(UuidUtil.getUUID());
				 wss.setUserid(record.getId());
				wordSetMapper.insertSelective(wss);
		  }
		  if(!isupdatetag){
				 WordSet wss = new WordSet();
				 wss.setName(AppConstant.standPower.ISUPDATE);
				 try {
						Date date = sdf.parse(record.getExpirydate());
						wss.setEndtime(date);
					} catch (ParseException e) {
						//Log.error(e.getMessage(),e);
					}
				 wss.setIsupdate(record.getIsupdate());
				 wss.setId(UuidUtil.getUUID());
				 wss.setUserid(record.getId());
				wordSetMapper.insertSelective(wss);
			 }
		  if(!focustag){
			  WordSet wss = new WordSet();
				 wss.setName(AppConstant.standPower.ATTENTIONNUM);
				 try {
						Date date = sdf.parse(record.getExpirydate());
						wss.setEndtime(date);
					} catch (ParseException e) {
						//Log.error(e.getMessage(),e);
					}
				 wss.setCansetword(record.getAuthority());
				 wss.setId(UuidUtil.getUUID());
				 wss.setUserid(record.getId());
				wordSetMapper.insertSelective(wss);
		  }
		  Boolean flag = false;
		 if(f > 0 && num > 0){
			 flag = true;
		 }
		return flag;
	}

	@Override
	public List<WordSet> selectAllWordSet() {
		// TODO Auto-generated method stub
		return wordSetMapper.selectAllWordSet();
	}

	@Override
	public int bathaddWordSet(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.batchInsert(record);
	}

	@Override
	public int updateWordSetById(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.updateByPrimaryKeySelective(record);
	}
}
