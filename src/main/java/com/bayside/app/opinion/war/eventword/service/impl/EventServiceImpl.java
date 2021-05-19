package com.bayside.app.opinion.war.eventword.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.eventword.dao.NegativeWordMapper;
import com.bayside.app.opinion.war.eventword.dao.TradeMapper;
import com.bayside.app.opinion.war.eventword.dao.WeiDuMapper;
import com.bayside.app.opinion.war.eventword.model.NegativeWord;
import com.bayside.app.opinion.war.eventword.model.Trade;
import com.bayside.app.opinion.war.eventword.model.WeiDu;
import com.bayside.app.opinion.war.eventword.service.EventService;

@Service("eventServiceImpl")
public class EventServiceImpl implements EventService {
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private WeiDuMapper weiDuMapper;
    @Autowired
    private NegativeWordMapper negativeWordMapper;

	@Override
	public List<Trade> selectAllTrade() {
		// TODO Auto-generated method stub
		return tradeMapper.selectAllTrade();
	}

	@Override
	public List<WeiDu> selectByTradeId(String tradeid) {
		// TODO Auto-generated method stub
		return weiDuMapper.selectByTradeId(tradeid);
	}

	@Override
	public List<NegativeWord> selectByWeiDuId(String weiduid) {
		// TODO Auto-generated method stub
		return negativeWordMapper.selectByWeiDuId(weiduid);
	}

	@Override
	public Trade selectByTradeName(String name) {
		// TODO Auto-generated method stub
		return tradeMapper.selectByTradeName(name);
	}

	@Override
	public List<WeiDu> selectByTradeByName(String name) {
		// TODO Auto-generated method stub
		return weiDuMapper.selectByTradeByName(name);
	}

}
