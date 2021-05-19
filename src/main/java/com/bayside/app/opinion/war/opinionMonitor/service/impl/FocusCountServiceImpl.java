package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.opinionMonitor.dao.FocusCountMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.FocusCount;
import com.bayside.app.opinion.war.opinionMonitor.service.FocusCountService;

@Service("focusCountServiceImpl")
@Transactional
public class FocusCountServiceImpl implements FocusCountService {

	@Autowired
	private	FocusCountMapper focusCountMapper;
	
	@Override
	public List<FocusCount> getFocusCount(String time) {
		return focusCountMapper.getByTime(time);
	}

	@Override
	public FocusCount getPieDate(String time,String type) {
		return focusCountMapper.getPieByTime(time,type);
	}

}
