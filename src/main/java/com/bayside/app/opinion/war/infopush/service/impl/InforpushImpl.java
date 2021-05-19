package com.bayside.app.opinion.war.infopush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.infopush.dao.InforpushMapper;
import com.bayside.app.opinion.war.infopush.model.Inforpush;
import com.bayside.app.opinion.war.infopush.service.InforpushService;


@Service("inforpushImpl")
public class InforpushImpl implements InforpushService {
    @Autowired
	private InforpushMapper inforpushMapper;
	@Override
	public int insert(Inforpush record) {
		return inforpushMapper.insert(record);
	}

	@Override
	public int setUpdateUserid(Inforpush userid) {
		return inforpushMapper.setUpdateUserid(userid);
	}

	@Override
	public List<Inforpush> selectPush(Inforpush record) {
		return inforpushMapper.selectPush(record);
	}

	@Override
	public List<Inforpush> selectUserid(Inforpush userid) {
		return inforpushMapper.selectUserid(userid);
	}

	@Override
	public List<Inforpush> seCidByuid(String userid) {
		return inforpushMapper.seCidByuid(userid);
	}

	@Override
	public int insertSelective(Inforpush record) {
		return inforpushMapper.insertSelective(record);
	}
    

}
