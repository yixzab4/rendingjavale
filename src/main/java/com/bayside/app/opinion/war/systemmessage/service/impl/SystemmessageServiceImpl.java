package com.bayside.app.opinion.war.systemmessage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.systemmessage.bo.SystemMessageBo;
import com.bayside.app.opinion.war.systemmessage.dao.SystemmessageMapper;
import com.bayside.app.opinion.war.systemmessage.dao.YuqingDealMapper;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
import com.bayside.app.opinion.war.systemmessage.model.YuqingDeal;
import com.bayside.app.opinion.war.systemmessage.service.SystemmessageService;
@Service
public class SystemmessageServiceImpl implements SystemmessageService {
	@Autowired
    private SystemmessageMapper systemmessageMapper;
	@Autowired
	private YuqingDealMapper yuqingDealMapper;
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<Systemmessage> selectByPrimaryKey(Systemmessage record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.selectByPrimaryKey(record);
	}
	@Override
	public int insertSystemMessage(Systemmessage record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.insertSelective(record);
	}
	@Override
	public int updateSystemMessage(SystemMessageBo record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.updateSystemMessage(record);
	}
	@Override
	public Systemmessage selectMessageNum(Systemmessage record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.selectMessageNum(record);
	}
	@Override
	public int updateSystemMessageById(Systemmessage record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateYuqingDeal(YuqingDeal record) {
		// TODO Auto-generated method stub
		return yuqingDealMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int insertYuqingDeal(YuqingDeal record) {
		// TODO Auto-generated method stub
		return yuqingDealMapper.insertSelective(record);
	}
	@Override
	public YuqingDeal selectDealByMid(YuqingDeal record) {
		// TODO Auto-generated method stub
		return yuqingDealMapper.selectDealByMid(record);
	}
	@Override
	public List<User> selectUserList(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectUserList(id);
	}
	@Override
	public int insertbatchDeal(List<YuqingDeal> list) {
		// TODO Auto-generated method stub
		return yuqingDealMapper.insertbatchDeal(list);
	}
	@Override
	public int batchinsertInfo(List<Systemmessage> list) {
		// TODO Auto-generated method stub
		return systemmessageMapper.batchinsertInfo(list);
	}
	@Override
	public int updateYuqingMessage(Systemmessage record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.updateYuqingMessage(record);
	}
	@Override
	public Systemmessage selectMessageTotal(Systemmessage record) {
		// TODO Auto-generated method stub
		return systemmessageMapper.selectMessageTotal(record);
	}
	

}
