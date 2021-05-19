package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.opinionMonitor.dao.HotWordsMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.HotWords;
import com.bayside.app.opinion.war.opinionMonitor.service.HotWordsService;

@Service("hotWordsServiceImpl")
@Transactional
public class HotWordsServiceImpl implements HotWordsService {

	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Override
	public List<HotWords> getAll() {
		
		return hotWordsMapper.getAll();
	}

}
