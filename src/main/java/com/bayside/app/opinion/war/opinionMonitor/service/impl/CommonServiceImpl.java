package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.opinionMonitor.dao.CommonMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.Common;
import com.bayside.app.opinion.war.opinionMonitor.service.CommonService;

@Service("commonServiceImpl")
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonMapper commonMapper;
	
	@Override
	public List<Common> getbyType(String type) {

		return commonMapper.getByType(type);
	}

}
