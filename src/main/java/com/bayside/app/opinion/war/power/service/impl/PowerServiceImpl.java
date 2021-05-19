package com.bayside.app.opinion.war.power.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.dao.ResourcesMapper;
import com.bayside.app.opinion.war.myuser.dao.StanderPowerMapper;
import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.dao.UserTypeMapper;
import com.bayside.app.opinion.war.myuser.dao.WordSetMapper;
import com.bayside.app.opinion.war.myuser.dao.userLogMapper;
import com.bayside.app.opinion.war.myuser.model.Resources;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.power.controller.PowerController;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.subject.dao.SubjectClassifyMapper;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.AddressUtils;
import com.bayside.util.IpUtil;
import com.bayside.util.SendCode;

import redis.clients.jedis.ShardedJedis;

@Service("powerServiceImpl")
@Transactional
@PropertySource("classpath:server.properties")
public class PowerServiceImpl implements PowerUserService {
	@Resource
	private Environment environment;

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
	private static Logger logger = Logger.getLogger(PowerServiceImpl.class);

	@Override
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

	/*
	 * @Override public int countUser(String userName, String userPassword) { //
	 * TODO Auto-generated method stub User user = new User();
	 * user.setName(userName); user.setPassword(userPassword); return
	 * userMapper.countUser(user); }
	 * 
	 * 
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
	public int insertSelective(User newUser, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 将登录用户保存到 日志表

		UserLogBo userlog = new UserLogBo();
		userlog.setId(UuidUtil.getUUID());
		userlog.setUserid(newUser.getId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		userlog.setOperatortime(df.format(new Date()));
		String ip="";
		try {
			// String ip = request.getRemoteAddr();
			ip = IpUtil.getIpAddr(request);
			ip=AddressUtils.getIpAddress(request);
			// mac 地址  从前端传过来 把mac地址
		//	String mac = AddressUtils.getMacAddress();
			//String mac  = AddressUtils.getMacAddress(ip);
			// 浏览器
			String brower = AddressUtils.getRequestBrowserInfo(request);
			// 用户地址
			AddressUtils addressUtils = new AddressUtils();
			// 外网ip
			String address = "";
			try {

				address = addressUtils.getAddresses("ip=" + ip, "utf-8");
				System.out.println(address);

			} catch (Exception e) {
				// e.printStackTrace();
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			String s = request.getHeader("user-agent");
			System.out.println(s);
			userlog.setAddress(address);
			userlog.setBrowser(brower);
			userlog.setIp(ip);
			//userlog.setMac(mac);
			userlog.setSystem(s);
			userlog.setType(1);
			userlog.setUsername(newUser.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.info(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		//修改用户
		User user = new User();
		user.setId(newUser.getId());
		user.setLastip(ip);
		user.setLastlogintime(new Date());
		userMapper.updateip(user);
		return userLogMapper.insertSelective(userlog);
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
	public int insert(UserType ut, UserTypeBo record) {
		// 插入权限
		List<String> list = new ArrayList<String>();
		list.add(AppConstant.standPower.AGENTNAME);
		list.add(AppConstant.standPower.CLOUDNAME);
		list.add(AppConstant.standPower.JIANCENAME);
		list.add(AppConstant.standPower.PERSONNAME);
		list.add(AppConstant.standPower.SONNAME);
		list.add(AppConstant.standPower.SUBJECTNAME);
		list.add(AppConstant.standPower.YUJINGNAME);
		// 附加权限项
		list.add(AppConstant.standPower.MICROOPENNAME);
		list.add(AppConstant.standPower.EXPIRDATE);
		list.add(AppConstant.standPower.SUBJECTREPORTNAME);
		list.add(AppConstant.standPower.DAYREPORTNAME);
		list.add(AppConstant.standPower.WEEKREPORTNAME);
		list.add(AppConstant.standPower.MONTHREPORTNAME);
		list.add(AppConstant.standPower.MODALNAME);
		list.add(AppConstant.standPower.WORDNAME);
		for (int i = 0; i < list.size(); i++) {
			StanderPower sp = new StanderPower();
			sp.setId(UuidUtil.getUUID());
			sp.setTypeid(ut.getId());
			sp.setSetword(0);
			sp.setTypename(record.getTypename());
			if (list.get(i).equals(AppConstant.standPower.AGENTNAME)) {
				sp.setName(AppConstant.standPower.AGENTNAME);
				sp.setCansetword(record.getAgentnum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.CLOUDNAME)) {
				sp.setName(AppConstant.standPower.CLOUDNAME);
				sp.setCansetword(record.getCloudnum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.JIANCENAME)) {
				sp.setName(AppConstant.standPower.JIANCENAME);
				sp.setCansetword(record.getJiancenum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.PERSONNAME)) {
				sp.setName(AppConstant.standPower.PERSONNAME);
				sp.setCansetword(record.getPersonnum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SONNAME)) {
				sp.setName(AppConstant.standPower.SONNAME);
				sp.setCansetword(record.getSonnum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SUBJECTNAME)) {
				sp.setName(AppConstant.standPower.SUBJECTNAME);
				sp.setCansetword(record.getSubjectnum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.YUJINGNAME)) {
				sp.setName(AppConstant.standPower.YUJINGNAME);
				sp.setCansetword(record.getYujingnum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.MICROOPENNAME)) {
				sp.setName(AppConstant.standPower.MICROOPENNAME);
				// sp.setCansetword(record.getMicroopen());
				sp.setStatus(record.getMicroopen());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.EXPIRDATE)) {
				sp.setName(AppConstant.standPower.EXPIRDATE);
				sp.setCansetword(record.getExpirdate());
				// sp.setSetword(record.getExpirdate());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				sp.setName(AppConstant.standPower.SUBJECTREPORTNAME);
				sp.setCansetword(record.getSubjectReport());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.DAYREPORTNAME)) {
				sp.setName(AppConstant.standPower.DAYREPORTNAME);
				sp.setStatus(record.getDayReport());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.WEEKREPORTNAME)) {
				sp.setName(AppConstant.standPower.WEEKREPORTNAME);
				sp.setStatus(record.getWeekReport());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.MONTHREPORTNAME)) {
				sp.setName(AppConstant.standPower.MONTHREPORTNAME);
				sp.setStatus(record.getMonthReport());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.MODALNAME)) {
				sp.setName(AppConstant.standPower.MODALNAME);
				sp.setCansetword(record.getModalNum());
				this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.WORDNAME)) {
				sp.setName(AppConstant.standPower.WORDNAME);
				sp.setCansetword(record.getKeywordNum());
				this.insertPower(sp);
			}
		}
		return userTypeMapper.insertSelective(ut);
	}

	@Override
	public List<UserTypeBo> selectUserTypeBo() {
		// TODO Auto-generated method stub
		List<UserType> list = userTypeMapper.selectUserType();
		List<UserTypeBo> utlist = new ArrayList<UserTypeBo>();
		for (int i = 0; i < list.size(); i++) {
			UserTypeBo ub = new UserTypeBo();
			ub.setId(list.get(i).getId());
			ub.setTypename(list.get(i).getTypename());
			List<StanderPower> listsp = new ArrayList<StanderPower>();
			StanderPower sp = new StanderPower();
			sp.setTypeid(list.get(i).getId());
			sp.setTypename(list.get(i).getTypename());
			listsp = this.selectStanderPower(sp);
			for (int j = 0; j < listsp.size(); j++) {
				if (listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)) {
					ub.setAgentnum(listsp.get(j).getCansetword());
				}
				if (listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)) {
					ub.setCloudnum(listsp.get(j).getCansetword());
				}
				if (listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)) {
					ub.setJiancenum(listsp.get(j).getCansetword());
				}
				if (listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)) {
					ub.setPersonnum(listsp.get(j).getCansetword());
				}
				if (listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)) {
					ub.setSonnum(listsp.get(j).getCansetword());
				}
				if (listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
					ub.setSubjectnum(listsp.get(j).getCansetword());
				}
				if (listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)) {
					ub.setYujingnum(listsp.get(j).getCansetword());
				}
			}
			utlist.add(ub);
		}
		return utlist;
	}

	@Override
	public List<UserType> selectUserType() {
		// TODO Auto-generated method stub
		List<UserType> list = userTypeMapper.selectUserType();
		return list;
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
	public UserTypeBo selectUserTypeBo(String typeid, String name) {
		// TODO Auto-generated method stub
		StanderPower sp = new StanderPower();
		sp.setTypeid(typeid);
		List<StanderPower> listsp = standerPowerMapper.selectStanderPower(sp);
		UserTypeBo ub = new UserTypeBo();
		ub.setId(typeid);
		ub.setTypename(name);
		for (int j = 0; j < listsp.size(); j++) {
			if (listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)) {
				ub.setAgentnum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)) {
				ub.setCloudnum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)) {
				ub.setJiancenum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)) {
				ub.setPersonnum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)) {
				ub.setSonnum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				ub.setSubjectnum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				ub.setYujingnum(listsp.get(j).getCansetword());
			}
			// 附加权限
			if (listsp.get(j).getName().equals(AppConstant.standPower.MICROOPENNAME)) {
				ub.setMicroopen(listsp.get(j).getStatus());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.EXPIRDATE)) {
				ub.setExpirdate(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				ub.setSubjectReport(listsp.get(j).getCansetword());
			}
			//
			if (listsp.get(j).getName().equals(AppConstant.standPower.DAYREPORTNAME)) {
				ub.setDayReport(listsp.get(j).getStatus());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.WEEKREPORTNAME)) {
				ub.setWeekReport(listsp.get(j).getStatus());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.MONTHREPORTNAME)) {
				ub.setMonthReport(listsp.get(j).getStatus());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.MODALNAME)) {
				ub.setModalNum(listsp.get(j).getCansetword());
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.WORDNAME)) {
				ub.setKeywordNum(listsp.get(j).getCansetword());
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

	public Boolean updateUS(UserTypeBo record) {
		UserType ut = new UserType();
		BeanUtils.copyProperties(record, ut);
		int num = userTypeMapper.updateByPrimaryKeySelective(ut);
		StanderPower sp = new StanderPower();
		sp.setTypeid(record.getId());
		// sp.setTypename(record.getTypename());
		List<StanderPower> listsp = standerPowerMapper.selectStanderPower(sp);
		for (int j = 0; j < listsp.size(); j++) {
			StanderPower spw = new StanderPower();
			spw.setTypeid(record.getId());
			spw.setTypename(record.getTypename());
			if (listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)) {
				spw.setCansetword(record.getAgentnum());
				spw.setName(AppConstant.standPower.AGENTNAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)) {
				spw.setCansetword(record.getCloudnum());
				spw.setName(AppConstant.standPower.CLOUDNAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)) {
				spw.setCansetword(record.getJiancenum());
				spw.setName(AppConstant.standPower.JIANCENAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)) {
				spw.setCansetword(record.getPersonnum());
				spw.setName(AppConstant.standPower.PERSONNAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)) {
				spw.setCansetword(record.getSonnum());
				spw.setName(AppConstant.standPower.SONNAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				spw.setCansetword(record.getSubjectnum());
				spw.setName(AppConstant.standPower.SUBJECTNAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				spw.setCansetword(record.getYujingnum());
				spw.setName(AppConstant.standPower.YUJINGNAME);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			// 附加权限
			if (listsp.get(j).getName().equals(AppConstant.standPower.MICROOPENNAME)) {
				// spw.setCansetword(record.getYujingnum());
				spw.setName(AppConstant.standPower.MICROOPENNAME);
				spw.setStatus(record.getMicroopen());
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.EXPIRDATE)) {
				spw.setCansetword(record.getExpirdate());
				spw.setName(AppConstant.standPower.EXPIRDATE);
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				spw.setCansetword(record.getSubjectReport());
				spw.setName(AppConstant.standPower.SUBJECTREPORTNAME);

				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.DAYREPORTNAME)) {
				// spw.setCansetword(record.getYujingnum());
				spw.setName(AppConstant.standPower.DAYREPORTNAME);
				spw.setStatus(record.getDayReport());
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.WEEKREPORTNAME)) {
				// spw.setCansetword(record.getYujingnum());
				spw.setName(AppConstant.standPower.WEEKREPORTNAME);
				spw.setStatus(record.getWeekReport());
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.MONTHREPORTNAME)) {
				// spw.setCansetword(record.getYujingnum());
				spw.setName(AppConstant.standPower.MONTHREPORTNAME);
				spw.setStatus(record.getMonthReport());
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.MODALNAME)) {
				spw.setCansetword(record.getModalNum());
				spw.setName(AppConstant.standPower.MODALNAME);
				// spw.setStatus(record.getMonthReport());
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
			if (listsp.get(j).getName().equals(AppConstant.standPower.WORDNAME)) {
				spw.setCansetword(record.getKeywordNum());
				spw.setName(AppConstant.standPower.WORDNAME);
				// spw.setStatus(record.getMonthReport());
				standerPowerMapper.updateByPrimaryKeySelective(spw);
			}
		}
		if (num > 0) {
			return true;
		} else {
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
	public int updatePassword(User user) {
		// TODO Auto-generated method stub
		return userMapper.updatePassword(user);
	}

	@Override
	public int updateUserStatus(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserStatus(user);
	}

	@Override
	public int updateUserShen(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserShen(user);
	}

	@Override
	public int updateUserAttr(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserAttr(user);
	}

	@Override
	public UserBo checkagentById(String id, User user) {
		// TODO Auto-generated method stub
		User userinfo = this.selectagentById(user.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		UserBo ub = new UserBo();
		BeanUtils.copyProperties(userinfo, ub);
		if (null!=userinfo.getExpirydate()) {
			String time = formatter.format(userinfo.getExpirydate());
			ub.setExpirydate(time);
		}
		WordSet wse = new WordSet();
		wse.setUserid(user.getId());
		List<WordSet> list = this.selectPowerByUserId(wse);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(AppConstant.standPower.SONNAME)) {
				ub.setChildtimes(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.AGENTNAME)) {
				ub.setUsertimes(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				ub.setWarntimes(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)) {
				ub.setCloudtimes(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
				ub.setPersontimes(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
				ub.setMonitortimes(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				ub.setSubjecttimes(list.get(i).getCansetword());
			}
			// 附加权限项
			/*
			 * list.add(AppConstant.standPower.MICROOPENNAME);
			 * list.add(AppConstant.standPower.EXPIRDATE);
			 * list.add(AppConstant.standPower.SUBJECTREPORTNAME);
			 * list.add(AppConstant.standPower.DAYREPORTNAME);
			 * list.add(AppConstant.standPower.WEEKREPORTNAME);
			 * list.add(AppConstant.standPower.MONTHREPORTNAME);
			 * list.add(AppConstant.standPower.MODALNAME);
			 */
			if (list.get(i).getName().equals(AppConstant.standPower.MICROOPENNAME)) {
				ub.setMicroopen(list.get(i).getStatus());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.DAYREPORTNAME)) {
				ub.setDayReport(list.get(i).getStatus());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.WEEKREPORTNAME)) {
				ub.setWeekReport(list.get(i).getStatus());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.MONTHREPORTNAME)) {
				ub.setMonthReport(list.get(i).getStatus());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.EXPIRDATE)) {
				ub.setExpirdate(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				ub.setSubjectReport(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.MODALNAME)) {
				ub.setModalNum(list.get(i).getCansetword());
			}
			if (list.get(i).getName().equals(AppConstant.standPower.WORDNAME)) {
				ub.setKeywordNum(list.get(i).getCansetword());
			}
		}
		return ub;
	}

	@Override
	public User updateUserA(UserBo record) {
		// TODO Auto-generated method stub
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if (record.getExpirydate() != null && !"".equals(record.getExpirydate())) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
		}
		if (record.getTag().equals(1)) {
			WordSet ws = new WordSet();
			ws.setUserid(user.getId());
			ws.setName(AppConstant.standPower.SUBJECTNAME);
			if (null == user.getSubjecttimes()) {
				ws.setCansetword(0);
			} else {
				ws.setCansetword(user.getSubjecttimes());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n1 = this.updateWordSet(ws);
			ws.setName(AppConstant.standPower.SONNAME);
			if (null == user.getUsertimes()) {
				ws.setCansetword(0);
			} else {
				ws.setCansetword(user.getUsertimes());
			}
			
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n2 = this.updateWordSet(ws);
			//
			ws.setName(AppConstant.standPower.YUJINGNAME);
			if (null == user.getWarntimes()) {
				ws.setCansetword(0);
			} else {
				ws.setCansetword(user.getWarntimes());
			}
			
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//System.out.println(e.getMessage());
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n3 = this.updateWordSet(ws);
			//
			ws.setUserid(user.getId());
			ws.setName(AppConstant.standPower.CLOUDNAME);
			if (null == user.getCloudtimes()) {
				ws.setCansetword(0);
			} else {
				ws.setCansetword(user.getCloudtimes());
			}
			
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n4 = this.updateWordSet(ws);
			//
			ws.setName(AppConstant.standPower.AGENTNAME);
			if (null == user.getUsertimes()) {
				ws.setCansetword(0);
			} else {
				ws.setCansetword(user.getPersontimes());
			}
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n5 = this.updateWordSet(ws);
			//
			ws.setName(AppConstant.standPower.JIANCENAME);
			if (null == user.getMonitortimes()) {
				ws.setCansetword(0);
			} else {
				ws.setCansetword(user.getMonitortimes());
			}
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n6 = this.updateWordSet(ws);
			//

			ws.setName(AppConstant.standPower.PERSONNAME);
			ws.setCansetword(user.getPersontimes());
			try {
				Date date = sdf.parse(record.getExpirydate());
				ws.setEndtime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
			int n7 = this.updateWordSet(ws);

			List<String> list = new ArrayList<String>();
			list.add(AppConstant.standPower.MICROOPENNAME);
			list.add(AppConstant.standPower.EXPIRDATE);
			list.add(AppConstant.standPower.SUBJECTREPORTNAME);
			list.add(AppConstant.standPower.DAYREPORTNAME);
			list.add(AppConstant.standPower.WEEKREPORTNAME);
			list.add(AppConstant.standPower.MONTHREPORTNAME);
			list.add(AppConstant.standPower.MODALNAME);
			list.add(AppConstant.standPower.WORDNAME);
			for (int i = 0; i < list.size(); i++) {
				WordSet wos = new WordSet();
				wos.setUserid(user.getId());
				if (list.get(i).equals(AppConstant.standPower.MICROOPENNAME)) {
					wos.setName(AppConstant.standPower.MICROOPENNAME);
					wos.setStatus(record.getMicroopen());
				}
				if (list.get(i).equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
					wos.setName(AppConstant.standPower.SUBJECTREPORTNAME);
					wos.setCansetword(record.getSubjectReport());
				}
				if (list.get(i).equals(AppConstant.standPower.WORDNAME)) {
					wos.setName(AppConstant.standPower.WORDNAME);
					wos.setCansetword(record.getKeywordNum());
				}
				if (list.get(i).equals(AppConstant.standPower.DAYREPORTNAME)) {
					wos.setName(AppConstant.standPower.DAYREPORTNAME);
					wos.setStatus(record.getDayReport());
				}
				if (list.get(i).equals(AppConstant.standPower.WEEKREPORTNAME)) {
					wos.setName(AppConstant.standPower.WEEKREPORTNAME);
					wos.setStatus(record.getWeekReport());
				}
				if (list.get(i).equals(AppConstant.standPower.MONTHREPORTNAME)) {
					wos.setName(AppConstant.standPower.MONTHREPORTNAME);
					wos.setStatus(record.getMonthReport());
				}
				if (list.get(i).equals(AppConstant.standPower.MODALNAME)) {
					wos.setName(AppConstant.standPower.MODALNAME);
					wos.setCansetword(record.getModalNum());
				}
				if (list.get(i).equals(AppConstant.standPower.EXPIRDATE)) {
					wos.setName(AppConstant.standPower.EXPIRDATE);
					
					try {
						Date date = sdf.parse(record.getExpirydate());
						wos.setEndtime(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						logger.info(e.getMessage());
						logger.error(e.getMessage(),e);
					}

				}
				try {
					Date date = sdf.parse(record.getExpirydate());
					wos.setEndtime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.info(e.getMessage());
					logger.error(e.getMessage(),e);
				}
				this.updateWordSet(wos);
			}
		}
		return user;
	}

	@Override
	public User userShen(UserBo record) {
		// TODO Auto-generated method stub
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (record.getExpirydate() != null && !"".equals(record.getExpirydate())) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info(e.getMessage());
				logger.error(e.getMessage(),e);
			}
		}
		user.setUsertype("0");
		// 向用户发送初始化密码
		Random random = new Random();

		String s = "";

		for (int i = 0; i < 6; i++) {

			s += random.nextInt(10);

		}
		user.setPassword(s);
	    user.setStartdate(new Date());
		String tel = user.getMobilephone();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("Account", "lyy"));
		formparams.add(new BasicNameValuePair("Pwd", "3355315CD86A2BC5B0A6F2114DC4"));
		formparams.add(new BasicNameValuePair("Content", user.getMobilephone() + "||" + s));
		formparams.add(new BasicNameValuePair("Mobile", user.getMobilephone()));
		formparams.add(new BasicNameValuePair("TemplateId", "30066"));

		formparams.add(new BasicNameValuePair("SignId", "30273"));
		try {
			SendCode.Post(formparams);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		// 添加分类 默认列表
		SubjectClassify sc = new SubjectClassify();
		sc.setUserid(record.getId());
		sc.setClassifyname("默认列表");
		sc.setCreateTime(new Date());
		int order = 1;
		sc.setOrderIndex(order);
		sc.setId(UuidUtil.getUUID());
		sc.setNavigation(false);
		int a = this.insertSubjectClassify(sc);
		StanderPower sp = new StanderPower();
		sp.setTypename("0");
		List<StanderPower> listpower = this.selectStanderPower(sp);
		Date d = new Date();
		for (int i = 0; i < listpower.size(); i++) {
			if (listpower.get(i).getName().equals(AppConstant.standPower.EXPIRDATE)) {

				// 日期处理
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, listpower.get(i).getCansetword());
				 d = c.getTime();
			}
		}
		for (int i = 0; i < listpower.size(); i++) {
			WordSet ws = new WordSet();
			ws.setId(UuidUtil.getUUID());
			ws.setUserid(record.getId());

			if (listpower.get(i).getName().equals(AppConstant.standPower.AGENTNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.SONNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			//
			if (listpower.get(i).getName().equals(AppConstant.standPower.EXPIRDATE)) {

				// 日期处理
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, listpower.get(i).getCansetword());
				System.out.println(c.getTime());
				user.setExpirydate(c.getTime());
				ws.setEndtime(c.getTime());
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.MODALNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.MICROOPENNAME)) {
				/*
				 * ws.setCansetword(0); ws.setSetword(0);
				 */
				ws.setStatus(listpower.get(i).getStatus());
				user.setMicroopen(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.DAYREPORTNAME)) {
				/*
				 * ws.setCansetword(0); ws.setSetword(0);
				 */
				ws.setStatus(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.WEEKREPORTNAME)) {
				/*
				 * ws.setCansetword(0); ws.setSetword(0);
				 */
				ws.setStatus(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.MONTHREPORTNAME)) {
				/*
				 * ws.setCansetword(0); ws.setSetword(0);
				 */
				ws.setStatus(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if (listpower.get(i).getName().equals(AppConstant.standPower.WORDNAME)) {
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			
			user.setExpirydate(d);
			ws.setEndtime(d);
			int num = this.insertWordSet(ws);
		}
		System.out.println(user.getId() + "KKKK" + user.getExpirydate());
		return user;
	}

	@Override
	public List<User> selectid() {
		// TODO Auto-generated method stub
		return userMapper.selectid();
	}


	@Override
	public int specialUser(User record) {
		// TODO Auto-generated method stub
		record.setUsertype("0");
		// 添加分类 默认列表
		SubjectClassify sc = new SubjectClassify();
		sc.setUserid(record.getId());
		sc.setClassifyname("默认列表");
		sc.setCreateTime(new Date());
		int order = 1;
		sc.setOrderIndex(order);
		sc.setId(UuidUtil.getUUID());
		sc.setNavigation(false);
		int a = this.insertSubjectClassify(sc);
		StanderPower sp = new StanderPower();
		sp.setTypename("0");
		List<StanderPower> listpower = this.selectStanderPower(sp);
		for(int i=0;i<listpower.size();i++){
			WordSet  ws = new WordSet();
			ws.setId(UuidUtil.getUUID());
			ws.setUserid(record.getId());
			
			if(listpower.get(i).getName().equals(AppConstant.standPower.AGENTNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.JIANCENAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.PERSONNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.SONNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			//
			if(listpower.get(i).getName().equals(AppConstant.standPower.EXPIRDATE)){
				
			    //日期处理
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, listpower.get(i).getCansetword());
				System.out.println(c.getTime());
				record.setExpirydate(c.getTime());
                ws.setEndtime(c.getTime());
				ws.setName(listpower.get(i).getName());		
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.MODALNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.MICROOPENNAME)){
				/*ws.setCansetword(0);
				ws.setSetword(0);*/
				ws.setStatus(listpower.get(i).getStatus());
				record.setMicroopen(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.DAYREPORTNAME)){
				/*ws.setCansetword(0);
				ws.setSetword(0);*/
				ws.setStatus(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.WEEKREPORTNAME)){
				/*ws.setCansetword(0);
				ws.setSetword(0);*/
				ws.setStatus(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.MONTHREPORTNAME)){
				/*ws.setCansetword(0);
				ws.setSetword(0);*/
				ws.setStatus(listpower.get(i).getStatus());
				ws.setName(listpower.get(i).getName());
			}
			if(listpower.get(i).getName().equals(AppConstant.standPower.WORDNAME)){
				ws.setCansetword(listpower.get(i).getCansetword());
				ws.setSetword(0);
				ws.setName(listpower.get(i).getName());
			}
			int num = this.insertWordSet(ws);
		}
		return userMapper.insertSelective(record);
	}

	@Override
	public List<User> selectByEndTime(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByEndTime(user);
	}

	@Override
	public UserBo selectPowerByUserId(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectPowerByUserId(record);
	}

	@Override
	public User selectUserById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserBo checkloginUserBo(User newUser,HttpSession session) {
		UserBo ub = new UserBo();
	
		 if(null!=newUser){ 
	        	//从redis 里获取缓存用户  查看是否已经超过最大个数 缓存用户 ip 与session ip 做对比  将新登录个数保存到缓存
	        	 String key = newUser.getId();
	        	  ObjectMapper mapper = new ObjectMapper();
	 			 ShardedJedis shardedJedis = null;
	 			if (shardedJedis == null) {
	 				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
	 						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
	 			}
	 			boolean flag = false;
	 		     List<UserBo> listbo = new ArrayList<UserBo>();
	 			try {
					String sttr = shardedJedis.hget(key, "getloginuser");
					if(sttr!=null&&!"".equals(sttr)){
						JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
								UserBo.class);
						listbo =  mapper.readValue(sttr,javaType);
						flag = true;
					
					}
				} catch (Exception e) {
					// TODO: handle exception
					
					logger.info(e.getMessage());
					logger.error(e.getMessage(),e);
				return null;
				}
	 			List<UserBo> newlist = new ArrayList<UserBo>(); 
	        	if(flag){
	        		//从缓存获取数据 与 session 做对比
	        		
	        		for(int i=0;i<listbo.size();i++){
	        		      //判断   redis里是否存在 session
	        			String name = "spring:session:sessions:"+listbo.get(i).getId();
	        			String idkey ="sessionAttr:userid";
	        		
	        			
	        			String sttr = shardedJedis.hget(idkey, name);
	        			
	        			if(sttr!=null&&!"".equals(sttr)){
	        				newlist.add(listbo.get(i));
	        			}
	        		}
	        		if(newlist.size()>=5){
	        			return null;
	        		}else{
	        			//将登录用户放入缓存
	           		
	           		
	           		UserBo ipuser = new UserBo();
	           		ipuser.setId(session.getId());
	           		newlist.add(ipuser);
	           		try {
	           			shardedJedis.hset(key, "getloginuser",mapper.writeValueAsString(newlist));
	           			shardedJedis.expire(key, 30 * 60);
					} catch (Exception e) {
						// TODO: handle exception

						logger.info(e.getMessage());
						logger.error(e.getMessage(),e);
					}finally {
						shardedJedis.disconnect();
						shardedJedis.close();
					} 
	        			
	        		}
	        	}else{
	        	  //将登录用户放入缓存
	        		//将登录用户放入缓存
	    		
	        		UserBo ipuser = new UserBo();
	        		ipuser.setId(session.getId());
	        		newlist.add(ipuser);
	        		try {
	           			shardedJedis.hset(key, "getloginuser",mapper.writeValueAsString(newlist));
	           			shardedJedis.expire(key, 30 * 60);
					} catch (Exception e) {
						// TODO: handle exception
						logger.info(e.getMessage());
						logger.error(e.getMessage(),e);
					}finally {
						shardedJedis.disconnect();
						shardedJedis.close();
					} 
	        		
	        	}
	        	session.setAttribute("stander", "userstander");
	           System.out.println(session.getId());
	         
	 			if(null==newUser.getParentid() || "".equals(newUser.getParentid())){
	 				 ub.setIsparent(1);
	 			}else{
	 				ub.setIsparent(0);
	 			}
	 			BeanUtils.copyProperties(newUser, ub);
	 			UserBo us = userMapper.selectPowerByUserId(ub);
	 			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	 			if (null!=newUser.getExpirydate()) {
	 				String time = sd.format(newUser.getExpirydate());
	 				ub.setExpirydate(time);
	 			}
	 			if (us != null) {
	 				ub.setSubsetword(us.getSubsetword());
	 				ub.setSubjecttimes(us.getSubjecttimes());
	 				ub.setPersonsetword(us.getPersonsetword());
	 				ub.setPersontimes(us.getPersontimes());
	 				ub.setKeywordNum(us.getKeywordNum());
	 				ub.setKeywordsetword(us.getKeywordsetword());
	 				ub.setMonitortimes(us.getMonitortimes());
	 				ub.setMonsetword(us.getMonsetword());
	 				ub.setWarnsetword(us.getWarnsetword());
	 				ub.setWarntimes(us.getWarntimes());
	 				ub.setSreportsetword(us.getSreportsetword());
	 				ub.setSubjectReport(us.getSubjectReport());
	 				ub.setCloudsetword(us.getCloudsetword());
	 				ub.setCloudtimes(us.getCloudtimes());
	 				ub.setDayReport(us.getDayReport());
	 				ub.setWeekReport(us.getWeekReport());
	 				ub.setMonthReport(us.getMonthReport());
	 				ub.setSmodalNum(us.getSmodalNum());
	 				ub.setModalNum(us.getModalNum());
	 				ub.setMicroopen(us.getMicroopen());
	 				ub.setUsertimes(us.getUsertimes());
	 				ub.setSetuserNum(us.getSetuserNum());
	 				ub.setSetTrade(us.getSetTrade());
	 				ub.setInnerAcount(us.getInnerAcount());
	 			     if(null!=us.getIsupdate()){
	 			    	 ub.setIsupdate(us.getIsupdate());
	 			     }else{
	 			    	 ub.setIsupdate(null);
	 			     }
	 			}
	 			
	 			List<String> formatslist = new ArrayList<String>();
	 			formatslist.add(AppConstant.mediaType.NEWS);
	 			formatslist.add(AppConstant.mediaType.LUNTAN);
	 			formatslist.add(AppConstant.mediaType.BLOG);
	 			formatslist.add(AppConstant.mediaType.TIEBA);
	 			formatslist.add(AppConstant.mediaType.WEIBO);
	 			formatslist.add(AppConstant.mediaType.PRINT_MEDIA);
	 			formatslist.add(AppConstant.mediaType.WEIXIN);
	 			formatslist.add(AppConstant.mediaType.VIDEO);
	 			formatslist.add(AppConstant.mediaType.APP);
	 			formatslist.add(AppConstant.mediaType.COMMENT);
	 			formatslist.add(AppConstant.mediaType.OTHER);
	 			formatslist.add(AppConstant.mediaType.ABROAD);
	 			formatslist.add(AppConstant.mediaType.TV);
	 			formatslist.add(AppConstant.mediaType.BT);
	 			if(null!=ub.getSetTrade()){
	 				if(1==ub.getSetTrade()){
	 					formatslist.add(AppConstant.mediaType.TRADE);
	 					session.setAttribute("setTrade", ub.getSetTrade());
	 				}else{
	 					session.setAttribute("setTrade", 0);
	 				}
	 			}else{
	 				session.setAttribute("setTrade", 0);
	 			}
	 			/*if(null!= newUser.getManagerid() && !"".equals(newUser.getManagerid())){
	 				ManagerUser mu = userServiceImpl.selectManagerUserById(newUser.getManagerid());
	 				if(null!=mu){
	 					ub.setStartTime(mu.getLogo());
	 					ub.setEndTime(mu.getImg());
	 					ub.setManagername(mu.getContent());
	 				}else{
	 					ub.setStartTime("");
	 					ub.setEndTime("");
	 					ub.setManagername("");

	 				}
	 			}*/
	 			session.setAttribute("formatslist",formatslist);
	 			session.setAttribute("userid", newUser.getId());
	 			session.setAttribute("user", newUser);
	 			session.setAttribute("name", newUser.getName());
	 			session.setAttribute("orgname", newUser.getOrgname());
	 			session.setAttribute("usertype", newUser.getUsertype());
	 			session.setAttribute("managerid", newUser.getManagerid());
	 			session.setAttribute("orgname", newUser.getOrgname());
	 	        session.setAttribute("ub", ub);
	 	       
	 	     //  session.setAttribute("stander", "userlogin");
	 			
		
		
		
		
		
		
		// TODO Auto-generated method stub
		
	}
		 return null;
	}

	@Override
	public User selectUserByIDForControl(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectUserByIDForControl(user);
	}

	


}
